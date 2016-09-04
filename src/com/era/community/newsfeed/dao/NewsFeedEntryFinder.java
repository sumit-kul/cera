package com.era.community.newsfeed.dao; 

public interface NewsFeedEntryFinder extends com.era.community.newsfeed.dao.generated.NewsFeedEntryFinderBase
{
    public NewsFeedEntry getEntryForFeedAndUri(NewsFeed feed, String uri) throws Exception;
}

