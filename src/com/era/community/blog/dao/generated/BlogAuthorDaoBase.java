package com.era.community.blog.dao.generated; 

import com.era.community.blog.dao.BlogAuthor;

public interface BlogAuthorDaoBase extends BlogAuthorFinderBase
{
  public void store(BlogAuthor o) throws Exception;
  public void deleteBlogAuthorForId(int id) throws Exception;
  public void delete(BlogAuthor o) throws Exception;
}

