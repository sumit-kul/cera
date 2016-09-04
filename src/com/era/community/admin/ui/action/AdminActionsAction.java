package com.era.community.admin.ui.action;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import org.springframework.web.servlet.ModelAndView;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;

/**
 *
 * @spring.bean name="/admin/actions.do" 
 */
public class AdminActionsAction extends AbstractCommandAction
{

    public static final String REQUIRES_AUTHENTICATION = "";
    
    private CommunityEraContextManager contextManager;
    
    protected ModelAndView handle(Object data) throws Exception
    {
        CommunityEraContext context = contextManager.getContext();
        
        if (!context.isUserSysAdmin())
            throw new Exception("You are not authorized to view this page");
   
        return new ModelAndView("admin/adminActions");   
    }

    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }
    
    public class Command extends CommandBeanImpl implements CommandBean
    {       
    }
}