package support.community.framework;

import java.io.*;

import javax.servlet.*;

import org.apache.commons.logging.*;


/**
 * 
 * 
 */
public class ThreadDecoratorFilter implements Filter
{
    /**
     *  
     */
    private ThreadDecorator decorator ;

    /**
     *  If this property is set then the object bound to the current thread will be exposed
     *  to the view invoked by the current request under this name.
     */
    private String m_exposeInViewAs = null;

    /**
     * Create a logger available to all subclasses.
     */
    protected final Log logger = LogFactory.getLog(getClass());

    
    public final void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException
    {
        try {
            decorator.initThread();
        }
        catch (Exception e) {
            logger.error("", e);
            throw new ServletException(e);
        }
        
        try {
            chain.doFilter(req, resp);
            decorator.clearThread(null);
        }
        catch (Exception x) {
            logger.error("", x);
            decorator.clearThread(x);
            throw new ServletException(x);
       }
    }
    
    public final void destroy()  {}
    public final void init(FilterConfig arg0) throws ServletException  { }

    public final String getExposeInViewAs()
    {
        return m_exposeInViewAs;
    }
    public final void setExposeInViewAs(String viewName)
    {
        m_exposeInViewAs = viewName;
    }
    
    public void setDecorator(ThreadDecorator decorator)
    {
        this.decorator = decorator;
    }
}

