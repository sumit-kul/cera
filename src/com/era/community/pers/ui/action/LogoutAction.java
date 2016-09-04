package com.era.community.pers.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.LoginSN;
import com.era.community.pers.dao.LoginSNFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/logout.do"
 */
public class LogoutAction extends AbstractCommandAction 
{
	// Injected references
	private CommunityEraContextManager contextManager;
	private UserFinder userFinder;
	private LoginSNFinder loginSNFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data;
		CommunityEraContext context = contextManager.getContext();
		User currentUser = context.getCurrentUser();
		HttpServletResponse resp = context.getResponse();
		JSONObject json = new JSONObject();
		LoginSN loginSN = null;
		if (currentUser != null) {
			try {
				loginSN = loginSNFinder.getLoginSNForLogoutAction(currentUser.getId());
				loginSN.setLogin(false);
				loginSN.update();
			} catch (ElementNotFoundException e) {
			}
			if (loginSN != null) {
				json.put("snType", loginSN.getSnType());
				json.put("loginID", loginSN.getLoginId());
			} else {
				json.put("snType", 0);
				json.put("loginID", 0);
			}
		}
		String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final UserFinder getUserFinder()
	{
		return userFinder;
	}

	public final void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public void setLoginSNFinder(LoginSNFinder loginSNFinder) {
		this.loginSNFinder = loginSNFinder;
	}
}