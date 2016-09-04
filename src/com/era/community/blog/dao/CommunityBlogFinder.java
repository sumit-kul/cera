package com.era.community.blog.dao; 

import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.blog.ui.dto.BlogEntryDto;
import com.era.community.communities.dao.Community;
import com.era.community.pers.dao.User;

public interface CommunityBlogFinder extends com.era.community.blog.dao.generated.CommunityBlogFinderBase
{
    public CommunityBlog getCommunityBlogForCommunity(Community comm) throws Exception;
    public CommunityBlog getCommunityBlogForCommunityId(int commId) throws Exception;
    public List getCommunityBlogOptionList() throws Exception;
    public QueryScroller getCommunityBlogOptionListForUser( User user ) throws Exception;
    public int getAuthorCountCommunityBlog(int communityBlogId) throws Exception;
    public List getAuthorsForCommunityBlog(int communityBlogId, int max) throws Exception;
    public List<BlogEntryDto> getEntrisForCommunityBlog(int communityBlogId, int max) throws Exception;
    public QueryScroller listAllBlogsForHeader(String sortBy, String filterTagList) throws Exception;
}