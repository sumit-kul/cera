package com.era.community.admin.ui.action;
import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.UserFinder;

/**
 * Deletes unvalidated users older than 3 months
 *
 * @spring.bean name="/admin/delete-unvalidated-users.do"
 */
public class UnvalidatedUsersDeleteAction extends AbstractCommandAction
{

    public static final String REQUIRES_AUTHENTICATION = "";
    
    /* Injected references */
    private CommunityEraContextManager contextManager;
    private UserFinder userFinder; 
    
    protected ModelAndView handle(Object data) throws Exception
    {
        Command cmd = (Command) data;
        CommunityEraContext context = contextManager.getContext();
        
        if (!context.isUserSysAdmin())
            throw new Exception("You are not authorized to view this information");
        
        //userFinder.deleteOneMonthOldUnvalidatedUsers();
        
            return new ModelAndView("admin/unvalidated-users-delete-confirm");
   
    }
    

    public class Command extends CommandBeanImpl implements CommandBean
    {
    }
    
    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }
    public void setUserFinder(UserFinder userFinder)
    {
        this.userFinder = userFinder;
    }
}
