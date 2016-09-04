package com.era.community.communities.ui.validator; 

import support.community.framework.CommandBeanImpl;

public class DeletedCommunityValidator extends com.era.community.communities.dao.generated.DeletedCommunityValidator 
{
    public String validateComment(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 32700)
            return ("Comment length cannot exceed 32700 characters");
        
        if (isNullOrWhitespace(value)) return "Please enter a comment";
        return null;
    }
}
