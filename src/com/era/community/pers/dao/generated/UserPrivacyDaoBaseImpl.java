package com.era.community.pers.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.pers.dao.UserPrivacy;

public abstract class UserPrivacyDaoBaseImpl extends AbstractJdbcDaoSupport implements UserPrivacyDaoBase
{
	public UserPrivacy getUserPrivacyForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (UserPrivacy)getEntity(UserPrivacy.class, keys);
	}

	public void store(UserPrivacy o) throws Exception
	{
		storeEntity(o);
	}
	
	public UserPrivacy newUserPrivacy() throws Exception
	{
		return (UserPrivacy)newEntity(UserPrivacy.class);
	}
}