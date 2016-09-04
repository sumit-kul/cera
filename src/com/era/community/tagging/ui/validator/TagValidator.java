package com.era.community.tagging.ui.validator; 

import support.community.framework.CommandBeanImpl;

public class TagValidator extends com.era.community.tagging.dao.generated.TagValidator 
{
	public String validateParentType(Object value, CommandBeanImpl data) throws Exception
	{
		if (value.toString()!=null && value.toString().trim().length()> 60)
			return ("Parent type length cannot exceed 60 characters");

		return null;
	}

	public String validateTagText(Object value, CommandBeanImpl data) throws Exception
	{
		if (value.toString()!=null && value.toString().trim().length()> 150)
			return ("Tag text length cannot exceed 150 characters");

		return null;
	}
}
