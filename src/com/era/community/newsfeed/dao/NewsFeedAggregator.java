package com.era.community.newsfeed.dao;

import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;


/**
 * 
 * @entity name="NWFA" 
 *
 * @entity.index name="01" unique="no" columns="CommunityId"   
 * 
 * @entity.foreignkey name="01" columns="CommunityId" target="COMM" ondelete="cascade"  
 */
public class NewsFeedAggregator extends CecAbstractEntity
{
	/**
	 * @column integer not null
	 */
	private int CommunityId;

	/**
	 * @column varchar(150) not null with default
	 */
	protected String Name;

	/**
	 * @column char(1) not null with default
	 */
	protected boolean Inactive = false;

	/*
	 * Injected dao references.
	 */
	protected NewsFeedAggregatorDao dao;     


	public QueryScroller listFeedsByName() throws Exception
	{
		return dao.listFeedsByName(this);
	}

	public List getFeeds() throws Exception
	{
		return dao.getFeeds(this);
	}



	public QueryScroller listEntriesByDate() throws Exception
	{
		return dao.listEntriesByDate(this);
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

	public final void setDao(NewsFeedAggregatorDao dao)
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

	public boolean isReadAllowed(UserEntity user)
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user)
	{
		if (user==null) return false;
		return false;  
	}
}