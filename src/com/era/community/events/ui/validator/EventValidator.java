package com.era.community.events.ui.validator; 

import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

public class EventValidator extends com.era.community.events.dao.generated.EventValidator 
{
	public String validateName(Object value, CommandBean cmd) throws Exception {
		if (value.toString().equals("")) {
			return "You must enter a name for this event";
		}
		if (value.toString().length() > 150) {
			return "The event name can be a maximum of 150 characters long";
		}
		return null;
	}

	public String validateStartDate(Object value, CommandBean cmd) throws Exception {
		return null;
	}
	
	public String validateEventCategory(Object value, CommandBean cmd) throws Exception {
		if (value == null || value.toString().equals("") || (Integer)value == 0) {
			return "Category must be selected for this event";
		}
		return null;
	}
	
	public String validateLocation(Object value, CommandBean cmd) throws Exception
	{
		if (value == null) return null;

		if (value.toString().length() > 150) {
			return "The event location can be a maximum of 200 characters long";
		}
		return null;
	}

	public String validateDescription(Object value, CommandBeanImpl data) throws Exception
	{
		if (value == null) return null;

		if (value.toString()!=null && value.toString().trim().length()> 33000)
			return ("Description length cannot exceed 33000 characters");

		return null;
	}

	public String validateContactEmail(Object value, CommandBean data) throws Exception
	{      
		if (value == null) return null;

		if (value.toString().length() > 60) {
			return "The email address can be a maximum of 60 characters long";
		}

		if (!value.toString().equals("") && !emailValidator.isValid(value.toString())) {
			return "Email address is invalid";
		}       
		return null;
	}

	public String validateContactTel(Object value, CommandBean data) throws Exception
	{
		if (value.toString().length() > 30) {
			return "The contact telephone number can be a maximum of 30 characters long";
		}
		return null;
	}

	public String validateContactName(Object value, CommandBean data) throws Exception
	{
		if (value == null) return null;

		if (value.toString().length() > 60) {
			return "The contact can be a maximum of 60 characters long";
		}
		return null;
	}       

}
