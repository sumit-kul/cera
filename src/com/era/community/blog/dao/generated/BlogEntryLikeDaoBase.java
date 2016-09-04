package com.era.community.blog.dao.generated; 

import com.era.community.blog.dao.BlogEntryLike;

public interface BlogEntryLikeDaoBase extends BlogEntryLikeFinderBase
{
  public void store(BlogEntryLike o) throws Exception;
  public void deleteBlogEntryLikeForId(int id) throws Exception;
  public void delete(BlogEntryLike o) throws Exception;
}

