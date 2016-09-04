package com.era.community.announcement.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.announcement.dao.Announcement;
import com.era.community.announcement.dao.AnnouncementFinder;
import com.era.community.announcement.ui.dto.AnnouncementDto;
import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;

/**
 *
 * @spring.bean name="/announcement/announcement-change-status.do"
 */
public class AnnouncementChangeStatusAction extends AbstractCommandAction
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
        	//return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}
        if ( announcement != null && context.getCurrentUser() != null && context.getCurrentUser().isSystemAdministrator()) {
            if (cmd.getActionType().equals("live"))
                announcement.setStatus(Announcement.STATUS_LIVE);
            
            if (cmd.getActionType().equals("archive"))
                announcement.setStatus(Announcement.STATUS_ARCHIVED);
            
            announcement.update();
            cmd.copyPropertiesFrom(announcement);
            
            return new ModelAndView("redirect:/announcement/announcement-display.do?id=" + cmd.getId());
        }
        else {
            return null;
        }
    }

    public static class Command extends AnnouncementDto implements CommandBean
    {
        private String actionType;
        
        public String getActionType()
        {
            return actionType;
        }
        public void setActionType(String actionType)
        {
            this.actionType = actionType;
        }
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
