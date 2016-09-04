package com.era.community.forum.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.forum.dao.Forum;

public abstract class ForumDaoBaseImpl extends AbstractJdbcDaoSupport implements ForumDaoBase
{
	/*
	 *
	 */
	public Forum getForumForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (Forum)getEntity(Forum.class, keys);
	}

	/*
	 *
	 */
	public void deleteForumForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(Forum o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(Forum o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public Forum newForum() throws Exception
	{
		return (Forum)newEntity(Forum.class);
	}

}
