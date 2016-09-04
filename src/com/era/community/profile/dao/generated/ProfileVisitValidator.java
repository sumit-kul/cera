package com.era.community.profile.dao.generated; 

import org.springframework.validation.Errors;

import support.community.framework.CommandBean;

public abstract class ProfileVisitValidator extends support.community.framework.CommandValidator
{
	public void validateCommand(CommandBean data, Errors errors) {}
	public String validateProfileUserId(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateVisitingUserId(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateId(Object value, CommandBean cmd) throws Exception { return null; }

}

