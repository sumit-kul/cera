package com.era.community.forum.dao;

import com.era.community.base.CecBaseEntity;
import com.era.community.pers.dao.generated.UserEntity;

public class ForumItemLike extends CecBaseEntity
{
	/**
	 * @column integer not null
	 */
	protected int ForumItemId;

	/**
	 * @column integer not null
	 */
	protected int PosterId ;

	/*
	 * Injected references.
	 */
	protected ForumItemLikeDao dao;

	public int getForumItemId()
	{
		return ForumItemId;
	}

	public void setForumItemId(int forumItemId)
	{
		ForumItemId = forumItemId;
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

	/** 
	 *  Delete this entity from the database.
	 */
	public void delete() throws Exception
	{
		dao.delete(this);
	} 

	public final void setDao(ForumItemLikeDao dao)
	{
		this.dao = dao;
	}

	public final ForumItemLikeDao getDao()
	{
		return dao;
	}
}