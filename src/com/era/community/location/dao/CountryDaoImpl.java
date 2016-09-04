package com.era.community.location.dao; 

import java.util.List;

public class CountryDaoImpl extends com.era.community.location.dao.generated.CountryDaoBaseImpl implements CountryDao
{

	@Override
	public List getAllCountry() throws Exception {
		String query = "select *  from Country where Active = 1";
        return getBeanList(query, Country.class);
	}
	
	public Country getCountryByCountryCode(String countryCode) throws Exception {
		String query = "select *  from Country where Active = 1 and Duplicate = 0 and CountryCode = ?";
        return getBean(query, Country.class, countryCode);
	}
}