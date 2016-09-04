package com.era.community.communities.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;

/**
 * @spring.bean name="/cid/[cec]/comm/feature-community.do"
 * TODO need to add later
 */
public class CommunityFeatureAction extends AbstractCommandAction
{
    private CommunityEraContextManager contextManager;
    protected CommunityFinder communityFinder; 
    
    protected ModelAndView handle(Object data) throws Exception
    {
        Command cmd = (Command) data;
        CommunityEraContext context = contextManager.getContext();
        
        if (!context.isUserSysAdmin()) throw new Exception("Not authorized");
        
        Community comm = communityFinder.getCommunityForId(cmd.getId());

        // If the action is '1' we want to remove the community from the featured list
        if (comm !=null & cmd.getAction()==1) {
        	comm.setFeatured(false);
            comm.update();
            return (new ModelAndView("redirect:/home.do"));
        }

        
        if ( comm != null && cmd.getAction()!=1 ) {
           // comm.setFeatured(true);
            comm.update();
            return (new ModelAndView("redirect:/home.do"));
        }
        else {
            return null;
        }
    }

    public static class Command extends CommandBeanImpl implements CommandBean
    {
        private int id;
        private int action;

        public final int getAction()
        {
            return action;
        }
        public final void setAction(int action)
        {
            this.action = action;
        }
        public int getId()
        {
            return id;
        }
        public void setId(int id)
        {
            this.id = id;
        }
    }

    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }
    
    public void setCommunityFinder(CommunityFinder communityFinder)
    {
        this.communityFinder = communityFinder;
    }
}