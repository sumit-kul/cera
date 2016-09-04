package com.era.community.config.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * @entity name="SYSP"
 *   
 * @entity.index name="01" unique="yes" columns="Name" include="Text"   
 * 
 */
public class SystemParameter extends CecAbstractEntity
{
	/**
	 * @column varchar(255) not null 
	 */
	protected String Name;

	/**
	 * @column varchar(255) 
	 */
	protected String Text ;

	/**
	 * @column long varchar 
	 */
	protected String LongText ;

	/**
	 * @column varchar(2000) not null with default
	 */
	protected String Description = "" ;

	protected SystemParameterDao dao;

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		return true;
	}    

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public void delete() throws Exception
	{
		dao.delete(this);
	}

	public final String getDescription()
	{
		return Description;
	}

	public final void setDescription(String description)
	{
		Description = description;
	}

	public final String getName()
	{
		return Name;
	}

	public final void setName(String name)
	{
		Name = name;
	}

	public final String getText()
	{
		return Text;
	}

	public final void setText(String text)
	{
		Text = text;
	}

	public final void setDao(SystemParameterDao dao)
	{
		this.dao = dao;
	}

	public final String getLongText()
	{
		return LongText;
	}

	public final void setLongText(String longText)
	{
		LongText = longText;
	}



}
