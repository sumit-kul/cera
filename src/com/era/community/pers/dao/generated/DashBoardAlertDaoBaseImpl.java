package com.era.community.pers.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.pers.dao.DashBoardAlert;

public abstract class DashBoardAlertDaoBaseImpl extends AbstractJdbcDaoSupport implements DashBoardAlertDaoBase
{
	public DashBoardAlert getDashBoardAlertForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (DashBoardAlert)getEntity(DashBoardAlert.class, keys);
	}

	public void deleteDashBoardAlertForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(DashBoardAlert o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(DashBoardAlert o) throws Exception
	{
		storeEntity(o);
	}

	public DashBoardAlert newDashBoardAlert() throws Exception
	{
		return (DashBoardAlert)newEntity(DashBoardAlert.class);
	}
}