package com.era.community.communities.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.framework.RunAsServerCallback;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;


/**
 * 
 * @spring.bean name="/cid/[cec]/commLogoRemove.img"
 */
public class CommunityLogoRemoveAction extends AbstractCommandAction
{
    /*   
     * Access markers.
     */
    public static final String REQUIRES_AUTHENTICATION = "";

    /*   
     * Injected context reference.
     */
    protected CommunityFinder communityFinder;
    CommunityEraContextManager contextManager;
    
    /*
     * 
     *
     */
    protected ModelAndView handle(Object data) throws Exception    
    {
    	Command cmd = (Command)data;
    	
    	// This is like set system authority
        getRunServerAsTemplate().execute(new RunAsServerCallback() {                
        	public Object doInSecurityContext() throws Exception
            {            	
            	CommunityEraContext context = contextManager.getContext();
            	Community comm = context.getCurrentCommunity();
            	comm.clearLogo();
                return null;
            }
        });
        
        return new ModelAndView("redirect:/cid/"+contextManager.getContext().getCurrentCommunity().getId()+"/community/editCommunity.do");
    }

    public static class Command extends CommandBeanImpl implements CommandBean
    {
        private int id;
        public final int getId()
        {
            return id;
        }
        public final void setId(int id)
        {
            this.id = id;
        }
    }
    
    
    public final void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }


    public final void setCommunityFinder(CommunityFinder communityFinder)
    {
        this.communityFinder = communityFinder;
    }


}
