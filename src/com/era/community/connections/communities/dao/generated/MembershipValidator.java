package com.era.community.connections.communities.dao.generated; 

import org.springframework.validation.Errors;

import support.community.framework.CommandBean;

public abstract class MembershipValidator extends support.community.framework.CommandValidator
{
	public void validateCommand(CommandBean data, Errors errors) {}
		  public String validateMemberListId(Object value, CommandBean cmd) throws Exception { return null; }
		  public String validateUserId(Object value, CommandBean cmd) throws Exception { return null; }
		  public String validateDateJoined(Object value, CommandBean cmd) throws Exception { return null; }
		  public String validateRole(Object value, CommandBean cmd) throws Exception { return null; }
		  public String validateDateLastVisit(Object value, CommandBean cmd) throws Exception { return null; }
		  public String validateApproverId(Object value, CommandBean cmd) throws Exception { return null; }
		  public String validateId(Object value, CommandBean cmd) throws Exception { return null; }
}

