package com.era.community.blog.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.blog.dao.BlogAuthor;

public abstract class BlogAuthorDaoBaseImpl extends AbstractJdbcDaoSupport implements BlogAuthorDaoBase
{
	public BlogAuthor getBlogAuthorForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (BlogAuthor)getEntity(BlogAuthor.class, keys);
	}

	public void deleteBlogAuthorForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(BlogAuthor o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(BlogAuthor o) throws Exception
	{
		storeEntity(o);
	}

	public BlogAuthor newBlogAuthor() throws Exception
	{
		return (BlogAuthor)newEntity(BlogAuthor.class);
	}
}