package support.community.framework.taglib;

import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.support.*;
import org.springframework.web.util.*;

/**
 *  Significantly modified version of the spring 2.0 tag. 
 * 
 */
public abstract class AbstractHtmlInputElementTag extends AbstractHtmlElementTag 
{
	/**
	 * The name of the '<code>onfocus</code>' attribute.
	 */
	public static final String ONFOCUS_ATTRIBUTE = "onfocus";

	/**
	 * The name of the '<code>onblur</code>' attribute.
	 */
	public static final String ONBLUR_ATTRIBUTE = "onblur";

	/**
	 * The name of the '<code>onchange</code>' attribute.
	 */
	public static final String ONCHANGE_ATTRIBUTE = "onchange";

	/**
	 * The name of the '<code>accesskey</code>' attribute.
	 */
	public static final String ACCESSKEY_ATTRIBUTE = "accesskey";

	/**
	 * The name of the '<code>disabled</code>' attribute.
	 */
	public static final String DISABLED_ATTRIBUTE = "disabled";

	/**
	 * The value of the '<code>onfocus</code>' attribute.
	 */
	private String onfocus;

	/**
	 * The value of the '<code>onblur</code>' attribute.
	 */
	private String onblur;

	/**
	 * The value of the '<code>onchange</code>' attribute.
	 */
	private String onchange;

	/**
	 * The value of the '<code>accesskey</code>' attribute.
	 */
	private String accesskey;

	/**
	 * The value of the '<code>disabled</code>' attribute.
	 */
	protected String disabled;

	/**
	 * Sets the value of the '<code>onfocus</code>' attribute.
	 * May be a runtime expression.
	 */
	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	/**
	 * Sets the value of the '<code>onfocus</code>' attribute.
	 * May be a runtime expression.
	 */
	protected String getOnfocus() {
		return this.onfocus;
	}

	/**
	 * Sets the value of the '<code>onblur</code>' attribute.
	 * May be a runtime expression.
	 */
	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}

	/**
	 * Gets the value of the '<code>onblur</code>' attribute.
	 * May be a runtime expression.
	 */
	protected String getOnblur() {
		return this.onblur;
	}

	/**
	 * Sets the value of the '<code>onchange</code>' attribute.
	 * May be a runtime expression.
	 */
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	/**
	 * Sets the value of the '<code>onchange</code>' attribute.
	 * May be a runtime expression.
	 */
	protected String getOnchange() {
		return this.onchange;
	}

	/**
	 * Sets the value of the '<code>accesskey</code>' attribute.
	 * May be a runtime expression.
	 */
	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

	/**
	 * Sets the value of the '<code>accesskey</code>' attribute.
	 * May be a runtime expression.
	 */
	protected String getAccesskey() {
		return this.accesskey;
	}

	/**
	 * Sets the value of the '<code>disabled</code>' attribute.
	 * May be a runtime expression.
	 */
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	/**
	 * Sets the value of the '<code>disabled</code>' attribute.
	 * May be a runtime expression.
	 */
	protected String getDisabled() {
		return disabled;
	}
    
    /**
     * The textual label for this field.
     */
    private String fieldLabel;
    private boolean mandatory;
      

	public boolean isMandatory()
    {
        return mandatory;
    }

    public void setMandatory(boolean mandatory)
    {
        this.mandatory = mandatory;
    }

    /**
	 * Writes the default attributes configured via this base class to the supplied {@link TagWriter}.
	 * Subclasses should call this when they want the base attribute set to be written to the output.
	 */
	protected void writeDefaultAttributes(TagWriter tagWriter) throws JspException {
		super.writeDefaultAttributes(tagWriter);
		writeOptionalAttribute(tagWriter, ONFOCUS_ATTRIBUTE, getOnfocus());
		writeOptionalAttribute(tagWriter, ONBLUR_ATTRIBUTE, getOnblur());
		writeOptionalAttribute(tagWriter, ONCHANGE_ATTRIBUTE, getOnchange());
		writeOptionalAttribute(tagWriter, ACCESSKEY_ATTRIBUTE, getAccesskey());
		writeOptionalAttribute(tagWriter, DISABLED_ATTRIBUTE, getDisabled());
	}
       
    
    /**
     * Subclasses should implement this method to perform tag content rendering.
     * @return valid tag render instruction as per {@link javax.servlet.jsp.tagext.Tag#doStartTag()}.
     */
    protected abstract int writeStartTagEditMode(TagWriter tagWriter) throws JspException;
    protected abstract int writeStartTagReadMode(TagWriter tagWriter) throws JspException;
    
    
    protected int writeEndTagReadMode(TagWriter tagWriter) throws JspException
    {
        return EVAL_PAGE;
    }
    protected int writeEndTagEditMode(TagWriter tagWriter) throws JspException
    {
        return EVAL_PAGE;        
    }
    
    /**
     * 
     */
    protected final int doEndTagInternal() throws JspException
    {
        TagWriter tagWriter = getTagWriter();
        
        int ret;
        if (isDisplayForRead()) 
            ret = writeEndTagReadMode(tagWriter);
        else
            ret = writeEndTagEditMode(tagWriter);
        
        BindStatus bs = getBindStatus();
        
        String errorMessage = null;
        if (bs != null)
        errorMessage = HtmlUtils.htmlEscape(bs.getErrorMessage());
        
        FieldGroupTag group = (FieldGroupTag)findAncestorWithClass(this, FieldGroupTag.class);
        if (group!=null) group.endField(tagWriter, errorMessage);

        if (errorMessage!=null && errorMessage.length()>0 && isShowFieldErrors()) {
        	tagWriter.startTag("br");
            tagWriter.endTag();
            tagWriter.startTag("span");
            tagWriter.writeAttribute("class", "errorText");
            tagWriter.appendValue(errorMessage);
            tagWriter.endTag();
            tagWriter.startTag("br");
            tagWriter.endTag();
            tagWriter.startTag("br");
            tagWriter.endTag();
        }
        
        if (group!=null) group.endElement(tagWriter, errorMessage);
        
        return ret;
    }
    
    /**
     * Provides a simple template method that calls {@link #createTagWriter()} and passes
     * the created {@link TagWriter} to the {@link #writeTagContent(TagWriter)} method.
     * @return the value returned by {@link #writeTagContent(TagWriter)}
     */
    protected final int doStartTagInternal() throws Exception 
    {
        TagWriter tagWriter = getTagWriter();
        
        if (getId()==null&&getPath()!=null) 
            setDefaultId();
        
        BindStatus bs = getBindStatus();
        String errorMessage = null;
        if (bs != null)
        	bs.getErrorMessage();
        
        FieldGroupTag group = (FieldGroupTag)findAncestorWithClass(this, FieldGroupTag.class);
        if (group!=null) group.beginElement(tagWriter, errorMessage);

        if (group!=null) group.beginLabel(tagWriter, errorMessage);
        writeStandardFieldLabel(tagWriter);
        if (group!=null) group.endLabel(tagWriter, errorMessage);
        
        if (group!=null) group.beginField(tagWriter, errorMessage);
        if (isDisplayForRead()) 
            return writeStartTagReadMode(tagWriter);
        else
            return writeStartTagEditMode(tagWriter);

    }

    
    /**
     */
    protected void setDefaultId() throws Exception 
    {
         setId(getPath().replace('.', '_'));
    }
    
    protected void writeStandardFieldLabel(TagWriter tagWriter) throws JspException
    {
        if (getFieldLabel()==null) return;
        tagWriter.startTag("label");
        tagWriter.writeOptionalAttributeValue("for", getId());
        tagWriter.appendValue(getFieldLabel());
        
        if (this.isMandatory()) {
            tagWriter.appendValue("<sup>*</sup>");            // Mandatory field indicator
        }
        tagWriter.endTag();
    }

    protected final String getFieldLabel()
    {
        return fieldLabel;
    }
    
    public final void setFieldLabel(String label)
    {
        this.fieldLabel = label;
    }

}
