package com.era.community.tagging.dao.generated; 

import org.springframework.validation.Errors;

import support.community.framework.CommandBean;

public abstract class TagValidator extends support.community.framework.CommandValidator
{
	public void validateCommand(CommandBean data, Errors errors) {}
	public String validateCommunityId(Object value, CommandBean cmd) throws Exception { return null; }
	public String validatePosterId(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateParentId(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateParentType(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateTagText(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateId(Object value, CommandBean cmd) throws Exception { return null; }

}

