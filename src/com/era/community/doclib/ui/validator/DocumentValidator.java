package com.era.community.doclib.ui.validator; 

import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

public class DocumentValidator extends com.era.community.doclib.dao.generated.DocumentValidator 
{
	public String validateDatePosted(Object value, CommandBean cmd) throws Exception
	{
		return super.validateDatePosted(value, cmd);
	}
	public String validateDateRevised(Object value, CommandBean cmd) throws Exception
	{
		return super.validateDateRevised(value, cmd);
	}
	public String validateDescription(Object value, CommandBean cmd) throws Exception
	{
		return super.validateDescription(value, cmd);
	}
	public String validatePosterId(Object value, CommandBean cmd) throws Exception
	{
		return super.validatePosterId(value, cmd);
	}
	public String validateTitle(Object value, CommandBean cmd) throws Exception
	{
		if (value.toString()!=null && value.toString().trim().length()> 150)
			return ("Title length cannot exceed 150 characters");

		return isNullOrWhitespace(value) 
		? "Please enter a title for the document" 
				: super.validateTitle(value, cmd);
	}

	public String validateFileName(Object value, CommandBeanImpl data) throws Exception
	{
		if (value.toString()!=null && value.toString().trim().length()> 150)
			return ("File name length cannot exceed 150 characters");

		return null;
	}

	public String validateDescription(Object value, CommandBeanImpl data) throws Exception
	{
		if (value.toString()!=null && value.toString().trim().length()> 32700)
			return ("Description length cannot exceed 32700 characters");

		return null;
	}
}
