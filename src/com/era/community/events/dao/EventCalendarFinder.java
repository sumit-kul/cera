package com.era.community.events.dao; 

import com.era.community.communities.dao.Community;

public interface EventCalendarFinder extends com.era.community.events.dao.generated.EventCalendarFinderBase
{
	public EventCalendar getEventCalendarForCommunity(Community comm) throws Exception;
	public EventCalendar getEventCalendarForCommunityId(int communityId) throws Exception;
	public Event getMostRecentEvent(int calendarId) throws Exception;
	public Community getCommunityForCalendar(EventCalendar calendar) throws Exception;
}