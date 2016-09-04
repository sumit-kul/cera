package com.era.community.events.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.events.dao.EventLike;

public abstract class EventLikeDaoBaseImpl extends AbstractJdbcDaoSupport implements EventLikeDaoBase
{
	public EventLike getEventLikeForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (EventLike)getEntity(EventLike.class, keys);
	}

	public void deleteEventLikeForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(EventLike o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(EventLike o) throws Exception
	{
		storeEntity(o);
	}

	public EventLike newEventLike() throws Exception
	{
		return (EventLike)newEntity(EventLike.class);
	}
}