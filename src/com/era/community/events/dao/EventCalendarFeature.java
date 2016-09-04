package com.era.community.events.dao;

import support.community.application.ElementNotFoundException;

import com.era.community.base.CommunityFeature;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.pers.dao.User;

public class EventCalendarFeature implements CommunityFeature
{
	CommunityEraContextManager contextManager;
	EventCalendarDao eventCalDao;

	public void setFeatureEnabledForCommunity(Community comm, boolean status) throws Exception
	{
		EventCalendar eventCal = (EventCalendar)getFeatureForCommunity(comm);
		if (eventCal==null && status==false) {
			return;
		}
		else if (eventCal == null && status == true) {
			eventCal = eventCalDao.newEventCalendar();
			eventCal.setCommunityId(comm.getId());
			eventCal.setName(comm.getName());
			eventCal.setInactive(false);
			eventCal.update();
			return;
		}
		else {
			eventCal.setInactive(!status);
			eventCal.update();
		}
	}

	public boolean isFeatureEnabledForCommunity(Community comm) throws Exception
	{
		EventCalendar eventCal = (EventCalendar)getFeatureForCommunity(comm);
		if (eventCal == null) return false;
		return !eventCal.isInactive();
	}

	public Object getFeatureForCommunity(Community comm) throws Exception
	{
		try {
			return eventCalDao.getEventCalendarForCommunity(comm);
		}
		catch (ElementNotFoundException e) {
			return null;
		}
	}

	public Object getFeatureForCurrentCommunity() throws Exception
	{
		return getFeatureForCommunity(contextManager.getContext().getCurrentCommunity());
	}

	public String getFeatureName() throws Exception
	{
		return "Events";
	}

	public String getFeatureLabel() throws Exception
	{
		return "<i class=\'fa fa-calendar-check-o\' aria-hidden=\'true\' style=\'margin-right: 10px;\'></i>Events";
	}

	public String getFeatureUri() throws Exception
	{
		return "/events";
	}

	public boolean isFeatureMandatory() throws Exception
	{
		return false;
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setEventCalDao(EventCalendarDao eventCalDao)
	{
		this.eventCalDao = eventCalDao;
	}

	public boolean isFeatureAvailableForUser(User user) throws Exception
	{
		return true;
	}

	public String getFeatureTitle() throws Exception
	{
		return "Event Calendar for this community";
	}

}
