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
public class SubmitTag extends AbstractHtmlInputElementTag 
{
	/**
	 * The name of the '<code>alt</code>' attribute.
	 */
	public static final String ALT_ATTRIBUTE = "alt";

	/**
	 * The name of the '<code>onselect</code>' attribute.
	 */
	public static final String ONSELECT_ATTRIBUTE = "onselect";

    /**
     * The text label for the submit button.
     */
    private String buttonLabel;

    /**
     * The image url for the submit button.
     */
    private String image;

	/**
	 * The value of the '<code>onselect</code>' attribute.
	 */
	private String onselect;

	/**
	 * Sets the value of the '<code>onselect</code>' attribute.
	 * May be a runtime expression.
	 */
	public void setOnselect(String onselect) {
		Assert.hasText(onselect, "'onselect' cannot be null or zero length.");
		this.onselect = onselect;
	}

	/**
	 * Gets the value of the '<code>onselect</code>' attribute.
	 * May be a runtime expression.
	 */
	protected String getOnselect() {
		return this.onselect;
	}


	/**
	 * Writes the '<code>input</code>' tag to the supplied {@link TagWriter}.
	 * Uses the value returned by {@link #getType()} to determine which
	 * type of '<code>input</code>' element to render.
	 */
	protected int writeStartTagEditMode(TagWriter tagWriter) throws JspException 
    {
		tagWriter.startTag("input");
		writeDefaultAttributes(tagWriter);
	
        if (getImage()!=null) {
            tagWriter.writeAttribute("type", "image");
            tagWriter.writeAttribute("src", getImage());
        }
        else {
            tagWriter.writeAttribute("type", "submit");
            tagWriter.writeAttribute("value", getButtonLabel());
        }

		// custom optional attributes
		writeOptionalAttribute(tagWriter, ONSELECT_ATTRIBUTE, getOnselect());
		writeOptionalAttribute(tagWriter, DISABLED_ATTRIBUTE, getDisabled());
        tagWriter.endTag();
        
		return EVAL_PAGE;
	}

    /**
     * 
     */
    protected int writeStartTagReadMode(TagWriter tagWriter) throws JspException
    {
        tagWriter.startTag("span");
        tagWriter.appendValue("&nbsp;"); 
        tagWriter.endTag();
        return EVAL_PAGE;
    }

    /**
     * Gets the value for the HTML '<code>name</code>' attribute. The default
     * implementation simply delegates to {@link #getPath} to use the property
     * path as the name. For the most part this is desirable as it links with
     * the server-side expectation for databinding. However, some subclasses
     * may wish to change the value of the '<code>name</code>' attribute without
     * changing the bind path.
     */
    protected String getName() throws JspException {
        return getPath()+"_submit";
    }

    public final String getImage()
    {
        return image;
    }

    public final void setImage(String image)
    {
        this.image = image;
    }

    public final String getButtonLabel()
    {
        return buttonLabel;
    }

    public final void setButtonLabel(String label)
    {
        this.buttonLabel = label;
    }


}
