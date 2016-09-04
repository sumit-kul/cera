package com.era.community.media.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.media.dao.Album;

public abstract class AlbumDaoBaseImpl extends AbstractJdbcDaoSupport implements AlbumDaoBase
{
	public Album getAlbumForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (Album)getEntity(Album.class, keys);
	}
	
	public void delete(Album o) throws Exception
	{
		deleteEntity(o);
	}
	
	public void store(Album o) throws Exception
	{
		storeEntity(o);
	}

	public Album newAlbum() throws Exception
	{
		return (Album)newEntity(Album.class);
	}
}