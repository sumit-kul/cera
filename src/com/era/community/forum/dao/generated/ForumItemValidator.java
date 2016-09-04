package com.era.community.forum.dao.generated; 

import org.springframework.validation.Errors;

import support.community.framework.CommandBean;

public abstract class ForumItemValidator extends support.community.framework.CommandValidator
{
	public void validateCommand(CommandBean data, Errors errors) {}
	public String validateForumId(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateSubject(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateBody(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateAuthorId(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateDatePosted(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateClosed(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateClosedById(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateClosedOn(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateSticky(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateDeleteStatus(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateId(Object value, CommandBean cmd) throws Exception { return null; }
}