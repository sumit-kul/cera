package support.community.framework;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.logging.*;
import org.springframework.web.servlet.*;

public abstract class AbstractRequestThreadLocalHolder implements HandlerInterceptor, Filter
{
    private InheritableThreadLocal<Object> holder = new InheritableThreadLocal<Object>();
    private String m_exposeInViewAs = null;
    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * 
      */
    public final void initThread(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        if (holder.get()!=null) throw new Exception("Thread already has an object bound to it. "+getClass());
        holder.set(createObject(request, response));
        
        if (m_exposeInViewAs!=null && holder.get()!=null) {
            request.setAttribute(getExposeInViewAs(), holder.get());
        }
    }

    /**
     * 
     */
    protected final Object getHolderContents() 
    {
        Object o = holder.get();
        if (o==null) throw new RuntimeException("ThreadLocal holder has not been initialized for this thread. Maybe "+getClass().getName()+" is not configured as a filter.");
        return o;
    }

    /**
     * 
      */
    public final void clearThread(HttpServletRequest request, HttpServletResponse response, Exception ex) 
    {
        Object o =holder.get();
        holder.set(null);
        destroyObject(o, request, response, ex);
    }

    protected abstract Object createObject(HttpServletRequest request, HttpServletResponse response) throws Exception;
    protected void destroyObject(Object o, HttpServletRequest request, HttpServletResponse response, Exception ex)  { }
    
    public boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception { return true; } 
    public void doPostHandle(HttpServletRequest req, HttpServletResponse resp, Object handler, ModelAndView mv) throws Exception { }
    public void doAfterCompletion(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex) throws Exception { }
    
       
    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception 
     {
         return doPreHandle(request, response, handler);
     }    
    
    public final void postHandle(HttpServletRequest req, HttpServletResponse resp, Object handler, ModelAndView mv) throws Exception
    {
        doPostHandle(req, resp, handler, mv);
        
        if (mv==null) return;

        String viewName = mv.getViewName();
        if (viewName!=null && viewName.startsWith("redirect:")) return;

        // For views other than JSP - but this will not cover error screens because on error we dont get here
        if (m_exposeInViewAs!=null && holder.get()!=null) {
            mv.addObject(m_exposeInViewAs, holder.get());
        }
    }
    
    public final void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex) throws Exception
    {
        doAfterCompletion(req, resp, handler, ex);
    }

    
    public final void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException
    {
        try {
            initThread((HttpServletRequest)req, (HttpServletResponse)resp);
        }
        catch (Exception e) {
            logger.error("", e);
            throw new ServletException(e);
        }
        
        try {
            chain.doFilter(req, resp);
            clearThread((HttpServletRequest)req, (HttpServletResponse)resp, null);
        }
        catch (Exception x) {
            logger.error("", x);
            clearThread((HttpServletRequest)req, (HttpServletResponse)resp, x);
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
    
}

