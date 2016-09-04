package com.era.community.location.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.location.dao.Region;

public abstract class RegionDaoBaseImpl extends AbstractJdbcDaoSupport implements RegionDaoBase
{
	public void delete(Region o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(Region o) throws Exception
	{
		storeEntity(o);
	}
	
	public Region newRegion() throws Exception
	{
		return (Region)newEntity(Region.class);
	}
}
