package com.era.community.blog.dao;

import com.era.community.base.CecBaseEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 *
 * @entity name="BCLIK"
 * 
 *  @entity.index name="01" unique="no" columns="BlogEntryId"   
 *  
 *  @entity.index name="02" unique="no" columns="BlogEntryCommentId" 
 *  
 * @entity.foreignkey name="01" columns="BlogEntryId" target="BENT" ondelete="cascade"
 * 
 * @entity.foreignkey name="02" columns="BlogEntryCommentId" target="BCOM" ondelete="cascade"
 */
public class BlogEntryCommentLike extends CecBaseEntity
{

	/**
	 * @column integer not null
	 */
	protected int BlogEntryId;
	
	/**
	 * @column integer not null
	 */
	protected int BlogEntryCommentId;

	/**
	 * @column integer not null
	 */
	protected int PosterId ;

	/*
	 * Injected references.
	 */
	protected BlogEntryCommentLikeDao dao;

	public int getBlogEntryId()
	{
		return BlogEntryId;
	}

	public void setBlogEntryId(int blogEntryId)
	{
		BlogEntryId = blogEntryId;
	}
	
	public int getBlogEntryCommentId()
	{
		return BlogEntryCommentId;
	}

	public void setBlogEntryCommentId(int blogEntryCommentId)
	{
		BlogEntryCommentId = blogEntryCommentId;
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

	public final void setDao(BlogEntryCommentLikeDao dao)
	{
		this.dao = dao;
	}

	public final BlogEntryCommentLikeDao getDao()
	{
		return dao;
	}
}