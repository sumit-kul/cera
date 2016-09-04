package com.era.community.blog.ui.validator; 

import support.community.framework.*;

public class BlogEntryValidator extends com.era.community.blog.dao.generated.BlogEntryValidator 
{
    public String validateTitle(Object value, CommandBeanImpl data) throws Exception
    {
        /*if (value.toString()!=null && value.toString().trim().length()> 100)
            return ("Title length cannot exceed 100 characters");*/
        
        return null;
    }
    
    public String validateBody(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 32700)
            return ("Body length cannot exceed 32700 characters");
        
        return null;
    }
    
    public String validateFileName1(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 150)
            return ("FileName1 length cannot exceed 150 characters");
        
        return null;
    }
    
    public String validateFileName2(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 150)
            return ("FileName2 length cannot exceed 150 characters");
        
        return null;
    }
    
    public String validateFileName3(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 150)
            return ("FileName3 length cannot exceed 150 characters");
        
        return null;
    }
   
}
