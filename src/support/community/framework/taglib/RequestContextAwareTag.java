
package support.community.framework.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.support.JspAwareRequestContext;
import org.springframework.web.servlet.support.RequestContext;

/**
 * Superclass for all tags that require a RequestContext.
 * The RequestContext instance provides easy access to current
 * state like WebApplicationContext, Locale, Theme, etc.
 *
 * <p>Mainly intended for DispatcherServlet requests;
 * will use fallbacks when used outside DispatcherServlet.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @see org.springframework.web.servlet.support.RequestContext
 * @see org.springframework.web.servlet.DispatcherServlet
 */
public abstract class RequestContextAwareTag extends TagSupport implements TryCatchFinally 
{
	/** PageContext attribute for page-level RequestContext instance */
	public static final String REQUEST_CONTEXT_PAGE_ATTRIBUTE =
			"org.springframework.web.servlet.tags.REQUEST_CONTEXT";

	protected final Log logger = LogFactory.getLog(getClass());

    /**
     * Store the request context here and make it available to subclasses.
     */
	private RequestContext requestContext;

    /**
     * Set a tag writer here and make it available to subclasses.
     */
    private TagWriter tagWriter;

    /**
     * Creates the {@link TagWriter} which all output will be written to. By default,
     * the {@link TagWriter} writes its output to the {@link javax.servlet.jsp.JspWriter}
     * for the current {@link javax.servlet.jsp.PageContext}. Subclasses may choose to
     * change the {@link java.io.Writer} to which output is actually written.
     */
    protected TagWriter createTagWriter() {
        return new TagWriter(this.pageContext.getOut());
    }
 
    /**
     * Create and expose the current RequestContext.
     * Delegates to <code>doStartTagInternal</code> for actual work.
     * @see #doStartTagInternal
     * @see #REQUEST_CONTEXT_PAGE_ATTRIBUTE
     * @see org.springframework.web.servlet.support.JspAwareRequestContext
     */
    public final int doStartTag() throws JspException 
    {   
        /*
         * If subclass returns true here then skip this tag and its body contents.
         */
        if (displaySuppressed()) return SKIP_BODY;
        
        /*
         * Create a tag writer.
         */
        this.tagWriter = createTagWriter();
        
        /*
         * Expose the request context.
         */
        this.requestContext = (RequestContext) this.pageContext.getAttribute(REQUEST_CONTEXT_PAGE_ATTRIBUTE);
        try {
            if (this.requestContext == null) {
                this.requestContext = new JspAwareRequestContext(this.pageContext);
                this.pageContext.setAttribute(REQUEST_CONTEXT_PAGE_ATTRIBUTE, this.requestContext);
            }
            return doStartTagInternal();
        }
        catch (JspException ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
        catch (RuntimeException ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new JspTagException(ex.getMessage());
        }
    }

    /**
     * Create and expose the current RequestContext.
     * Delegates to <code>doStartTagInternal</code> for actual work.
     * @see #doStartTagInternal
     * @see #REQUEST_CONTEXT_PAGE_ATTRIBUTE
     * @see org.springframework.web.servlet.support.JspAwareRequestContext
     */
    public final int doEndTag() throws JspException 
    {
        /*
         * If rendering of this tag is skipped then exit.
         */
        if (displaySuppressed()) return EVAL_PAGE;

        try {
            return doEndTagInternal();
        }
        catch (JspException ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
        catch (RuntimeException ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
        catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new JspTagException(ex.getMessage());
        }
    }


    /**
     *  A sublcass can override to conditionally skip rendering of this tag
     *  and its body contents. Used to support hide-when attributes. 
     */
    protected boolean displaySuppressed() throws JspException
    {
        return false;
    }
    
    /**
     * Return the current RequestContext.
     */
	protected final RequestContext getRequestContext() {
		return this.requestContext;
	}

    /**
     * Return the tag writer.
     */
    protected final TagWriter getTagWriter()
    {
        return tagWriter;
    }

	/**
	 * Called by doStartTag to perform the actual work.
	 * @return same as TagSupport.doStartTag
	 * @throws Exception any exception, any checked one other than
	 * a JspException gets wrapped in a JspException by doStartTag
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag
	 */
    protected abstract int doStartTagInternal() throws Exception;

    protected int doEndTagInternal() throws Exception
    {
        return EVAL_PAGE;
    }

	public void doCatch(Throwable throwable) throws Throwable {
		throw throwable;
	}

    /**
     * Clear (non-attribute) instance variables.
     */
	public void doFinally() {
		this.requestContext = null;
        this.tagWriter = null;
	}

}
