package com.era.community.blog.dao.generated; 

import com.era.community.blog.dao.CommunityBlog;

public interface CommunityBlogFinderBase
{
    public CommunityBlog getCommunityBlogForId(int id) throws Exception;
    public CommunityBlog newCommunityBlog() throws Exception;
}