package com.era.community.wiki.dao; 

import support.community.database.QueryScroller;

interface WikiEntryCommentLikeDao extends com.era.community.wiki.dao.generated.WikiEntryCommentLikeDaoBase, WikiEntryCommentLikeFinder
{
    QueryScroller listCommentLikesForWikiEntry(WikiEntry entry) throws Exception;
    
}