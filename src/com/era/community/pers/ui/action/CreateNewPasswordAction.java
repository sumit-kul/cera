package com.era.community.pers.ui.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.framework.CommandValidator;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.CecUserDetailsService.LoginFormBean;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.UserDto;

/**
 * @spring.bean name="/pers/createNewPassword.do"
 */
public class CreateNewPasswordAction extends AbstractFormAction 
{
	protected UserFinder userFinder;
	protected CommunityEraContextManager contextManager; 

	protected String getView()
	{
		return "pers/createNewPassword";
	}

	protected void onDisplay(Object data) throws Exception
	{
		if (contextManager.getContext().getCurrentUser() == null) {
			String emailAddress = (String)contextManager.getContext().getRequest().getSession().getAttribute("emailAddress");
			String changeKey = (String)contextManager.getContext().getRequest().getSession().getAttribute("changeKey");
			contextManager.getContext().getRequest().getSession().removeAttribute("emailAddress");
			contextManager.getContext().getRequest().getSession().removeAttribute("changeKey");
			Command cmd = (Command)data;
			cmd.setEmailAddress(emailAddress);
			cmd.setChangeKey(changeKey);
		}
	}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		final Command cmd = (Command)data;
		User user = null;
		if (contextManager.getContext().getCurrentUser() == null) {
			if (cmd.getEmailAddress() == null || "".equals(cmd.getEmailAddress()) 
					|| cmd.getChangeKey() == null || "".equals(cmd.getChangeKey())) {
				return new ModelAndView("pers/changePassExpired");
			}

			user = userFinder.getUserForEmailAddress(cmd.getEmailAddress());
			if (user.getChangeKey() == null || !cmd.getChangeKey().equals(user.getChangeKey())) { 
				//Change password from mail is only allowed once.
				return new ModelAndView("pers/changePassExpired");
			}
		} else {
			user = contextManager.getContext().getCurrentUser();
			if (!(cmd.getEmailAddress() == null || "".equals(cmd.getEmailAddress()))
					&& !user.getEmailAddress().equals(cmd.getEmailAddress()))
					return new ModelAndView("pers/changePassExpired");;
		}

		user.setPassword(cmd.getPassword());
		user.setChangeKey(null);
		user.update();

		//contextManager.getContext().getRequest().getSession().removeAttribute("emailAddress");
		//contextManager.getContext().getRequest().getSession().removeAttribute("changeKey");

		LoginFormBean bean = new LoginFormBean();
		bean.setLoginMessage("You have successfully reset your password");
		contextManager.getContext().getRequest().getSession().setAttribute("sucsFMyPassword", bean);
		return new ModelAndView("redirect:/jlogout.do");
	}

	protected Map referenceData(Object data) throws Exception
	{
		return new HashMap();
	}

	public class Validator extends com.era.community.pers.dao.generated.UserValidator
	{
		public String validatePassword(Object value, CommandBeanImpl data) throws Exception
		{
			if (isNullOrWhitespace(value) || value.toString().equals("Password")) return "Please enter a password";
	        if (value.toString().length()<6) return "Please enter a password of at least 6 characters";
	        if (value.toString().trim().length()> 12) return "Password length cannot exceed 12 characters";

			Command cmd = (Command) data;
			if (cmd.hasRequestDataFor("password")) {
				if (!cmd.getPassword().equals(cmd.getConfirmPassword()))
					return "Password and confirmation do not match";
			}

			return null;
		}
	}

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	public static class Command extends UserDto implements CommandBean
	{
		private String confirmPassword;

		public String getConfirmPassword() {
			return confirmPassword;
		}

		public void setConfirmPassword(String confirmPassword) {
			this.confirmPassword = confirmPassword;
		}
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}
}