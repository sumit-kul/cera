package com.era.community.config.ui.validator; 

import support.community.framework.CommandBeanImpl;

public class MailMessageDefinitionValidator extends com.era.community.config.dao.generated.MailMessageDefinitionValidator 
{
    public String validateName(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 255)
            return ("Name length cannot exceed 255 characters");
        
        return null;
    }
    
    public String validateDescription(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 255)
            return ("Description length cannot exceed 255 characters");
        
        return null;
    }
    
    public String validateSubject(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 255)
            return ("Subject length cannot exceed 255 characters");
        
        return null;
    }
    
    public String validateVariableNames(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 255)
            return ("Variable names length cannot exceed 255 characters");
        
        return null;
    }
    
    public String validateFrom(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 120)
            return ("From names length cannot exceed 120 characters");
        
        return null;
    }
    
    public String validateDefaultText(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 2000)
            return ("Default text names length cannot exceed 2000 characters");
        
        return null;
    }
}
