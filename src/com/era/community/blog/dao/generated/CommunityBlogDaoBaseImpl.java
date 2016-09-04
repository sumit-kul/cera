package com.era.community.blog.dao.generated; 

import support.community.database.*;
import com.era.community.blog.dao.*;

public abstract class CommunityBlogDaoBaseImpl extends AbstractJdbcDaoSupport implements CommunityBlogDaoBase
{
	public CommunityBlog getCommunityBlogForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (CommunityBlog)getEntity(CommunityBlog.class, keys);
	}

	public void deleteCommunityBlogForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(CommunityBlog o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(CommunityBlog o) throws Exception
	{
		storeEntity(o);
	}

	public CommunityBlog newCommunityBlog() throws Exception
	{
		return (CommunityBlog)newEntity(CommunityBlog.class);
	}
}