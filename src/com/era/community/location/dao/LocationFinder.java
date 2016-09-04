package com.era.community.location.dao; 

public interface LocationFinder extends com.era.community.location.dao.generated.LocationFinderBase
{      
	public Location getLocationByUserId(int userId) throws Exception;
}