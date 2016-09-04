package com.era.community.blog.dao.generated; 

import com.era.community.blog.dao.PersonalBlog;

public interface PersonalBlogDaoBase extends PersonalBlogFinderBase
{
  public void store(PersonalBlog o) throws Exception;
  public void deletePersonalBlogForId(int id) throws Exception;
  public void delete(PersonalBlog o) throws Exception;
}