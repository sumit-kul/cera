package com.era.community.blog.dao.generated; 

import com.era.community.blog.dao.*;

public interface BlogEntryDaoBase extends BlogEntryFinderBase
{
  public void store(BlogEntry o) throws Exception;
  public void store(BlogEntry o, boolean isAllowed) throws Exception;
  public void deleteBlogEntryForId(int id) throws Exception;
  public void delete(BlogEntry o) throws Exception;
}