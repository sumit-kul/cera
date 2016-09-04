package com.era.community.faqs.ui.validator; 

import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

public class FaqValidator extends com.era.community.faqs.dao.generated.FaqValidator 
{
	public String validateSubject(Object value, CommandBean data) throws Exception
	{
		if (value.toString()!=null && value.toString().trim().length()> 250)
			return ("Subject length cannot exceed 250 characters");

		return isNullOrWhitespace(value) 
		? "Please enter a question" 
				: super.validateSubject(value, data);
	}

	public String validateBody(Object value, CommandBeanImpl data) throws Exception
	{
		if (value.toString()!=null && value.toString().trim().length()> 32700)
			return ("Body length cannot exceed 32700 characters");

		return null;
	}
}
