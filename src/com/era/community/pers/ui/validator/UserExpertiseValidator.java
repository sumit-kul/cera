package com.era.community.pers.ui.validator; 

import support.community.framework.CommandBeanImpl;

public class UserExpertiseValidator extends com.era.community.pers.dao.generated.UserExpertiseValidator 
{
    public String validateName(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 50)
            return ("Name length cannot exceed 50 characters");
        
        return null;
    }
}
