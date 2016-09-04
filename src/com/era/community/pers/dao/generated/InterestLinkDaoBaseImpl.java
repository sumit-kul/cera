package com.era.community.pers.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.pers.dao.InterestLink;

public abstract class InterestLinkDaoBaseImpl extends AbstractJdbcDaoSupport implements InterestLinkDaoBase
{
	public InterestLink getInterestLinkForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (InterestLink)getEntity(InterestLink.class, keys);
	}

	public void deleteInterestLinkForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(InterestLink o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(InterestLink o) throws Exception
	{
		storeEntity(o);
	}

	public InterestLink newInterestLink() throws Exception
	{
		return (InterestLink)newEntity(InterestLink.class);
	}
}