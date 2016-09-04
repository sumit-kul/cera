package com.era.community.pers.dao.generated; 

import org.springframework.validation.Errors;

import support.community.framework.CommandBean;

public abstract class ContactValidator extends support.community.framework.CommandValidator
{
	public void validateCommand(CommandBean data, Errors errors) {}
		  public String validateOwningUserId(Object value, CommandBean cmd) throws Exception { return null; }
		  public String validateContactUserId(Object value, CommandBean cmd) throws Exception { return null; }
		  public String validateId(Object value, CommandBean cmd) throws Exception { return null; }

}

