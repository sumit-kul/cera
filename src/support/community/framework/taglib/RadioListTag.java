package support.community.framework.taglib;

import javax.servlet.jsp.JspException;

import org.springframework.util.*;


/**
 * 
 * 
 */
public class RadioListTag extends AbstractHtmlInputElementTag implements OptionContainer 
{
    /*
     * Tag used to enclose each input tag emitted.
     */
    private String m_wrapperTag = "span";
    
    /*
     * Separator to emit between input tags.
     */
    private String m_separator = "";
    
	/**
     * 
	 */
	protected int writeStartTagEditMode(TagWriter tagWriter) throws JspException 
    {
        /*
         * We dont want an ID because we are emitting multiple elements rather
         * than a single element so ID would be duplicated. 
         */
        if (getId()!=null) throw new JspException("Do not specify ID for radiolist tag");
        
        /*
         *  write out the marker field
      
        tagWriter.startTag("input");
        tagWriter.writeAttribute("type", "hidden");
        tagWriter.writeAttribute("class", "hidden");
        tagWriter.writeAttribute("value", "1");
        tagWriter.writeAttribute("name", "_" + getPath());
        tagWriter.endTag();
           */
        
		return EVAL_BODY_INCLUDE;
	}

    /*
     * We dont want an ID because we are emitting multiple elements rather
     * than a single element so ID would be duplicated. 
     */
    protected void setDefaultId() throws Exception 
    {
    }
    
    /**
     * 
     */
    protected int writeEndTagEditMode(TagWriter tagWriter) throws JspException
    {
        return EVAL_PAGE;
    }

    
    /**
     * 
     */
    protected int writeStartTagReadMode(TagWriter tagWriter) throws JspException
    {
        return EVAL_PAGE;
    }

    /**
     * 
     */
    protected int writeEndTagReadMode(TagWriter tagWriter) throws JspException
    {
        return EVAL_PAGE;
    }


    /**
     * Called by option and optionlist tags in the body.
     */
    public void addOption(String label, Object value, String cssClass) throws JspException
    {
        if (isDisplayForRead())
            addOptionReadMode(label, value);
        else
            addOptionEditMode(label, value);
    }

    public void addOptionEditMode(String label, Object value) throws JspException
    {
        TagWriter tagWriter = getTagWriter();
        
        tagWriter.startTag(m_wrapperTag); 
        
        tagWriter.startTag("input");
        
        writeDefaultAttributes(tagWriter);
        tagWriter.writeAttribute("type", "radio");
        
        tagWriter.writeAttribute("value", ObjectUtils.getDisplayString(value)); ////////////////

        if (isSelected(value)) {
            tagWriter.writeAttribute("checked", "true");
        }

        tagWriter.endTag(); //close input tag
        
        tagWriter.appendValue("&nbsp;"+label+"&nbsp;");

        tagWriter.endTag();  //close wrapper tag

    }

    public void addOptionReadMode(String label, Object value) throws JspException
    {
        TagWriter tagWriter = getTagWriter();
        if (isSelected(value)) {
            tagWriter.startTag("span");
            tagWriter.appendValue(label);
            tagWriter.endTag();
        }
    }

    /*
     * Compare the passed object against the value of the property 
     * bound to this tag. Return true if the object matches the field value.
     */
    private boolean isSelected(Object o) throws JspException 
    {
        return SelectedValueComparator.isSelected(getBindStatus(), o);
    }
    
    public void setWrapperTag(String wrapperTag)
    {
        m_wrapperTag = wrapperTag;
    }
    public void setSeparator(String separator)
    {
        m_separator = separator;
    }
}
