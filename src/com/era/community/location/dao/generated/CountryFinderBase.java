package com.era.community.location.dao.generated; 

import java.util.List;

import com.era.community.location.dao.Country;

public interface CountryFinderBase
{
    public List getAllCountry() throws Exception;
    public Country newCountry() throws Exception;
    public Country getCountryForId(int id) throws Exception;
}