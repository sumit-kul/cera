package com.era.community.newsfeed.ui.validator; 

import support.community.framework.CommandBeanImpl;

public class NewsFeedValidator extends com.era.community.newsfeed.dao.generated.NewsFeedValidator 
{

    public String validateName(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 64)
            return ("Name length cannot exceed 64 characters");
        
        return null;
    }
    
    public String validateUrl(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 255)
            return ("Url length cannot exceed 255 characters");
        
        return null;
    }
    
    public String validateUri(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 255)
            return ("Uri length cannot exceed 255 characters");
        
        return null;
    }
    
    public String validateLink(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 255)
            return ("Link length cannot exceed 255 characters");
        
        return null;
    }
    
    public String validateTitle(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 400)
            return ("Title length cannot exceed 400 characters");
        
        return null;
    }
    
    public String validateCopyright(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 400)
            return ("Copyright type length cannot exceed 400 characters");
        
        return null;
    }
    
    public String validateDescription(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 32700)
            return ("Description length cannot exceed 32700 characters");
        
        return null;
    }
    
    public String validateAuthor(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 255)
            return ("Author cannot exceed 255 characters");
        
        return null;
    }

}
