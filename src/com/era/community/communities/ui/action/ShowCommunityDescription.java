package com.era.community.communities.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;


/**
 * 
 * Site Home page "Welcome" page
 *
 * @spring.bean name="/community/showCommunityDescription.ajx"
 */
public class ShowCommunityDescription extends AbstractCommandAction 
{
    protected CommunityEraContextManager contextManager;
    protected CommunityFinder communityFinder;
        
    protected ModelAndView handle(Object data) throws Exception
    {
        Command cmd = (Command) data;
        
        CommunityEraContext context = contextManager.getContext();
        HttpServletResponse resp = context.getResponse();
        Community com = communityFinder.getCommunityForId(cmd.getCommunityId());
		resp.setContentType("text/html");
		Writer out = resp.getWriter();
		out.write(com.getDescription());
		out.close();
		return null;
    }
    
    public class Command extends IndexCommandBeanImpl implements CommandBean
    {
        private int communityId;

		public int getCommunityId() {
			return communityId;
		}

		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}
    }

    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}
}