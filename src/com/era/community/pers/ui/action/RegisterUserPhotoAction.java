package com.era.community.pers.ui.action;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.User;

/**
 * @spring.bean name="/pers/registeruserPhoto.img"
 */
public class RegisterUserPhotoAction extends AbstractCommandAction 
{
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
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()), "command", cmd);
		}

		// Handle the user photo file attachment
		if (cmd.getFile() != null && !cmd.getFile().isEmpty()) {
			User currentUser =  context.getCurrentUser();
			if (cmd.getType() != null && "Cover".equalsIgnoreCase(cmd.getType())) {
				currentUser.storeCover(cmd.getFile().getInputStream());
				currentUser.update();
			} else {
				currentUser.storePhoto(cmd.getFile().getInputStream());
				currentUser.update();
			}
		}
		return null;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int Id;
		private MultipartFile file;
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
}