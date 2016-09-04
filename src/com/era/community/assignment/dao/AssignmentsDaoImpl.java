package com.era.community.assignment.dao; 

import java.util.Date;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.communities.dao.Community;

public class AssignmentsDaoImpl extends com.era.community.assignment.dao.generated.AssignmentsDaoBaseImpl implements AssignmentsDao
{

	@Override
	public Assignments getAssignmentsForCommunity(Community comm) throws Exception {
		return (Assignments)getEntityWhere("CommunityId=?", comm.getId() );
	}

	@Override
	public Assignments getAssignmentsForCommunityId(int commId) throws Exception {
		return (Assignments)getEntityWhere("CommunityId=?", commId );
	}
	
	public Date getLatestPostDate(Assignments assignments) throws Exception
	{
		String query = "select max(DatePosted) from Assignment where AssignmentsId = ?";
		return (Date)getSimpleJdbcTemplate().queryForObject(query, Date.class, assignments.getId() );
	}
	
	public QueryScroller listAssignmentsForCommunity(Assignments assignments, String sortBy) throws Exception
	{
		String sQuery = "select A.Id, A.Title, A.Body, A.CreatorId, A.DatePosted, A.DueDate, A.AssignmentsId, " +
		" CONCAT_WS(' ',U.FirstName,U.LastName) as Assigner, U.PhotoPresent, " +
		" coalesce ( (select count(*)  from AssignmentEntry AE where AE.TaskId = A.Id and AE.EntryType = 0), 0) as EntryCount, " +
		" coalesce ( (select count(*)  from AssignmentEntry TD where TD.TaskId = A.Id and TD.EntryType = 1), 0) as ToDoCount" +
		" from AssignmentTask A, User U " +
		" where U.Id = A.CreatorId and A.AssignmentsId = ? ";

		QueryScroller scroller = getQueryScroller(sQuery, true,  null,   assignments.getId());

		if (sortBy != null && sortBy.equalsIgnoreCase("Name")) {
			scroller.addScrollKey("Name", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		} else if (sortBy != null && sortBy.equalsIgnoreCase("Author")) {
			scroller.addScrollKey("Assigner", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_TEXT);
		} else {
			scroller.addScrollKey("DueDate", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}
		return scroller;
	}
}