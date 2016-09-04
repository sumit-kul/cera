package com.era.community.wiki.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.wiki.dao.WikiEntryReference;

public abstract class WikiEntryReferenceDaoBaseImpl extends AbstractJdbcDaoSupport implements WikiEntryReferenceDaoBase
{
	public WikiEntryReference getWikiEntryReferenceForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (WikiEntryReference)getEntity(WikiEntryReference.class, keys);
	}

	public void deleteWikiEntryReferenceForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(WikiEntryReference o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(WikiEntryReference o) throws Exception
	{
		storeEntity(o);
	}

	public WikiEntryReference newWikiEntryReference() throws Exception
	{
		return (WikiEntryReference)newEntity(WikiEntryReference.class);
	}
}