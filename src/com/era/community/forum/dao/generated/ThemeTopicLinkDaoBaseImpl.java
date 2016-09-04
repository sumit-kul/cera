package com.era.community.forum.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.forum.dao.ThemeTopicLink;

public abstract class ThemeTopicLinkDaoBaseImpl extends AbstractJdbcDaoSupport implements ThemeTopicLinkDaoBase
{
	/*
	 *
	 */
	public ThemeTopicLink getThemeTopicLinkForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (ThemeTopicLink)getEntity(ThemeTopicLink.class, keys);
	}

	/*
	 *
	 */
	public void deleteThemeTopicLinkForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(ThemeTopicLink o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(ThemeTopicLink o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public ThemeTopicLink newThemeTopicLink() throws Exception
	{
		return (ThemeTopicLink)newEntity(ThemeTopicLink.class);
	}

}
