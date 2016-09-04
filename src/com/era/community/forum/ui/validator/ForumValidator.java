package com.era.community.forum.ui.validator; 

import support.community.framework.CommandBeanImpl;

public class ForumValidator extends com.era.community.forum.dao.generated.ForumValidator 
{
	public String validateName(Object value, CommandBeanImpl data) throws Exception
	{
		if (value.toString()!=null && value.toString().trim().length()> 150)
			return ("Name length cannot exceed 150 characters");

		return null;
	}     
}
