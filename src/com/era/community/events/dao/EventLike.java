package com.era.community.events.dao;

import com.era.community.base.CecBaseEntity;
import com.era.community.pers.dao.generated.UserEntity;

public class EventLike extends CecBaseEntity
{

	/**
	 * @column integer not null
	 */
	protected int EventId;

	/**
	 * @column integer not null
	 */
	protected int PosterId ;

	/*
	 * Injected references.
	 */
	protected EventLikeDao dao;

	public int getEventId()
	{
		return EventId;
	}

	public void setEventId(int eventId)
	{
		EventId = eventId;
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

	 public final void setDao(EventLikeDao dao)
	 {
		 this.dao = dao;
	 }

	 public final EventLikeDao getDao()
	 {
		 return dao;
	 }
}