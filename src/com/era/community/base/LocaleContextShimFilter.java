package com.era.community.base;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;

public class LocaleContextShimFilter implements Filter
{
    protected Log logger = LogFactory.getLog(getClass());

    public final void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        
        LocaleContext lc ;

        try {

            if ((lc=LocaleContextHolder.getLocaleContext())!=null) {
                logger.error("****************************************************************");
                logger.error("LocaleContext "+lc.getClass().getName()+" has been found attached to a request thread.");
                logger.error("****************************************************************");
                //LocaleContextHolder.setLocaleContext(null);
            }

            chain.doFilter(req, resp);

        } 
        catch (Throwable x) {
            
            logger.error("", x);
            throw new ServletException(x);
        
        } 
        finally {
            
            if ((lc=LocaleContextHolder.getLocaleContext())!=null) {
                logger.error("****************************************************************");
                logger.error("LocaleContext "+lc.getClass().getName()+" has been left attached to a request thread.");
                logger.error("****************************************************************");
                //LocaleContextHolder.setLocaleContext(null);
            }
        }
    }

    public final void destroy()
    {
    }

    public final void init(FilterConfig arg0) throws ServletException
    {
    }
}
