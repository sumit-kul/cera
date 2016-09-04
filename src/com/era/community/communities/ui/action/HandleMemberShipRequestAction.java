package com.era.community.communities.ui.action;

import java.io.Writer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.RunAsAsyncThread;
import com.era.community.blog.dao.CommunityBlog;
import com.era.community.blog.dao.CommunityBlogFeature;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.communities.dao.CommunityJoiningRequest;
import com.era.community.communities.dao.CommunityJoiningRequestFinder;
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
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.ContactDto;
import com.era.community.wiki.dao.Wiki;
import com.era.community.wiki.dao.WikiFeature;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/community/handleMemberShipRequest.ajx"
 */
public class HandleMemberShipRequestAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected CommunityJoiningRequestFinder requestFinder;
	protected CommunityFinder communityFinder;
	protected UserFinder userFinder;

	protected SubscriptionFinder subscriptionFinder;
	protected CommunityBlogFeature communityBlogFeature;  
	protected EventCalendarFeature eventCalendarFeature;
	protected ForumFeature forumFeature;
	protected WikiFeature wikiFeature;
	protected DocumentLibraryFeature documentLibraryFeature;

	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;
	protected RunAsAsyncThread taskExecutor;

	protected ModelAndView handle(Object data) throws Exception    
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command)data;
		User currentUser = context.getCurrentUser();
		if (currentUser == null) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do");
		}

		CommunityJoiningRequest req = null;
		String returnString = "";
		try {
			req = requestFinder.getCommunityJoiningRequestForId(cmd.getSelectedId());
		} catch (Exception e) {
			return null;
		}

		if (cmd.getActionType() == 1) {
			Community comm = null;
			User requestor = null;
			try {
				comm = communityFinder.getCommunityForId(req.getCommunityId());
			} catch (Exception e) {
				return null;
			}

			try {
				requestor = userFinder.getUserEntity(req.getUserId());
			} catch (Exception e) {
				return null;
			}
			Integer userId = Integer.valueOf(requestor.getId());
			Integer commId = Integer.valueOf(comm.getId());

			comm.getMemberList().addMember(requestor, contextManager.getContext().getCurrentUser());
			req.setStatus(CommunityJoiningRequest.STATUS_ACCEPTED);
			req.setApproveRejectDate(new java.util.Date());
			req.update();

			CommunitySubscription commSub;
			CommunityBlogSubscription blogConSub;
			EventCalendarSubscription eventSub;
			ForumSubscription forumSub;
			DocLibSubscription libSub;
			WikiSubscription wikiSub;

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			String dt = sdf.format(now);
			Timestamp ts = Timestamp.valueOf(dt);

			try {
				commSub= subscriptionFinder.getCommunitySubscriptionForUser(comm.getId(), requestor.getId());
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

			mailUserAccept(comm, requestor);
			returnString = "Accepted";

		} else if (cmd.getActionType() == 2) {
			Community comm = null;
			User requestor = null;
			try {
				comm = communityFinder.getCommunityForId(req.getCommunityId());
			} catch (Exception e) {
				return null;
			}

			try {
				requestor = userFinder.getUserEntity(req.getUserId());
			} catch (Exception e) {
				return null;
			}
			req.setStatus(CommunityJoiningRequest.STATUS_REJECTED);
			req.setApproveRejectDate(new java.util.Date());
			req.update();
			mailUserReject(comm, requestor);
			returnString = "Rejected";

		} else if (cmd.getActionType() == 3) {
			req.delete();
			returnString = "Deleted";
		}

		HttpServletResponse resp = contextManager.getContext().getResponse();
		JSONObject json = new JSONObject();
		json.put("returnString", returnString);
		String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
	}

	private void mailUserAccept(final Community comm, final User requestor) throws Exception
	{         
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					if (!requestor.isSuperAdministrator()) {
						CommunityEraContext context = contextManager.getContext(); 
						MimeMessage message = mailSender.createMimeMessage();
						MimeMessageHelper helper = new MimeMessageHelper(message, true);
						helper.setTo(requestor.getEmailAddress());
						helper.setFrom(new InternetAddress("support@jhapak.com ") );
						helper.setSubject(comm.getName()+" - New membership request");
						helper.setSentDate(new Date());
						Map model = new HashMap();  
						String sLink = context.getContextUrl()+"/cid/"+comm.getId()+"/home.do";
						model.put("communityName", comm.getName());
						model.put("communityLink", sLink);
						model.put("userName", requestor.getFullName());

						String text = VelocityEngineUtils.mergeTemplateIntoString(
								velocityEngine, "main/resources/velocity/MembershipRequestApproval.vm", "UTF-8", model);
						helper.setText(text, true);
						mailSender.send(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void mailUserReject(final Community comm, final User requestor) throws Exception
	{            
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					if (!requestor.isSuperAdministrator()) {
						CommunityEraContext context = contextManager.getContext(); 
						MimeMessage message = mailSender.createMimeMessage();
						MimeMessageHelper helper = new MimeMessageHelper(message, true);
						helper.setTo(requestor.getEmailAddress());
						helper.setFrom(new InternetAddress("support@jhapak.com ") );
						helper.setSubject(comm.getName()+" - New membership request");
						helper.setSentDate(new Date());
						Map model = new HashMap();  
						model.put("communityName", comm.getName());
						model.put("userName", requestor.getFullName());
						model.put("currentUserName", context.getCurrentUser().getFullName());

						String text = VelocityEngineUtils.mergeTemplateIntoString(
								velocityEngine, "main/resources/velocity/MembershipRequestRejection.vm", "UTF-8", model);
						helper.setText(text, true);
						mailSender.send(message);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static class Command extends ContactDto implements CommandBean
	{
		private int selectedId;        
		private String messageText;
		private int actionType;
		public int getSelectedId() {
			return selectedId;
		}
		public void setSelectedId(int selectedId) {
			this.selectedId = selectedId;
		}
		public String getMessageText() {
			return messageText;
		}
		public void setMessageText(String messageText) {
			this.messageText = messageText;
		}
		public int getActionType() {
			return actionType;
		}
		public void setActionType(int actionType) {
			this.actionType = actionType;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public void setRequestFinder(CommunityJoiningRequestFinder requestFinder) {
		this.requestFinder = requestFinder;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
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

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}

	public void setTaskExecutor(RunAsAsyncThread taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setCommunityBlogFeature(CommunityBlogFeature communityBlogFeature) {
		this.communityBlogFeature = communityBlogFeature;
	}
}