package com.era.community.wiki.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.wiki.dao.WikiEntrySection;

public abstract class WikiEntrySectionDaoBaseImpl extends AbstractJdbcDaoSupport implements WikiEntrySectionDaoBase
{
	public WikiEntrySection getWikiEntrySectionForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (WikiEntrySection)getEntity(WikiEntrySection.class, keys);
	}

	public void deleteWikiEntrySectionForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(WikiEntrySection o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(WikiEntrySection o) throws Exception
	{
		storeEntity(o);
	}

	public WikiEntrySection newWikiEntrySection() throws Exception
	{
		return (WikiEntrySection)newEntity(WikiEntrySection.class);
	}
	
	public String getSectionBody(WikiEntrySection o) throws Exception
	{
		return (String)getColumn(o, "SectionBody", String.class);
	}
	
	public void setSectionBody(WikiEntrySection o, String data) throws Exception
	{
		setColumn(o, "SectionBody", data);
	}
}