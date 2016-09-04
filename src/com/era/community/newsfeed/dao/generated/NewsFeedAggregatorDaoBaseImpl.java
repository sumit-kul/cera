package com.era.community.newsfeed.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.newsfeed.dao.NewsFeedAggregator;

public abstract class NewsFeedAggregatorDaoBaseImpl extends AbstractJdbcDaoSupport implements NewsFeedAggregatorDaoBase
{
	/*
	 *
	 */
	public NewsFeedAggregator getNewsFeedAggregatorForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (NewsFeedAggregator)getEntity(NewsFeedAggregator.class, keys);
	}

	/*
	 *
	 */
	public void deleteNewsFeedAggregatorForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(NewsFeedAggregator o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(NewsFeedAggregator o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public NewsFeedAggregator newNewsFeedAggregator() throws Exception
	{
		return (NewsFeedAggregator)newEntity(NewsFeedAggregator.class);
	}

}
