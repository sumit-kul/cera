package com.era.community.communities.ui.action;

import java.io.Writer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.RunAsAsyncThread;
import com.era.community.blog.dao.BlogAuthor;
import com.era.community.blog.dao.BlogAuthorFinder;
import com.era.community.blog.dao.CommunityBlog;
import com.era.community.blog.dao.CommunityBlogFeature;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.communities.dao.CommunityJoiningRequest;
import com.era.community.communities.dao.CommunityJoiningRequestFinder;
import com.era.community.communities.ui.validator.CommunityJoiningRequestValidator;
import com.era.community.doclib.dao.DocumentLibrary;
import com.era.community.doclib.dao.DocumentLibraryFeature;
import com.era.community.events.dao.EventCalendar;
import com.era.community.events.dao.EventCalendarFeature;
import com.era.community.forum.dao.Forum;
import com.era.community.forum.dao.ForumFeature;
import com.era.community.monitor.dao.CommunityBlogSubscription;
import com.era.community.monitor.dao.CommunitySubscription;
import com.era.community.monitor.dao.DocLibSubscription;
import com.era.community.monitor.dao.EventCalendarSubscription;
import com.era.community.monitor.dao.ForumSubscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.monitor.dao.WikiSubscription;
import com.era.community.pers.dao.DashBoardAlert;
import com.era.community.pers.dao.DashBoardAlertFinder;
import com.era.community.pers.dao.Notification;
import com.era.community.pers.dao.NotificationFinder;
import com.era.community.pers.dao.User;
import com.era.community.wiki.dao.Wiki;
import com.era.community.wiki.dao.WikiFeature;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/community/joinCommunityRequest.ajx"
 */
public class JoinCommunityRequestAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected CommunityFinder communityFinder; 
	protected CommunityJoiningRequestFinder joiningRequestFinder;
	protected SubscriptionFinder subscriptionFinder;
	protected CommunityBlogFeature communityBlogFeature;  
	protected EventCalendarFeature eventCalendarFeature;
	protected ForumFeature forumFeature;
	protected WikiFeature wikiFeature;
	protected DocumentLibraryFeature documentLibraryFeature;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;
	protected BlogAuthorFinder blogAuthorFinder;
	protected RunAsAsyncThread taskExecutor;
	protected NotificationFinder notificationFinder;
	protected DashBoardAlertFinder dashBoardAlertFinder;

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data;
		CommunityEraContext context = contextManager.getContext();
		Community comm = communityFinder.getCommunityForId(cmd.getId());
		CommunityJoiningRequest request = null;
		cmd.setCommunity(comm);

		if (contextManager.getContext().getCurrentUser() == null) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(contextManager.getContext().getRequestUrl()));
		}

		try { 
			CommunityJoiningRequest joiningRequest = joiningRequestFinder.getRequestForUserAndCommunity(contextManager.getContext().getCurrentUser().getId(), comm.getId());
			if (joiningRequest != null && joiningRequest.getStatus() == 0) {
				throw new Exception("You have already requested to join this community.");
			}
		} 
		catch (ElementNotFoundException ex) {             
		}
		cmd.setApprovalRequired(!comm.userCanJoinWithoutApproval(contextManager.getContext().getCurrentUser()));

		User user = contextManager.getContext().getCurrentUser();
		String returnString;

		if (cmd.isApprovalRequired()) {
			request = joiningRequestFinder.newCommunityJoiningRequest();
			request.setCommunityId(comm.getId());
			request.setUserId(user.getId());
			request.setOptionalComment(cmd.getOptionalComment());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			String dt = sdf.format(now);
			Timestamp ts = Timestamp.valueOf(dt);
			request.setRequestDate(ts);

			try {
				request.update();
			}
			catch (Exception e) { 
				e.printStackTrace();
			}  
			mailToApprovers(comm,user,cmd);
			mailToJoineeForRequest(comm,user,cmd);
			returnString = "<span>Membership requested</span>";
		} else {
			returnString = "";
			comm.getMemberList().addMember(user);
			CommunitySubscription commSub;
			CommunityBlogSubscription blogConSub;
			EventCalendarSubscription eventSub;
			ForumSubscription forumSub;
			DocLibSubscription libSub;
			WikiSubscription wikiSub;
			BlogAuthor blogAuthor;

			Integer userId = Integer.valueOf(user.getId());
			Integer commId = Integer.valueOf(comm.getId());

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			String dt = sdf.format(now);
			Timestamp ts = Timestamp.valueOf(dt);

			try {
				commSub= subscriptionFinder.getCommunitySubscriptionForUser(comm.getId(), user.getId());
			} catch (ElementNotFoundException e) {
				commSub = subscriptionFinder.newCommunitySubscription();
				commSub.setUserId(userId);
				commSub.setCommunityId(commId);
				commSub.setFrequency(1);
				commSub.setWebSubscription(1);
				commSub.setMailSubscription(1);
				commSub.setPageSubscription(1);
				commSub.setModified(ts);
				commSub.update();            
			}

			CommunityBlog cons = (CommunityBlog)communityBlogFeature.getFeatureForCommunity(comm);
			try {
				blogConSub= subscriptionFinder.getCommunityBlogSubscriptionForUser(cons.getId(), userId);
			} catch (ElementNotFoundException e) {
				blogConSub = subscriptionFinder.newCommunityBlogSubscription();
				blogConSub.setCommunityBlogId(Integer.valueOf(cons.getId()));
				blogConSub.setUserId(userId);
				blogConSub.setCommunityId(commId);
				blogConSub.setFrequency(1);
				blogConSub.setWebSubscription(1);
				blogConSub.setMailSubscription(1);
				blogConSub.setPageSubscription(1);
				blogConSub.setModified(ts);
				blogConSub.update();           
			}

			try {
				blogAuthor = blogAuthorFinder.getBlogAuthorForBlogAndUser(cons.getId(), userId);
			} catch (ElementNotFoundException e) {
				blogAuthor = blogAuthorFinder.newBlogAuthor();
				blogAuthor.setActive(0);
				blogAuthor.setBlogId(cons.getId());
				blogAuthor.setUserId(userId);
				blogAuthor.setRole(2);
				blogAuthor.update();
			}

			EventCalendar ev = (EventCalendar)eventCalendarFeature.getFeatureForCommunity(comm);
			try {
				eventSub= subscriptionFinder.getEventCalendarSubscriptionForUser(ev.getId(), userId);
			} catch (ElementNotFoundException e) {
				eventSub = subscriptionFinder.newEventCalendarSubscription();
				eventSub.setEventCalendarId(ev.getId());
				eventSub.setUserId(userId);
				eventSub.setCommunityId(commId);
				eventSub.setFrequency(1);
				eventSub.setWebSubscription(1);
				eventSub.setMailSubscription(1);
				eventSub.setPageSubscription(1);
				eventSub.setModified(ts);
				eventSub.update();           
			}

			Forum forum = (Forum)forumFeature.getFeatureForCommunity(comm);
			try {
				forumSub = subscriptionFinder.getForumSubscriptionForUser(forum.getId(), userId);
			} catch (ElementNotFoundException e) {
				forumSub = subscriptionFinder.newForumSubscription();
				forumSub.setForumId(forum.getId());
				forumSub.setUserId(userId);
				forumSub.setCommunityId(commId);
				forumSub.setFrequency(1);
				forumSub.setWebSubscription(1);
				forumSub.setMailSubscription(1);
				forumSub.setPageSubscription(1);
				forumSub.setModified(ts);
				forumSub.update();         
			}

			DocumentLibrary lib = (DocumentLibrary)documentLibraryFeature.getFeatureForCommunity(comm);
			try {
				libSub = subscriptionFinder.getDocLibSubscriptionForUser(lib.getId(), userId);
			} catch (ElementNotFoundException e) {
				libSub = subscriptionFinder.newDocLibSubscription();
				libSub.setDocLibId(lib.getId());
				libSub.setUserId(userId);
				libSub.setCommunityId(commId);
				libSub.setFrequency(1);
				libSub.setWebSubscription(1);
				libSub.setMailSubscription(1);
				libSub.setPageSubscription(1);
				libSub.setModified(ts);
				libSub.update();       
			}

			Wiki wiki = (Wiki)wikiFeature.getFeatureForCommunity(comm);
			try {
				wikiSub = subscriptionFinder.getWikiSubscriptionForUser(wiki.getId(), userId);
			} catch (ElementNotFoundException e) {
				wikiSub = subscriptionFinder.newWikiSubscription();
				wikiSub.setWikiId(wiki.getId());
				wikiSub.setUserId(userId);
				wikiSub.setCommunityId(commId);
				wikiSub.setFrequency(1);
				wikiSub.setWebSubscription(1);
				wikiSub.setMailSubscription(1);
				wikiSub.setPageSubscription(1);
				wikiSub.setModified(ts);
				wikiSub.update();       
			}
			//mailToJoinee(comm, user, cmd);
		}
		HttpServletResponse resp = context.getResponse();
		JSONObject json = new JSONObject();
		json.put("returnString", returnString);
		String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
	}

	private void mailToApprovers(final Community comm, final User user, final Command cmd) throws Exception
	{
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					CommunityEraContext context = contextManager.getContext(); 
					List<User> admins = comm.getMemberList().getAdminMembers();
					for (User admin : admins) {

						Notification notification = notificationFinder.newNotification();
						notification.setCommunityId(comm.getId());
						notification.setUserId(admin.getId());
						notification.setItemId(comm.getId());
						notification.setItemType("MembershipPending");
						notification.update();

						DashBoardAlert dashBoardAlert = dashBoardAlertFinder.getDashBoardAlertForUserId(admin.getId());
						dashBoardAlert.setNotificationCount(dashBoardAlert.getNotificationCount() + 1);
						dashBoardAlert.update();

						MimeMessage message = mailSender.createMimeMessage();
						message.setFrom(new InternetAddress("support@jhapak.com"));
						message.setRecipients(Message.RecipientType.TO, admin.getEmailAddress());
						message.setSubject(user.getFullName()+ " requested to join " + comm.getName());

						Map model = new HashMap();  
						String sLink = context.getContextUrl()+"/cid/"+comm.getId()+"/connections/showJoiningRequests.do";
						String sQuestion = cmd.getOptionalComment();
						if(sQuestion == null) sQuestion = "";
						model.put("communityName", comm.getName());
						model.put("userImgURL", context.getContextUrl()+"/pers/userPhoto.img?id="+user.getId());
						model.put("communityLink", sLink);
						model.put("userName", user.getFullName());
						model.put("question", sQuestion);
						model.put("croot", context.getContextUrl());
						model.put("cEmail", user.getEmailAddress());
						model.put("cUnsubscribe", context.getContextUrl()+"/pers/unsubscribeMe.do?mid="+user.getId()+"&type=jcrequest");
						model.put("mailPreferences", context.getContextUrl()+"/pers/manageSubscriptions.do");
						model.put("cPrivacy", context.getContextUrl()+"/privacy.do");
						model.put("cHelp", context.getContextUrl()+"/help.do");
						model.put("cFeedback", context.getContextUrl()+"/feedback.do");
						SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy HH:mm:ss");
						Date now = new Date();
						String dt = sdf.format(now);
						model.put("cDate", dt);

						String text = VelocityEngineUtils.mergeTemplateIntoString(
								velocityEngine, "main/resources/velocity/MembershipRequest.vm", "UTF-8", model);
						message.setContent(text, "text/html");
						message.setSentDate(new Date());

						// Prepare a multipart HTML
						Multipart multipart = new MimeMultipart();
						// Prepare the HTML
						BodyPart htmlPart = new MimeBodyPart();
						htmlPart.setContent(text, "text/html");
						multipart.addBodyPart(htmlPart);            
						message.setContent(multipart);
						if (!admin.isSuperAdministrator()) {
							mailSender.send(message);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void mailToJoineeForRequest(final Community comm, final User user, final Command cmd) throws Exception
	{
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					CommunityEraContext context = contextManager.getContext(); 
					if (!user.isSuperAdministrator()) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date now = new Date();
						String dt = sdf.format(now);

						MimeMessage message = mailSender.createMimeMessage();
						message.setFrom(new InternetAddress("support@jhapak.com"));
						message.setRecipients(Message.RecipientType.TO, user.getEmailAddress());
						message.setSubject("Your requested to join " + comm.getName()+" is being processed.");

						Map model = new HashMap();  
						String sLink = context.getContextUrl()+"/cid/"+comm.getId()+"/connections/showJoiningRequests.do";
						String sQuestion = cmd.getOptionalComment();
						if(sQuestion == null) sQuestion = "";
						model.put("communityName", comm.getName());
						model.put("userImgURL", context.getContextUrl()+"/pers/userPhoto.img?id="+user.getId());
						model.put("communityLink", sLink);
						model.put("userName", user.getFullName());
						model.put("question", sQuestion);
						model.put("croot", context.getContextUrl());
						model.put("cDate", dt);

						model.put("cEmail", user.getEmailAddress());
						model.put("cUnsubscribe", context.getContextUrl()+"/pers/unsubscribeMe.do?mid="+user.getId()+"&key="+user.getFirstKey()+"&type=daily");
						model.put("mailPreferences", context.getContextUrl()+"/pers/manageSubscriptions.do");
						model.put("cPrivacy", context.getContextUrl()+"/privacy.do");
						model.put("cHelp", context.getContextUrl()+"/help.do");
						model.put("cFeedback", context.getContextUrl()+"/feedback.do");

						String text = VelocityEngineUtils.mergeTemplateIntoString(
								velocityEngine, "main/resources/velocity/MembershipRequestApprovalPending.vm", "UTF-8", model);
						message.setContent(text, "text/html");
						message.setSentDate(new Date());

						// Prepare a multipart HTML
						Multipart multipart = new MimeMultipart();
						// Prepare the HTML
						BodyPart htmlPart = new MimeBodyPart();
						htmlPart.setContent(text, "text/html");
						multipart.addBodyPart(htmlPart);            
						message.setContent(multipart);

						mailSender.send(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int id;
		private boolean approvalRequired;
		private boolean alreadyRequested;
		private boolean requestExists; 
		private Community community;
		private String redirect;
		private String referer;
		private String optionalComment;

		public Community getCommunity()
		{
			return community;
		}
		public void setCommunity(Community community)
		{
			this.community = community;
		}
		public int getId()
		{
			return id;
		}
		public void setId(int id)
		{
			this.id = id;
		}
		public final String getRedirect()
		{
			return redirect;
		}
		public final void setRedirect(String redirect)
		{
			this.redirect = redirect;
		}
		public final boolean isApprovalRequired()
		{
			return approvalRequired;
		}
		public final void setApprovalRequired(boolean requiresApproval)
		{
			this.approvalRequired = requiresApproval;
		}
		public final boolean isAlreadyRequested()
		{
			return alreadyRequested;
		}
		public final void setAlreadyRequested(boolean alreadyRequested)
		{
			this.alreadyRequested = alreadyRequested;
		}
		public final String getReferer()
		{
			return referer;
		}
		public final void setReferer(String referer)
		{
			this.referer = referer;
		}
		public final String getOptionalComment()
		{
			return optionalComment;
		}
		public final void setOptionalComment(String optionalComment)
		{
			this.optionalComment = optionalComment;
		}
	}

	public static class Validator extends CommunityJoiningRequestValidator
	{
	}

	public void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setJoiningRequestFinder(CommunityJoiningRequestFinder joiningRequestFinder)
	{
		this.joiningRequestFinder = joiningRequestFinder;
	}

	public CommunityEraContextManager getContextHolder()
	{
		return contextManager;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setEventCalendarFeature(EventCalendarFeature eventCalendarFeature) {
		this.eventCalendarFeature = eventCalendarFeature;
	}

	public void setForumFeature(ForumFeature forumFeature) {
		this.forumFeature = forumFeature;
	}

	public void setWikiFeature(WikiFeature wikiFeature) {
		this.wikiFeature = wikiFeature;
	}

	public void setDocumentLibraryFeature(
			DocumentLibraryFeature documentLibraryFeature) {
		this.documentLibraryFeature = documentLibraryFeature;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void setBlogAuthorFinder(BlogAuthorFinder blogAuthorFinder) {
		this.blogAuthorFinder = blogAuthorFinder;
	}

	public void setTaskExecutor(RunAsAsyncThread taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setNotificationFinder(NotificationFinder notificationFinder) {
		this.notificationFinder = notificationFinder;
	}

	public void setDashBoardAlertFinder(DashBoardAlertFinder dashBoardAlertFinder) {
		this.dashBoardAlertFinder = dashBoardAlertFinder;
	}

	public void setCommunityBlogFeature(CommunityBlogFeature communityBlogFeature) {
		this.communityBlogFeature = communityBlogFeature;
	}
}