package com.era.community.pers.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.UserDto;

/**
 * @spring.bean name="/pers/registerConfirm.do"
 * 
 * New user registration confirmation action.
 * 
 */
public class RegisterConfirmAction extends AbstractCommandAction
{
    protected CommunityEraContextManager contextManager;
    protected UserFinder userFinder;

    public static class Command extends UserDto implements CommandBean
    {
    }
    
    public final void setUserFinder(UserFinder userFinder)
    {
        this.userFinder = userFinder;
    }

    public final void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }
    
	protected ModelAndView handle(Object data) throws Exception {
		return new ModelAndView("pers/registerConfirm");
	}
}