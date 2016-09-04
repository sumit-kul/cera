package com.era.community.location.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.location.dao.Location;

public abstract class LocationDaoBaseImpl extends AbstractJdbcDaoSupport implements LocationDaoBase
{
	public void delete(Location o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(Location o) throws Exception
	{
		storeEntity(o);
	}
	
	public Location newLocation() throws Exception
	{
		return (Location)newEntity(Location.class);
	}
}