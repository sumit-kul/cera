package com.era.community.forum.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.forum.dao.ForumItemLike;

public abstract class ForumItemLikeDaoBaseImpl extends AbstractJdbcDaoSupport implements ForumItemLikeDaoBase
{
	public ForumItemLike getForumItemLikeForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (ForumItemLike)getEntity(ForumItemLike.class, keys);
	}

	public void deleteForumItemLikeForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(ForumItemLike o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(ForumItemLike o) throws Exception
	{
		storeEntity(o);
	}

	public ForumItemLike newForumItemLike() throws Exception
	{
		return (ForumItemLike)newEntity(ForumItemLike.class);
	}
}