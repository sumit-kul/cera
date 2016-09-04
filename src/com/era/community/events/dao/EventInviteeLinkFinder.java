package com.era.community.events.dao; 

import java.util.List;

public interface EventInviteeLinkFinder extends com.era.community.events.dao.generated.EventInviteeLinkFinderBase
{      
	public EventInviteeLink getEventInviteeLinkForEventAndUser(int eventId, int userId) throws Exception;
	public int countInviteesForEvent(int eventId) throws Exception;
	public List getInviteesForEvent(int eventId, int max) throws Exception;
}

