package com.era.community.blog.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.blog.dao.BlogEntry;
import com.era.community.blog.dao.BlogEntryCommentLike;

public abstract class BlogEntryCommentLikeDaoBaseImpl extends AbstractJdbcDaoSupport implements BlogEntryCommentLikeDaoBase
{
	public BlogEntryCommentLike getBlogEntryCommentLikeForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (BlogEntryCommentLike)getEntity(BlogEntryCommentLike.class, keys);
	}

	public void deleteBlogEntryCommentLikeForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}
	
	public void clearBlogEntryCommentLikesForBlogEntry(BlogEntry entry) throws Exception
	{
		String sql="delete from " + getTableName() + " where  BlogEntryId = ?";

		getSimpleJdbcTemplate().update(sql, entry.getId());     

	}

	public void delete(BlogEntryCommentLike o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(BlogEntryCommentLike o) throws Exception
	{
		storeEntity(o);
	}

	public BlogEntryCommentLike newBlogEntryCommentLike() throws Exception
	{
		return (BlogEntryCommentLike)newEntity(BlogEntryCommentLike.class);
	}
}