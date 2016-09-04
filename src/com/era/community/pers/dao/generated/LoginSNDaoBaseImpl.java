package com.era.community.pers.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.pers.dao.LoginSN;

public abstract class LoginSNDaoBaseImpl extends AbstractJdbcDaoSupport implements LoginSNDaoBase
{
	public LoginSN getLoginSNForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (LoginSN)getEntity(LoginSN.class, keys);
	}

	public void delete(LoginSN o) throws Exception
	{
		deleteEntity(o);
	}
	
	public void store(LoginSN o) throws Exception
	{
		storeEntity(o);
	}
	
	public LoginSN newLoginSN() throws Exception
	{
		return (LoginSN)newEntity(LoginSN.class);
	}
}