package com.era.community.jobs.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.jobs.dao.ScheduledJob;

public abstract class ScheduledJobDaoBaseImpl extends AbstractJdbcDaoSupport implements ScheduledJobDaoBase
{
	public ScheduledJob getScheduledJobForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (ScheduledJob)getEntity(ScheduledJob.class, keys);
	}

	public void deleteScheduledJobForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(ScheduledJob o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(ScheduledJob o) throws Exception
	{
		storeEntity(o);
	}

	public ScheduledJob newScheduledJob() throws Exception
	{
		return (ScheduledJob)newEntity(ScheduledJob.class);
	}
}