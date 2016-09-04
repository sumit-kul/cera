package com.era.community.monitor.dao;

import java.util.Date;

import com.era.community.events.dao.EventCalendarFinder;

public class EventCalendarSubscription extends Subscription
{
	protected Integer EventCalendarId;
	protected EventCalendarFinder eventCalendarFinder;

	public Integer getEventCalendarId()
	{
		return EventCalendarId;
	}

	public void setEventCalendarId(Integer eventCalendarId)
	{
		EventCalendarId = eventCalendarId;
	}

	@Override
	public String getItemName() throws Exception
	{
		return "Event Calendar";
	}

	@Override
	public String getItemUrl() throws Exception
	{
		return "cid/" + this.getCommunityId() + "/event/showEvents.do";
	}

	@Override
	public String getItemType() throws Exception
	{
		return "Event Calendar";
	}

	@Override
	public Date getItemLastUpdateDate() throws Exception
	{
		return eventCalendarFinder.getEventCalendarForId(getEventCalendarId().intValue()).getLatestPostDate();
	}

	public final void setEventCalendarFinder(EventCalendarFinder eventCalendarFinder)
	{
		this.eventCalendarFinder = eventCalendarFinder;
	}

	@Override
	public Object getItem() throws Exception
	{
		return eventCalendarFinder.getEventCalendarForId(this.getEventCalendarId());
	}

	@Override
	public int getSortOrder() throws Exception
	{
		return 7;
	}

	public int compareTo(Subscription o) 
	{
		return this.sortOrder-o.sortOrder;
	}
}