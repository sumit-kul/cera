package com.era.community.events.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.events.dao.EventCalendar;

public abstract class EventCalendarDaoBaseImpl extends AbstractJdbcDaoSupport implements EventCalendarDaoBase
{
	/*
	 *
	 */
	public EventCalendar getEventCalendarForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (EventCalendar)getEntity(EventCalendar.class, keys);
	}

	/*
	 *
	 */
	public void deleteEventCalendarForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(EventCalendar o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(EventCalendar o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public EventCalendar newEventCalendar() throws Exception
	{
		return (EventCalendar)newEntity(EventCalendar.class);
	}

}
