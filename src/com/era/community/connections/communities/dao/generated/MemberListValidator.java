package com.era.community.connections.communities.dao.generated; 

import org.springframework.validation.Errors;

import support.community.framework.CommandBean;

public abstract class MemberListValidator extends support.community.framework.CommandValidator
{
	public void validateCommand(CommandBean data, Errors errors) {}
		  public String validateCommunityId(Object value, CommandBean cmd) throws Exception { return null; }
		  public String validateName(Object value, CommandBean cmd) throws Exception { return null; }
		  public String validateInactive(Object value, CommandBean cmd) throws Exception { return null; }
		  public String validateId(Object value, CommandBean cmd) throws Exception { return null; }

}

