package com.era.community.communities.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.communities.ui.dto.CommunityDto;
/**
 * @spring.bean name="/cid/[cec]/community/changeToProtected.do" 
 */
public class ChangeToProtectedAction extends AbstractCommandAction
{
    CommunityFinder communityFinder;
    CommunityEraContextManager contextManager;

    public final void setCommunityFinder(CommunityFinder communityFinder)
    {
        this.communityFinder = communityFinder;
    }

    protected ModelAndView handle(Object data) throws Exception
    {
        Command cmd = (Command)data;
        Community community;

        if (cmd.getId()==0)
            return null;
        else
            community=communityFinder.getCommunityForId(cmd.getId());

        if (community.getCommunityType().equalsIgnoreCase("Private")) {
            community.setSysType(community, "ProtectedCommunity");
        }

        return new ModelAndView("redirect:"+contextManager.getContext().getCurrentCommunityUrl()+"/community/editCommunity.do");
    }

    public static class Command extends CommunityDto implements CommandBean
    {
    }

    public final void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

}
