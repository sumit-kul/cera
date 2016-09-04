package com.era.community.communities.ui.validator; 

import support.community.framework.CommandBeanImpl;

public class CommunityMembershipDomainValidator extends com.era.community.communities.dao.generated.CommunityMembershipDomainValidator 
{
    public String validateDomain(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 255)
            return ("Domain length cannot exceed 255 characters");
        
        return null;
    }
}
