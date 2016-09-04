package com.era.community.assignment.dao.generated; 

import org.springframework.validation.Errors;

import support.community.framework.CommandBean;

public abstract class AssignmentAssigneeLinkValidator extends support.community.framework.CommandValidator
{
	public void validateCommand(CommandBean data, Errors errors) {}
	public String validateUserId(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateAssignmentId(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateId(Object value, CommandBean cmd) throws Exception { return null; }
}