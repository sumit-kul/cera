package com.era.community.wiki.dao;

import java.util.Date;
import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.base.CecAbstractEntity;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * 
 * @entity name="WIKI" 
 *
 * @entity.index name="01" unique="no" columns="CommunityId"   
 * 
 * @entity.foreignkey name="01" columns="CommunityId" target="COMM" ondelete="cascade"  
 */
public class Wiki extends CecAbstractEntity
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

	protected WikiDao dao;     
	protected CommunityFinder communityFinder;

	public List getItemsSince(Date date) throws Exception
	{
		return dao.getItemsSince(this, date);
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

	public final void setDao(WikiDao dao)
	{
		this.dao = dao;
	}

	public boolean isReadAllowed(UserEntity user)
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user)
	{
		if (user==null) return false;
		return true;  
	}

	public Date getLatestPostDate() throws Exception
	{
		return dao.getLatestPostDate(this);
	}

	public Community getCommunity() throws Exception
	{
		return communityFinder.getCommunityForId(CommunityId);
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

	public QueryScroller listEntriesByTitle() throws Exception
	{
		return dao.listEntriesByTitle(this);
	}

	public QueryScroller listEntriesByUpdateDate() throws Exception
	{
		return dao.listEntriesByUpdateDate(this);
	}

	public QueryScroller listEntriesForUser(User user) throws Exception
	{
		return dao.listEntriesForUser(this, user);
	}

	public boolean entryExists(String title, Integer entryId) throws Exception
	{
		return dao.entryExists(this, title, entryId);
	}

	public final void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}
}