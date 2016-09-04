package com.era.community.events.ui.action;

import java.math.BigDecimal;
import java.text.ParseException;
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
import support.community.framework.CommandValidator;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.RunAsAsyncThread;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityActivity;
import com.era.community.communities.dao.CommunityActivityFinder;
import com.era.community.events.dao.Event;
import com.era.community.events.dao.EventCalendar;
import com.era.community.events.dao.EventCalendarFinder;
import com.era.community.events.dao.EventFinder;
import com.era.community.events.dao.EventInviteeLink;
import com.era.community.events.dao.EventInviteeLinkFinder;
import com.era.community.events.ui.dto.EventCommandDto;
import com.era.community.events.ui.validator.EventValidator;
import com.era.community.location.dao.Country;
import com.era.community.location.dao.CountryFinder;
import com.era.community.monitor.dao.EventCalendarSubscription;
import com.era.community.monitor.dao.EventSubscription;
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
 *   @spring.bean name="/cid/[cec]/event/editEvent.do" 
 */
public class EditEventAction extends AbstractFormAction
{
	private CommunityEraContextManager contextManager;
	private EventFinder eventFinder; 
	private UserFinder userFinder; 
	private EventCalendarFinder eventCalFinder;
	private CountryFinder countryFinder;
	private EventInviteeLinkFinder eventInviteeLinkFinder;
	private SubscriptionFinder subscriptionFinder;
	protected JavaMailSenderImpl mailSender;
	protected VelocityEngine velocityEngine;
	protected RunAsAsyncThread taskExecutor;
	protected NotificationFinder notificationFinder;
	protected DashBoardAlertFinder dashBoardAlertFinder;
	protected ContactFinder contactFinder;
	protected CommunityActivityFinder communityActivityFinder;
	protected UserActivityFinder userActivityFinder;

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	protected String getView()
	{
		return "event/editEvent";
	}

	protected void onDisplay(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		Event event = eventFinder.getEventForId(cmd.getId());
		cmd.copyPropertiesFrom(event);
		cmd.setStartDate(event.getStartDate());
		cmd.setEndDate(event.getEndDate());
		cmd.setEventId(event.getId());
		List invitee = eventInviteeLinkFinder.getInviteesForEvent(event.getId(), 0);
		if (invitee != null && invitee.size() > 0 ) {
			cmd.setInviteeList(invitee);
		} else if (context.getCurrentCommunity() != null) {
			cmd.setInviteeList(userFinder.getDefaultInviteeListForCommunityEvent(context.getCurrentCommunity().getId(), event.getPosterId(), 12));
		}
		if (event.getWeblink() != null) {
			cmd.setWeblink(event.getWeblink().equals("")?"http://":"");
		}
		try {
			cmd.setLatitude(BigDecimal.valueOf(event.getLatitude()));
			cmd.setHlatitude(Double.toString(event.getLatitude()));
		} catch (Exception e) {}
		try {
			cmd.setLongitude(BigDecimal.valueOf(event.getLongitude()));
			cmd.setHlongitude(Double.toString(event.getLongitude()));
		} catch (Exception e) {}

		cmd.setHeventType(event.getEventType());
		cmd.setHstate(event.getStatus());
		User poster = userFinder.getUserEntity(event.getPosterId());
		cmd.setPosterPhotoPresent(poster.isPhotoPresent());
		cmd.setPosterId(event.getPosterId());
		cmd.setHposterId(event.getPosterId());
		cmd.setEventPhotoPresent(event.isPhotoPresent());
		cmd.setPosterName(poster.getFullName());
		//cmd.setPosterWeb(poster.getWebSiteAddress());
		cmd.setPosterEmail(poster.getEmailAddress());
		cmd.setEventType(event.getEventType());
		cmd.createDateStarted();
		cmd.createDateEnded();
		//Country country = countryFinder.getCountryForId(poster.getCuntryCodeId());
		//cmd.setPosterMobile(country.getPhoneCountryCode() + " - "+ poster.getMobileNumber());

		cmd.setStartDateTimeForEvent(cmd.getStartDate());
		cmd.setEndDateTimeForEvent(cmd.getEndDate());

		cmd.setTags(event.getTags());
	}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();

		if (context.getCurrentUser() == null) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}

		Event event = eventFinder.getEventForId(cmd.getId());
		int oldStatus = event.getStatus();
		int calid = event.getCalendarId();

		if (event != null){
			event.setName(cmd.getName());
			event.setDescription(cmd.getDescription());

			Date sd = convertDateForEvent(cmd.getInStartDate(), cmd.getInStartTime());
			if (sd != null) {
				event.setStartDate(sd);
			} else {
				event.setStartDate(null);
			}
			Date ed = convertDateForEvent(cmd.getInEndDate(), cmd.getInEndTime());
			if (ed != null) {
				event.setEndDate(ed);
			} else {
				event.setEndDate(null);
			}

			event.setLocation(cmd.getLocation());
			event.setVenue(cmd.getVenue());
			event.setAddress(cmd.getAddress());
			event.setCity(cmd.getCity());
			event.setCstate(cmd.getCstate());
			event.setPostalCode(cmd.getPostalCode());
			event.setCountry(cmd.getCountry());
			event.setStatus(cmd.getHstate());

			try {
				event.setLatitude(new BigDecimal(cmd.getHlatitude()));
			} catch (NumberFormatException e) {}

			try {
				event.setLongitude(new BigDecimal(cmd.getHlongitude()));
			} catch (NumberFormatException e) {}

			event.setEventType(cmd.getHeventType());
			event.setEventCategory(cmd.getEventCategory());
			//event.setStartDate(cmd.getStartAsDate());
			//event.setEndDate(cmd.getEndAsDate());

			List invList = new ArrayList();
			if (cmd.getFinalList() != null && !"".equals(cmd.getFinalList())) {
				StringTokenizer outer = new StringTokenizer(cmd.getFinalList(), ",");
				while (outer.hasMoreTokens()) {
					String outerTag = outer.nextToken().trim().toLowerCase();
					StringTokenizer inner = new StringTokenizer(outerTag, "#");
					while (inner.hasMoreTokens()) {
						String innerTag = inner.nextToken().trim().toLowerCase();
						if (innerTag != null && !"".equals(innerTag)) {
							invList.add(innerTag);
						}
						break;
					}
				}
			}

			if (invList.size() > 0) {
				int guestCounter = 0;
				for (Iterator iterator = invList.iterator(); iterator.hasNext();) {
					int invitee = Integer.parseInt((String) iterator.next());
					try {
						eventInviteeLinkFinder.getEventInviteeLinkForEventAndUser(event.getId(), invitee);
					} catch (ElementNotFoundException e) {
						EventInviteeLink eventInviteeLink = eventInviteeLinkFinder.newEventInviteeLink();
						eventInviteeLink.setEventId(event.getId());
						eventInviteeLink.setUserId(invitee);
						eventInviteeLink.update();
						guestCounter ++;
					}
				}
				event.setInvited(event.getInvited()+guestCounter);
			}
			//event.setHostCount(event.getHostCount());
			//event.setConfirmed(event.getConfirmed());
			//event.setMayBe(event.getMayBe());
			event.update();

			event.setTags(cmd.getTags());
			final EventCalendar eventCal = eventCalFinder.getEventCalendarForCommunity( context.getCurrentCommunity() );
			if (oldStatus == 0 && event.getStatus() == 1) {
				CommunityActivity cActivity = communityActivityFinder.newCommunityActivity();
				cActivity.setCommunityId(context.getCurrentCommunity().getId());
				cActivity.setUserId(event.getPosterId());
				cActivity.setEventId(event.getId());
				cActivity.update();
				
				UserActivity uActivity = userActivityFinder.newUserActivity();
				uActivity.setCommunityActivityId(cActivity.getId());
				uActivity.setUserId(event.getPosterId());
				uActivity.update();
				
				mailSubscribers(event, context.getCurrentUser(), context.getCurrentCommunity());
			}
		}
		return new ModelAndView("redirect:" + context.getCurrentCommunityUrl() + 
				"/event/showEventEntry.do?id=" + event.getId() );       
	}

	private void mailSubscribers(final Event event, final User currentUser, final Community currentCommunity) throws Exception
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
							notification.setItemId(event.getId());
							notification.setItemType("Event");
							notification.update();

							DashBoardAlert dashBoardAlert = dashBoardAlertFinder.getDashBoardAlertForUserId(member.getId());
							dashBoardAlert.setNotificationCount(dashBoardAlert.getNotificationCount() + 1);
							dashBoardAlert.update();
							mailSent.add(member.getId());
						}
					}

					List subs = subscriptionFinder.getSubscriptionsForEventCalendar(event.getCalendarId());
					Iterator it = subs.iterator();

					while (it.hasNext()) {
						EventCalendarSubscription sub = (EventCalendarSubscription) it.next();

						EventSubscription eventSub = subscriptionFinder.newEventSubscription();
						eventSub.setCommunityId(new Integer(context.getCurrentCommunity().getId()));
						eventSub.setEventId(new Integer(event.getId()));
						eventSub.setUserId(sub.getUserId());
						eventSub.setFrequency(0);
						eventSub.update();

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
								message.setSubject(currentUser.getFullName()+ " published an event \"" + event.getName()+"\"");

								Map model = new HashMap();  
								String sLink = context.getCurrentCommunityUrl()+ "/event/showEventEntry.do?id=" + event.getId();
								model.put("ownerName", currentUser.getFullName());
								model.put("userImgURL", context.getContextUrl()+"/pers/userPhoto.img?id="+currentUser.getId());
								model.put("subject", event.getName());
								model.put("eventLink", sLink);
								model.put("communityName", currentCommunity.getName());
								model.put("croot", context.getContextUrl());
								model.put("cEmail", subscriber.getEmailAddress());
								model.put("cUnsubscribe", context.getContextUrl()+"/pers/unsubscribeMe.do?mid="+subscriber.getId()+"&type=evrequest");
								model.put("mailPreferences", context.getContextUrl()+"/pers/manageSubscriptions.do");
								model.put("cPrivacy", context.getContextUrl()+"/privacy.do");
								model.put("cHelp", context.getContextUrl()+"/help.do");
								model.put("cFeedback", context.getContextUrl()+"/feedback.do");
								SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy HH:mm:ss");
								Date now = new Date();
								String dt = sdf.format(now);
								model.put("cDate", dt);

								String text = VelocityEngineUtils.mergeTemplateIntoString(
										velocityEngine, "main/resources/velocity/NewEventForSubs.vm", "UTF-8", model);
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

	private Date convertDateForEvent(String inDate, String inTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm a");

		String time = "00:00 AM";
		if (inTime != null && !"".equals(inTime)) {
			time = inTime;
		}

		Date date;
		try {
			date = (Date)sdf.parse(inDate + " " + time);
		} catch (ParseException e) {
			return null;
		}
		return date;
	}

	public static class Command extends EventCommandDto 
	{
		private List inviteeList;
		private String finalList;
		private int eventId;
		private int hposterId;
		private boolean posterPhotoPresent;
		private String posterName;
		private String posterMobile;
		private String posterEmail;
		private String posterWeb;
		private String dateStarted;
		private String dateEnded;
		private boolean eventPhotoPresent;

		private int hstate;
		private String hlatitude;
		private String hlongitude;
		private int heventType;
		private int cCode;
		private String mobPhoneCode;

		public void setDateStarted(String dateStarted) {
			this.dateStarted = dateStarted;
		}

		public void setDateEnded(String dateEnded) {
			this.dateEnded = dateEnded;
		}

		public String getDateStarted() throws Exception
		{
			return dateStarted;
		}

		public String getDateEnded() throws Exception
		{
			return dateEnded;
		}

		public void createDateStarted() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd");   
			Date today = new Date();
			String sToday = fmter.format(today);
			try {
				Date date = fmter.parse(getStartDate());
				if (fmter.format(date).equals(sToday)) {
					this.dateStarted =  "Today " + fmt.format(date);
				}
				this.dateStarted =  fmt2.format(date) + " at " + fmt.format(date);
			} catch (Exception e) {
				this.dateStarted =  "";
			}
		}

		public void createDateEnded() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd");      
			Date today = new Date();
			String sToday = fmter.format(today);
			try {
				Date date = fmter.parse(getEndDate());
				if (fmter.format(date).equals(sToday)) {
					this.dateEnded = "Today " + fmt.format(date);
				}
				this.dateEnded = fmt2.format(date) + " at " + fmt.format(date);
			} catch (Exception e) {
				this.dateEnded = "";
			}
		}

		public List getInviteeList() {
			return inviteeList;
		}

		public void setInviteeList(List inviteeList) {
			this.inviteeList = inviteeList;
		}

		public String getFinalList() {
			return finalList;
		}

		public void setFinalList(String finalList) {
			this.finalList = finalList;
		}

		public int getEventId() {
			return eventId;
		}

		public void setEventId(int eventId) {
			this.eventId = eventId;
		}

		public boolean isPosterPhotoPresent() {
			return posterPhotoPresent;
		}

		public void setPosterPhotoPresent(boolean posterPhotoPresent) {
			this.posterPhotoPresent = posterPhotoPresent;
		}

		public String getHlatitude() {
			return hlatitude;
		}

		public void setHlatitude(String hlatitude) {
			this.hlatitude = hlatitude;
		}

		public String getHlongitude() {
			return hlongitude;
		}

		public void setHlongitude(String hlongitude) {
			this.hlongitude = hlongitude;
		}

		public int getHstate() {
			return hstate;
		}

		public void setHstate(int hstate) {
			this.hstate = hstate;
		}

		public int getHeventType() {
			return heventType;
		}

		public void setHeventType(int heventType) {
			this.heventType = heventType;
		}

		public int getCCode() {
			return cCode;
		}

		public void setCCode(int code) {
			cCode = code;
		}

		public String getMobPhoneCode() {
			return mobPhoneCode;
		}

		public void setMobPhoneCode(String mobPhoneCode) {
			this.mobPhoneCode = mobPhoneCode;
		}

		public String getPosterName() {
			return posterName;
		}

		public void setPosterName(String posterName) {
			this.posterName = posterName;
		}

		public String getPosterMobile() {
			return posterMobile;
		}

		public void setPosterMobile(String posterMobile) {
			this.posterMobile = posterMobile;
		}

		public String getPosterEmail() {
			return posterEmail;
		}

		public void setPosterEmail(String posterEmail) {
			this.posterEmail = posterEmail;
		}

		public String getPosterWeb() {
			return posterWeb;
		}

		public void setPosterWeb(String posterWeb) {
			this.posterWeb = posterWeb;
		}

		public int getHposterId() {
			return hposterId;
		}

		public void setHposterId(int hposterId) {
			this.hposterId = hposterId;
		}

		public boolean isEventPhotoPresent() {
			return eventPhotoPresent;
		}

		public void setEventPhotoPresent(boolean eventPhotoPresent) {
			this.eventPhotoPresent = eventPhotoPresent;
		}
	}

	public class Validator extends EventValidator
	{
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setEventFinder(EventFinder eventFinder)
	{
		this.eventFinder = eventFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public void setEventInviteeLinkFinder(
			EventInviteeLinkFinder eventInviteeLinkFinder) {
		this.eventInviteeLinkFinder = eventInviteeLinkFinder;
	}

	public void setCountryFinder(CountryFinder countryFinder) {
		this.countryFinder = countryFinder;
	}

	public void setTaskExecutor(RunAsAsyncThread taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setEventCalFinder(EventCalendarFinder eventCalFinder) {
		this.eventCalFinder = eventCalFinder;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
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