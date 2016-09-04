/*
 * Copyright 2002-2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package support.community.framework.taglib;

import javax.servlet.jsp.JspException;

import org.springframework.util.*;

/**
 * 
 * 
 */
public class DropdownTag extends AbstractHtmlInputElementTag implements OptionContainer 
{

	/**
	 * The value of the HTML '<code>size</code>' attribute rendered
	 * on the final '<code>select</code>' element.
	 */
	private String size;

	/**
	 * Sets the value of the HTML '<code>size</code>' attribute rendered
	 * on the final '<code>select</code>' element. May be a runtime
	 * expression.
	 */
	public void setSize(String size) {
		Assert.hasText(size, "'size' cannot be null or zero length.");
		this.size = size;
	}

	protected String getSize() {
		return size;
	}

	/**
	 * Renders the HTML '<code>select</code>' tag to supplied {@link TagWriter}.
	 * Renders nested '<code>option</code>' tags if the {@link #setItems items}
	 * properties are set, otherwise exposes the bound value for the
	 * nested {@link OptionTag OptionTags}.
	 */
	protected int writeStartTagEditMode(TagWriter tagWriter) throws JspException 
    {
		tagWriter.startTag("select");
		writeDefaultAttributes(tagWriter);

		tagWriter.writeOptionalAttributeValue("size", ObjectUtils.getDisplayString(evaluate("size", getSize())));

        tagWriter.forceBlock();

		return EVAL_BODY_INCLUDE;
	}


    /**
     * 
     */
    protected int writeEndTagEditMode(TagWriter tagWriter) throws JspException
    {
        if (tagWriter != null) {
            tagWriter.endTag();
        }
        
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


    public void addOption(String label, Object value, String cssClass) throws JspException
    {
        if (isDisplayForRead())
            addOptionReadMode(label, value, cssClass);
        else
            addOptionEditMode(label, value, cssClass);
    }

    public void addOptionEditMode(String label, Object value, String cssClass) throws JspException
    {
        TagWriter tagWriter = getTagWriter();
        
        tagWriter.startTag("option");

        tagWriter.writeAttribute("value", ObjectUtils.getDisplayString(value));
        if (isSelected(value)) {
            tagWriter.writeAttribute("selected", "selected");
        }
        tagWriter.writeAttribute("class", cssClass);
        tagWriter.appendValue(label);

        tagWriter.endTag();
    }

    public void addOptionReadMode(String label, Object value, String cssClass) throws JspException
    {
        TagWriter tagWriter = getTagWriter();
        if (isSelected(value)) {
            tagWriter.startTag("span");
            tagWriter.appendValue(label);
            tagWriter.endTag();
        }
    }

    private boolean isSelected(Object resolvedValue) throws JspException 
    {
        return SelectedValueComparator.isSelected(getBindStatus(), resolvedValue);
    }
    
}
