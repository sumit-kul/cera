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
import javax.servlet.jsp.tagext.*;

import org.springframework.util.*;

/**
 *
 */
public class OptionTag extends AbstractFormTag 
{
	/**
	 * The 'value' attribute of the rendered HTML <code>&lt;option&gt;</code> tag.
	 */
	private String value;

	/**
	 * The text body of the rendered HTML <code>&lt;option&gt;</code> tag.
	 */
	private String label;


	/**
	 * Sets the 'value' attribute of the rendered HTML <code>&lt;option&gt;</code> tag.
	 * May be a runtime expression.
	 */
	public void setValue(String value) {
		Assert.notNull(value, "'value' cannot be null.");
		this.value = value;
	}

	/**
	 * Gets the 'value' attribute of the rendered HTML <code>&lt;option&gt;</code> tag.
	 * May be a runtime expression.
	 */
	protected String getValue() {
		return this.value;
	}

	/**
	 * Sets the text body of the rendered HTML <code>&lt;option&gt;</code> tag.
	 * May be a runtime expression.
	 */
	public void setLabel(String label) {
		Assert.notNull(label, "'label' cannot be null.");
		this.label = label;
	}

	/**
	 * Gets the text body of the rendered HTML <code>&lt;option&gt;</code> tag.
	 * May be a runtime expression.
	 */
	protected String getLabel() {
		return this.label;
	}

    /**
     * Returns the value of the label for this '<code>option</code>' element.
     * If the {@link #setLabel label} property is set then the resolved value
     * of that property is used, otherwise the value of the <code>resolvedValue</code>
     * argument is used.
     */
    private String getLabelValue(Object resolvedValue) throws JspException 
    {
        String label1 = getLabel();
        Object labelObj = (label1 == null ? resolvedValue : evaluate("label", label1));
        return ObjectUtils.getDisplayString(labelObj);
    }
    
	/**
     * 
     * 
	 */
    protected int doStartTagInternal() throws Exception 
    {
        Tag parent = getParent();
        if (!(parent instanceof OptionContainer))
            throw new JspException("Option tag cannot be nested in "+parent.getClass());
        
        OptionContainer oc = (OptionContainer)parent;
        
        Object resolvedValue = evaluate("value", getValue());
        oc.addOption(getLabelValue(resolvedValue), resolvedValue, null);

		return EVAL_PAGE;
	}

}
