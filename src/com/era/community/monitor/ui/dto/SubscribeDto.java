package com.era.community.monitor.ui.dto;

import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

public  class SubscribeDto extends CommandBeanImpl implements CommandBean
{
	private int Id;
	private int Frequency;
	private int subsId;
	private int themeId;

	public int getSubsId()
	{
		return subsId;
	}

	public void setSubsId(int subsId)
	{
		this.subsId = subsId;
	}

	public int getFrequency()
	{
		return Frequency;
	}

	public void setFrequency(int frequency)
	{
		Frequency = frequency;
	}

	public final int getId()
	{
		return Id;
	}

	public final void setId(int id)
	{
		Id = id;
	}

	public final int getThemeId()
	{
		return themeId;
	}

	public final void setThemeId(int themeId)
	{
		this.themeId = themeId;
	}
}