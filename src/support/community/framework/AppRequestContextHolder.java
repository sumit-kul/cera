package support.community.framework;

import javax.servlet.http.*;

import org.springframework.context.*;
import org.springframework.web.servlet.*;

import support.community.application.*;

public class AppRequestContextHolder extends AbstractRequestThreadLocalHolder implements ApplicationContextAware
{
    private ApplicationContext applicationContext;
    private String contextBeanId;

    protected Object createObject(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        AppRequestContext ctx =  (AppRequestContext)applicationContext.getBean(contextBeanId) ;
        ctx.init(request, response);
        return ctx;
    }

    public void doPostHandle(HttpServletRequest req, HttpServletResponse resp, Object handler, ModelAndView mv) throws Exception
    {
        if (mv==null||mv.isEmpty()||mv.getViewName()==null) return;
        
        if (!mv.getViewName().startsWith("redirect:")) {
            if (req.getMethod().equalsIgnoreCase("post")) {
                resp.setHeader("Cache-Control", "private, max-age=600");
                resp.setHeader("Pragma", "");
                resp.setDateHeader("Expires", System.currentTimeMillis()+600000);
            }
            else if (!resp.containsHeader("Cache-Control")) {
                resp.setHeader("Pragma", "No-cache");
                resp.setDateHeader("Expires", 1L);
                resp.setHeader("Cache-Control", "no-cache");
                resp.addHeader("Cache-Control", "no-store");
            }
        }
        
        try {
            AppRequestContext context = getRequestContext();
            BacklinkStack bs = context.getBacklinkStack();
            if (bs!=null) bs.storeToCookie(req, resp);
        }
        catch (Exception x) {
            logger.error("", x);
        }
    }

    public AppRequestContext getRequestContext() 
    {
        return (AppRequestContext)getHolderContents();
    }

    public final void setContextBeanId(String contextBeanId)
    {
        this.contextBeanId = contextBeanId;
    }

    public final void setApplicationContext(ApplicationContext applicationContext)
    {
        this.applicationContext = applicationContext;
    }
    

}

