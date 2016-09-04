package com.era.community.config.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;


/**
 * @entity name="MMDE"
 *   
 * @entity.index name="01" unique="yes" columns="Name" 
 * 
 */
public class MailMessageDefinition extends CecAbstractEntity
{
	/**
	 * @column varchar(255) not null 
	 */
	protected String Name;

	/**
	 * @column varchar(255)  
	 */
	protected String Description;

	/**
	 * @column varchar(255)  
	 */
	protected String VariableNames;

	/**
	 * @column varchar(2000) not null with default
	 */
	protected String DefaultText = "";

	/*
	 * Injected references.
	 */
	protected MailMessageDefinitionDao dao;

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		return true;
	}  

	public final void setDao(MailMessageDefinitionDao dao)
	{
		this.dao = dao;
	}

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public void delete() throws Exception
	{
		dao.delete(this);
	}

	public final String getName()
	{
		return Name;
	}

	public final void setName(String name)
	{
		Name = name;
	}

	public final String getDescription()
	{
		return Description;
	}

	public final void setDescription(String description)
	{
		Description = description;
	}

	public final String getDefaultText()
	{
		return DefaultText;
	}

	public final void setDefaultText(String defaultText)
	{
		DefaultText = defaultText;
	}

	public final String getVariableNames()
	{
		return VariableNames;
	}

	public final void setVariableNames(String variableNames)
	{
		VariableNames = variableNames;
	}
}