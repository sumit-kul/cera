
package support.community.framework;

import javax.servlet.*;

import org.springframework.web.context.*;

/**
 * 
 */
public class WebsphereRuntimeContext implements RuntimeContext, ServletContextAware
{
    ServletContext servletContext;
    

    public String getServerName()
    {
        return (String)servletContext.getServletContextName();
        
        //Attribute("com.ibm.websphere.servlet.application.host");
    }

    public void setServletContext(ServletContext servletContext)
    {
        this.servletContext = servletContext;
    }

}
