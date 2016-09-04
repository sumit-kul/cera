package com.era.community.connections.communities.ui.validator; 

import support.community.framework.CommandBeanImpl;

public class MemberListValidator extends com.era.community.connections.communities.dao.generated.MemberListValidator 
{
    public String validateName(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 150)
            return ("Name length cannot exceed 150 characters");
        
        return null;
    }
}
