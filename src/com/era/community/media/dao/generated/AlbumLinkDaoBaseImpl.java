package com.era.community.media.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.media.dao.AlbumLink;

public abstract class AlbumLinkDaoBaseImpl extends AbstractJdbcDaoSupport implements AlbumLinkDaoBase
{
	public AlbumLink getAlbumLinkForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (AlbumLink)getEntity(AlbumLink.class, keys);
	}
	
	public void delete(AlbumLink o) throws Exception
	{
		deleteEntity(o);
	}
	public void store(AlbumLink o) throws Exception
	{
		storeEntity(o);
	}

	public AlbumLink newAlbumLink() throws Exception
	{
		return (AlbumLink)newEntity(AlbumLink.class);
	}
}