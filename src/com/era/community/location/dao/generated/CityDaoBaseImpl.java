package com.era.community.location.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.location.dao.City;

public abstract class CityDaoBaseImpl extends AbstractJdbcDaoSupport implements CityDaoBase
{
	public void delete(City o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(City o) throws Exception
	{
		storeEntity(o);
	}
	
	public City newCity() throws Exception
	{
		return (City)newEntity(City.class);
	}
}
