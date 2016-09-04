package com.era.community.doclib.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.doclib.dao.DocGroup;

public abstract class DocGroupDaoBaseImpl extends AbstractJdbcDaoSupport implements DocGroupDaoBase
{
	public DocGroup getDocGroupForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (DocGroup)getEntity(DocGroup.class, keys);
	}
	
	public void delete(DocGroup o) throws Exception
	{
		deleteEntity(o);
	}
	
	public void store(DocGroup o) throws Exception
	{
		storeEntity(o);
	}

	public DocGroup newDocGroup() throws Exception
	{
		return (DocGroup)newEntity(DocGroup.class);
	}
}