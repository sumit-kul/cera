package com.era.community.wiki.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.wiki.dao.Wiki;

public abstract class WikiDaoBaseImpl extends AbstractJdbcDaoSupport implements WikiDaoBase
{
	public Wiki getWikiForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (Wiki)getEntity(Wiki.class, keys);
	}

	public void deleteWikiForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(Wiki o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(Wiki o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public Wiki newWiki() throws Exception
	{
		return (Wiki)newEntity(Wiki.class);
	}

}
