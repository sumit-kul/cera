package com.era.community.location.dao; 

public interface CountryFinder extends com.era.community.location.dao.generated.CountryFinderBase
{      
	public Country getCountryByCountryCode(String countryCode) throws Exception;
}