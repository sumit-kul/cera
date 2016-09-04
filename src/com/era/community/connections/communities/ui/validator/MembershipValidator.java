package com.era.community.connections.communities.ui.validator; 

import support.community.framework.CommandBeanImpl;

public class MembershipValidator extends com.era.community.connections.communities.dao.generated.MembershipValidator 
{
    public String validateRole(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 32)
            return ("Role length cannot exceed 32 characters");
        
        return null;
    }
}
