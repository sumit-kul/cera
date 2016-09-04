package com.era.community.wiki.dao;

import com.era.community.base.CecBaseEntity;
import com.era.community.pers.dao.generated.UserEntity;

public class WikiEntryLike extends CecBaseEntity
{
	/**
	 * @column integer not null
	 */
	protected int WikiEntryId;

	/**
	 * @column integer not null
	 */
	protected int PosterId ;

	/*
	 * Injected references.
	 */
	protected WikiEntryLikeDao dao;

	public int getWikiEntryId()
	{
		return WikiEntryId;
	}

	public void setWikiEntryId(int wikiEntryId)
	{
		WikiEntryId = wikiEntryId;
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


	/**
	 * Update or insert this entity in the database.
	 */
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

	public final void setDao(WikiEntryLikeDao dao)
	{
		this.dao = dao;
	}

	public final WikiEntryLikeDao getDao()
	{
		return dao;
	}
}