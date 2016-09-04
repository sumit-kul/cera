package com.era.community.location.dao; 

import java.util.List;

public class RegionDaoImpl extends com.era.community.location.dao.generated.RegionDaoBaseImpl implements RegionDao
{
	public List getAllRegion() throws Exception {
		String query = "select *  from Region where Active = 1";
        return getBeanList(query, Region.class);
	}
	
	public Region getRegionByName(String regionName) throws Exception {
		String query = "select *  from Region where Active = 1 and Duplicate = 0 and RegionName = ?";
        return getBean(query, Region.class, regionName);
	}
	
	public Region getRegionByNameAndCountry(String regionName, String countryCode) throws Exception {
		String query = "select R.*  from Region R, Country C where R.CountryId = C.Id and R.Active = 1 and R.Duplicate = 0 and R.RegionName = ? and C.CountryCode = ?";
        return getBean(query, Region.class, regionName, countryCode);
	}
}