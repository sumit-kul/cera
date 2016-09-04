package com.era.community.events.dao; 

public interface EventLikeFinder extends com.era.community.events.dao.generated.EventLikeFinderBase
{
	public int getLikeCount() throws Exception;

	public int getLikeCountForEvent(int entryId) throws Exception;

	public EventLike getLikeForEventAndUser(int entryId, int userId) throws Exception;
}

