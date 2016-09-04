package com.era.community.customtag;

import javax.servlet.jsp.JspException;

import support.community.framework.taglib.AbstractHtmlElementTag;
import support.community.framework.taglib.TagWriter;

public class GridTag extends AbstractHtmlElementTag 
{
    /**
     * Number of columns in the grid. 
     */
    private int m_columns = 2;
    
    private int colno = 0;
          
    
    /**
     * 
     * 
     */
    protected int doStartTagInternal() throws Exception
    {
        TagWriter tagWriter = getTagWriter();
        return EVAL_PAGE;
    }


    /**
     * 
     * 
     */
    protected int doEndTagInternal() throws Exception
    {
        TagWriter tagWriter = getTagWriter();

        if (colno>0) endRow(tagWriter);
        
        return EVAL_PAGE;
    }

    public void beginRow(TagWriter tagWriter) throws JspException
    {        
        if (colno>0) endRow(tagWriter);
        tagWriter.startTag("div");
        tagWriter.writeAttribute("class", "clearer");
        tagWriter.forceBlock();

        colno = 1;
    }

    public void endRow(TagWriter tagWriter) throws JspException
    { 
        // for colno < columns .........?
        tagWriter.endTag();
    }

    public void beginGridElement() throws JspException
    {
        TagWriter tagWriter = getTagWriter();
        if (colno==0||colno>m_columns) beginRow(tagWriter);
        
        tagWriter.startTag("div");
        if (colno==1)
            tagWriter.writeAttribute("class", "bubble left");
        else
            tagWriter.writeAttribute("class", "bubble right");
        tagWriter.forceBlock();
        colno++;
    }

    
            
    
    public void endGridElement() throws JspException
    {
        TagWriter tagWriter = getTagWriter();
                                  
        tagWriter.endTag();
    }

    
}
