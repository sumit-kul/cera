package com.era.community.location.dao; 


public class LocationDaoImpl extends com.era.community.location.dao.generated.LocationDaoBaseImpl implements LocationDao
{
	public Location getLocationByUserId(int userId) throws Exception {
		String query = "select *  from Location where UserId = ?";
        return getBean(query, Location.class, userId);
	}
}