package com.era.community.customtag;

import javax.servlet.jsp.JspException;

import support.community.framework.taglib.AbstractHtmlElementTag;
import support.community.framework.taglib.FieldGroupTag;
import support.community.framework.taglib.TagWriter;

public class FormSectionTag extends AbstractHtmlElementTag implements FieldGroupTag
{
    /**
     * The title of the section. 
     */
    private String label;
    
    /**
     * Is this the last section on the page (may display differently). 
     */
    private boolean lastOnPage; 
          
    
    /**
     * 
     * 
     */
    protected int doStartTagInternal() throws Exception
    {
        TagWriter tagWriter = getTagWriter();
        
        tagWriter.startTag("h2");
        writeDefaultAttributes(tagWriter);
        
        tagWriter.appendValue(label);
        
        tagWriter.endTag();
        
        return EVAL_PAGE;
    }


    /**
     * 
     * 
     */
    protected int doEndTagInternal() throws Exception
    {
                
        return EVAL_PAGE;
    }

    public void beginElement(TagWriter tagWriter, String errorMessage) throws JspException
    {
        tagWriter.startTag("p");
        tagWriter.forceBlock();
    }

    public void endElement(TagWriter tagWriter, String errorMessage) throws JspException
    {
        tagWriter.endTag();
    }

    public void beginLabel(TagWriter tagWriter, String errorMessage) throws JspException
    {
    }


    public void endLabel(TagWriter tagWriter, String errorMessage) throws JspException
    {
    }


    public void beginField(TagWriter tagWriter, String errorMessage) throws JspException
    {

    }


    public void endField(TagWriter tagWriter, String errorMessage) throws JspException
    {
    }


    public final void setLabel(String label)
    {
        this.label = label;
    }

    public final void setLastOnPage(boolean lastOnPage)
    {
        this.lastOnPage = lastOnPage;
    }
}
