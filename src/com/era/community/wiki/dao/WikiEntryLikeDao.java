package com.era.community.wiki.dao; 

import support.community.database.QueryScroller;

interface WikiEntryLikeDao extends com.era.community.wiki.dao.generated.WikiEntryLikeDaoBase, WikiEntryLikeFinder
{
	QueryScroller listLikesForWikiEntry(WikiEntry entry) throws Exception;
}