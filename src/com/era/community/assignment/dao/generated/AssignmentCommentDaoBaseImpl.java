package com.era.community.assignment.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.assignment.dao.Assignment;
import com.era.community.assignment.dao.AssignmentComment;

public abstract class AssignmentCommentDaoBaseImpl extends AbstractJdbcDaoSupport implements AssignmentCommentDaoBase
{
	public AssignmentComment getAssignmentCommentForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (AssignmentComment)getEntity(AssignmentComment.class, keys);
	}

	public void deleteAssignmentCommentForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}
	
	public void clearAssignmentCommentsForAssignment(Assignment assignment) throws Exception
	{
		String sql="delete from " + getTableName() + " where  AssignmentId = ?";

		getSimpleJdbcTemplate().update(sql, assignment.getId());     

	}

	public void delete(AssignmentComment o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(AssignmentComment o) throws Exception
	{
		storeEntity(o);
	}

	public AssignmentComment newAssignmentComment() throws Exception
	{
		return (AssignmentComment)newEntity(AssignmentComment.class);
	}
}