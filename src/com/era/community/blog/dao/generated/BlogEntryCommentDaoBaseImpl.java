package com.era.community.blog.dao.generated; 

import support.community.database.*;
import com.era.community.blog.dao.*;

public abstract class BlogEntryCommentDaoBaseImpl extends AbstractJdbcDaoSupport implements BlogEntryCommentDaoBase
{
	public BlogEntryComment getBlogEntryCommentForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (BlogEntryComment)getEntity(BlogEntryComment.class, keys);
	}

	public void deleteBlogEntryCommentForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}
	
	public void clearBlogEntryCommentsForBlogEntry(BlogEntry entry) throws Exception
	{
		String sql="delete from " + getTableName() + " where  BlogEntryId = ?";

		getSimpleJdbcTemplate().update(sql, entry.getId());     

	}

	public void delete(BlogEntryComment o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(BlogEntryComment o) throws Exception
	{
		storeEntity(o);
	}

	public BlogEntryComment newBlogEntryComment() throws Exception
	{
		return (BlogEntryComment)newEntity(BlogEntryComment.class);
	}
}