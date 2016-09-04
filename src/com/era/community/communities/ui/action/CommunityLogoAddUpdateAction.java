package com.era.community.communities.ui.action;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.AuthorizationFailedException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;

/**
 * @spring.bean name="/community/commLogoAddUpdate.do"
 * @spring.bean name="/cid/[cec]/community/commLogoAddUpdate.do"
 */
public class CommunityLogoAddUpdateAction extends AbstractCommandAction
{   
	protected CommunityFinder communityFinder;
	protected CommunityEraContextManager contextManager;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data;
		
		CommunityEraContext context = contextManager.getContext();
		
		if (context.getCurrentUser() == null ) {
    		String reqUrl = context.getRequestUrl();
        	if(reqUrl != null) {
        		context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
        	}
        	return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}

		// Handle the logo file attachment
		if (cmd.getFile() != null && !cmd.getFile().isEmpty() && cmd.getCommunityId() != null && !"".equals(cmd.getCommunityId())) {
			Community comm = communityFinder.getCommunityForId(Integer.parseInt(cmd.getCommunityId()));
			
			if (!context.isUserCommunityAdmin()) 
	        	throw new AuthorizationFailedException("You are not authorized to administrate <strong>\'" +comm.getName()+ "\'</strong> community");
			
			MultipartFile file = cmd.getFile();
			if (cmd.getType() != null && "Banner".equalsIgnoreCase(cmd.getType())) {
				//comm.storeCommunityBanner(file.getInputStream());
				comm.storeCommunityBanner(file.getInputStream());
			} else {
				//comm.storeCommunityLogo(file.getInputStream());
				comm.storeCommunityLogo(file.getInputStream());
			}
			comm.update();
		}
		return null;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int Id;
		private MultipartFile file;
		private String communityId;
		private String type;
		
		public final int getId()
		{
			return Id;
		}
		public final void setId(int id)
		{
			Id = id;
		}
		public MultipartFile getFile() {
			return file;
		}
		public void setFile(MultipartFile file) {
			this.file = file;
		}
		public String getCommunityId() {
			return communityId;
		}
		public void setCommunityId(String communityId) {
			this.communityId = communityId;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
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