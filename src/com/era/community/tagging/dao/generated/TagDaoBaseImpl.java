package com.era.community.tagging.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.tagging.dao.Tag;

public abstract class TagDaoBaseImpl extends AbstractJdbcDaoSupport implements TagDaoBase
{
	/*
	 *
	 */
	public Tag getTagForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (Tag)getEntity(Tag.class, keys);
	}

	/*
	 *
	 */
	public void deleteTagForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(Tag o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(Tag o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public Tag newTag() throws Exception
	{
		return (Tag)newEntity(Tag.class);
	}

}
