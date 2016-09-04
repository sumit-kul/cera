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

import java.util.*;

import javax.servlet.jsp.JspException;

import support.community.application.*;
import support.community.framework.*;

import org.springframework.util.*;
import org.springframework.beans.factory.*;
import org.springframework.validation.*;
import org.springframework.web.util.ExpressionEvaluationUtils;

/**
 * Base class for all JSP form tags. Provides utility methods for
 * null-safe EL evaluation and for accessing and working with a {@link TagWriter}.
 *
 * <p>Subclasses should implement the {@link #writeTagContent(TagWriter)} to perform
 * actual tag rendering.
 *
 * <p>Subclasses (or test classes) can override the {@link #createTagWriter()} method to
 * redirect output to a {@link java.io.Writer} other than the {@link javax.servlet.jsp.JspWriter}
 * associated with the current {@link javax.servlet.jsp.PageContext}.
 *
 * @author Rob Harrop
 * @since 2.0
 */
public abstract class AbstractFormTag extends HtmlEscapingAwareTag 
{
    /**
     * Hide-when attributes.
     */
    private boolean show = true;
    private boolean hide = false;
    private boolean hideOnRead = false;
    private boolean hideOnUpdate = false;
    private boolean hideOnCreate = false;
    private boolean hideOnDelete = false;
    
    
	/**
	 * Evaluates the supplied value for the supplied attribute name. If the supplied value
	 * is <code>null</code> then <code>null</code> is returned, otherwise evaluation is
	 * handled using {@link ExpressionEvaluationUtils#evaluate(String, String, javax.servlet.jsp.PageContext)}.
	 */
	protected Object evaluate(String attributeName, String value) throws JspException {
		if (value == null) {
			return null;
		}
		return ExpressionEvaluationUtils.evaluate(attributeName, value, this.pageContext);
	}

	/**
	 * Optionally writes the supplied value under the supplied attribute name into the supplied
	 * {@link TagWriter}. In this case, the supplied value is {@link #evaluate evaluated} first
	 * and then the {@link ObjectUtils#getDisplayString String representation} is written as the
	 * attribute value. If the resultant <code>String</code> representation is <code>null</code>
	 * or empty, no attribute is written.
	 * @see TagWriter#writeOptionalAttributeValue(String, String)
	 */
	protected final void writeOptionalAttribute(TagWriter tagWriter, String attributeName, String value) throws JspException {
		tagWriter.writeOptionalAttributeValue(attributeName, ObjectUtils.getDisplayString(evaluate(attributeName, value)));
	}
    
    /**
     *  
     */
    protected FormTag getFormTag()
    {
        FormTag form = (FormTag)findAncestorWithClass(this, FormTag.class);
        //if (form==null) throw new 
        return form;
    }
    
    protected final boolean isShowFieldErrors()
    {
        return getFormTag().getFieldErrorsEnabled();
    }
    
    protected final boolean isDisplayForRead()
    {
        FormTag form = getFormTag();
        return form==null?false:AppConstants.MODE_READ.equals(form.getMode()); 
    }
    
    protected final boolean isDisplayForUpdate()
    {
        FormTag form = getFormTag();
        return form==null?false:AppConstants.MODE_UPDATE.equals(form.getMode()); 
    }
    
    protected final boolean isDisplayForCreate()
    {
        FormTag form = getFormTag();
        return form==null?false:AppConstants.MODE_CREATE.equals(form.getMode()); 
    }
    
    protected final boolean isDisplayForDelete()
    {
        FormTag form = getFormTag();
        return form==null?false:AppConstants.MODE_DELETE.equals(form.getMode()); 
    }

    protected Errors getFormErrors()
    {
        return getRequestContext().getErrors("command", isHtmlEscape());
    }
    
    protected boolean displaySuppressed() throws JspException
    {
        if (!show) return true;
        if (hideOnRead && (isDisplayForRead()||isDisplayForDelete())) return true;
        if (hideOnUpdate && isDisplayForUpdate()) return true;
        if (hideOnCreate && isDisplayForCreate()) return true;
        if (hideOnDelete && isDisplayForDelete()) return true;

        if (hide) return true;
        return false;
    }
    
    protected AppRequestContext getAppRequestContext() throws JspException
    {
        Map beans = BeanFactoryUtils.beansOfTypeIncludingAncestors(getRequestContext().getWebApplicationContext(), AppRequestContextHolder.class);
        if (beans.size()>1) throw new JspException("Multiple beans of type "+AppRequestContextHolder.class+" found in application context" );
        if (beans.size()==0) return null;
        return ((AppRequestContextHolder)beans.values().iterator().next()).getRequestContext();
    }

    public final boolean isHide()
    {
        return hide;
    }
    public final void setHide(boolean hide)
    {
        this.hide = hide;
    }
    public final boolean isHideOnCreate()
    {
        return hideOnCreate;
    }
    public final void setHideOnCreate(boolean hideOnCreate)
    {
        this.hideOnCreate = hideOnCreate;
    }
    public final boolean isHideOnDelete()
    {
        return hideOnDelete;
    }
    public final void setHideOnDelete(boolean hideOnDelete)
    {
        this.hideOnDelete = hideOnDelete;
    }
    public final boolean isHideOnRead()
    {
        return hideOnRead;
    }
    public final void setHideOnRead(boolean hideOnRead)
    {
        this.hideOnRead = hideOnRead;
    }
    public final boolean isHideOnUpdate()
    {
        return hideOnUpdate;
    }
    public final void setHideOnUpdate(boolean hideOnUpdate)
    {
        this.hideOnUpdate = hideOnUpdate;
    }
    public final boolean isShow()
    {
        return show;
    }
    public final void setShow(boolean show)
    {
        this.show = show;
    }
}
