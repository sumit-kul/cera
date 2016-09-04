package com.era.community.blog.dao; 

import java.util.List;

import support.community.database.QueryScroller;

public interface BlogAuthorFinder extends com.era.community.blog.dao.generated.BlogAuthorFinderBase
{      
	public BlogAuthor getBlogAuthorForBlogAndUser(int blogId, int userId) throws Exception;
	public BlogAuthor getPersonalBlogAuthorForBlogAndUser(int personalBlogId, int userId) throws Exception;
	
	public List getBlogAuthorsListForBlog(int blogId, int max) throws Exception;
	public List getPersonalBlogAuthorsListForBlog(int persBlogId, int max) throws Exception;
	public int getBlogAuthorsCountForBlog(int blogId) throws Exception;
	public QueryScroller getBlogAuthorsListForBlog(int blogId, String sortBy) throws Exception;
	public QueryScroller getPersonalBlogAuthorsListForBlog(int personalBlogId) throws Exception;
}