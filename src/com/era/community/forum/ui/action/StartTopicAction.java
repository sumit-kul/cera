package com.era.community.forum.ui.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandValidator;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.ImageManipulation;
import com.era.community.base.RunAsAsyncThread;
import com.era.community.base.Taggable;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityActivity;
import com.era.community.communities.dao.CommunityActivityFinder;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.forum.dao.Forum;
import com.era.community.forum.dao.ForumFeature;
import com.era.community.forum.dao.ForumFinder;
import com.era.community.forum.dao.ForumTopic;
import com.era.community.forum.ui.dto.ForumItemDto;
import com.era.community.forum.ui.validator.ForumItemValidator;
import com.era.community.monitor.dao.ForumSubscription;
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

/**
 * @spring.bean name="/cid/[cec]/forum/startTopic.do"
 */
public class StartTopicAction extends AbstractFormAction
{
	protected ForumFeature forumFeature;
	private ForumFinder forumFinder;
	protected CommunityEraContextManager contextManager;
	protected SubscriptionFinder subscriptionFinder;
	protected UserFinder userFinder;
	protected CommunityFinder communityFinder;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;
	protected RunAsAsyncThread taskExecutor;
	protected NotificationFinder notificationFinder;
	protected DashBoardAlertFinder dashBoardAlertFinder;
	protected ContactFinder contactFinder;
	protected CommunityActivityFinder communityActivityFinder;
	protected UserActivityFinder userActivityFinder;

	protected String getView()
	{
		return "forum/startTopic";
	}

	protected void onDisplay(Object data) throws Exception
	{
		Command cmd = (Command) data;
		Forum forum = (Forum) forumFeature.getFeatureForCurrentCommunity();
		if (forum.getCommunityId()>0) {
			Community comm = communityFinder.getCommunityForId(forum.getCommunityId());
			cmd.setCommunity(comm);
		}
	}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();

		if (context.getCurrentUser() == null ) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()), "command", cmd);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);
		
		Forum forum = (Forum) forumFeature.getFeatureForCurrentCommunity();
		ForumTopic topic = forum.newTopic();

		topic.setSubject(cmd.getSubject());

		topic.setAuthorId(context.getCurrentUser().getId());
		topic.setForumId(forum.getId());
		topic.setVisitors(0);

		topic.setLft(1);
		topic.setRht(2);
		topic.setLvl(0);
		topic.setModified(ts);
		topic.update();

		topic.setBody( ImageManipulation.manageImages(context, cmd.getBody(), cmd.getSubject(), context.getCurrentUser().getId(), topic.getId(), "ForumItem") );
		topic.update();

		Community comm = context.getCurrentCommunity();
		comm.setCommunityUpdated(ts);
		comm.update();
		
		CommunityActivity cActivity = communityActivityFinder.newCommunityActivity();
		cActivity.setCommunityId(comm.getId());
		cActivity.setForumItemId(topic.getId());
		cActivity.setUserId(topic.getAuthorId());
		cActivity.update();
		
		UserActivity uActivity = userActivityFinder.newUserActivity();
		uActivity.setCommunityActivityId(cActivity.getId());
		uActivity.setUserId(topic.getAuthorId());
		uActivity.update();

		topic.setTags(cmd.getTags());
		//mailSubscribers(topic, context.getCurrentUser(), comm);

		return new ModelAndView("redirect:" + context.getCurrentCommunityUrl() + "/forum/forumThread.do?id="+topic.getId());
	}

	private void mailSubscribers(final ForumTopic topic, final User currentUser, final Community currentCommunity) throws Exception
	{
		taskExecutor.execute(new Runnable() {
			public void run() {
				try {
					CommunityEraContext context = contextManager.getContext(); 
					List mailSent = new ArrayList();
					List<User> members = currentCommunity.getMemberList().getMembersByDateJoined();
					List <User> contacts = contactFinder.listAllMyContacts(currentUser.getId(), 0);
					members.addAll(contacts);

					for (User member : members){
						if (!mailSent.contains(member.getId())) {
							if (currentUser == null || member.getId() == currentUser.getId())
								continue;
							Notification notification = notificationFinder.newNotification();
							notification.setCommunityId(currentCommunity.getId());
							notification.setUserId(member.getId());
							notification.setItemId(topic.getId());
							notification.setItemType("ForumTopic");
							notification.update();

							DashBoardAlert dashBoardAlert = dashBoardAlertFinder.getDashBoardAlertForUserId(member.getId());
							dashBoardAlert.setNotificationCount(dashBoardAlert.getNotificationCount() + 1);
							dashBoardAlert.update();
							mailSent.add(member.getId());
						}
					}

					List subs = subscriptionFinder.getSubscriptionsForForum(topic.getForumId());
					Iterator it = subs.iterator();

					while (it.hasNext()) {

						ForumSubscription sub = (ForumSubscription) it.next();
						if (sub.getFrequency() == 0) { // Only email the subscribers who want immediate alerts
							int uid = sub.getUserId();
							if (currentCommunity.isMember(uid)==false)
								continue;
							if (uid == currentUser.getId())
								continue;
							User subscriber = userFinder.getUserEntity(uid);

							if (!subscriber.isSuperAdministrator()) {
								MimeMessage message = mailSender.createMimeMessage();
								message.setFrom(new InternetAddress("support@jhapak.com"));
								message.setRecipients(Message.RecipientType.TO, subscriber.getEmailAddress());
								message.setSubject(currentUser.getFullName()+ " started a new topic in " + currentCommunity.getName());

								Map model = new HashMap();  
								String sLink = context.getCurrentCommunityUrl()+ "/forum/forumThread.do?id=" + topic.getId();
								String sUnSubscribe = context.getContextUrl()+"reg/watch.do";
								model.put("#ownerName#", currentUser.getFullName());
								model.put("#userImgURL#", context.getContextUrl()+"/pers/userPhoto.img?id="+currentUser.getId());
								model.put("#subject#", topic.getSubject());
								model.put("#threadLink#", sLink);
								model.put("#unSubscribe#", sUnSubscribe);
								model.put("#communityName#", currentCommunity.getName());
								model.put("#croot#", context.getContextUrl());

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
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public class Command extends ForumItemDto implements CommandBean, Taggable
	{
		private Community community;

		public TreeMap getPopularTags()
		{
			return null;
		}

		public Community getCommunity() {
			return community;
		}

		public void setCommunity(Community community) {
			this.community = community;
		}
	}

	public class Validator extends ForumItemValidator
	{
		public String validateSubject(Object value, CommandBean cmd)
		{
			if (value.toString().equals("")) {
				return "You must enter a title for your topic";
			}
			if (value.toString().length() > 100) {
				return "The maximum length of the title is 100 characters, please shorten your title";
			}
			return null;
		}
	}

	public void setFeature(ForumFeature feature)
	{
		this.forumFeature = feature;
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setForumFeature(ForumFeature forumFeature)
	{
		this.forumFeature = forumFeature;
	}

	public CommunityEraContextManager getContextHolder()
	{
		return contextManager;
	}

	public ForumFeature getForumFeature()
	{
		return forumFeature;
	}

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	public SubscriptionFinder getSubscriptionFinder()
	{
		return subscriptionFinder;
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

	public final void setForumFinder(ForumFinder forumFinder)
	{
		this.forumFinder = forumFinder;
	}

	public final ForumFinder getForumFinder()
	{
		return forumFinder;
	}

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
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

	public void setCommunityActivityFinder(
			CommunityActivityFinder communityActivityFinder) {
		this.communityActivityFinder = communityActivityFinder;
	}

	public void setUserActivityFinder(UserActivityFinder userActivityFinder) {
		this.userActivityFinder = userActivityFinder;
	}
}