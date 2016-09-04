package com.era.community.forum.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.forum.dao.ForumResponseLike;

public abstract class ForumResponseLikeDaoBaseImpl extends AbstractJdbcDaoSupport implements ForumResponseLikeDaoBase
{
	public ForumResponseLike getForumResponseLikeForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (ForumResponseLike)getEntity(ForumResponseLike.class, keys);
	}

	public void deleteForumResponseLikeForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(ForumResponseLike o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(ForumResponseLike o) throws Exception
	{
		storeEntity(o);
	}

	public ForumResponseLike newForumResponseLike() throws Exception
	{
		return (ForumResponseLike)newEntity(ForumResponseLike.class);
	}
}