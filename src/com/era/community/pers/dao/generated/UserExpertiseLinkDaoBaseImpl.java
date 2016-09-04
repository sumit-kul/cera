package com.era.community.pers.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.pers.dao.UserExpertiseLink;

public abstract class UserExpertiseLinkDaoBaseImpl extends AbstractJdbcDaoSupport implements UserExpertiseLinkDaoBase
{
	/*
	 *
	 */
	public UserExpertiseLink getUserExpertiseLinkForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (UserExpertiseLink)getEntity(UserExpertiseLink.class, keys);
	}

	/*
	 *
	 */
	public void deleteUserExpertiseLinkForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(UserExpertiseLink o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(UserExpertiseLink o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public UserExpertiseLink newUserExpertiseLink() throws Exception
	{
		return (UserExpertiseLink)newEntity(UserExpertiseLink.class);
	}

}
