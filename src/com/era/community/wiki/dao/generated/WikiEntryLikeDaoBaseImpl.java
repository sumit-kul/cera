package com.era.community.wiki.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.wiki.dao.WikiEntryLike;

public abstract class WikiEntryLikeDaoBaseImpl extends AbstractJdbcDaoSupport implements WikiEntryLikeDaoBase
{
	public WikiEntryLike getWikiEntryLikeForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (WikiEntryLike)getEntity(WikiEntryLike.class, keys);
	}

	public void deleteWikiEntryLikeForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(WikiEntryLike o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(WikiEntryLike o) throws Exception
	{
		storeEntity(o);
	}

	public WikiEntryLike newWikiEntryLike() throws Exception
	{
		return (WikiEntryLike)newEntity(WikiEntryLike.class);
	}
}