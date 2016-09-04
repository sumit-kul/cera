package com.era.community.assignment.dao; 

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.assignment.ui.dto.AssignmentCommentDto;

public class AssignmentCommentDaoImpl extends com.era.community.assignment.dao.generated.AssignmentCommentDaoBaseImpl implements AssignmentCommentDao
{
    public QueryScroller listCommentsForAssignment(int assignmentId) throws Exception
    {
        final String SQL = "select C.*, U.FirstName FirstName, U.LastName LastName, U.PhotoPresent as PhotoPresent " + 
            "from AssignmentComment C, User U " +
            "where C.AssignmentId = ? " +
            "and C.PosterId = U.Id";
        
        QueryScroller scroller = getQueryScroller(SQL, AssignmentCommentDto.class, assignmentId);
        scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
        return scroller;
    }

    public int getCommentCountForAssignment(int assignmentId) throws Exception
    {
        String query = "select count(*) from AssignmentComment where AssignmentComment.AssignmentId = ? ";
        return getSimpleJdbcTemplate().queryForInt(query, assignmentId);
    }
    
    public void deleteAllAssignmentComments(int assignmentId) throws Exception
	{
		String sql="delete from " + getTableName() + " where AssignmentId = ?";
		getSimpleJdbcTemplate().update(sql, assignmentId);        
	}
}