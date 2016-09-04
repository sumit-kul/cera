package com.era.community.communities.ui.validator; 

import support.community.framework.CommandBeanImpl;

public class CommunityJoiningRequestValidator extends com.era.community.communities.dao.generated.CommunityJoiningRequestValidator 
{
    public String validateOptionalComment(Object value, CommandBeanImpl data) throws Exception
    {
      
            if (isNullOrWhitespace(value)) return "Please tell us why you want to join this community";
            
            if (value.toString().length() > 500) {
                return "The text should not exceed 500 characters";
            }
            return null;
        }
  
}
