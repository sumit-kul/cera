package com.era.community.newsfeed.dao; 

import com.era.community.communities.dao.Community;

public interface NewsFeedAggregatorFinder extends com.era.community.newsfeed.dao.generated.NewsFeedAggregatorFinderBase
{
    public NewsFeedAggregator getNewsFeedAggregatorForCommunity(Community comm) throws Exception;

}

