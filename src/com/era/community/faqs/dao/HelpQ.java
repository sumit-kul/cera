package com.era.community.faqs.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 *
 */
public class HelpQ extends CecAbstractEntity
{

	/**
	 * @column varchar(250) not null with default
	 */
	protected String title;
	
	/**
	 * @column integer not null with default
	 */
	protected int ParentId = 0;

	/**
	 * @column integer not null with default
	 */
	protected int Sequence = 0;

	/**
	 * @column char(1) not null with default
	 */
	protected boolean Inactive = false;

	/*
	 * Injected references.
	 */
	protected HelpQDao dao;     

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

	public final void setDao(HelpQDao dao)
	{
		this.dao = dao;
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

	public final boolean isInactive()
	{
		return Inactive;
	}

	public final void setInactive(boolean inactive)
	{
		Inactive = inactive;
	}

	public final int getSequence()
	{
		return Sequence;
	}

	public final void setSequence(int sequence)
	{
		Sequence = sequence;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getParentId() {
		return ParentId;
	}

	public void setParentId(int parentId) {
		ParentId = parentId;
	}
}