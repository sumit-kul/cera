package com.era.community.pers.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.framework.RunAsServerCallback;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.User;

/**
 * @spring.bean name="/pers/commLogoRemove.img"
 */
public class RemoveUserPhotoAction extends AbstractCommandAction
{
    public static final String REQUIRES_AUTHENTICATION = "";

    protected CommunityEraContextManager contextManager;

    protected ModelAndView handle(Object data) throws Exception    
    {
    	Command cmd = (Command)data;
    	CommunityEraContext context = contextManager.getContext();
    	
    	if (context.getCurrentUser() == null ) {
    		String reqUrl = context.getRequestUrl();
        	if(reqUrl != null) {
        		context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
        	}
        	return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()), "command", cmd);
		}
    	
    	// This is like set system authority
        getRunServerAsTemplate().execute(new RunAsServerCallback() {                
        	public Object doInSecurityContext() throws Exception
            {            	
            	CommunityEraContext context = contextManager.getContext();
            	User user = context.getCurrentUser();
            	user.clearPhoto();
                return null;
            }
        });
        
        return new ModelAndView("redirect:/pers/myProfile.do?mode=u");
    }

    public static class Command extends CommandBeanImpl implements CommandBean
    {
        private int id;
        public final int getId()
        {
            return id;
        }
        public final void setId(int id)
        {
            this.id = id;
        }
    }
    
    
    public final void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }
}