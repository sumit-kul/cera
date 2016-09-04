package com.era.community.doclib.ui.validator; 

import support.community.framework.CommandBeanImpl;

public class DocumentLibraryValidator extends com.era.community.doclib.dao.generated.DocumentLibraryValidator 
{
	public String validateName(Object value, CommandBeanImpl data) throws Exception
	{
		if (value.toString()!=null && value.toString().trim().length()> 150)
			return ("Name length cannot exceed 150 characters");

		return null;
	}
}
