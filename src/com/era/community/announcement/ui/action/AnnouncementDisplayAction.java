package com.era.community.announcement.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.announcement.dao.Announcement;
import com.era.community.announcement.dao.AnnouncementFinder;
import com.era.community.announcement.ui.dto.AnnouncementDto;
import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 *
 * @spring.bean name="/announcement/announcement-display.do" 
 */
public class AnnouncementDisplayAction extends AbstractCommandAction
{
    public static final String REQUIRES_AUTHENTICATION = "";


    protected CommunityEraContextManager contextManager;
    protected AnnouncementFinder announcementFinder;
    protected UserFinder userFinder;


    protected ModelAndView handle(Object data) throws Exception
    {
        Command cmd = (Command) data;
        CommunityEraContext context = contextManager.getContext();

        context.setH2Header("Announcement");

        Announcement announcement = announcementFinder.getAnnouncementForId(cmd.getId());
        cmd.copyPropertiesFrom(announcement);
        User user = userFinder.getUserEntity(cmd.getAuthorId());
        cmd.setAuthorName(user.getFullName());

        return new ModelAndView("announcement/announcement-display", "command" , cmd);    

    }

    public class Command extends AnnouncementDto implements CommandBean
    {
        
    } 
    
    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

    public final void setAnnouncementFinder(AnnouncementFinder announcementFinder)
    {
        this.announcementFinder = announcementFinder;
    }

    public void setUserFinder(UserFinder userFinder)
    {
        this.userFinder = userFinder;
    }

}
