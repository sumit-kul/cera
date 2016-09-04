package com.era.community.pers.ui.validator; 

import support.community.framework.CommandBeanImpl;

public class MessageValidator extends com.era.community.pers.dao.generated.MessageValidator 
{
    public String validateSubject(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 120)
            return ("Subject length cannot exceed 120 characters");
        
        return null;
    }
    
    public String validateBody(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 32700)
            return ("Body length cannot exceed 32700 characters");
        
        return null;
    }
}
