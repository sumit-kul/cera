package com.era.community.events.dao.generated; 

import org.springframework.validation.Errors;

import support.community.framework.CommandBean;

public abstract class EventValidator extends support.community.framework.CommandValidator
{
	public void validateCommand(CommandBean data, Errors errors) {}
	public String validateName(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateDescription(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateStartDate(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateEndDate(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateLocation(Object value, CommandBean cmd) throws Exception { return null; }
	public String validatePosterId(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateWeblink(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateContactName(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateContactTel(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateContactEmail(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateCalendarId(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateId(Object value, CommandBean cmd) throws Exception { return null; }
	public String validateEventCategory(Object value, CommandBean cmd) throws Exception { return null; }
}