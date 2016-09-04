package com.era.community.pers.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.ContactDto;

/**
 * 
 * This action is called from a People Finder Result Page and adds the person to the current user's My Contacts list
 * 
 * @spring.bean name="/pers/removeConnection.do"
 */
public class RemoveConnectionAction extends AbstractCommandAction
{
    protected UserFinder userFinder;
    protected ContactFinder contactFinder;
    protected CommunityEraContextManager contextManager;

    protected ModelAndView handle(Object data) throws Exception    
    {
        CommunityEraContext context = contextManager.getContext();
        
        User currentUser = context.getCurrentUser();
        
        if (currentUser == null) {
			String reqUrl = context.getRequestUrl();
			
            if(reqUrl != null) {
            	context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
            }
			return new ModelAndView("redirect:/login.do");
		}
        
        Command cmd = (Command) data;

        contactFinder.deleteUserAsConnection(currentUser.getId(), cmd.getId());

        return REDIRECT_TO_BACKLINK;
    }

    public static class Command extends ContactDto implements CommandBean
    {
        private String userName;

        public String getUserName()
        {
            return userName;
        }

        public void setUserName(String userName)
        {
            this.userName = userName;
        }
    }

    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

    public void setUserFinder(UserFinder userFinder)
    {
        this.userFinder = userFinder;
    }

    public void setContactFinder(ContactFinder contactFinder)
    {
        this.contactFinder = contactFinder;
    }
}
