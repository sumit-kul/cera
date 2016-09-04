package com.era.community.blog.dao; 

import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.blog.ui.dto.BlogEntryDto;
import com.era.community.pers.dao.User;

public interface PersonalBlogFinder extends com.era.community.blog.dao.generated.PersonalBlogFinderBase
{
	public PersonalBlog getPersonalBlogForId(int id) throws Exception;
    public PersonalBlog getPersonalBlogForUser(int consId, int userId) throws Exception;
    public QueryScroller getAllBlogsForUser(User usr) throws Exception;
    public QueryScroller getBlogsForBlogAuther(User usr, User current) throws Exception;
    public List getAuthorsForPersonalBlog(int blogPersonalId, int max) throws Exception;
    public int getAuthorCountPersonalBlog(int blogPersonalId) throws Exception;
    public List<BlogEntryDto> getEntrisForPersonalBlog(int blogPersonalId, int max) throws Exception;
}