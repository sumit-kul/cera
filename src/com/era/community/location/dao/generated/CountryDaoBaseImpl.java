package com.era.community.location.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.location.dao.Country;

public abstract class CountryDaoBaseImpl extends AbstractJdbcDaoSupport implements CountryDaoBase
{
	public void delete(Country o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(Country o) throws Exception
	{
		storeEntity(o);
	}
	
	public Country newCountry() throws Exception
	{
		return (Country)newEntity(Country.class);
	}
	
	public Country getCountryForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (Country)getEntity(Country.class, keys);
	}
}