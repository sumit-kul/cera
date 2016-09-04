package com.era.community.media.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.media.dao.PhotoLike;

public abstract class PhotoLikeDaoBaseImpl extends AbstractJdbcDaoSupport implements PhotoLikeDaoBase
{
	public PhotoLike getPhotoLikeForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (PhotoLike)getEntity(PhotoLike.class, keys);
	}

	public void deletePhotoLikeForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(PhotoLike o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(PhotoLike o) throws Exception
	{
		storeEntity(o);
	}

	public PhotoLike newPhotoLike() throws Exception
	{
		return (PhotoLike)newEntity(PhotoLike.class);
	}
}