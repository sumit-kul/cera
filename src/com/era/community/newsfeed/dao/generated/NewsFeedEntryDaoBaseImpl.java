package com.era.community.newsfeed.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.newsfeed.dao.NewsFeedEntry;

public abstract class NewsFeedEntryDaoBaseImpl extends AbstractJdbcDaoSupport implements NewsFeedEntryDaoBase
{
	public NewsFeedEntry getNewsFeedEntryForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (NewsFeedEntry)getEntity(NewsFeedEntry.class, keys);
	}

	/*
	 *
	 */
	public void deleteNewsFeedEntryForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(NewsFeedEntry o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(NewsFeedEntry o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public NewsFeedEntry newNewsFeedEntry() throws Exception
	{
		return (NewsFeedEntry)newEntity(NewsFeedEntry.class);
	}

}
