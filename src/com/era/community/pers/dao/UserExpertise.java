package com.era.community.pers.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 *@entity name="UEXP" 
 */
public class UserExpertise extends CecAbstractEntity
{
	/**
	 * @column varchar(50) not null with default
	 */
	private String Name;

	/*
	 * Injected dao references.
	 */
	protected UserExpertiseDao dao;

	/**
	 * Update or insert the object in the database.
	 * 
	 * @throws Exception
	 */
	public void update() throws Exception
	{
		dao.store(this); 
	}

	public void delete() throws Exception
	{
		dao.delete(this);
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

	public String getName()
	{
		return Name;
	}

	public void setName(String name)
	{
		Name = name;
	}

	public void setDao(UserExpertiseDao dao)
	{
		this.dao = dao;
	}
}