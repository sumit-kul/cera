package com.era.community.blog.dao; 

import java.util.Date;
import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.pers.dao.User;

interface CommunityBlogDao extends com.era.community.blog.dao.generated.CommunityBlogDaoBase, CommunityBlogFinder
{
    QueryScroller listBlogEntries(CommunityBlog communityBlog) throws Exception;    
    QueryScroller listBlogEntriesForUser(CommunityBlog communityBlog, User user, String sortBy) throws Exception;
    QueryScroller listBlogEntriesForUserId(CommunityBlog communityBlog, int userId, String sortBy) throws Exception;    
    
     // This method is used for the watch list
    public Date getLatestPostDate(CommunityBlog communityBlog) throws Exception;

    // This method is used for the monitor action
    public List getItemsSince(CommunityBlog communityBlog, Date date) throws Exception;
}