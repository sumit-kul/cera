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

import java.util.Collection;

import javax.servlet.jsp.JspException;

import org.springframework.util.*;
import org.springframework.util.Assert;

/**
 * Databinding-aware JSP tag for rendering an HTML '<code>input</code>'
 * element with a '<code>type</code>' of '<code>checkbox</code>'.
 *
 * <p>May be used in one of three different approaches depending on the
 * type of the {@link #getValue bound value}.
 *
 * <h3>Approach One</h3>
 * When the bound value is of type {@link Boolean} then the '<code>input(checkbox)</code>'
 * is marked as 'checked' if the bound value is <code>true</code>. The '<code>value</code>'
 * attribute corresponds to the resolved value of the {@link #setValue(Object) value} property.
 * <h3>Approach Two</h3>
 * When the bound value is of type {@link Collection} then the '<code>input(checkbox)</code>'
 * is marked as 'checked' if the configured {@link #setValue(Object) value} is present in
 * the bound {@link Collection}.
 * <h3>Approach Three</h3>
 * For any other bound value type, the '<code>input(checkbox)</code>' is marked as 'checked'
 * if the the configured {@link #setValue(Object) value} is equal to the bound value.
 * 
 * @author Rob Harrop
 * @since 2.0
 */
public class CheckboxTag extends AbstractHtmlInputElementTag {

	/**
	 * The value of the '<code>value</code>' attribute.
	 */
	private Object value;


	/**
	 * Sets the value of the '<code>value</code>' attribute.
	 * May be a runtime expression.
	 */
	public void setValue(Object value) {
		Assert.notNull(value, "'value' cannot be null.");
		this.value = value;
	}

	/**
	 * Gets the value of the '<code>value</code>' attribute.
	 * May be a runtime expression.
	 */
	protected Object getValue() {
		return this.value;
	}


	/**
	 * Writes the '<code>input(checkbox)</code>' to the supplied {@link TagWriter}
	 * marking it as 'checked' if appropriate.
	 */
	protected int writeStartTagEditMode(TagWriter tagWriter) throws JspException 
    {
        tagWriter.startTag("label"); 
        
        tagWriter.startTag("input");
		writeDefaultAttributes(tagWriter);
		tagWriter.writeAttribute("type", "checkbox");

		Object boundValue = getBoundValue();

		if (boundValue instanceof Boolean) {
			renderFromBoolean((Boolean) boundValue, tagWriter);
		}
		else {

			Object value1 = getValue();
			if (value1 == null) {
				throw new IllegalArgumentException("Attribute 'value' is required when binding to non-Boolean values.");
			}

			Object resolvedValue = (value1 instanceof String ? evaluate("value", (String)value1) : value1);

			if (boundValue != null && boundValue.getClass().isArray()) {
				renderFromCollection(resolvedValue, CollectionUtils.arrayToList(boundValue), tagWriter);
			}
			else if (boundValue instanceof Collection) {
				renderFromCollection(resolvedValue, (Collection) boundValue, tagWriter);
			}
			else {
				renderSingleValue(resolvedValue, tagWriter);
			}
		}

        tagWriter.endTag();
        if (getFieldLabel()!=null)
            tagWriter.appendValue(getFieldLabel());
        tagWriter.endTag();

		/*
         *  write out the marker field
         */
        tagWriter.startTag("input");
		tagWriter.writeAttribute("type", "hidden");
        tagWriter.writeAttribute("class", "hidden");
		tagWriter.writeAttribute("boundValue", "1");
		tagWriter.writeAttribute("name", "_" + getPath());
		tagWriter.endTag();


		return EVAL_PAGE;
	}
    
    /**
     * 
     */
    protected int writeStartTagReadMode(TagWriter tagWriter) throws JspException
    {
        tagWriter.startTag("span");
        tagWriter.appendValue(ObjectUtils.getDisplayString(getBoundValue()));
        tagWriter.endTag();
        return EVAL_PAGE;
    }
    

	/**
	 * Renders the '<code>input(checkbox)</code>' with the supplied value, marking the
	 * '<code>input</code>' element as 'checked' if the supplied value matches the
	 * bound value.
	 */
	private void renderSingleValue(Object resolvedValue, TagWriter tagWriter) throws JspException {
		tagWriter.writeAttribute("value", ObjectUtils.getDisplayString(resolvedValue));

		if (SelectedValueComparator.isSelected(getBindStatus(), resolvedValue)) {
			tagWriter.writeAttribute("checked", "true");
		}
	}

	/**
	 * Renders the '<code>input(checkbox)</code>' with the supplied value, marking the
	 * '<code>input</code>' element as 'checked' if the supplied value is present
	 * in the bound {@link Collection} value.
	 */
	private void renderFromCollection(Object resolvedValue, Collection boundValue, TagWriter tagWriter) throws JspException {
		tagWriter.writeAttribute("value", ObjectUtils.getDisplayString(resolvedValue));

		if (boundValue.contains(resolvedValue)) {
			tagWriter.writeAttribute("checked", "true");
		}
	}

	/**
	 * Renders the '<code>input(checkbox)</code>' with the supplied value, marking the
	 * '<code>input</code>' element as 'checked' if the supplied {@link Boolean} is
	 * <code>true</code>.
	 */
	private void renderFromBoolean(Boolean boundValue, TagWriter tagWriter) throws JspException {
		tagWriter.writeAttribute("value", "true");
		if (boundValue.booleanValue()) {
			tagWriter.writeAttribute("checked", "true");
		}
	}

    protected void writeStandardFieldLabel(TagWriter tagWriter) throws JspException
    {
        //Disable this - we must write the label after the checkbox.
    }
}
