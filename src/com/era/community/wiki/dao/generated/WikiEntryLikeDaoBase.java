package com.era.community.wiki.dao.generated; 

import com.era.community.wiki.dao.WikiEntryLike;

public interface WikiEntryLikeDaoBase extends WikiEntryLikeFinderBase
{
	public void store(WikiEntryLike o) throws Exception;
	public void deleteWikiEntryLikeForId(int id) throws Exception;
	public void delete(WikiEntryLike o) throws Exception;
}