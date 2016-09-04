package com.era.community.pers.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.pers.dao.UserExpertise;

public abstract class UserExpertiseDaoBaseImpl extends AbstractJdbcDaoSupport implements UserExpertiseDaoBase
{
	/*
	 *
	 */
	public UserExpertise getUserExpertiseForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (UserExpertise)getEntity(UserExpertise.class, keys);
	}

	/*
	 *
	 */
	public void deleteUserExpertiseForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(UserExpertise o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(UserExpertise o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public UserExpertise newUserExpertise() throws Exception
	{
		return (UserExpertise)newEntity(UserExpertise.class);
	}

}
