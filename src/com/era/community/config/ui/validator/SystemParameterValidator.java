package com.era.community.config.ui.validator; 

import support.community.framework.CommandBeanImpl;

public class SystemParameterValidator extends com.era.community.config.dao.generated.SystemParameterValidator 
{
    public String validateName(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 255)
            return ("Name length cannot exceed 255 characters");
        
        return null;
    }
    
    public String validateText(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 255)
            return ("Text length cannot exceed 255 characters");
        
        return null;
    }
    
    public String validateLongText(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 32700)
            return ("Long text length cannot exceed 32700 characters");
        
        return null;
    }
    
    public String validateDescription(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 2000)
            return ("Description length cannot exceed 2000 characters");
        
        return null;
    }
}
