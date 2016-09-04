package com.era.community.events.dao; 


interface EventDao extends com.era.community.events.dao.generated.EventDaoBase, EventFinder
{
	public boolean isPhotoPresent(Event event) throws Exception;
    public void clearPhoto(Event event) throws Exception;
}

