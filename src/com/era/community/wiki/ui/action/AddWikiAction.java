package com.era.community.wiki.ui.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

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
import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandValidator;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.ImageManipulation;
import com.era.community.base.RunAsAsyncThread;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityActivity;
import com.era.community.communities.dao.CommunityActivityFinder;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.monitor.dao.WikiSubscription;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.DashBoardAlert;
import com.era.community.pers.dao.DashBoardAlertFinder;
import com.era.community.pers.dao.Notification;
import com.era.community.pers.dao.NotificationFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserActivity;
import com.era.community.pers.dao.UserActivityFinder;
import com.era.community.pers.dao.UserFinder;
import com.era.community.tagging.dao.Tag;
import com.era.community.tagging.dao.TagFinder;
import com.era.community.wiki.dao.Wiki;
import com.era.community.wiki.dao.WikiEntry;
import com.era.community.wiki.dao.WikiEntryFinder;
import com.era.community.wiki.dao.WikiEntrySection;
import com.era.community.wiki.dao.WikiEntrySectionFinder;
import com.era.community.wiki.dao.WikiFeature;
import com.era.community.wiki.ui.dto.Section;
import com.era.community.wiki.ui.dto.WikiEntryDto;
import com.era.community.wiki.ui.validator.WikiEntryValidator;

/**
 * @spring.bean name="/cid/[cec]/wiki/addWiki.do"
 */
public class AddWikiAction extends AbstractFormAction
{
	protected WikiFeature wikiFeature;
	protected CommunityEraContextManager contextManager;
	protected WikiEntryFinder wikiEntryFinder;
	protected WikiEntrySectionFinder wikiEntrySectionFinder;
	protected SubscriptionFinder subscriptionFinder;
	protected UserFinder userFinder;
	protected TagFinder tagFinder;
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
		return "wiki/addWiki";
	}

	protected void onDisplay(Object data) throws Exception
	{
		Command cmd = (Command) data;
		Wiki wiki = (Wiki) wikiFeature.getFeatureForCurrentCommunity();
		cmd.setWikiEntryFinder(wikiEntryFinder);

		try {
			WikiEntry entry = wikiEntryFinder.getLatestWikiEntryForEntryId(cmd.getEntryId());
			cmd.copyPropertiesFrom(entry);
		} catch (ElementNotFoundException e) {
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
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}

		Community comm = context.getCurrentCommunity();
		Wiki wiki = (Wiki) wikiFeature.getFeatureForCommunity(comm);

		WikiEntry entry = wikiEntryFinder.getLatestWikiEntryForEntryId( cmd.getEntryId() );

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);

		entry.setBody( ImageManipulation.manageImages(context, cmd.getBody(), cmd.getTitle(), context.getCurrentUser().getId(), entry.getId(), "WikiEntry") );
		entry.setWikiId(wiki.getId());
		entry.setModified(ts);
		entry.update();

		// add section if exists
		for (Section section : cmd.getSections()) {
			if ((section.getHeader() != null && !"".equals(section.getHeader().trim())) || (section.getBody() != null && !"".equals(section.getBody().trim()))) {
				WikiEntrySection wikiEntrySection = wikiEntrySectionFinder.newWikiEntrySection();
				wikiEntrySection.setSectionSeq(section.getSectionSeq());
				wikiEntrySection.setSectionTitle(section.getHeader());
				wikiEntrySection.setWikiEntryId(entry.getId());
				wikiEntrySection.update();

				wikiEntrySection.setSectionBody( ImageManipulation.manageImages(context, section.getBody(), cmd.getTitle(), context.getCurrentUser().getId(), wikiEntrySection.getId(), "WikiEntrySection") );
				wikiEntrySection.setSectionSeq(section.getSectionSeq());
				wikiEntrySection.update();
			}
		}

		if (!cmd.getTags().equals(""))
			tagFinder.clearTagsForParentIdUserId(entry.getId(), context.getCurrentUser().getId());    

		StringTokenizer st = new StringTokenizer(cmd.getTags(), " ");
		while (st.hasMoreTokens()) {
			String tag = st.nextToken().trim().toLowerCase();
			Tag newTag = tagFinder.newTag();
			newTag.setCommunityId(context.getCurrentCommunity().getId());
			newTag.setTagText(tag);         
			newTag.setPosterId(context.getCurrentUser().getId());
			newTag.setParentId(entry.getId());
			newTag.setParentType("WikiEntry");
			newTag.update();            
		}      

		Community currentCommunity = context.getCurrentCommunity();
		currentCommunity.setCommunityUpdated(ts);
		currentCommunity.update();
		
		CommunityActivity cActivity = communityActivityFinder.newCommunityActivity();
		cActivity.setCommunityId(comm.getId());
		cActivity.setWikiEntryId(entry.getId());
		cActivity.setUserId(entry.getPosterId());
		cActivity.update();
		
		UserActivity uActivity = userActivityFinder.newUserActivity();
		uActivity.setCommunityActivityId(cActivity.getId());
		uActivity.setUserId(entry.getPosterId());
		uActivity.update();

		mailSubscribers(wiki, entry, context.getCurrentUser(), currentCommunity);

		return new ModelAndView("redirect:" + context.getCurrentCommunityUrl() + "/wiki/showWikiEntries.do");
	}

	public class Command extends WikiEntryDto implements CommandBean
	{    
		private String tags;
		private List<Section> sections;

		public Command() {
			sections = new ArrayList<Section>();
			for (int i = 0; i < 10; i++) {
				sections.add(new Section());
			}
		}

		private final String WIKI_ACTION = "Add";

		public String getTags() {
			return tags;
		}

		public void setTags(String tags) {
			this.tags = tags;
		}

		public String getWikiAction()
		{
			return WIKI_ACTION;
		}

		public Date getToday()
		{
			return new Date();
		}

		public Map getPopularTags()
		{
			return null;
		}

		public List<Section> getSections() {
			return sections;
		}

		public void setSections(List<Section> sections) {
			this.sections = sections;
		}
	}

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	public class Validator extends WikiEntryValidator
	{

		public String validateTitle(Object value, CommandBean cmd) throws Exception
		{
			if (value.toString().length() == 0) {
				return "You must enter a title for this entry";
			}

			if (value.toString().length() > 100) {
				return "The maximum length of the title is 100 characters, please shorten your title";
			}

			/*Pass a null id to the validation on entry title duplication because this is a new entry */ 
			Wiki wiki = (Wiki) wikiFeature.getFeatureForCurrentCommunity();
			if (wiki.entryExists(value.toString(), null)) {
				return "An entry with the same title already exists in this wiki. Either change title or edit the existing entry.";
			}
			return null;
		}

		public String validateBody(Object value, CommandBean cmd) throws Exception
		{
			if (value.toString().length() == 0) {
				return "You must enter some text";
			}
			return null;
		}
	}

	private void mailSubscribers(final Wiki wiki, final WikiEntry entry, final User currentUser, final Community currentCommunity) throws Exception
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
							notification.setItemId(entry.getId());
							notification.setItemType("WikiEntry");
							notification.update();

							DashBoardAlert dashBoardAlert = dashBoardAlertFinder.getDashBoardAlertForUserId(member.getId());
							dashBoardAlert.setNotificationCount(dashBoardAlert.getNotificationCount() + 1);
							dashBoardAlert.update();
							mailSent.add(member.getId());
						}
					}

					List subs = subscriptionFinder.getSubscriptionsForWiki(wiki.getId());
					Iterator it = subs.iterator();
					while (it.hasNext()) {
						WikiSubscription sub = (WikiSubscription) it.next();

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
								String sLink = context.getCurrentCommunityUrl() + "/wiki/wikiDisplay.do?id=" + entry.getId();
								String sUnSubscribe = context.getContextUrl() + "reg/watch.do";
								model.put("#ownerName#", currentUser.getFullName());
								model.put("#userImgURL#", context.getContextUrl()+"/pers/userPhoto.img?id="+currentUser.getId());
								model.put("#entryTitle#", entry.getTitle());
								model.put("#entryLink#", sLink);
								model.put("#unSubscribe#", sUnSubscribe);
								model.put("#communityName#", currentCommunity.getName());
								model.put("#croot#", context.getContextUrl());

								String text = VelocityEngineUtils.mergeTemplateIntoString(
										velocityEngine, "main/resources/velocity/NewWikiEntry.vm", "UTF-8", model);
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

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setWikiEntryFinder(WikiEntryFinder wikiEntryFinder)
	{
		this.wikiEntryFinder = wikiEntryFinder;
	}

	public void setWikiFeature(WikiFeature wikiFeature)
	{
		this.wikiFeature = wikiFeature;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
	{
		this.subscriptionFinder = subscriptionFinder;
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public final void setTagFinder(TagFinder tagFinder)
	{
		this.tagFinder = tagFinder;
	}

	public void setWikiEntrySectionFinder(
			WikiEntrySectionFinder wikiEntrySectionFinder) {
		this.wikiEntrySectionFinder = wikiEntrySectionFinder;
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
}