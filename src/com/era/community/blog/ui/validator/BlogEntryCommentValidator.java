package com.era.community.blog.ui.validator; 

import support.community.framework.*;

public class BlogEntryCommentValidator extends com.era.community.blog.dao.generated.BlogEntryCommentValidator 
{
    public String validateComment(Object value, CommandBeanImpl data) throws Exception
    {
        if (value.toString()!=null && value.toString().trim().length()> 32700)
            return ("Comment length cannot exceed 32700 characters");
        
        return null;
    }
}
