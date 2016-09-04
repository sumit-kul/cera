package com.era.community.assignment.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.assignment.dao.Assignment;
import com.era.community.assignment.dao.AssignmentEntry;
import com.era.community.assignment.dao.AssignmentTask;

public abstract class AssignmentDaoBaseImpl extends AbstractJdbcDaoSupport implements AssignmentDaoBase
{
	public Assignment getAssignmentForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (Assignment)getEntity(Assignment.class, keys);
	}

	public void deleteAssignmentForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(Assignment o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(Assignment o) throws Exception
	{
		storeEntity(o);
	}

	public void store(Assignment o, boolean forCountOnly) throws Exception
	{
		storeEntity(o, forCountOnly);
	}

	public String getBody(Assignment o) throws Exception
	{
		return (String)getColumn(o, "Body", String.class);
	}
	
	public void setBody(Assignment o, String data) throws Exception
	{
		setColumn(o, "Body", data);
	}

	public Assignment newTask() throws Exception
	{
		return (AssignmentTask)newEntity(AssignmentTask.class);
	}
	
	public AssignmentTask newAssignmentTask() throws Exception
	{
		return (AssignmentTask)newEntity(AssignmentTask.class);
	}
	
	public AssignmentEntry newAssignmentEntry() throws Exception
	{
		return (AssignmentEntry)newEntity(AssignmentEntry.class);
	}
}