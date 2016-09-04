package com.era.community.communities.ui.validator; 

import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

public class FeedbackValidator extends com.era.community.communities.dao.generated.FeedbackValidator 
{
    public String validateSubject(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 200)
            return ("Subject cannot exceed 200 characters");
        
        return null;
    }
    
    public String validateBody(Object value, CommandBean cmd) throws Exception {
        if (isNullOrWhitespace(value)) return "Please provide your feedback";
        if (value.toString().trim().length()> 32700)
            return ("Body cannot exceed 32000 characters");
        
        return null;
    }
}
