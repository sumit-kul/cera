package com.era.community.newsfeed.dao.generated; 
import com.era.community.newsfeed.dao.NewsFeed;

public interface NewsFeedFinderBase
{
    public NewsFeed getNewsFeedForId(int id) throws Exception;
    public NewsFeed newNewsFeed() throws Exception;
}

