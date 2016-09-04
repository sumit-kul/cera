package com.era.community.newsfeed.dao.generated; 

import com.era.community.newsfeed.dao.NewsFeed;

public interface NewsFeedDaoBase extends NewsFeedFinderBase
{
  public void store(NewsFeed o) throws Exception;
  public void deleteNewsFeedForId(int id) throws Exception;
  public void delete(NewsFeed o) throws Exception;
}

