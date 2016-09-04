package com.era.community.location.dao; 

public interface RegionFinder extends com.era.community.location.dao.generated.RegionFinderBase
{      
	public Region getRegionByName(String regionName) throws Exception;
	
	public Region getRegionByNameAndCountry(String regionName, String countryCode) throws Exception;
}