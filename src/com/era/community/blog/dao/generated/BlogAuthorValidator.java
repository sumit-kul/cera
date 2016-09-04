package com.era.community.blog.dao.generated; 

import org.springframework.validation.Errors;

import support.community.framework.CommandBean;

public abstract class BlogAuthorValidator extends support.community.framework.CommandValidator
{
	public void validateCommand(CommandBean data, Errors errors) {}
		  public String validateUserId(Object value, CommandBean cmd) throws Exception { return null; }
		  public String validateBlogId(Object value, CommandBean cmd) throws Exception { return null; }
		  public String validateId(Object value, CommandBean cmd) throws Exception { return null; }

}