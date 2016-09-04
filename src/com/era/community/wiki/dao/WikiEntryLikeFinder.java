package com.era.community.wiki.dao; 


public interface WikiEntryLikeFinder extends com.era.community.wiki.dao.generated.WikiEntryLikeFinderBase
{
	public int getLikeCount() throws Exception;
	public int getLikeCountForWikiEntry(int entryId) throws Exception;
	public WikiEntryLike getLikeForWikiEntryAndUser(int entryId, int userId) throws Exception;
}