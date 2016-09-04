package com.era.community.events.dao; 

import java.util.List;

import com.era.community.pers.dao.User;

public class EventInviteeLinkDaoImpl extends com.era.community.events.dao.generated.EventInviteeLinkDaoBaseImpl implements EventInviteeLinkDao
{
	public EventInviteeLink getEventInviteeLinkForEventAndUser(int eventId, int userId) throws Exception
	{
		return (EventInviteeLink) getEntityWhere("EventId = ? and UserId = ?", eventId, userId);
	}
	
	public int countInviteesForEvent(int eventId) throws Exception
	{
		String query = "select count(EL.ID) from EventInviteeLink EL where EL.EventId = ?" ;

		return getSimpleJdbcTemplate().queryForInt(query, eventId); 
	}
	
	public List getInviteesForEvent(int eventId, int max) throws Exception
	{
		String query = "select U.* from User U, EventInviteeLink link " 
			+ " where U.ID = link.UserId and U.Inactive = 'N' and link.EventId = ? " ;

		if (max != 0) {
			query += " LIMIT " + max + " ";
		}
		return getBeanList(query, User.class, eventId);
	}
}