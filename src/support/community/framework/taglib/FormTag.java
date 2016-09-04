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

import support.community.framework.*;

import org.springframework.util.*;
import org.springframework.util.Assert;


/**
 * Data-binding aware JSP tag for rendering an HTML '<code>form</code>' whose
 * inner elements are bound to properties on a {@link #setCommandName command object}.
 *
 * <p>Users should place the command object into the {@link org.springframework.web.servlet.ModelAndView}
 * when populating the data for their view. The name of this command object must
 * be configured using the {@link #setCommandName commandName} property.
 *
 * <p>The default value for the {@link #setCommandName commandName} property is
 * '<code>command</code>' which corresponds to the default name when using the
 * {@link org.springframework.web.servlet.mvc.SimpleFormController}.
 *
 * <p>Inner tags can access the name of the command object via the
 * {@link javax.servlet.jsp.PageContext}. The attribute name is defined in
 * {@link #COMMAND_NAME_VARIABLE_NAME}.
 *
 * @since 2.0
 * @see org.springframework.web.servlet.mvc.SimpleFormController
 */
public class FormTag extends AbstractFormTag 
{
	/**
	 * The name of the {@link javax.servlet.jsp.PageContext} attribute under which the
	 * command object name is exposed.
	 */
	public static final String COMMAND_NAME_VARIABLE_NAME = "org.springframework.web.servlet.tags.form.FormTag.commandName";

	/**
	 * The default HTTP method using which form values are sent to the server.
	 */
	private static final String DEFAULT_METHOD = "post";

	/**
	 * The default command objcet name.
	 */
	public static final String DEFAULT_COMMAND_NAME = "command";

	/**
	 * The name of the '<code>commandName</code>' attribute.
	 */
	public static final String COMMAND_NAME_ATTRIBUTE = "commandName";

	/**
	 * The name of the '<code>name</code>' attribute.
	 */
	public static final String NAME_ATTRIBUTE = "name";

	/**
	 * The name of the '<code>onsubmit</code>' attribute.
	 */
	public static final String ONSUBMIT_ATTRIBUTE = "onsubmit";

	/**
	 * The name of the '<code>onreset</code>' attribute.
	 */
	public static final String ONRESET_ATTRIBUTE = "onreset";

	/**
	 * The name of the '<code>method</code>' attribute.
	 */
	public static final String METHOD_ATTRIBUTE = "method";

	/**
	 * The name of the '<code>action</code>' attribute.
	 */
	public static final String ACTION_ATTRIBUTE = "action";

	/**
	 * The name of the '<code>enctype</code>' attribute.
	 */
	public static final String ENCTYPE_ATTRIBUTE = "enctype";

	/**
	 * The name of the command object.
	 */
	private String commandName = DEFAULT_COMMAND_NAME;

	/**
	 * The value of the '<code>name</code>' attribute.
	 */
	private String name;

	/**
	 * The value of the '<code>action</code>' attribute.
	 */
	private String action;

	/**
	 * The value of the '<code>method</code>' attribute.
	 */
	private String method = DEFAULT_METHOD;

	/**
	 * The value of the '<code>enctype</code>' attribute.
	 */
	private String enctype;

	/**
	 * The value of the '<code>onsubmit</code>' attribute.
	 */
	private String onsubmit;

	/**
	 * The value of the '<code>onreset</code>' attribute.
	 */
	private String onreset;
    
    /**
     * The mode (update, read, create, delete) in which the form is 
     * being displayed.
     */
    private String mode;
  
    /**
     * Should field errors be written after the field.
     */
    private boolean showFieldErrors = true;
  
    /**
     * Is the form multipart.
     */
    private boolean multipart = false;
  

	/**
	 * Sets the name of the command object.
	 * May be a runtime expression.
	 */
	public void setCommandName(String commandName) {
		Assert.notNull(commandName, "'commandName' cannot be null");
		this.commandName = commandName;
	}

	/**
	 * Sets the value of the '<code>name</code>' attribute.
	 * May be a runtime expression.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the value of the '<code>action</code>' attribute.
	 * May be a runtime expression.
	 */
	public void setAction(String action) {
		this.action = (action != null ? action : "");
	}

	/**
	 * Sets the value of the '<code>method</code>' attribute.
	 * May be a runtime expression.
	 */
	public void setMethod(String method) {
		Assert.hasText(method, "'method' cannot be null or zero length");
		this.method = method;
	}

	/**
	 * Sets the value of the '<code>enctype</code>' attribute.
	 * May be a runtime expression.
	 */
	public void setEnctype(String enctype) {
		Assert.hasText(enctype, "'enctype' cannot be null or zero length");
		this.enctype = enctype;
	}

	/**
	 * Sets the value of the '<code>onsubmit</code>' attribute.
	 * May be a runtime expression.
	 */
	public void setOnsubmit(String onsubmit) {
		Assert.hasText(onsubmit, "'onsubmit' cannot be null or zero length");
		this.onsubmit = onsubmit;
	}

	/**
	 * Sets the value of the '<code>onreset</code>' attribute.
	 * May be a runtime expression.
	 */
	public void setOnreset(String onreset) {
		Assert.hasText(onreset, "'onreset' cannot be null or zero length");
		this.onreset = onreset;
	}


	/**
	 * Writes the opening part of the block	'<code>form</code>' tag and exposes
	 * the command name in the {@link javax.servlet.jsp.PageContext}.
	 */
	protected int doStartTagInternal() throws JspException 
    {
        TagWriter tagWriter = getTagWriter();
        
        /*
         * Try to get an AppRequestContext.
         */
        AppRequestContext arc = getAppRequestContext();
        
        tagWriter.startTag("form");
        
        String resolvedMethod = ObjectUtils.getDisplayString(evaluate(METHOD_ATTRIBUTE, this.method));
        tagWriter.writeAttribute(METHOD_ATTRIBUTE,  resolvedMethod);
        
        writeOptionalAttribute(tagWriter, NAME_ATTRIBUTE, this.name);
        writeOptionalAttribute(tagWriter, ACTION_ATTRIBUTE, this.action!=null?this.action:arc.getRequestUri());
        writeOptionalAttribute(tagWriter, ONSUBMIT_ATTRIBUTE, this.onsubmit);
        writeOptionalAttribute(tagWriter, ONRESET_ATTRIBUTE, this.onreset);
        
        /*
         * If multipart attribute specified then set the enctype.
         */
        if (multipart) tagWriter.writeAttribute(ENCTYPE_ATTRIBUTE, "multipart/form-data");

        /*
         * If AppRequestContext found then use it find the true request URL and write it into a hidden field so that it is
         * sent during the form submission. (This allows simple redirect back to the form after submit.)
         */
        if (arc!=null && resolvedMethod.equalsIgnoreCase("post")) {
            
            tagWriter.startTag("input");
            tagWriter.writeAttribute("type", "hidden");
            tagWriter.writeAttribute("class", "hidden");
            tagWriter.writeAttribute("name", "formUrl");
            tagWriter.writeAttribute("value", arc.getFullRequestUrl());
            tagWriter.endTag();
            
            Map params = arc.getRequestParameters();
            if (params.containsKey("mode")) {
                tagWriter.startTag("input");
                tagWriter.writeAttribute("type", "hidden");
                tagWriter.writeAttribute("name", "mode");
                tagWriter.writeAttribute("value", (String)params.get("mode"));
                tagWriter.endTag();
            }

        }
        
        tagWriter.forceBlock();

        // expose the command name for nested tags
		this.pageContext.setAttribute(COMMAND_NAME_VARIABLE_NAME, resolveCommandName());
        
		return EVAL_BODY_INCLUDE;
	}

	/**
	 * {@link #evaluate Resolves} and returns the name of the command object.
	 * @throws IllegalArgumentException if the command object resolves to null.
	 */
	private String resolveCommandName() throws JspException {
		Object resolvedCommmandName = evaluate(COMMAND_NAME_ATTRIBUTE, this.commandName);
		if (resolvedCommmandName == null) {
			throw new IllegalArgumentException("'commandName' cannot be null");
		}
		return (String) resolvedCommmandName;
	}

	/**
	 * Closes the '<code>form</code>' block tag and removes the command name
	 * from the {@link javax.servlet.jsp.PageContext}.
	 */
	public int doEndTagInternal() throws JspException 
    {
		getTagWriter().endTag();
		this.pageContext.removeAttribute(COMMAND_NAME_VARIABLE_NAME);
		return EVAL_PAGE;
	}


    public final void setMode(String mode)
    {
        this.mode = mode;
    }
    
    public final String getMode()
    {
        return mode;
    }
    
    public final void setShowFieldErrors(boolean showFieldErrors)
    {
        this.showFieldErrors = showFieldErrors;
    }
    
    protected final boolean getFieldErrorsEnabled()
    {
        return showFieldErrors;
    }
    
    public final void setMultipart(boolean multipart)
    {
        this.multipart = multipart;
    }
}
