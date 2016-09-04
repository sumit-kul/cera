package com.era.community.media.dao;

import com.era.community.base.CecBaseEntity;
import com.era.community.pers.dao.generated.UserEntity;

public class PhotoCommentLike extends CecBaseEntity
{
	/**
	 * @column integer not null
	 */
	protected int PhotoId;

	/**
	 * @column integer not null
	 */
	protected int PhotoCommentId;

	/**
	 * @column integer not null
	 */
	protected int PosterId ;

	/*
	 * Injected references.
	 */
	protected PhotoCommentLikeDao dao;

	public int getPhotoId()
	{
		return PhotoId;
	}

	public void setPhotoId(int photoId)
	{
		PhotoId = photoId;
	}

	public int getPhotoCommentId()
	{
		return PhotoCommentId;
	}

	public void setPhotoCommentId(int photoCommentId)
	{
		PhotoCommentId = photoCommentId;
	}

	public int getPosterId()
	{
		return PosterId;
	}

	public void setPosterId(int posterId)
	{
		PosterId = posterId;
	}

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		if (user==null) return false;
		return false;
	}

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public void delete() throws Exception
	{
		dao.delete(this);
	} 

	public final void setDao(PhotoCommentLikeDao dao)
	{
		this.dao = dao;
	}

	public final PhotoCommentLikeDao getDao()
	{
		return dao;
	}
}