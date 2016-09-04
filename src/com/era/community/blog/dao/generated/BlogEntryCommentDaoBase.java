package com.era.community.blog.dao.generated; 

import com.era.community.blog.dao.*;

public interface BlogEntryCommentDaoBase extends BlogEntryCommentFinderBase
{
  public void store(BlogEntryComment o) throws Exception;
  public void deleteBlogEntryCommentForId(int id) throws Exception;
  public void delete(BlogEntryComment o) throws Exception;
}