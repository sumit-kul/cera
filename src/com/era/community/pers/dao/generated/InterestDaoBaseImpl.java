package com.era.community.pers.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.pers.dao.Interest;

public abstract class InterestDaoBaseImpl extends AbstractJdbcDaoSupport implements InterestDaoBase
{
	public Interest getInterestForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (Interest)getEntity(Interest.class, keys);
	}

	public void deleteInterestForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(Interest o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(Interest o) throws Exception
	{
		storeEntity(o);
	}

	public Interest newInterest() throws Exception
	{
		return (Interest)newEntity(Interest.class);
	}
}