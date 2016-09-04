package com.era.community.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class CecBaseEntity 
{
	protected final Log logger = LogFactory.getLog(getClass());
	protected int Id;
	protected boolean community = false;

	public final int getId()
	{
		return Id;
	}

	public void setPrimaryKey(int id) 
	{
		Id = id;
	}

	public Object[] getPrimaryKeys() 
	{
		return new Object[] { new Integer(Id) };
	}

	protected void setId(int id)
	{
		this.Id = id;
	}

	public boolean isCommunity() {
		return community;
	}

	public void setCommunity(boolean community) {
		this.community = community;
	}
}
