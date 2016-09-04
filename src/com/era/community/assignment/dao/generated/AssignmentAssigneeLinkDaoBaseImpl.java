package com.era.community.assignment.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.assignment.dao.AssignmentAssigneeLink;

public abstract class AssignmentAssigneeLinkDaoBaseImpl extends AbstractJdbcDaoSupport implements AssignmentAssigneeLinkDaoBase
{
	public AssignmentAssigneeLink getAssignmentAssigneeLinkForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (AssignmentAssigneeLink)getEntity(AssignmentAssigneeLink.class, keys);
	}

	public void deleteAssignmentAssigneeLinkForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(AssignmentAssigneeLink o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(AssignmentAssigneeLink o) throws Exception
	{
		storeEntity(o);
	}

	public AssignmentAssigneeLink newAssignmentAssigneeLink() throws Exception
	{
		return (AssignmentAssigneeLink)newEntity(AssignmentAssigneeLink.class);
	}
}