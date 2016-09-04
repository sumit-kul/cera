package com.era.community.pers.dao;

import com.era.community.base.CecBaseEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * The User Expertise Thin Entity Link Table - links user rows to expertise rows
 *
 * @entity name="UEXL"
 * 
 * @entity.index name="01" unique="yes" columns="UserId, UserExpertiseId"  cluster="yes"
 * 
 * @entity.foreignkey name="01" columns="UserId" target="USER" ondelete="cascade"
 * @entity.foreignkey name="02" columns="UserExpertiseId" target="UEXP" ondelete="restrict"
 * 
 */
public class UserExpertiseLink extends CecBaseEntity
{

	/**
	 * @column int 
	 */
	private int UserId;

	/**
	 * @column int 
	 */    
	private int UserExpertiseId;

	protected UserExpertiseLinkDao dao;

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public int getUserExpertiseId()
	{
		return UserExpertiseId;
	}

	public void setUserExpertiseId(int userExpertiseId)
	{
		UserExpertiseId = userExpertiseId;
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

	public void setDao(UserExpertiseLinkDao dao)
	{
		this.dao = dao;
	}
}
