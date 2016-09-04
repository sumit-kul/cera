package com.era.community.media.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.media.dao.PhotoGroup;

public abstract class PhotoGroupDaoBaseImpl extends AbstractJdbcDaoSupport implements PhotoGroupDaoBase
{
	public PhotoGroup getPhotoGroupForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (PhotoGroup)getEntity(PhotoGroup.class, keys);
	}
	
	public void delete(PhotoGroup o) throws Exception
	{
		deleteEntity(o);
	}
	
	public void store(PhotoGroup o) throws Exception
	{
		storeEntity(o);
	}

	public PhotoGroup newPhotoGroup() throws Exception
	{
		return (PhotoGroup)newEntity(PhotoGroup.class);
	}
}