package com.era.community.media.dao;

import com.era.community.base.CecBaseEntity;
import com.era.community.pers.dao.generated.UserEntity;

public class PhotoLike extends CecBaseEntity
{
	/**
	 * @column integer not null
	 */
	protected int PhotoId;
	
	/**
	 * @column integer not null
	 */
	protected int PosterId ;

	/*
	 * Injected references.
	 */
	protected PhotoLikeDao dao;

	public int getPhotoId()
	{
		return PhotoId;
	}

	public void setPhotoId(int photoId)
	{
		PhotoId = photoId;
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
		return true;
	}

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public void delete() throws Exception
	{
		dao.delete(this);
	} 

	public void setDao(PhotoLikeDao dao)
	{
		this.dao = dao;
	}
}