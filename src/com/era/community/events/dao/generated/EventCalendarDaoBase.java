package com.era.community.events.dao.generated; 

import com.era.community.events.dao.EventCalendar;

public interface EventCalendarDaoBase extends EventCalendarFinderBase
{
	public void store(EventCalendar o) throws Exception;
	public void deleteEventCalendarForId(int id) throws Exception;
	public void delete(EventCalendar o) throws Exception;
}

