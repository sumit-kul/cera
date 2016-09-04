package com.era.community.blog.dao.generated; 

import com.era.community.blog.dao.CommunityBlog;

public interface CommunityBlogDaoBase extends CommunityBlogFinderBase
{
  public void store(CommunityBlog o) throws Exception;
  public void deleteCommunityBlogForId(int id) throws Exception;
  public void delete(CommunityBlog o) throws Exception;
}