package com.era.community.location.dao; 

import java.util.List;

public class CityDaoImpl extends com.era.community.location.dao.generated.CityDaoBaseImpl implements CityDao
{
	public List getAllCity() throws Exception {
		String query = "select *  from City where Active = 1";
        return getBeanList(query, City.class);
	}
	
	public City getCityByName(String cityName) throws Exception {
		String query = "select *  from City where Active = 1 and Duplicate = 0 and CityName = ?";
        return getBean(query, City.class, cityName);
	}
	
	public City getCityByNameAndCountry(String cityName, String countryCode) throws Exception {
		String query = "select distinct CI.*  from City CI, Country C where " +
		" CI.CountryId = C.Id and CI.Active = 1 and CI.Duplicate = 0 and CI.CityName = ? and C.CountryCode = ? ";
    return getBean(query, City.class, cityName, countryCode);
	}
	
	public City getCityByNameRegionAndCountry(String cityName, String regionName, String countryCode) throws Exception {
		String query = "select distinct CI.*  from City CI, Region R, Country C where CI.CountryId = R.CountryId and CI.RegionId = R.ID and " +
			" R.CountryId = C.Id and CI.Active = 1 and CI.Duplicate = 0 and CI.CityName = ? and R.RegionName = ? and C.CountryCode = ? ";
        return getBean(query, City.class, cityName, regionName, countryCode);
	}
}