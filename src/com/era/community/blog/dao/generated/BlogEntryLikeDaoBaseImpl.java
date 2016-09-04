package com.era.community.blog.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.blog.dao.BlogEntry;
import com.era.community.blog.dao.BlogEntryLike;

public abstract class BlogEntryLikeDaoBaseImpl extends AbstractJdbcDaoSupport implements BlogEntryLikeDaoBase
{
	public BlogEntryLike getBlogEntryLikeForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (BlogEntryLike)getEntity(BlogEntryLike.class, keys);
	}

	public void deleteBlogEntryLikeForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}
	
	public void clearBlogEntryLikesForBlogEntry(BlogEntry entry) throws Exception
	{
		String sql="delete from " + getTableName() + " where  BlogEntryId = ?";

		getSimpleJdbcTemplate().update(sql, entry.getId());     

	}

	public void delete(BlogEntryLike o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(BlogEntryLike o) throws Exception
	{
		storeEntity(o);
	}

	public BlogEntryLike newBlogEntryLike() throws Exception
	{
		return (BlogEntryLike)newEntity(BlogEntryLike.class);
	}
}