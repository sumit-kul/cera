package com.era.community.blog.dao;

import com.era.community.base.CecBaseEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 *
 * @entity name="BLIK"
 * 
 *  @entity.index name="01" unique="no" columns="BlogEntryId"   
 *  
 * @entity.foreignkey name="01" columns="BlogEntryId" target="BENT" ondelete="cascade"  
 */
public class BlogEntryLike extends CecBaseEntity
{

	/**
	 * @column integer not null
	 */
	protected int BlogEntryId;

	/**
	 * @column integer not null
	 */
	protected int PosterId ;

	/*
	 * Injected references.
	 */
	protected BlogEntryLikeDao dao;

	public int getBlogEntryId()
	{
		return BlogEntryId;
	}

	public void setBlogEntryId(int blogEntryId)
	{
		BlogEntryId = blogEntryId;
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

	 public final void setDao(BlogEntryLikeDao dao)
	 {
		 this.dao = dao;
	 }

	 public final BlogEntryLikeDao getDao()
	 {
		 return dao;
	 }
}