package com.era.community.events.dao; 

import java.util.Date;
import java.util.List;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.communities.dao.Community;
import com.era.community.events.ui.dto.EventDto;
import com.era.community.monitor.ui.dto.SubscriptionItemDto;

public class EventCalendarDaoImpl extends com.era.community.events.dao.generated.EventCalendarDaoBaseImpl implements EventCalendarDao
{
	public EventCalendar getEventCalendarForCommunity(Community comm) throws Exception
	{
		return (EventCalendar)getEntityWhere("CommunityId=?", comm.getId() );
	}
	
	public EventCalendar getEventCalendarForCommunityId(int communityId) throws Exception
	{
		return (EventCalendar)getEntityWhere("CommunityId=?", communityId);
	}

	public QueryScroller listFutureEvents(EventCalendar eventCal, int currentUserId) throws Exception
	{        
		String SQL = "";
		SQL = "select * from  ( "
				+ " select E.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, C.ID as CommunityId, C.CommunityBannerPresent CommunityBannerPresent, "
				+ "(select COUNT(ID) from EventLike EL where EL.EventId = E.id) LikeCount from Event E, EventCalendar EC, Community C, "
				+ " User U where (EC.CommunityID = C.Id and EC.Id = E.CalendarId and E.CalendarId = ? and date(E.EndDate) >= date(CURRENT_TIMESTAMP) and U.ID = E.PosterId "
				+" and E.PosterId = ? and E.status = 0 ) "
				+ " union " 																	
				+ " select E.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, C.ID as CommunityId, C.CommunityBannerPresent CommunityBannerPresent, "
				+ "(select COUNT(ID) from EventLike EL where EL.EventId = E.id) LikeCount from Event E, EventCalendar EC, Community C, "
				+ " User U where (EC.CommunityID = C.Id and EC.Id = E.CalendarId and E.CalendarId = ? and date(E.EndDate) >= date(CURRENT_TIMESTAMP) and U.ID = E.PosterId and E.status > 0) "
				+ " ) as T ";

		QueryScroller scroller = getQueryScroller(SQL, true, EventDto.class, eventCal.getId(), currentUserId, eventCal.getId());
		scroller.addScrollKey("STEMP.StartDate", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}

	public QueryScroller listEventsForUserId(EventCalendar eventCal, int userId) throws Exception
	{
		final String SQL = "select * from  ( select E.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, C.ID as CommunityId, C.CommunityBannerPresent CommunityBannerPresent, "
			+ "(select COUNT(ID) from EventLike EL where EL.EventId = E.id) LikeCount  from Event E, EventCalendar EC, Community C, "
			+ " User U where (EC.CommunityID = C.Id and EC.Id = E.CalendarId and E.CalendarId = ? and E.PosterId = ? and U.ID = E.PosterId)  ) as T ";

		QueryScroller scroller = getQueryScroller(SQL, EventDto.class, eventCal.getId(), userId);
		scroller.addScrollKey("STEMP.StartDate", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}

	/* (non-Javadoc)
	 * @see com.era.community.events.dao.EventCalendarDao#listPastEvents(com.era.community.events.dao.EventCalendar)
	 */
	public QueryScroller listPastEvents(EventCalendar eventCal, int currentUserId) throws Exception
	{
		final String SQL = "select * from  ( "
			+ " select E.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, C.ID as CommunityId, C.CommunityBannerPresent CommunityBannerPresent, "
			+ "(select COUNT(ID) from EventLike EL where EL.EventId = E.id) LikeCount from Event E, EventCalendar EC, Community C, "
			+ " User U where (EC.CommunityID = C.Id and EC.Id = E.CalendarId and E.CalendarId = ? and date(E.EndDate) < date(CURRENT_TIMESTAMP) and U.ID = E.PosterId "
			+" and E.PosterId = ? and E.status = 0 ) "
			+ " union " 
			+ " select E.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, C.ID as CommunityId, C.CommunityBannerPresent CommunityBannerPresent, "
			+ "(select COUNT(ID) from EventLike EL where EL.EventId = E.id) LikeCount from Event E, EventCalendar EC, Community C, "
			+ " User U where (EC.CommunityID = C.Id and EC.Id = E.CalendarId and E.CalendarId = ? and date(E.EndDate) < date(CURRENT_TIMESTAMP) and U.ID = E.PosterId and E.status > 0) "
			+ " ) as T ";
		
		QueryScroller scroller = getQueryScroller(SQL, EventDto.class, eventCal.getId(), currentUserId, eventCal.getId());
		scroller.addScrollKey("STEMP.StartDate", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_DATE);
		return scroller; 
	}

	public Event getMostRecentEvent(int calendarId) throws Exception
	{
		String where = "CalendarId = ? order by dateposted desc LIMIT 1 ";
		return (Event)getEntityWhere(where, calendarId);

	}

	public Community getCommunityForCalendar(EventCalendar calendar) throws Exception
	{
		String where = "Id = ?";
		return (Community)getEntityWhere(where, calendar.getCommunityId());
	}

	public Date getLatestPostDate(EventCalendar cal) throws Exception
	{
		String query = "select max(Created) from Event where CalendarId = ?";
		return (Date)getSimpleJdbcTemplate().queryForObject(query, Date.class,  cal.getId());
	}

	public List<SubscriptionItemDto> getItemsSince(EventCalendar cal, Date date) throws Exception
	{
		String sQuery = "Select E.Id AS ItemId, E.Name  AS ItemTitle , E.Created AS ItemDate," +
				" E.PosterId AS AuthorId, CONCAT_WS(' ',U.FirstName,U.LastName) AS AuthorName, C.ID AS CommunityId, C.Name AS CommunityName " +
				" from Event E, User U, EventCalendar EC, Community C" +
				" where U.ID = E.PosterId and E.Calendarid = EC.ID and EC.CommunityId = C.ID and E.Created > ? and E.Calendarid=?";
		return  getBeanList(sQuery, SubscriptionItemDto.class, date, cal.getId());
	}
}