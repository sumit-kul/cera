package com.era.community.search.ui.validator; 

import support.community.framework.CommandBean;

public class SearchSiteValidator extends com.era.community.search.dao.generated.SearchSiteValidator 
{
	@Override
	public String validateDescription(Object value, CommandBean cmd) throws Exception
	{
		if (isNullOrWhitespace(value)) return null;
		if (((String)value).length() > 300) return "Value is too long";
		return null;
	}

	@Override
	public String validateHostname(Object value, CommandBean cmd) throws Exception
	{
		if (isNullOrWhitespace(value)) return "Please enter a value";

		if (value.toString()!=null && value.toString().trim().length()> 255)
			return ("Hostname length cannot exceed 255 characters");

		// String host = (String)value;
		// if (!host.matches("^([\\-\\w]+\\.)+[\\-\\w]+$")) return "Invalid value. Enter a value of form: news.bbc.co.uk";

		return null; 
	}

	@Override
	public String validateName(Object value, CommandBean cmd) throws Exception
	{
		if (isNullOrWhitespace(value)) return "Please enter a value";

		if (value.toString()!=null && value.toString().trim().length()> 60)
			return ("Name length cannot exceed 60 characters");

		return null;
	}

}
