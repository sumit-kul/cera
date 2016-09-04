package com.era.community.announcement.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.util.StringFormat;

import com.era.community.announcement.dao.Announcement;
import com.era.community.announcement.dao.AnnouncementFinder;
import com.era.community.announcement.ui.dto.AnnouncementDto;
import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;

/**
 *
 * @spring.bean name="/announcement/announcement-delete.do"
 */
public class AnnouncementDeleteAction extends AbstractCommandAction
{

    public static final String REQUIRES_AUTHENTICATION = "";
    
    /* Injected references */
    private CommunityEraContextManager contextManager;
    private AnnouncementFinder announcementFinder; 
    
    protected ModelAndView handle(Object data) throws Exception
    {
        Command cmd = (Command) data;
        CommunityEraContext context = contextManager.getContext();
        Announcement announcement = announcementFinder.getAnnouncementForId(cmd.getId());
        if (context.getCurrentUser() == null ) {
        	String reqUrl = context.getRequestUrl();
        	if(reqUrl != null) {
        	  context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
        	}
        	return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}
        if ( announcement != null && context.getCurrentUser().isSystemAdministrator()) {
            cmd.copyPropertiesFrom(announcement);
            announcement.delete();
            return new ModelAndView("announcement/announcement-delete-confirm", "command" , cmd);
        }
        else {
            return null;
        }
    }

    public static class Command extends AnnouncementDto implements CommandBean
    {
    }
    
    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }
    public void setAnnouncementFinder(AnnouncementFinder announcementFinder)
    {
        this.announcementFinder = announcementFinder;
    }
}
