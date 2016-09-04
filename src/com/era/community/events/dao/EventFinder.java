package com.era.community.events.dao; 

import java.util.Date;
import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.communities.dao.Community;
import com.era.community.events.ui.dto.EventHeaderDto;
import com.era.community.events.ui.dto.EventPannelDto;

public interface EventFinder extends com.era.community.events.dao.generated.EventFinderBase
{
	public QueryScroller listAllEvents() throws Exception;
	public EventCalendar getEventCalendarForEvent(Event event) throws Exception;

	public int getEventCount(int groupId) throws Exception;
	public int getEventCountForCommunity(Community comm) throws Exception;

	public QueryScroller listMasterCalendarFutureEvents() throws Exception;
	public QueryScroller listMasterCalendarPastEvents() throws Exception;
	
	public int getEventCountForHost(int hostId) throws Exception;
	public QueryScroller listUpcomingEvents(String sortBy, String filterTagList, int currentUserId) throws Exception;
	
	public List<EventPannelDto> listEventsForPannel(String sortBy, int max) throws Exception;
	public List<EventPannelDto> listEventsForUserForCurrentMonth(int userId, Date start, Date end) throws Exception;
	
	public EventHeaderDto getEventForHeader(int eventId) throws Exception;
}