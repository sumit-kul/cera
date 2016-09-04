package com.era.community.pers.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.pers.dao.UserActivity;

public abstract class UserActivityDaoBaseImpl extends AbstractJdbcDaoSupport implements UserActivityDaoBase
{
	public UserActivity getUserActivityForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (UserActivity)getEntity(UserActivity.class, keys);
	}

	public void deleteUserActivityForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(UserActivity o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(UserActivity o) throws Exception
	{
		storeEntity(o);
	}

	public UserActivity newUserActivity() throws Exception
	{
		return (UserActivity)newEntity(UserActivity.class);
	}
}