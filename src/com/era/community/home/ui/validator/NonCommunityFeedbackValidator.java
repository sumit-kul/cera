package com.era.community.home.ui.validator; 

import support.community.framework.CommandBeanImpl;

public class NonCommunityFeedbackValidator extends com.era.community.home.dao.generated.NonCommunityFeedbackValidator 
{
	public String validateName(Object value, CommandBeanImpl data) throws Exception
	{
		if (value.toString()!=null && value.toString().trim().length()> 80)
			return ("Name length cannot exceed 80 characters");

		return null;
	}

	public String validateEmailAddress(Object value, CommandBeanImpl data) throws Exception
	{
		if (value.toString()!=null && value.toString().trim().length()> 120)
			return ("Email address length cannot exceed 120 characters");

		return null;
	}

	public String validateBody(Object value, CommandBeanImpl data) throws Exception
	{
		if (value.toString()!=null && value.toString().trim().length()> 32700)
			return ("Body length cannot exceed 32700 characters");

		return null;
	}
}
