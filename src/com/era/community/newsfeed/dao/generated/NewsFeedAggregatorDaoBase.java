package com.era.community.newsfeed.dao.generated; 

import com.era.community.newsfeed.dao.NewsFeedAggregator;

public interface NewsFeedAggregatorDaoBase extends NewsFeedAggregatorFinderBase
{
  public void store(NewsFeedAggregator o) throws Exception;
  public void deleteNewsFeedAggregatorForId(int id) throws Exception;
  public void delete(NewsFeedAggregator o) throws Exception;
}

