package com.era.community.admin.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.dao.generated.UserEntity;

/**
 *
 * @spring.bean name="/admin/withdraw-admin-rights.do"
 */
public class WithdrawAdministrationRightsAction extends AbstractCommandAction
{

    public static final String REQUIRES_AUTHENTICATION = "";

    /* Injected references */
    private CommunityEraContextManager contextManager;
    protected UserFinder userFinder;

    protected ModelAndView handle(Object data) throws Exception
    {
        Command cmd = (Command) data;
        CommunityEraContext context = contextManager.getContext();
        User user = userFinder.getUserEntity(cmd.getId());
        cmd.copyPropertiesFrom(user);
        context.setH2Header(user.getFullName());
        if ( user != null && user.isWriteAllowed( context.getCurrentUserDetails() )) {
            user.setSystemAdministrator(false);
            user.update();
            return new ModelAndView("admin/withdraw-admin-rights-confirm");
        }
        else {
            return null;
        }
    }

    public class Command extends UserEntity implements CommandBean
    {
    }

    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

    public final void setUserFinder(UserFinder userFinder)
    {
        this.userFinder = userFinder;
    }

}
