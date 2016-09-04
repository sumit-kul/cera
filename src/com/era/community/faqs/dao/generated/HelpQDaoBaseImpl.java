package com.era.community.faqs.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.faqs.dao.HelpQ;

public abstract class HelpQDaoBaseImpl extends AbstractJdbcDaoSupport implements HelpQDaoBase
{
	public HelpQ getHelpQForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (HelpQ)getEntity(HelpQ.class, keys);
	}

	public void deleteHelpQForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(HelpQ o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(HelpQ o) throws Exception
	{
		storeEntity(o);
	}

	public HelpQ newHelpQ() throws Exception
	{
		return (HelpQ)newEntity(HelpQ.class);
	}
}