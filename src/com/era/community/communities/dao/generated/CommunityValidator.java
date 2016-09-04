package com.era.community.communities.dao.generated; 

import org.springframework.validation.*;
import support.community.framework.*;

public abstract class CommunityValidator extends support.community.framework.CommandValidator
{
	public void validateCommand(CommandBean data, Errors errors) {}
		  public String validateName(Object value, CommandBean cmd) throws Exception { return null; }
		  public String validateCommunityType(Object value, CommandBean bean) throws Exception { return null; }
		  public String validateWelcomeText(Object value, CommandBean cmd) throws Exception { return null; }
		  public String validateDescription(Object value, CommandBean cmd) throws Exception { return null; }
		  public String validateId(Object value, CommandBean cmd) throws Exception { return null; }

}

