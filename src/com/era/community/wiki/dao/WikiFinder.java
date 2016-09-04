package com.era.community.wiki.dao; 

import support.community.database.QueryScroller;

import com.era.community.communities.dao.Community;

public interface WikiFinder extends com.era.community.wiki.dao.generated.WikiFinderBase
{
	public Wiki getWikiForCommunity(Community comm) throws Exception;
	public Wiki getWikiForCommunityId(int commId) throws Exception;
	public WikiEntry getMostRecentWikiEntry(int wikiId) throws Exception;
	public QueryScroller listAllWikiEntriesByUpdateDate(String filterTagList) throws Exception;
	public QueryScroller listAllWikiEntriesByTitle(String filterTagList) throws Exception;
}