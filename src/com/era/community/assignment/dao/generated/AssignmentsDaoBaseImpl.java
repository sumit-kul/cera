package com.era.community.assignment.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.assignment.dao.Assignments;

public abstract class AssignmentsDaoBaseImpl extends AbstractJdbcDaoSupport implements AssignmentsDaoBase
{
	public Assignments getAssignmentsForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (Assignments)getEntity(Assignments.class, keys);
	}

	public void deleteAssignmentsForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(Assignments o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(Assignments o) throws Exception
	{
		storeEntity(o);
	}

	public Assignments newAssignments() throws Exception
	{
		return (Assignments)newEntity(Assignments.class);
	}
}