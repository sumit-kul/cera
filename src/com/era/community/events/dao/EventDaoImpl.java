package com.era.community.events.dao;

import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.communities.dao.Community;
import com.era.community.events.ui.dto.EventDto;
import com.era.community.events.ui.dto.EventHeaderDto;
import com.era.community.events.ui.dto.EventPannelDto;
import com.era.community.wiki.ui.dto.WikiEntryHeaderDto;

public class EventDaoImpl extends com.era.community.events.dao.generated.EventDaoBaseImpl implements EventDao
{
	public QueryScroller listAllEvents() throws Exception
	{
		String sql = "select E.* from Event E ,EventCalendar EC, Community C where " +
		" EC.Id = E.CalendarId and C.ID = EC.CommunityId and C.SysType != 'PrivateCommunity' ";
		QueryScroller scroller = getQueryScroller(sql, null);
		return scroller;
	}

	public EventCalendar getEventCalendarForEvent(Event event) throws Exception
	{
		return (EventCalendar) getEntityWhere("Id = ?", event.getCalendarId());
	}

	public int getEventCount(int groupId) throws Exception
	{
		String query = "select count(*) from Event";
		String queryForGroup = "select count(*) from Event E, EventCalendar P, CommunityGroupLink L where "+
		"L.CommunityId = P.CommunityId and  L.CommunityGroupId=? and E.CalendarId = P.Id ";

		if ( groupId==0  )
			return getJdbcTemplate().queryForInt(query);
		else
		{
			return getSimpleJdbcTemplate().queryForInt(queryForGroup, groupId );
		}

	}

	public int getEventCountForCommunity(Community comm) throws Exception
	{
		String query = "select count(*) from EventCalendar C, Event E"
			+ " where C.Id = E.CalendarId and C.CommunityId = ?";
		return getSimpleJdbcTemplate().queryForInt(query, comm.getId() );
	}
	
	public int getEventCountForHost(int hostId) throws Exception
	{
		String query = "select count(*) from Event E where E.PosterId = ? and E.Status > 0 and E.EndDate > CURRENT_TIMESTAMP";
		
		return getSimpleJdbcTemplate().queryForInt(query, hostId );
	}
	
	public QueryScroller listEventsForUserId(EventCalendar eventCal, int userId) throws Exception
	{
		final String SQL = "select * from  ( select E.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PHOTOPRESENT as userPhotoPresent, "
			+ "(select COUNT(ID) from EventLike EL where EL.EventId = E.id) LikeCount  from Event E, EventCalendar EC, Community C, "
			+ " User U where (EC.CommunityID = C.Id and Ec.Id = E.CalendarId and E.CalendarId = ? and E.PosterId = ? and U.ID = E.PosterId)  ) as T ";

		QueryScroller scroller = getQueryScroller(SQL, EventDto.class, eventCal.getId(), userId);
		scroller.addScrollKey("STEMP.StartDate", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}

	public QueryScroller listMasterCalendarFutureEvents() throws Exception
	{
		final String SQL = "select E.* from Event E where E.ShowInMaster = 'Y' and E.StartDate > CURRENT_TIMESTAMP";

		QueryScroller scroller = getQueryScroller(SQL,EventDto.class);
		scroller.addScrollKey("STEMP.StartDate", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}

	public QueryScroller listMasterCalendarPastEvents() throws Exception
	{
		final String SQL = "select E.* from Event E where E.ShowInMaster = 'Y' and E.StartDate < CURRENT_TIMESTAMP";

		QueryScroller scroller = getQueryScroller(SQL,  EventDto.class);
		scroller.addScrollKey("STEMP.StartDate", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}
	
	public boolean isPhotoPresent(Event event) throws Exception
    {
        Integer v = (Integer) getValue(event, "case when Photo is null then 0 else 1 end", Integer.class);
        return v.intValue() == 1;
    }

    public void clearPhoto(Event event) throws Exception
    {
        setColumn(event, "Photo", null);
        setColumn(event, "PhotoContentType", null);
    }
    
    public QueryScroller listUpcomingEvents(String sortBy, String filterTagList, int currentUserId) throws Exception
	{
    	String filterTag = "";
		int count = 0;
		if(filterTagList != null && !"".equals(filterTagList)) {
			String filterTagPart = "";
			if (filterTagList.contains(",")) {
				StringTokenizer st = new StringTokenizer(filterTagList, ",");
				while (st.hasMoreTokens()) {
					String tag = st.nextToken().trim().toLowerCase();
					filterTagPart += " T.TagText = \'"+tag+"\' ";
					if (st.hasMoreTokens()) filterTagPart += " or ";
					count++;
				}
			} else {
				filterTagPart = " T.TagText = \'"+filterTagList+"\' ";
				count = 1;
			}
			filterTag = " and exists (select ParentId from (select count(ID), ParentId  from Tag T where PARENTTYPE = 'Event' "+
			" and " +filterTagPart+ " group by ParentId having count(ID) >= "+count+" ) as T where T.ParentId = E.Id) " ;
		}
		
    	String sql = "select distinct E.Id as EntryId, E.Name, E.PosterId, E.ContactName, E.StartDate, E.EndDate, E.City, E.Country, EC.CommunityID, U.PhotoPresent as PhotoPresent, " +
		" E.PhotoPresent as EventPhotoPresent from Event E, EventCalendar EC, Community C, User U where EC.CommunityID = C.Id and E.CalendarId = EC.ID and C.SysType = 'PublicCommunity' " +
		" and U.Id = E.PosterId and (E.status > 0 or U.Id = ?) and E.EndDate >= CURRENT_TIMESTAMP and C.Status = " + Community.STATUS_ACTIVE + filterTag;

		QueryScroller scroller = getQueryScroller(sql,  EventPannelDto.class, currentUserId);
		if (sortBy.equalsIgnoreCase("Date")) {
			scroller.addScrollKey("STEMP.StartDate", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_DATE);
		} else {
			scroller.addScrollKey("STEMP.Name", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		}
		return scroller;
	}
    
    public List<EventPannelDto> listEventsForPannel(String sortBy, int max) throws Exception 
	{              
    	String sub = " order by E.Created desc ";
    	if ("popular".equalsIgnoreCase(sortBy)) {
    		sub = " order by Confirmed desc ";
		}
    	String sql = "select distinct E.Id as EntryId, E.Name, E.PosterId, E.ContactName, E.StartDate, E.EndDate, E.City, E.Country, EC.CommunityID, U.PhotoPresent as PhotoPresent, " +
		" E.PhotoPresent as EventPhotoPresent from Event E, EventCalendar EC, Community C, User U where EC.CommunityID = C.Id and E.CalendarId = EC.ID and C.SysType = 'PublicCommunity' " +
		" and U.Id = E.PosterId and E.status > 0 and C.Status = " + Community.STATUS_ACTIVE + sub;

		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}
		List<EventPannelDto> list = getBeanList(sql, EventPannelDto.class);           
		return list;                      
	}
    
    public List<EventPannelDto> listEventsForUserForCurrentMonth(int userId, Date start, Date end) throws Exception 
	{              
	    	String sql = "Select E.Id as EntryId, E.Name, E.StartDate, E.EndDate, EC.CommunityId communityID, E.SysType as Type " +
			" from Event E, EventInviteeLink EIL, EventCalendar EC where EC.ID = E.CalendarId and EIL.UserId = ? and E.Id = EIL.EventId and (E.StartDate >= ? or E.StartDate <= ?) " +
			" union " +
			" select E2.Id as EntryId, E2.Name, E2.StartDate, E2.EndDate, EC2.CommunityId communityID, E2.SysType as Type " +
			" from Event E2, EventCalendar EC2 where EC2.ID = E2.CalendarId and E2.PosterId = ? and (E2.StartDate >= ? or E2.StartDate <= ?) " +
			" union " +
			" select A.Id as EntryId, A.Title as Name, A.DatePosted as StartDate, A.DueDate as EndDate , ATS.CommunityId, A.SysType as Type " +
			" from Assignment A, AssignmentAssigneeLink AL, Assignments ATS where A.id = AL.AssignmentId and ATS.ID = A.AssignmentsId and AL.UserId = ? and (A.DatePosted >= ? or A.DueDate <= ?) " +
			" union " +
			" select A.Id as EntryId, A.Title as Name, A.DatePosted as StartDate, A.DueDate as EndDate , ATS.CommunityId, A.SysType as Type " +
			" from Assignment A, Assignments ATS where ATS.ID = A.AssignmentsId and A.CreatorId = ? and (A.DatePosted >= ? or A.DueDate <= ?) ";
		List<EventPannelDto> list = getBeanList(sql, EventPannelDto.class, userId, start, end, userId, start, end, userId, start, end, userId, start, end);           
		return list;                      
	}
    
    public EventHeaderDto getEventForHeader(int eventId) throws Exception
	{
		String query = "select e.id, e.name, CAST(e.Description as CHAR(1000) ) itemDesc, e.PhotoPresent photoPresent, e.Created DatePosted, " +
			" e.Venue, e.Location, e.Address, e.StartDate, e.EndDate, e.modified, e.Invited Visitors, U.Id Posterid, " +
			" U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT userPhotoPresent, 0 as ImageCount, " +
			" (select COUNT(ID) from EventLike EL where EL.EventId = e.id) likeCount " +
			" from Event e, User U where U.Id = e.PosterId and e.status > 0 and e.Id = ?" ;
		return getBean(query, EventHeaderDto.class, eventId);
	}
}