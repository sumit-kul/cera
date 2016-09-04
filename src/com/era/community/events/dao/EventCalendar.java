package com.era.community.events.dao;

import java.util.Date;
import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.base.CecAbstractEntity;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * 
 * @entity name="EVCL" 
 *
 * @entity.index name="01" unique="no" columns="CommunityId"   
 * 
 * @entity.foreignkey name="01" columns="CommunityId" target="COMM" ondelete="cascade"  
 */
public class EventCalendar extends CecAbstractEntity
{
	/**
	 * @column integer not null
	 */
	protected int CommunityId;

	/**
	 * @column varchar(150) not null with default
	 */
	protected String Name;

	/**
	 * @column char(1) not null with default
	 */
	protected boolean Inactive = false;

	/*
	 * Injected references.
	 */
	protected EventCalendarDao dao;     
	protected CommunityFinder communityFinder;

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

	public final void setDao(EventCalendarDao dao)
	{
		this.dao = dao;
	}   

	public final int getCommunityId()
	{
		return CommunityId;
	}

	public final void setCommunityId(int communityId)
	{
		CommunityId = communityId;
	}

	public final boolean isInactive()
	{
		return Inactive;
	}

	public final void setInactive(boolean inactive)
	{
		Inactive = inactive;
	}

	public final String getName()
	{
		return Name;
	}

	public final void setName(String name)
	{
		Name = name;
	}

	public Community getCommunity() throws Exception
	{
		return communityFinder.getCommunityForId(getCommunityId());
	}

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		if (!getCommunity().isPrivate()) return true;
		if (user==null) return false;
		return getCommunity().isMember(user.getId());
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		if (user==null) return false;
		return getCommunity().isMember(user.getId());
	}

	public Date getLatestPostDate() throws Exception
	{
		return dao.getLatestPostDate(this);
	}

	public QueryScroller listFutureEvents(int currentUserId) throws Exception {
		return dao.listFutureEvents(this, currentUserId);
	} 

	public QueryScroller listEventsForUserId(int userId) throws Exception {
		return dao.listEventsForUserId(this, userId);
	}

	public QueryScroller listPastEvents(int currentUserId) throws Exception {
		return dao.listPastEvents(this, currentUserId);
	}

	public List getItemsSince(Date date) throws Exception
	{
		return dao.getItemsSince(this, date);
	}

	public final void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}
}