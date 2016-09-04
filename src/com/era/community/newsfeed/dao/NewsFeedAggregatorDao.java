package com.era.community.newsfeed.dao; 

import java.util.List;

import support.community.database.QueryScroller;

interface NewsFeedAggregatorDao extends com.era.community.newsfeed.dao.generated.NewsFeedAggregatorDaoBase, NewsFeedAggregatorFinder
{
    public QueryScroller listEntriesByDate(NewsFeedAggregator aggregator) throws Exception;
    public QueryScroller listFeedsByName(NewsFeedAggregator aggregator) throws Exception;
    public List getFeeds(NewsFeedAggregator aggregator) throws Exception;
}

