package com.era.community.faqs.dao.generated; 

import org.springframework.validation.Errors;

import support.community.framework.CommandBean;

public abstract class FaqValidator extends support.community.framework.CommandValidator
{
	public void validateCommand(CommandBean data, Errors errors) {}
	public String validateSubject(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateBody(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateFileName(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateSequence(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateInactive(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateId(Object value, CommandBean cmd) throws Exception { return null; }

}