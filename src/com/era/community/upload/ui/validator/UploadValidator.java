package com.era.community.upload.ui.validator; 

import support.community.framework.CommandBeanImpl;

public class UploadValidator extends com.era.community.upload.dao.generated.UploadValidator 
{
	public String validateTitle(Object value, CommandBeanImpl data) throws Exception
	{
		if (value.toString()!=null && value.toString().trim().length()> 120)
			return ("Title length cannot exceed 120 characters");

		return null;
	}

	public String validateDescription(Object value, CommandBeanImpl data) throws Exception
	{
		if (value.toString()!=null && value.toString().trim().length()> 500)
			return ("Description length cannot exceed 500 characters");

		return null;
	}
}
