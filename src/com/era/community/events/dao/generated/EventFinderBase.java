package com.era.community.events.dao.generated;

import com.era.community.events.dao.Event;

public interface EventFinderBase
{
	public Event getEventForId(int id) throws Exception;
	public Event newEvent() throws Exception;
}

