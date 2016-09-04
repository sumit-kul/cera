package com.era.community.events.dao;

import com.era.community.base.CecBaseEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * 
 */
public class EventInviteeLink extends CecBaseEntity
{

	/**
	 * @column int 
	 */
	private int UserId;

	/**
	 * @column int 
	 */    
	private int EventId;
	
	/**
	 * @column int 
	 */    
	private int JoiningStatus;

	protected EventInviteeLinkDao dao;

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public int getEventId()
	{
		return EventId;
	}

	public void setEventId(int eventId)
	{
		EventId = eventId;
	}

	public boolean isReadAllowed(UserEntity user)
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user)
	{
		return true;        
	}

	public int getUserId()
	{
		return UserId;
	}

	public void setUserId(int userId)
	{
		UserId = userId;
	}

	public void setDao(EventInviteeLinkDao dao)
	{
		this.dao = dao;
	}

	public int getJoiningStatus() {
		return JoiningStatus;
	}

	public void setJoiningStatus(int joiningStatus) {
		JoiningStatus = joiningStatus;
	}
}