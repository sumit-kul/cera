package com.era.community.blog.dao; 

import java.util.List;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.pers.dao.User;

public class BlogAuthorDaoImpl extends com.era.community.blog.dao.generated.BlogAuthorDaoBaseImpl implements BlogAuthorDao
{
	public BlogAuthor getBlogAuthorForBlogAndUser(int blogId, int userId) throws Exception
	{
		return (BlogAuthor) getEntityWhere("BlogId = ? and UserId = ?", blogId, userId);
	}
	
	public BlogAuthor getPersonalBlogAuthorForBlogAndUser(int personalBlogId, int userId) throws Exception
	{
		return (BlogAuthor) getEntityWhere("PersonalBlogId = ? and UserId = ?", personalBlogId, userId);
	}

	public List getBlogAuthorsListForBlog(int blogId, int max) throws Exception
	{
		String query = "select distinct U.* from User U, BlogAuthor BEA " 
			+ " where U.ID = BEA.UserId and U.Inactive = 'N' and BEA.Active = 1 and BEA.BlogId = ? ";

		if (max != 0) {
			query += " LIMIT " + max + " ";
		}
		return getBeanList(query, User.class, blogId);
	}
	
	public List getPersonalBlogAuthorsListForBlog(int persBlogId, int max) throws Exception
	{
		String query = "select distinct U.* from User U, BlogAuthor BEA " 
			+ " where U.ID = BEA.UserId and U.Inactive = 'N' and BEA.Active = 1 and BEA.PersonalBlogId = ? ";

		if (max != 0) {
			query += " LIMIT " + max + " ";
		}
		return getBeanList(query, User.class, persBlogId);
	}
	
	public int getBlogAuthorsCountForBlog(int blogId) throws Exception
	{
		String query = "select count(distinct U.ID) from User U, BlogAuthor BEA " 
			+ " where U.ID = BEA.UserId and U.Inactive = 'N' and BEA.Active = 1 and BEA.BlogId = ? ";

		return getSimpleJdbcTemplate().queryForInt(query, blogId); 
	}
	
	public QueryScroller getBlogAuthorsListForBlog(int blogId, String sortBy) throws Exception
	{
		String query = "select distinct U.* from User U, BlogAuthor BEA " 
			+ " where U.ID = BEA.UserId and U.Inactive = 'N' and BEA.Active = 1 and BEA.BlogId = ? ";

		QueryScroller scroller = getQueryScroller(query, null, blogId);
		scroller.addScrollKey("STEMP.FirstName", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
    	return scroller;
	}
	
	public QueryScroller getPersonalBlogAuthorsListForBlog(int personalBlogId) throws Exception
	{
		String query = "select distinct U.*, BA.Role, BA.Id as authId from User U, BlogAuthor BA " 
			+ " where U.ID = BA.UserId and U.Inactive = 'N' and BA.Active = 1 and BA.PersonalBlogId = ? ";

		QueryScroller scroller = getQueryScroller(query, null, personalBlogId);
    	return scroller;
	}
}