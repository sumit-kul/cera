package com.era.community.location.dao.generated; 

import com.era.community.location.dao.Location;


public interface LocationDaoBase extends LocationFinderBase
{
	public void store(Location o) throws Exception;
	public void delete(Location o) throws Exception;
}