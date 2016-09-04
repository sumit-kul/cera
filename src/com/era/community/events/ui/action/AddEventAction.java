package com.era.community.events.ui.action;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractFormAction;
import support.community.framework.CommandValidator;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.CommunityActivity;
import com.era.community.communities.dao.CommunityActivityFinder;
import com.era.community.events.dao.Event;
import com.era.community.events.dao.EventCalendar;
import com.era.community.events.dao.EventCalendarFinder;
import com.era.community.events.dao.EventFinder;
import com.era.community.events.ui.dto.EventCommandDto;
import com.era.community.events.ui.validator.EventValidator;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 * @spring.bean name="/cid/[cec]/event/addEvent.do" 
 */
public class AddEventAction extends AbstractFormAction
{
	private EventCalendarFinder eventCalFinder;
	private EventFinder eventFinder;
	private CommunityEraContextManager contextManager;
	private SubscriptionFinder subscriptionFinder;
	private UserFinder userFinder;
	protected CommunityActivityFinder communityActivityFinder;

	protected String getView()
	{
		return "event/addEvent";
	}

	protected void onDisplay(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();  
		if (context.getCurrentUser() != null) {
			User currentUser = context.getCurrentUser();
			cmd.setContactName(currentUser.getFullName());
			cmd.setContactEmail(currentUser.getEmailAddress());
			//cmd.setContactTel(currentUser.getMobileNumber());
		}
		cmd.setEventType(0);
		cmd.setWeblink("http://");
	}

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		if (context.getCurrentUser() == null ) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}

		final Command cmd = (Command) data;        
		final EventCalendar eventCal = eventCalFinder.getEventCalendarForCommunity( context.getCurrentCommunity() );
		Event event = eventFinder.newEvent();
		cmd.setEventId(event.getId());
		event.setHostCount(0);
		event.setConfirmed(0);
		event.setInvited(0);
		event.setPhoneCodeId(0);
		event.setMayBe(0);
		event.setDeclined(0);
		event.setRollup("N");
		event.setStatus(0);
		try{
			cmd.copyNonNullPropertiesTo(event);
		}catch (Exception e) {
		}
		event.setCalendarId(eventCal.getId());
		event.setPosterId( context.getCurrentUser().getId() );
		event.setContactName(context.getCurrentUser().getFullName());
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

		event.setCity(cmd.getHcity());
		event.setCstate(cmd.getHstate());
		event.setPostalCode(cmd.getHpostalCode());
		event.setCountry(cmd.getHcountry());

		try {
			event.setLatitude(new BigDecimal(cmd.getHlatitude()));
		} catch (NumberFormatException e) {}

		try {
			event.setLongitude(new BigDecimal(cmd.getHlongitude()));
		} catch (NumberFormatException e) {}

		event.setEventCategory(cmd.getEventCategory());

		if (event.getWeblink() != null && event.getWeblink().toLowerCase().startsWith("http://")==false)
			event.setWeblink("http://"+event.getWeblink());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);
		event.setModified(ts);

		event.update();
		cmd.setId( event.getId() );
		/*
		EventInviteeLink eventInviteeLink = eventInviteeLinkFinder.newEventInviteeLink();
		eventInviteeLink.setEventId(event.getId());
		eventInviteeLink.setUserId( context.getCurrentUser().getId());
		eventInviteeLink.update();*/
		
		if (context.getCurrentCommunity() != null) {
			CommunityActivity cActivity = communityActivityFinder.newCommunityActivity();
			cActivity.setCommunityId(context.getCurrentCommunity().getId());
			cActivity.setWikiEntryId(event.getId());
			cActivity.update();
			return new ModelAndView("redirect:/cid/" + context.getCurrentCommunity().getId() + "/event/manageEvent.do?id="+event.getId());
		} else {
			return new ModelAndView("redirect:/pers/myEvents.do");
		}
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

		private String hcity;
		private String hstate;
		private String hpostalCode;
		private String hcountry;
		private String hlatitude;
		private String hlongitude;

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

		public String getHcity() {
			return hcity;
		}

		public void setHcity(String hcity) {
			this.hcity = hcity;
		}

		public String getHstate() {
			return hstate;
		}

		public void setHstate(String hstate) {
			this.hstate = hstate;
		}

		public String getHpostalCode() {
			return hpostalCode;
		}

		public void setHpostalCode(String hpostalCode) {
			this.hpostalCode = hpostalCode;
		}

		public String getHcountry() {
			return hcountry;
		}

		public void setHcountry(String hcountry) {
			this.hcountry = hcountry;
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
	}

	public class Validator extends EventValidator
	{
	}

	public void setEventFinder(EventFinder eventFinder)
	{
		this.eventFinder = eventFinder;
	}

	public void setEventCalFinder(EventCalendarFinder eventCalFinder)
	{
		this.eventCalFinder = eventCalFinder;
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
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

	public CommunityEraContextManager getContextHolder()
	{
		return contextManager;
	}

	public EventCalendarFinder getEventCalFinder()
	{
		return eventCalFinder;
	}

	public EventFinder getEventFinder()
	{
		return eventFinder;
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setCommunityActivityFinder(
			CommunityActivityFinder communityActivityFinder) {
		this.communityActivityFinder = communityActivityFinder;
	}
}