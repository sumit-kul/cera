package com.era.community.doclib.ui.validator; 

import support.community.framework.CommandBeanImpl;

public class DocumentCommentValidator extends com.era.community.doclib.dao.generated.DocumentCommentValidator 
{
	public String validateComment(Object value, CommandBeanImpl data) throws Exception
	{
		if (value.toString()!=null && value.toString().trim().length()> 32700)
			return ("Comment length cannot exceed 32700 characters");

		return null;
	}
}
