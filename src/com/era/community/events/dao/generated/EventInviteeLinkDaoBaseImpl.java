package com.era.community.events.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.events.dao.EventInviteeLink;
import com.era.community.pers.dao.UserExpertiseLink;

public abstract class EventInviteeLinkDaoBaseImpl extends AbstractJdbcDaoSupport implements EventInviteeLinkDaoBase
{
	/*
	 *
	 */
	public EventInviteeLink getEventInviteeLinkForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (EventInviteeLink)getEntity(EventInviteeLink.class, keys);
	}

	/*
	 *
	 */
	public void deleteEventInviteeLinkForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(EventInviteeLink o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(EventInviteeLink o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public EventInviteeLink newEventInviteeLink() throws Exception
	{
		return (EventInviteeLink)newEntity(EventInviteeLink.class);
	}

}
