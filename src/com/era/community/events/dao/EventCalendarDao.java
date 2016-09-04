package com.era.community.events.dao; 

import java.util.Date;
import java.util.List;

import support.community.database.QueryScroller;

interface EventCalendarDao extends com.era.community.events.dao.generated.EventCalendarDaoBase, EventCalendarFinder
{
	public QueryScroller listFutureEvents(EventCalendar eventCal, int currentUserId) throws Exception;
	public QueryScroller listPastEvents(EventCalendar eventCal, int currentUserId) throws Exception;
	public QueryScroller listEventsForUserId(EventCalendar eventCal, int userId) throws Exception;            

	public Date getLatestPostDate(EventCalendar eventCal) throws Exception;
	public List getItemsSince(EventCalendar eventCal, Date date) throws Exception;
}