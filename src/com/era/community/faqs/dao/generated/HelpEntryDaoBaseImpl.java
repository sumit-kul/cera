package com.era.community.faqs.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.faqs.dao.HelpEntry;

public abstract class HelpEntryDaoBaseImpl extends AbstractJdbcDaoSupport implements HelpEntryDaoBase
{
	public HelpEntry getHelpEntryForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (HelpEntry)getEntity(HelpEntry.class, keys);
	}

	public void deleteHelpEntryForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(HelpEntry o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(HelpEntry o) throws Exception
	{
		storeEntity(o);
	}

	public HelpEntry newHelpEntry() throws Exception
	{
		return (HelpEntry)newEntity(HelpEntry.class);
	}
}