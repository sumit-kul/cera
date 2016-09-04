package com.era.community.pers.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.pers.dao.MasterData;

public abstract class MasterDataDaoBaseImpl extends AbstractJdbcDaoSupport implements MasterDataDaoBase
{
	public MasterData getMasterDataForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (MasterData)getEntity(MasterData.class, keys);
	}

	public void deleteMasterDataForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(MasterData o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(MasterData o) throws Exception
	{
		storeEntity(o);
	}

	public MasterData newMasterData() throws Exception
	{
		return (MasterData)newEntity(MasterData.class);
	}
}