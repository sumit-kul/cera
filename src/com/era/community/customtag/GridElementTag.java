package com.era.community.customtag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import support.community.framework.taglib.AbstractHtmlElementTag;
import support.community.framework.taglib.TagWriter;

public class GridElementTag extends AbstractHtmlElementTag 
{  
    protected int doStartTagInternal() throws Exception
    {
        TagWriter tagWriter = getTagWriter();

        Tag parent = getParent();
        if (!(parent instanceof GridTag))  throw new JspException("Tag is invalid in this location");
        
        ((GridTag)parent).beginGridElement();
        
        return EVAL_PAGE;
    }

    protected int doEndTagInternal() throws Exception
    {
        TagWriter tagWriter = getTagWriter();

        Tag parent = getParent();
        ((GridTag)parent).endGridElement();
        
        return EVAL_PAGE;
    }
    
}
