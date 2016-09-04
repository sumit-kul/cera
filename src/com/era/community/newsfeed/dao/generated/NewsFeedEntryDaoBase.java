package com.era.community.newsfeed.dao.generated; 

import com.era.community.newsfeed.dao.NewsFeedEntry;

public interface NewsFeedEntryDaoBase extends NewsFeedEntryFinderBase
{
  public void store(NewsFeedEntry o) throws Exception;
  public void deleteNewsFeedEntryForId(int id) throws Exception;
  public void delete(NewsFeedEntry o) throws Exception;
}

