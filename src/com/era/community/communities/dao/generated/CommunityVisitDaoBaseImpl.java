package com.era.community.communities.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.communities.dao.CommunityVisit;

public abstract class CommunityVisitDaoBaseImpl extends AbstractJdbcDaoSupport implements CommunityVisitDaoBase
{
	/*
	 *
	 */
	public CommunityVisit getCommunityVisitForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (CommunityVisit)getEntity(CommunityVisit.class, keys);
	}

	/*
	 *
	 */
	public void deleteCommunityVisitForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(CommunityVisit o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(CommunityVisit o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public CommunityVisit newCommunityVisit() throws Exception
	{
		return (CommunityVisit)newEntity(CommunityVisit.class);
	}
}