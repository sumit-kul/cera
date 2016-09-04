package com.era.community.upload.dao;

import org.springframework.web.multipart.MultipartFile;

import support.community.database.BlobData;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * @entity name="UPLD"
 * @entity.blob name="Data"
 *   
 * @entity.index name="01" unique="no" columns="CreatorId"   
 * 
 * @entity.foreignkey name="01" columns="CreatorId" target="USER" ondelete="restrict"  
 */
public class Upload extends CecAbstractEntity
{
	/**
	 * @column integer not null
	 */
	protected int CreatorId;

	/**
	 * @column varchar(120) not null with default
	 */
	protected String Title ;

	/**
	 * @column varchar(500) not null with default
	 */
	protected String Description ;

	/*
	 * Injected references.
	 */
	protected UploadDao dao;
	protected UserFinder userFinder;


	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		return true;
	}    

	public BlobData readData() throws Exception
	{
		return dao.readData(this);
	}

	public void storeData(MultipartFile file) throws Exception
	{
		dao.storeData(this, file);

	}

	public String getDataContentType() throws Exception
	{
		return dao.getDataContentType(this);
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

	public final void setDao(UploadDao dao)
	{
		this.dao = dao;
	} 
	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public final int getCreatorId()
	{
		return CreatorId;
	}

	public final void setCreatorId(int creatorId)
	{
		CreatorId = creatorId;
	}

	public final String getDescription()
	{
		return Description;
	}

	public final void setDescription(String description)
	{
		Description = description;
	}

	public final String getTitle()
	{
		return Title;
	}

	public final void setTitle(String title)
	{
		Title = title;
	}


}
