package com.era.community.pers.ui.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 * Change password for the user
 * @spring.bean name="/pers/changePass.do"
 */
public class ChangePasswordAction extends AbstractCommandAction
{
	protected UserFinder userFinder;
	protected CommunityEraContextManager contextManager;
	public final static long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;

	protected ModelAndView handle(Object data) throws Exception    
	{
		contextManager.getContext().getRequest().getSession().removeAttribute("cecFMyPassword");
		Command cmd = (Command)data;
		User user = null;
		if (contextManager.getContext().getCurrentUser() == null) {
			try {
				user = userFinder.getUserEntity(cmd.getId());
				if (user == null) {
		        	return new ModelAndView("/pageNotFound");
				}
				Date today = new Date();
				boolean moreThanDay = Math.abs(today.getTime() - user.getDateDeactivated().getTime()) > MILLIS_PER_DAY;
				if (moreThanDay) {
					HttpServletRequest request = contextManager.getContext().getRequest();
					String failmsg = "The reset link you clicked has expired. Please request a new one.";
					cmd.setFailMsg(failmsg);
					request.getSession().setAttribute("failmsg", failmsg);
					return new ModelAndView("redirect:/pers/fPassword.do", "command" , cmd);
				}
				
				if (cmd.getMid() != user.getDateRegistered().getTime() || cmd.getKey() == null 
						|| "".equals(cmd.getKey()) || !cmd.getKey().equals(user.getChangeKey())){
					return new ModelAndView("pers/changePassExpired");
				}
				contextManager.getContext().getRequest().getSession().setAttribute("emailAddress", user.getEmailAddress());
				contextManager.getContext().getRequest().getSession().setAttribute("changeKey", cmd.getKey());
			} catch (ElementNotFoundException e) {
				return new ModelAndView("/pageNotFound");
			}
		}
		
		return new ModelAndView("redirect:/pers/createNewPassword.do");
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int id;
		private long mid;
		private String key;

		private String emailAddress;
		private String password;
		private String loginMessage;
		private String failMsg = "";

		public final int getId()
		{
			return id;
		}
		public final void setId(int id)
		{
			this.id = id;
		}
		public final String getEmailAddress()
		{
			return emailAddress;
		}
		public final void setEmailAddress(String emailAddress)
		{
			this.emailAddress = emailAddress;
		}
		public final String getPassword()
		{
			return password;
		}
		public final void setPassword(String password)
		{
			this.password = password;
		}
		public final String getLoginMessage()
		{
			return loginMessage;
		}
		public final void setLoginMessage(String loginMessage)
		{
			this.loginMessage = loginMessage;
		}
		public long getMid() {
			return mid;
		}
		public void setMid(long mid) {
			this.mid = mid;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getFailMsg() {
			return failMsg;
		}
		public void setFailMsg(String failMsg) {
			this.failMsg = failMsg;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}
}