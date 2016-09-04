package com.era.community.newsfeed.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.newsfeed.dao.NewsFeed;

public abstract class NewsFeedDaoBaseImpl extends AbstractJdbcDaoSupport implements NewsFeedDaoBase
{
	/*
	 *
	 */
	public NewsFeed getNewsFeedForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (NewsFeed)getEntity(NewsFeed.class, keys);
	}

	/*
	 *
	 */
	public void deleteNewsFeedForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(NewsFeed o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(NewsFeed o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public NewsFeed newNewsFeed() throws Exception
	{
		return (NewsFeed)newEntity(NewsFeed.class);
	}

}
