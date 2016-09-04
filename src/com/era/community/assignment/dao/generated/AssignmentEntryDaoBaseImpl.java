package com.era.community.assignment.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.assignment.dao.AssignmentEntry;

public abstract class AssignmentEntryDaoBaseImpl extends AbstractJdbcDaoSupport implements AssignmentEntryDaoBase
{
	public AssignmentEntry getAssignmentEntryForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (AssignmentEntry)getEntity(AssignmentEntry.class, keys);
	}

	public void deleteAssignmentEntryForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(AssignmentEntry o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(AssignmentEntry o) throws Exception
	{
		storeEntity(o);
	}

	public void store(AssignmentEntry o, boolean forCountOnly) throws Exception
	{
		storeEntity(o, forCountOnly);
	}

	public String getBody(AssignmentEntry o) throws Exception
	{
		return (String)getColumn(o, "Body", String.class);
	}
	
	public void setBody(AssignmentEntry o, String data) throws Exception
	{
		setColumn(o, "Body", data);
	}

	public AssignmentEntry newAssignmentEntry() throws Exception
	{
		return (AssignmentEntry)newEntity(AssignmentEntry.class);
	}
}