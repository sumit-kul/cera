package com.era.community.doclib.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.doclib.dao.Folder;

public abstract class FolderDaoBaseImpl extends AbstractJdbcDaoSupport implements FolderDaoBase
{
	public Folder getFolderForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (Folder)getEntity(Folder.class, keys);
	}
	
	public void delete(Folder o) throws Exception
	{
		deleteEntity(o);
	}
	
	public void store(Folder o) throws Exception
	{
		storeEntity(o);
	}

	public Folder newFolder() throws Exception
	{
		return (Folder)newEntity(Folder.class);
	}
}