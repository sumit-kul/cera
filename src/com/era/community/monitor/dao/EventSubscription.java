package com.era.community.monitor.dao;

import java.util.Date;

import com.era.community.events.dao.Event;
import com.era.community.events.dao.EventFinder;

public class EventSubscription extends Subscription
{
	protected Integer EventId  ;
	protected EventFinder eventFinder;

	@Override
	public String getItemName() throws Exception
	{
		Event event = eventFinder.getEventForId(EventId);
		return event.getName();
	}

	@Override
	public String getItemUrl() throws Exception
	{
		return  "cid/"+this.getCommunityId()+"/event/showEventEntry.do?id="+this.getEventId().intValue();
	}

	@Override
	public String getItemType() throws Exception
	{
		return "Event";
	}
	
	@Override
	public Date getItemLastUpdateDate() throws Exception
	{
		Event event = eventFinder.getEventForId(EventId);
		return event.getModified();
	}
	
	@Override
	public Object getItem() throws Exception
	{
		return null;
	}
	
	@Override
	public int getSortOrder() throws Exception
	{
		return 1;
	}

	public int compareTo(Subscription o) 
	{
		return this.sortOrder-o.sortOrder;

	}

	public Integer getEventId() {
		return EventId;
	}

	public void setEventId(Integer eventId) {
		EventId = eventId;
	}

	public void setEventFinder(EventFinder eventFinder) {
		this.eventFinder = eventFinder;
	}
}