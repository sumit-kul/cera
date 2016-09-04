package com.era.community.wiki.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.wiki.dao.WikiEntry;

public abstract class WikiEntryDaoBaseImpl extends AbstractJdbcDaoSupport implements WikiEntryDaoBase
{
	public WikiEntry getWikiEntryForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (WikiEntry)getEntity(WikiEntry.class, keys);
	}

	public void deleteWikiEntryForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(WikiEntry o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(WikiEntry o) throws Exception
	{
		storeEntity(o);
	}

	public void store(WikiEntry o, boolean forCountOnly) throws Exception
	{
		storeEntity(o, forCountOnly);
	}

	public String getBody(WikiEntry o) throws Exception
	{
		return (String)getColumn(o, "Body", String.class);
	}
	
	public void setBody(WikiEntry o, String data) throws Exception
	{
		setColumn(o, "Body", data);
	}

	public WikiEntry newWikiEntry() throws Exception
	{
		return (WikiEntry)newEntity(WikiEntry.class);
	}
}