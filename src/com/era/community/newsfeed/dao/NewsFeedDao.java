package com.era.community.newsfeed.dao; 

import support.community.database.QueryScroller;

interface NewsFeedDao extends com.era.community.newsfeed.dao.generated.NewsFeedDaoBase, NewsFeedFinder
{
    public QueryScroller listEntriesByDate(NewsFeed feed) throws Exception;
}

