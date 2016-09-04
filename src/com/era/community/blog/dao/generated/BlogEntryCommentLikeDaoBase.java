package com.era.community.blog.dao.generated; 

import com.era.community.blog.dao.BlogEntryCommentLike;

public interface BlogEntryCommentLikeDaoBase extends BlogEntryCommentLikeFinderBase
{
  public void store(BlogEntryCommentLike o) throws Exception;
  public void deleteBlogEntryCommentLikeForId(int id) throws Exception;
  public void delete(BlogEntryCommentLike o) throws Exception;
}

