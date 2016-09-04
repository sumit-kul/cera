package com.era.community.blog.ui.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.EntityWrapper;
import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandValidator;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.ImageManipulation;
import com.era.community.base.LinkBuilderContext;
import com.era.community.base.RunAsAsyncThread;
import com.era.community.base.Taggable;
import com.era.community.blog.dao.BlogAuthor;
import com.era.community.blog.dao.BlogAuthorFinder;
import com.era.community.blog.dao.BlogEntry;
import com.era.community.blog.dao.BlogEntryFinder;
import com.era.community.blog.dao.CommunityBlog;
import com.era.community.blog.dao.CommunityBlogFinder;
import com.era.community.blog.ui.dto.BlogEntryDto;
import com.era.community.blog.ui.validator.BlogEntryValidator;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityActivity;
import com.era.community.communities.dao.CommunityActivityFinder;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.communities.dao.generated.CommunityEntity;
import com.era.community.monitor.dao.BlogEntrySubscription;
import com.era.community.monitor.dao.CommunityBlogSubscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.DashBoardAlert;
import com.era.community.pers.dao.DashBoardAlertFinder;
import com.era.community.pers.dao.Notification;
import com.era.community.pers.dao.NotificationFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserActivity;
import com.era.community.pers.dao.UserActivityFinder;
import com.era.community.pers.dao.UserFinder;
import com.era.community.tagging.dao.TagFinder;
import com.era.community.tagging.ui.dto.TagDto;

/**
 * @spring.bean name="/blog/addEditBlog.do"
 */
public class AddEditCommunityBlogEntryAction extends AbstractFormAction
{
	protected BlogEntryFinder blogEntryFinder;
	protected CommunityBlogFinder communityBlogFinder;
	protected SubscriptionFinder subscriptionFinder;
	protected UserFinder userFinder;
	protected CommunityFinder communityFinder;
	protected CommunityEraContextManager contextManager;
	protected TagFinder tagFinder;
	protected BlogAuthorFinder blogAuthorFinder;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;
	protected RunAsAsyncThread taskExecutor;
	protected NotificationFinder notificationFinder;
	protected DashBoardAlertFinder dashBoardAlertFinder;
	protected ContactFinder contactFinder;
	protected CommunityActivityFinder communityActivityFinder;
	protected UserActivityFinder userActivityFinder;

	private int[] oldBlogConsIds;

	public int[] getOldBlogConsIds()
	{
		return oldBlogConsIds;
	}

	public void setOldBlogConsIds(int[] oldBlogConsIds)
	{
		this.oldBlogConsIds = oldBlogConsIds;
	}

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	protected String getView()
	{
		return "blog/addEditCommBlogEntry";
	}

	protected void onDisplay(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		String beID = context.getRequest().getParameter("beID");
		int cmdId = 0;
		if (cmd.getId() == 0 && beID != null && !"".equals(beID)&& !"undefined".equals(beID)) {
			cmdId = Integer.parseInt(beID);
			cmd.setId(cmdId);
		}
		if (context.getCurrentCommunity() != null) {
			Community currentCommunity = context.getCurrentCommunity();
			cmd.setCommunityId(currentCommunity.getId());
			cmd.setCurrentCommunity(currentCommunity);
		}

		/* If there is a blog entry id in the command bean, load the details of the specified entry for editing */
		if (cmd.getId() != 0) {
			BlogEntry entry = blogEntryFinder.getBlogEntryForId(cmd.getId());
			cmd.copyPropertiesFrom(entry);
			cmd.setBlogActionLabel("Edit '"+entry.getTitle()+"'");
			cmd.setSearchType("Blog");
			cmd.setQueryText(entry.getTitle());
		}
	}

	protected Map referenceData(Object command) throws Exception
	{
		Command cmd = (Command) command;
		CommunityEraContext context = contextManager.getContext();
		Community comm = null;
		LinkBuilderContext linkBuilder = context.getLinkBuilder();

		if (cmd.getId() != 0) {
			BlogEntry entry = blogEntryFinder.getBlogEntryForId(cmd.getId());
			if (entry.isWriteAllowed(context.getCurrentUserDetails())) {
				linkBuilder.addToolLink("Delete this entry", "/blog/deleteBlogEntry.do?beID=" + entry.getId(), "Delete this entry from my blog", "cross");
			}
		}

		CommunityBlog cb = null;
		if (context.getCurrentCommunity() != null) {
			comm = context.getCurrentCommunity();
			cmd.setCurrentCommunity(comm);
			cb = communityBlogFinder.getCommunityBlogForCommunity(comm);
			cmd.setCommunityBlog(cb);
			cmd.setAuthorsCount(blogAuthorFinder.getBlogAuthorsCountForBlog(cb.getId()));
		}

		if (cmd.getId() == 0) {
			cmd.setBlogActionLabel("Post a new blog entry");
			if (context.getCurrentCommunity() != null) {
				cmd.setSearchType("Blog");
				cmd.setQueryText(cb.getName());
			}
		}
		return new HashMap();
	}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command) data;
		User user =  context.getCurrentUser();
		Community currentCommunity = context.getCurrentCommunity();
		boolean isNewEntry = false;
		if (cmd.getId()==0) isNewEntry=true;
		BlogEntry entry = null;
		CommunityBlog cb = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);

		if (user == null ) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}

		if (isNewEntry) { // Creating a new blog entry
			entry = blogEntryFinder.newBlogEntry();
			entry.setPosterId(user.getId());
			entry.setDatePosted(ts);
			entry.setBody("");

		} else { // Editing an existing blog entry
			entry = blogEntryFinder.getBlogEntryForId(cmd.getId());
		} 
		cb = communityBlogFinder.getCommunityBlogForCommunity(currentCommunity);
		entry.setCommunityBlogId(cb.getId());
		entry.setTitle(cmd.getTitle());
		entry.setModified(ts);
		entry.update();

		if (!currentCommunity.getCommunityType().equals("Private")) {
			BlogAuthor blogAuthor;
			try {
				blogAuthor = blogAuthorFinder.getBlogAuthorForBlogAndUser(cb.getId(), user.getId());
				blogAuthor.setActive(1);
				blogAuthor.update();
			} catch (ElementNotFoundException e) {
				blogAuthor = blogAuthorFinder.newBlogAuthor();
				blogAuthor.setActive(1);
				blogAuthor.setBlogId(cb.getId());
				blogAuthor.setUserId(user.getId());
				blogAuthor.setRole(1);
				blogAuthor.update();
			}
		} else {
			//For private community user must be a blog author...
			BlogAuthor blogAuthor;
			blogAuthor = blogAuthorFinder.getBlogAuthorForBlogAndUser(cb.getId(), user.getId());
			if (blogAuthor.getActive() == 0) {
				blogAuthor.setActive(1);
				blogAuthor.update();
			}
		}

		currentCommunity.setCommunityUpdated(ts);
		currentCommunity.update();

		entry.setBody( ImageManipulation.manageImages(context, cmd.getBody(), cmd.getTitle(), context.getCurrentUser().getId(), entry.getId(), "BlogEntry") );
		entry.update();
		entry.setTags(cmd.getTags());

		if (isNewEntry) {
			CommunityActivity cActivity = communityActivityFinder.newCommunityActivity();
			cActivity.setCommunityId(currentCommunity.getId());
			cActivity.setBlogEntryId(entry.getId());
			cActivity.setUserId(entry.getPosterId());
			cActivity.update();
			
			UserActivity uActivity = userActivityFinder.newUserActivity();
			uActivity.setCommunityActivityId(cActivity.getId());
			uActivity.setUserId(entry.getPosterId());
			uActivity.update();
			try {
				if (currentCommunity != null) {
					BlogEntrySubscription subs = subscriptionFinder.newBlogEntrySubscription();
					subs.setBlogEntryId(new Integer(entry.getId()));
					subs.setUserId(new Integer(context.getCurrentUser().getId()));
					subs.setCommunityId(new Integer(currentCommunity.getId()));
					subs.setModified(ts);
					subs.update();
					mailConsolidatorSubscribers(entry, entry.getCommunityBlogId(), context.getCurrentUser(), currentCommunity);
				}
			}catch (ElementNotFoundException ex) {
			}
		}

		return new ModelAndView("redirect:/cid/"+currentCommunity.getId()+"/blog/blogEntry.do?id="+entry.getId());
	}

	private void mailConsolidatorSubscribers(final BlogEntry entry,  final int communityBlogId, final User currentUser, final Community currentCommunity)  throws Exception
	{
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
						CommunityEraContext context = contextManager.getContext(); 
						List mailSent = new ArrayList();
						List<User> members = currentCommunity.getMemberList().getMembersByDateJoined();
						List <User> contacts = contactFinder.listAllMyContacts(currentUser.getId(), 0);
						members.addAll(contacts);
						
						List<CommunityBlogSubscription> subs = subscriptionFinder.getSubscriptionsForCommunityBlog(communityBlogId);
						for(CommunityBlogSubscription sub : subs) {
							mailSent.add(sub.getUserId());
							if (sub.getFrequency() == 0) { // Only email the subscribers who want immediate alerts
								//Skip the current user
								int uid = sub.getUserId();
								if (currentUser == null || uid == currentUser.getId())
									continue;
								User subscriber = userFinder.getUserEntity(uid);
								if (!subscriber.isSuperAdministrator()) {
									MimeMessage message = mailSender.createMimeMessage();
									message.setFrom(new InternetAddress("support@jhapak.com"));
									message.setRecipients(Message.RecipientType.TO, subscriber.getEmailAddress());
									message.setSubject(currentUser.getFullName()+ " posted in " + currentCommunity.getName());

									Map model = new HashMap();  
									String sLink = context.getContextUrl()+ "/cid/" + currentCommunity.getId()+ "/blog/blogEntry.do?id=" + entry.getId();
									model.put("ownerName", currentUser.getFullName());
									model.put("userImgURL", context.getContextUrl()+"/pers/userPhoto.img?id="+currentUser.getId());
									model.put("blogEntryTitle", entry.getTitle());
									model.put("blogEntryLink", sLink);
									model.put("communityName", currentCommunity.getName());
									model.put("croot", context.getContextUrl());
									model.put("cEmail", subscriber.getEmailAddress());
									model.put("cUnsubscribe", context.getContextUrl()+"/pers/unsubscribeMe.do?mid="+subscriber.getId()+"&type=bEntry");
									model.put("mailPreferences", context.getContextUrl()+"/pers/manageSubscriptions.do");
									model.put("cPrivacy", context.getContextUrl()+"/privacy.do");
									model.put("cHelp", context.getContextUrl()+"/help.do");
									model.put("cFeedback", context.getContextUrl()+"/feedback.do");
									SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy HH:mm:ss");
									Date now = new Date();
									String dt = sdf.format(now);
									model.put("cDate", dt);

									String text = VelocityEngineUtils.mergeTemplateIntoString(
											velocityEngine, "main/resources/velocity/NewCommunityBlogEntry.vm", "UTF-8", model);
									message.setContent(text, "text/html");
									message.setSentDate(new Date());

									Multipart multipart = new MimeMultipart();
									BodyPart htmlPart = new MimeBodyPart();
									htmlPart.setContent(text, "text/html");
									multipart.addBodyPart(htmlPart);            
									message.setContent(multipart);
									mailSender.send(message);
								}
							}
						}
						
						for (User member : members){
							if (!mailSent.contains(member.getId())) {
								if (currentUser == null || member.getId() == currentUser.getId())
									continue;
								Notification notification = notificationFinder.newNotification();
								notification.setCommunityId(currentCommunity.getId());
								notification.setUserId(member.getId());
								notification.setItemId(entry.getId());
								notification.setItemType("BlogEntry");
								notification.update();

								DashBoardAlert dashBoardAlert;
								try {
									dashBoardAlert = dashBoardAlertFinder.getDashBoardAlertForUserId(member.getId());
									dashBoardAlert.setNotificationCount(dashBoardAlert.getNotificationCount() + 1);
									dashBoardAlert.update();
								} catch (ElementNotFoundException ex) {
									dashBoardAlert = dashBoardAlertFinder.newDashBoardAlert();
									dashBoardAlert.setUserId(member.getId());
									dashBoardAlert.setConnectionReceivedCount(0);
									dashBoardAlert.setConnectionApprovedCount(0);
									dashBoardAlert.setNotificationCount(1);
									dashBoardAlert.setLikeCount(0);
									dashBoardAlert.setMessageCount(0);
									dashBoardAlert.setProfileVisitCount(0);
									dashBoardAlert.update();
								}
								mailSent.add(member.getId());
							}
						}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public class Validator extends BlogEntryValidator
	{
		public String validateTitle(Object value, CommandBean cmd)
		{
			if (value.toString().equals("")) {
				return "You must set a title for this blog entry";
			}
			if (value.toString().length() > 100) {
				return "The maximum length of the title is 100 characters, please shorten your title";
			}
			return null;
		}

		public String validateBody(Object value, CommandBean cmd)
		{
			if (value.toString().equals("")) {
				return "You must enter some body text for this blog entry";
			}
			return null;
		}

	}

	public class Command extends BlogEntryDto implements CommandBean, Taggable
	{
		private String blogActionLabel = "Create a new";
		private Community currentCommunity;
		private String firstName;
		private int authorsCount;
		private int bid;
		private CommunityBlog communityBlog;

		public String getTaggedKeywords(){
			String taggedKeywords = "";
			try {
				List tags = tagFinder.getTagsForParentTypeByPopularity(communityBlog.getId(), 0, 20, "CommunityBlog");
				for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
					TagDto tb = (TagDto) iterator.next();
					String tag = tb.getTagText().trim().toLowerCase();
					taggedKeywords += "<a href='blog/allBlogs.do?filterTag="+tag+"' class='normalTip euInfoSelect' style='display: inline;' title='Click to filter by tag &#39;"+tag+"&#39;'>"+tag+"</a>";
					if (iterator.hasNext())taggedKeywords += " , ";
				}
			} catch (Exception e) {
				return taggedKeywords;
			}
			return taggedKeywords;
		}

		public String getBlogActionLabel()
		{
			return blogActionLabel;
		}

		public void setBlogActionLabel(String blogActionLabel)
		{
			this.blogActionLabel = blogActionLabel;
		}

		public Map getPopularTags()
		{
			return null;
		}

		public Community getCurrentCommunity() {
			return currentCommunity;
		}

		public void setCurrentCommunity(Community currentCommunity) {
			this.currentCommunity = currentCommunity;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public int getBid() {
			return bid;
		}

		public void setBid(int bid) {
			this.bid = bid;
		}

		public int getAuthorsCount() {
			return authorsCount;
		}

		public void setAuthorsCount(int authorsCount) {
			this.authorsCount = authorsCount;
		}

		public CommunityBlog getCommunityBlog() {
			return communityBlog;
		}

		public void setCommunityBlog(CommunityBlog communityBlog) {
			this.communityBlog = communityBlog;
		}
	}

	public class RowBean extends CommunityEntity implements EntityWrapper
	{
		private int resultSetIndex;
		private int level = 1;
		private int communityBlogId;

		// Community entity
		private Community community;

		private boolean member;

		private boolean membershipRequested;

		private Date dateJoined;

		private String role;

		public String getType () throws Exception
		{
			return community.getCommunityType();
		}

		public boolean isMember() throws Exception
		{
			if (contextManager.getContext().getCurrentUser() == null ) return false;
			if (contextManager.getContext().isUserAuthenticated() == false) return false;
			int currentUser = contextManager.getContext().getCurrentUser().getId();
			return community.isMember(currentUser);
		}

		public boolean isMembershipRequested() throws Exception
		{
			if (contextManager.getContext().getCurrentUser() == null ) return false;
			if (contextManager.getContext().isUserAuthenticated() == false) return false;
			User user = contextManager.getContext().getCurrentUser();
			return community.isMemberShipRequestPending(user);
		}

		public boolean isEvenRow()
		{
			return resultSetIndex % 2 == 0;
		}

		public boolean isOddRow()
		{
			return resultSetIndex % 2 == 1;
		}

		public final int getResultSetIndex()
		{
			return resultSetIndex;
		}

		public final void setResultSetIndex(int resultSetIndex)
		{
			this.resultSetIndex = resultSetIndex;
		}

		public void setCommunity(Community community)
		{
			this.community = community;
		}

		public int getLevel()
		{
			return level;
		}

		public void setLevel(int level)
		{
			this.level = level;
		}

		/**
		 * @return the dateJoined
		 */
		public Date getDateJoined() {
			return dateJoined;
		}

		/**
		 * @param dateJoined the dateJoined to set
		 */
		public void setDateJoined(Date dateJoined) {
			this.dateJoined = dateJoined;
		}

		/**
		 * @return the role
		 */
		public String getRole() {
			return role;
		}

		/**
		 * @param role the role to set
		 */
		public void setRole(String role) {
			this.role = role;
		}

		public int getCommunityBlogId() {
			return communityBlogId;
		}

		public void setCommunityBlogId(int communityBlogId) {
			this.communityBlogId = communityBlogId;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setBlogEntryFinder(BlogEntryFinder blogEntryFinder)
	{
		this.blogEntryFinder = blogEntryFinder;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
	{
		this.subscriptionFinder = subscriptionFinder;
	}

	public UserFinder getUserFinder()
	{
		return userFinder;
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public final void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setTagFinder(TagFinder tagFinder) {
		this.tagFinder = tagFinder;
	}

	public void setBlogAuthorFinder(BlogAuthorFinder blogAuthorFinder) {
		this.blogAuthorFinder = blogAuthorFinder;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
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

	public void setContactFinder(ContactFinder contactFinder) {
		this.contactFinder = contactFinder;
	}

	public void setCommunityBlogFinder(CommunityBlogFinder communityBlogFinder) {
		this.communityBlogFinder = communityBlogFinder;
	}

	public void setCommunityActivityFinder(
			CommunityActivityFinder communityActivityFinder) {
		this.communityActivityFinder = communityActivityFinder;
	}

	public void setUserActivityFinder(UserActivityFinder userActivityFinder) {
		this.userActivityFinder = userActivityFinder;
	}
}