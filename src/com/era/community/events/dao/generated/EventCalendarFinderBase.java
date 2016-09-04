package com.era.community.events.dao.generated; 

import com.era.community.events.dao.EventCalendar;

public interface EventCalendarFinderBase
{
	public EventCalendar getEventCalendarForId(int id) throws Exception;
	public EventCalendar newEventCalendar() throws Exception;
}

