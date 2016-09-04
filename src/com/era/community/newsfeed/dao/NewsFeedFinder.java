package com.era.community.newsfeed.dao; 

import support.community.database.QueryScroller;

public interface NewsFeedFinder extends com.era.community.newsfeed.dao.generated.NewsFeedFinderBase
{
    public QueryScroller listAllFeeds() throws Exception;
    
    public QueryScroller listAllFeedsByAggregatorId() throws Exception;
}

