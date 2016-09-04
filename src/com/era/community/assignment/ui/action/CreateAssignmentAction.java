package com.era.community.assignment.ui.action;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandValidator;
import support.community.util.StringFormat;

import com.era.community.assignment.dao.AssignmentFeature;
import com.era.community.assignment.dao.AssignmentTask;
import com.era.community.assignment.dao.Assignments;
import com.era.community.assignment.dao.generated.AssignmentEntity;
import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.ImageManipulation;
import com.era.community.base.RunAsAsyncThread;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.forum.ui.validator.ForumItemValidator;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.DashBoardAlertFinder;
import com.era.community.pers.dao.NotificationFinder;
import com.era.community.pers.dao.UserFinder;

/**
 * @spring.bean name="/cid/[cec]/assignment/createAssignment.do"
 */
public class CreateAssignmentAction extends AbstractFormAction
{
	protected AssignmentFeature assignmentFeature;
	protected CommunityEraContextManager contextManager;
	protected UserFinder userFinder;
	protected CommunityFinder communityFinder;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;
	protected RunAsAsyncThread taskExecutor;
	protected NotificationFinder notificationFinder;
	protected DashBoardAlertFinder dashBoardAlertFinder;
	protected ContactFinder contactFinder;

	protected String getView()
	{
		return "assignment/createAssignment";
	}

	protected void onDisplay(Object data) throws Exception
	{
		Command cmd = (Command) data;
		Assignments assignments = (Assignments) assignmentFeature.getFeatureForCurrentCommunity();
		if (assignments.getCommunityId()>0) {
			Community comm = communityFinder.getCommunityForId(assignments.getCommunityId());
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
		Assignments assignments = (Assignments) assignmentFeature.getFeatureForCurrentCommunity();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);
		
		AssignmentTask assignment = assignments.newAssignmentTask();
		assignment.setTitle(cmd.getTitle());
		
		Date date;
		try {
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			date = (Date)sdf2.parse(cmd.getDdate());
		} catch (ParseException e) {
			date = now;
		}
		assignment.setDueDate(date);
		assignment.setCreatorId(context.getCurrentUser().getId());
		assignment.setBody( ImageManipulation.manageImages(contextManager.getContext(), cmd.getBody(), cmd.getTitle(), context.getCurrentUser().getId(), assignment.getId(), "Assignment") );
		assignment.update();

		Community comm = context.getCurrentCommunity();
		comm.setCommunityUpdated(ts);
		comm.update();

		assignment.setTags(cmd.getTags());
		//mailSubscribers(topic, context.getCurrentUser(), comm);

		return new ModelAndView("redirect:" + context.getCurrentCommunityUrl() + "/assignment/showAssignment.do?id="+assignment.getId());
	}
	
	

	/*private void mailSubscribers(final ForumTopic topic, final User currentUser, final Community currentCommunity) throws Exception
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
	}*/

	public class Command extends AssignmentEntity implements CommandBean
	{
		private Community community;
		private String tags;
		private String ddate;

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
		
		public String getTags() {
			return tags;
		}
		
		public void setTags(String tags) {
			this.tags = tags;
		}

		public String getDdate() {
			return ddate;
		}

		public void setDdate(String ddate) {
			this.ddate = ddate;
		}
	}

	public class Validator extends ForumItemValidator
	{
		public String validateName(Object value, CommandBean cmd)
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

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
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

	public void setAssignmentFeature(AssignmentFeature assignmentFeature) {
		this.assignmentFeature = assignmentFeature;
	}

}