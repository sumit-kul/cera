package com.era.community.wiki.dao;

import com.era.community.base.CecBaseEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * @entity name="WCLIK"
 */
public class WikiEntryCommentLike extends CecBaseEntity
{
	/**
	 * @column integer not null
	 */
	protected int WikiEntryId;
	
	/**
	 * @column integer not null
	 */
	protected int WikiEntryCommentId;

	/**
	 * @column integer not null
	 */
	protected int PosterId ;

	/*
	 * Injected references.
	 */
	protected WikiEntryCommentLikeDao dao;

	public int getWikiEntryId()
	{
		return WikiEntryId;
	}

	public void setWikiEntryId(int wikiEntryId)
	{
		WikiEntryId = wikiEntryId;
	}
	
	public int getWikiEntryCommentId()
	{
		return WikiEntryCommentId;
	}

	public void setWikiEntryCommentId(int wikiEntryCommentId)
	{
		WikiEntryCommentId = wikiEntryCommentId;
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

	public final void setDao(WikiEntryCommentLikeDao dao)
	{
		this.dao = dao;
	}

	public final WikiEntryCommentLikeDao getDao()
	{
		return dao;
	}
}