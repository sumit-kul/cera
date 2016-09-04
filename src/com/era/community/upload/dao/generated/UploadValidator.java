package com.era.community.upload.dao.generated; 

import org.springframework.validation.Errors;

import support.community.framework.CommandBean;

public abstract class UploadValidator extends support.community.framework.CommandValidator
{
	public void validateCommand(CommandBean data, Errors errors) {}
	public String validateCreatorId(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateTitle(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateDescription(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateId(Object value, CommandBean cmd) throws Exception { return null; }
}