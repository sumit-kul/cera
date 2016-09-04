package com.era.community.profile.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.profile.dao.ProfileVisit;

public abstract class ProfileVisitDaoBaseImpl extends AbstractJdbcDaoSupport implements ProfileVisitDaoBase
{
	/*
	 *
	 */
	public ProfileVisit getProfileVisitForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (ProfileVisit)getEntity(ProfileVisit.class, keys);
	}

	/*
	 *
	 */
	public void deleteProfileVisitForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(ProfileVisit o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(ProfileVisit o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public ProfileVisit newProfileVisit() throws Exception
	{
		return (ProfileVisit)newEntity(ProfileVisit.class);
	}

}
