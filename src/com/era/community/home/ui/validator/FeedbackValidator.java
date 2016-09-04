package com.era.community.home.ui.validator; 

import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

public class FeedbackValidator extends com.era.community.communities.dao.generated.FeedbackValidator 
{
	public String validateBody(Object value, CommandBean cmd) throws Exception {
		if (isNullOrWhitespace(value)) return "Please enter your feedback";
		if (value.toString()!=null && value.toString().trim().length()> 32700)
			return ("Body length cannot exceed 32700 characters");
		return null;
	}

	public String validateEmail(Object value, CommandBeanImpl cmd) throws Exception
	{
		if (isNullOrWhitespace(value)) {
			return "Please enter your email";
		}
		if (!emailValidator.isValid(value.toString())) return "Email address is invalid";
		return null;
	}

	public String validateName(Object value, CommandBean cmd) throws Exception {
		if (isNullOrWhitespace(value)) {
			return "Please enter your name";
		}

		return null;
	}

	public String validateSubject(Object value, CommandBeanImpl data) throws Exception
	{
		if (value.toString()!=null && value.toString().trim().length()> 250)
			return ("Subject length cannot exceed 250 characters");

		return null;
	}

}
