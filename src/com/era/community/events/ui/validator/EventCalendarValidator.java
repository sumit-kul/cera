package com.era.community.events.ui.validator; 

import support.community.framework.CommandBeanImpl;

public class EventCalendarValidator extends com.era.community.events.dao.generated.EventCalendarValidator 
{
	public String validateName(Object value, CommandBeanImpl data) throws Exception
	{
		if (value.toString()!=null && value.toString().trim().length()> 150)
			return ("Name length cannot exceed 150 characters");

		return null;
	}
}
