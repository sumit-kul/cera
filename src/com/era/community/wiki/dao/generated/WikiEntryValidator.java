package com.era.community.wiki.dao.generated; 

import org.springframework.validation.Errors;

import support.community.framework.CommandBean;

public abstract class WikiEntryValidator extends support.community.framework.CommandValidator
{
	public void validateCommand(CommandBean data, Errors errors) {}
	public String validateWikiId(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateEntryId(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateEntrySequence(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateTitle(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateDatePosted(Object value, CommandBean cmd) throws Exception { return null; }
	public String validatePosterId(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateReasonForUpdate(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateId(Object value, CommandBean cmd) throws Exception { return null; }
}