package com.era.community.communities.ui.action;

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

import org.apache.commons.lang.ArrayUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandValidator;
import support.community.framework.RunAsServerCallback;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.CommunityFeature;
import com.era.community.base.ImageManipulation;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.communities.dao.CommunityRoleConstants;
import com.era.community.communities.ui.dto.CommunityDto;
import com.era.community.communities.ui.validator.CommunityValidator;
import com.era.community.connections.communities.dao.MemberList;
import com.era.community.connections.communities.dao.MemberListFeature;
import com.era.community.doclib.dao.DocumentLibrary;
import com.era.community.doclib.dao.DocumentLibraryFeature;
import com.era.community.monitor.dao.CommunitySubscription;
import com.era.community.monitor.dao.DocLibSubscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.DashBoardAlert;
import com.era.community.pers.dao.DashBoardAlertFinder;
import com.era.community.pers.dao.Notification;
import com.era.community.pers.dao.NotificationFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 * @spring.bean name="/community/createNewCommunity.do"
 */
public class CreateNewCommunityAction extends AbstractFormAction
{
	protected CommunityEraContextManager contextManager;
	protected CommunityFinder communityFinder;
	protected UserFinder userFinder;
	protected ContactFinder contactFinder;
	protected MemberListFeature memberListFeature;
	protected SubscriptionFinder subscriptionFinder;
	protected DocumentLibraryFeature documentLibraryFeature;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;
	protected NotificationFinder notificationFinder;
	protected DashBoardAlertFinder dashBoardAlertFinder;

	@Override
	protected String getView() {
		return "community/createCommunityDetail";
	}

	@Override
	protected void onDisplay(Object data) throws Exception {
		Command cmd = (Command) data;
		User user = contextManager.getContext().getCurrentUser();
	}

	@Override
	protected ModelAndView onSubmit(Object data) throws Exception {
		CommunityEraContext context = contextManager.getContext();
		if (context.getCurrentUser() == null ) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}
		final User user = context.getCurrentUser();

		final Command cmd = (Command) data;

		final Community comm;
		if ("Protected".equalsIgnoreCase(cmd.communityType)) {
			comm = (Community) communityFinder.newProtectedCommunity();
		} else if ("Private".equalsIgnoreCase(cmd.communityType)) {
			comm = (Community) communityFinder.newPrivateCommunity();
		} else {
			comm = (Community) communityFinder.newPublicCommunity();
		}

		cmd.copyNonNullPropertiesTo(comm);
		getRunServerAsTemplate().execute(new RunAsServerCallback() {
			public Object doInSecurityContext() throws Exception
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date now = new Date();
				String dt = sdf.format(now);
				Timestamp ts = Timestamp.valueOf(dt);

				comm.setCreatorId(user.getId());
				comm.setStatus(Community.STATUS_ACTIVE);
				comm.setVisitCount(0);
				comm.update();
				comm.setDescription(cmd.getDescription());
				comm.setDescription( ImageManipulation.manageImages(contextManager.getContext(), cmd.getDescription(), cmd.getName(), user.getId(), comm.getId(), "CommunityDescription") );
				comm.setTags(cmd.getTags());
				comm.setModified(ts);
				comm.setCommunityUpdated(ts);
				comm.update();

				Iterator i = contextManager.getContext().getFeatures().iterator();
				while (i.hasNext()) {
					CommunityFeature feature = (CommunityFeature) i.next();
					if (feature.isFeatureMandatory() || cmd.isFeatureSelected(feature))
						feature.setFeatureEnabledForCommunity(comm, true);
				}

				MemberList mlist = (MemberList)memberListFeature.getFeatureForCommunity(comm);
				if (mlist != null) {
					mlist.addMember(user, CommunityRoleConstants.OWNER);
				}

				// Add all top level subscription initially...
				Integer userId = Integer.valueOf(user.getId());
				Integer commId = Integer.valueOf(comm.getId());

				CommunitySubscription commSub = subscriptionFinder.newCommunitySubscription();
				commSub.setUserId(userId);
				commSub.setCommunityId(commId);
				commSub.setFrequency(1);
				commSub.setWebSubscription(1);
				commSub.setMailSubscription(1);
				commSub.setPageSubscription(1);
				commSub.setModified(ts);
				commSub.update();

				DocLibSubscription libSub = subscriptionFinder.newDocLibSubscription();
				DocumentLibrary lib = (DocumentLibrary)documentLibraryFeature.getFeatureForCommunity(comm);
				libSub.setDocLibId(Integer.valueOf(lib.getId()));
				libSub.setUserId(userId);
				libSub.setCommunityId(commId);
				libSub.setFrequency(1);
				libSub.setWebSubscription(1);
				libSub.setMailSubscription(1);
				libSub.setPageSubscription(1);
				libSub.setModified(ts);
				libSub.update();

				mailToCommunityCreator(comm);
				//TODO mail required... for user followers 
				return null;
			}
		});

		return new ModelAndView("redirect:/community/completeNewCommunity.do", "communityId", comm.getId());
	}

	private void mailToCommunityCreator(Community comm) throws Exception
	{
		try {
			CommunityEraContext context = contextManager.getContext();
			int uid = context.getCurrentUser().getId();
			User submitter = userFinder.getUserEntity(uid);
			if (!submitter.isSuperAdministrator()) {
				List <User> contacts = contactFinder.listAllMyContacts(uid, 0);

				for (User contact : contacts){
					if (submitter == null || contact.getId() == submitter.getId())
						continue;
					Notification notification = notificationFinder.newNotification();
					notification.setCommunityId(comm.getId());
					notification.setUserId(contact.getId());
					notification.setItemId(comm.getId());
					notification.setItemType("Community");
					notification.update();

					DashBoardAlert dashBoardAlert = dashBoardAlertFinder.getDashBoardAlertForUserId(contact.getId());
					dashBoardAlert.setNotificationCount(dashBoardAlert.getNotificationCount() + 1);
					dashBoardAlert.update();
				}

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date now = new Date();
				String dt = sdf.format(now);
				
				MimeMessage message = mailSender.createMimeMessage();
				message.setFrom(new InternetAddress("support@jhapak.com"));
				message.setRecipients(Message.RecipientType.TO, submitter.getEmailAddress());
				message.setSubject("Your new community '"+comm.getName()+"' has been published");

				Map model = new HashMap();    
				model.put("communityType", comm.getCommunityType());
				model.put("commName", comm.getName());
				model.put("userName", submitter.getFirstName());
				model.put("communityId", comm.getId());
				model.put("link1", context.getContextUrl()+"/cid/"+comm.getId()+"/community/editCommunity.do");
				model.put("croot", context.getContextUrl());
				model.put("cDate", dt);
				
				model.put("cEmail", submitter.getEmailAddress());
				model.put("cUnsubscribe", context.getContextUrl()+"/pers/unsubscribeMe.do?mid="+submitter.getId()+"&key="+submitter.getFirstKey()+"&type=daily");
				model.put("mailPreferences", context.getContextUrl()+"/pers/manageSubscriptions.do");
				model.put("cPrivacy", context.getContextUrl()+"/privacy.do");
				model.put("cHelp", context.getContextUrl()+"/help.do");
				model.put("cFeedback", context.getContextUrl()+"/feedback.do");

				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "main/resources/velocity/CreateNewCommunity.vm", "UTF-8", model);
				message.setContent(text, "text/html");
				message.setSentDate(new Date());

				Multipart multipart = new MimeMultipart();
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

	public static class Command extends CommunityDto implements CommandBean
	{
		private String communityType = "Public";
		private String[] selectedFeatureNames;
		private String linkedOrganisation = "N";

		private String admins = "";
		private String additionalMembers = "";

		private String membershipDomains = "";
		private String parentCommunityName = null;
		private boolean parentCommunityPrivate = false;
		private MultipartFile upload1;
		private String tags;

		public final MultipartFile getUpload1()
		{
			return upload1;
		}
		public final void setUpload1(MultipartFile upload1)
		{
			this.upload1 = upload1;
		}
		public String getCommunityType()
		{
			return communityType;
		}
		public void setCommunityType(String communityType)
		{
			this.communityType = communityType;
		}
		public String[] getSelectedFeatureNames()
		{
			return selectedFeatureNames;
		}
		public void setSelectedFeatureNames(String[] selectedFeatureNames)
		{
			this.selectedFeatureNames = selectedFeatureNames;
		}
		public boolean isFeatureSelected(CommunityFeature feature) throws Exception
		{
			return ArrayUtils.contains(selectedFeatureNames, feature.getFeatureName());
		}
		public String[] getMembershipDomainList()
		{
			return StringFormat.explode(membershipDomains, ", ");
		}
		public void setMembershipDomainList(String[] list)
		{
			membershipDomains = StringFormat.implode(list, ",");
		}
		public boolean isPrivateCommunity()
		{
			return communityType.equalsIgnoreCase("Private");
		}
		public String getAdditionalMembers()
		{
			return additionalMembers;
		}
		public void setAdditionalMembers(String additionalMembers)
		{
			this.additionalMembers = additionalMembers;
		}
		public String getParentCommunityName()
		{
			return parentCommunityName;
		}
		public void setParentCommunityName(String parentCommunityName)
		{
			this.parentCommunityName = parentCommunityName;
		}
		public List<String> getAdmins() throws Exception
		{
			List<String> list = new ArrayList<String>(3);
			/*if (admin1.trim().length() > 0) list.add(admin1.trim());
            if (admin2.trim().length() > 0) list.add(admin2.trim());
            if (admin3.trim().length() > 0) list.add(admin3.trim());
            if (admin4.trim().length() > 0) list.add(admin4.trim());
            if (admin5.trim().length() > 0) list.add(admin5.trim());
            if (admin6.trim().length() > 0) list.add(admin6.trim());*/
			StringTokenizer tok = new StringTokenizer(admins, " ,\n\r", false);
			while (tok.hasMoreTokens())
				list.add(tok.nextToken());
			return list;
		}
		public List<String> getAdditionalMemberList() throws Exception
		{
			List<String> list = new ArrayList<String>(50);
			StringTokenizer tok = new StringTokenizer(additionalMembers, " ,\n\r", false);
			while (tok.hasMoreTokens())
				list.add(tok.nextToken());
			return list;
		}
		public final String getMembershipDomains()
		{
			return membershipDomains;
		}
		public final void setMembershipDomains(String membershipDomains)
		{
			this.membershipDomains = membershipDomains;
		}
		public final boolean isParentCommunityPrivate()
		{
			return parentCommunityPrivate;
		}
		public final void setParentCommunityPrivate(boolean parentCommunityPrivate)
		{
			this.parentCommunityPrivate = parentCommunityPrivate;
		}
		public String getLinkedOrganisation() {
			return linkedOrganisation;
		}
		public void setLinkedOrganisation(String linkedOrganisation) {
			this.linkedOrganisation = linkedOrganisation;
		}
		public void setAdmins(String admins) {
			this.admins = admins;
		}
		public String getTags() {
			return tags;
		}
		public void setTags(String tags) {
			this.tags = tags;
		}
	}

	public class Validator extends CommunityValidator    
	{
		public String validateName(Object value, CommandBean data) throws Exception
		{
			String err = super.validateName(value, data);
			if (err != null)
				return err;

			if (value.toString()!=null && value.toString().trim().length()> 100)
				return ("Length of Community Name cannot exceed 100 characters");

			try {
				communityFinder.getCommunityForName(value.toString().toUpperCase());
				return "A Community with the name '" +value+"' already exists";
			} catch (ElementNotFoundException e) { }

			return null;
		}
	}

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	public void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setMemberListFeature(MemberListFeature memberListFeature)
	{
		this.memberListFeature = memberListFeature;
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
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

	public void setContactFinder(ContactFinder contactFinder) {
		this.contactFinder = contactFinder;
	}

	public void setNotificationFinder(NotificationFinder notificationFinder) {
		this.notificationFinder = notificationFinder;
	}

	public void setDashBoardAlertFinder(DashBoardAlertFinder dashBoardAlertFinder) {
		this.dashBoardAlertFinder = dashBoardAlertFinder;
	}
}