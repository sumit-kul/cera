package com.era.community.pers.ui.action;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.User;

/**
 *  @spring.bean name="/pers/accountSettings.do" 
 */
public class AccountSettingsAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		CommunityEraContext context = contextManager.getContext();
		User currentUser = context.getCurrentUser();
		if (currentUser == null ) {
        	String reqUrl = context.getRequestUrl();
        	if(reqUrl != null) {
        	    context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
        	}
        	return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		} else if (!currentUser.isValidated()) {
			return new ModelAndView("/pageNotFound");
		} else {
			cmd.setQueryText(currentUser.getFullName());
			cmd.setSearchType("People");
			cmd.setUser(currentUser);
			cmd.setEmailAddress(currentUser.getEmailAddress());
		}
		return new ModelAndView("/pers/accountSettings");
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private User user;
		private String emailAddress;
		private String password;
		private String confirmPassword;

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public String getEmailAddress() {
			return emailAddress;
		}

		public void setEmailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getConfirmPassword() {
			return confirmPassword;
		}

		public void setConfirmPassword(String confirmPassword) {
			this.confirmPassword = confirmPassword;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}
}