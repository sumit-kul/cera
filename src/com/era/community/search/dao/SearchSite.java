package com.era.community.search.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * @entity name="SSIT"
 *   
 * @entity.index name="01" unique="yes" columns="CommunityId,HostName" cluster="yes"  
 * @entity.index name="02" unique="yes" columns="CommunityId,Name"   
 * 
 * @entity.foreignkey name="01" columns="CommunityId" target="COMM" ondelete="cascade"  
 */
public class SearchSite extends CecAbstractEntity
{
	/**
	 * @column integer not null
	 */
	protected int CommunityId;

	/**
	 * @column varchar(60) not null
	 */
	protected String Name;

	/**
	 * @column varchar(255) not null
	 */
	protected String Hostname;

	/**
	 * @column varchar(1000) not null
	 */
	protected String Description;

	/**
	 * @column integer
	 */
	protected int Sequence;

	protected SearchSiteDao dao;

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		if (user==null) return false;
		return true;
	}  

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




	public void setDao(SearchSiteDao dao)
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

	public final String getDescription()
	{
		return Description;
	}

	public final void setDescription(String description)
	{
		Description = description;
	}

	public final String getHostname()
	{
		return Hostname;
	}

	public final void setHostname(String hostname)
	{
		Hostname = hostname;
	}

	public final String getName()
	{
		return Name;
	}

	public final void setName(String name)
	{
		Name = name;
	}

	public int getSequence()
	{
		return Sequence;
	}

	public void setSequence(int sequence)
	{
		Sequence = sequence;
	}



}
