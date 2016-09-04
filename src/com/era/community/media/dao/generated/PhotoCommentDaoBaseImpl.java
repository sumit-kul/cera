package com.era.community.media.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.media.dao.PhotoComment;

public abstract class PhotoCommentDaoBaseImpl extends AbstractJdbcDaoSupport implements PhotoCommentDaoBase
{
	public PhotoComment getPhotoCommentForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (PhotoComment)getEntity(PhotoComment.class, keys);
	}

	public void deletePhotoCommentForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(PhotoComment o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(PhotoComment o) throws Exception
	{
		storeEntity(o);
	}

	public PhotoComment newPhotoComment() throws Exception
	{
		return (PhotoComment)newEntity(PhotoComment.class);
	}
}