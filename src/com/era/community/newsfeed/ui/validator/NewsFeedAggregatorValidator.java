package com.era.community.newsfeed.ui.validator; 

import support.community.framework.CommandBeanImpl;

public class NewsFeedAggregatorValidator extends com.era.community.newsfeed.dao.generated.NewsFeedAggregatorValidator 
{
    public String validateName(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 150)
            return ("Nam length cannot exceed 150 characters");
        
        return null;
    }
}
