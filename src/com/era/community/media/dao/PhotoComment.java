package com.era.community.media.dao;

import java.util.Date;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

public class PhotoComment extends CecAbstractEntity
{

	/**
	 * @column integer 
	 */
	protected int PhotoId;

	/**
	 * @column long varchar not null with default
	 */
	protected String Comment = "";

	/**
	 * @column timestamp not null with default
	 */
	protected Date DatePosted;

	/**
	 * @column integer not null
	 */
	protected int PosterId ;

	protected PhotoCommentDao dao;

	public String getComment()
	{
		return Comment;
	}

	public void setComment(String comment)
	{
		Comment = comment;
	}

	public Date getDatePosted()
	{
		return DatePosted;
	}

	public void setDatePosted(Date datePosted)
	{
		DatePosted = datePosted;
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
		if (getPosterId()==user.getId()) return true;
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

	public void setDao(PhotoCommentDao dao)
	{
		this.dao = dao;
	}

	public int getPhotoId() {
		return PhotoId;
	}

	public void setPhotoId(int photoId) {
		PhotoId = photoId;
	}
}