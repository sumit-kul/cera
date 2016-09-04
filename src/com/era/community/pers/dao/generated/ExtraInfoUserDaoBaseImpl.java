package com.era.community.pers.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.pers.dao.ExtraInfoUser;

public abstract class ExtraInfoUserDaoBaseImpl extends AbstractJdbcDaoSupport implements ExtraInfoUserDaoBase
{
	public ExtraInfoUser getExtraInfoUserEntity(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (ExtraInfoUser)getEntity(ExtraInfoUser.class, keys);
	}
	
	public void deleteExtraInfoUserForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(ExtraInfoUser o) throws Exception
	{
		deleteEntity(o);
	}
	
	public void store(ExtraInfoUser o) throws Exception
	{
		storeEntity(o);
	}
	
	public ExtraInfoUser newExtraInfoUser() throws Exception
	{
		return (ExtraInfoUser)newEntity(ExtraInfoUser.class);
	}
}