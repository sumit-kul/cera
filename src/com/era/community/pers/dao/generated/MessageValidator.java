package com.era.community.pers.dao.generated; 

import org.springframework.validation.Errors;

import support.community.framework.CommandBean;

public abstract class MessageValidator extends support.community.framework.CommandValidator
{
	public void validateCommand(CommandBean data, Errors errors) {}
		  public String validateToUserId(Object value, CommandBean cmd) throws Exception { return null; }
		  public String validateFromUserId(Object value, CommandBean cmd) throws Exception { return null; }
		  public String validateSubject(Object value, CommandBean cmd) throws Exception { return null; }
		  public String validateBody(Object value, CommandBean cmd) throws Exception { return null; }
		  public String validateDateSent(Object value, CommandBean cmd) throws Exception { return null; }
		  public String validateRead(Object value, CommandBean cmd) throws Exception { return null; }
		  public String validateId(Object value, CommandBean cmd) throws Exception { return null; }

}

