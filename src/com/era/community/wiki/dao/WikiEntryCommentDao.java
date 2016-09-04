package com.era.community.wiki.dao; 

import support.community.database.QueryScroller;

interface WikiEntryCommentDao extends com.era.community.wiki.dao.generated.WikiEntryCommentDaoBase, WikiEntryCommentFinder
{
    QueryScroller listCommentsForWikiEntry(WikiEntry entry) throws Exception;
}