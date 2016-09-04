package com.era.community.media.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.media.dao.PhotoCommentLike;

public abstract class PhotoCommentLikeDaoBaseImpl extends AbstractJdbcDaoSupport implements PhotoCommentLikeDaoBase
{
	public PhotoCommentLike getPhotoCommentLikeForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (PhotoCommentLike)getEntity(PhotoCommentLike.class, keys);
	}

	public void deletePhotoCommentLikeForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(PhotoCommentLike o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(PhotoCommentLike o) throws Exception
	{
		storeEntity(o);
	}

	public PhotoCommentLike newPhotoCommentLike() throws Exception
	{
		return (PhotoCommentLike)newEntity(PhotoCommentLike.class);
	}
}