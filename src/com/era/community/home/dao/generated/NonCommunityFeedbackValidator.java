package com.era.community.home.dao.generated; 

import org.springframework.validation.Errors;

import support.community.framework.CommandBean;

public abstract class NonCommunityFeedbackValidator extends support.community.framework.CommandValidator
{
	public void validateCommand(CommandBean data, Errors errors) {}
	public String validateName(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateEmailAddress(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateBody(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateId(Object value, CommandBean cmd) throws Exception { return null; }

}