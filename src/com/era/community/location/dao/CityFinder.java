package com.era.community.location.dao; 

public interface CityFinder extends com.era.community.location.dao.generated.CityFinderBase
{      
	public City getCityByName(String cityName) throws Exception;
	
	public City getCityByNameAndCountry(String cityName, String countryCode) throws Exception;
	
	public City getCityByNameRegionAndCountry(String cityName, String regionName, String countryCode) throws Exception;
}