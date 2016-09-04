package com.era.community.communities.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * @entity name="PVST"
 *   
 * @entity.index name="01" unique="no" columns="ProfileUserId"   
 * @entity.index name="02" unique="no" columns="VisitingUserId"   
 * 
 * @entity.foreignkey name="01" columns="ProfileUserId" target="USER" ondelete="cascade"  
 * @entity.foreignkey name="02" columns="VisitingUserId" target="USER" ondelete="cascade"  
 */
public class CommunityVisit extends CecAbstractEntity
{
	public static final int STATUS_UNSEEN = 0;
	public static final int STATUS_SEEN = 1;

	/**
	 * @column integer not null
	 */
	protected int CommunityId;

	/**
	 * @column integer not null
	 */
	protected int VisitingUserId;

	/*
	 * Injected references.
	 */
	protected CommunityVisitDao dao;
	protected UserFinder userFinder;

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
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

	public void setDao(CommunityVisitDao dao)
	{
		this.dao = dao;
	}

	public int getCommunityId() {
		return CommunityId;
	}

	public void setCommunityId(int communityId) {
		CommunityId = communityId;
	}

	public int getVisitingUserId() {
		return VisitingUserId;
	}

	public void setVisitingUserId(int visitingUserId) {
		VisitingUserId = visitingUserId;
	}
}