package com.era.community.pers.ui.action;

import java.io.Writer;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserPrivacy;
import com.era.community.pers.dao.UserPrivacyFinder;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/updatePrivacy.ajx" 
 */
public class UpdatePrivacyAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private UserPrivacyFinder userPrivacyFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		CommunityEraContext context = contextManager.getContext();
		HttpServletResponse resp = context.getResponse();
		User currUser = context.getCurrentUser();
		if (currUser != null && cmd.getField() != null && !"".equals(cmd.getField())) {
			UserPrivacy userPrivacy;
			try {
				userPrivacy = userPrivacyFinder.getUserPrivacyForUserId(currUser.getId());
			} catch (ElementNotFoundException e) {
				userPrivacy = userPrivacyFinder.newUserPrivacy();
				userPrivacy.setUserId(currUser.getId());
			}
			
			for (Method m : userPrivacy.getClass().getMethods()) {
				String name = m.getName(); 
				if (name.equalsIgnoreCase("set"+cmd.getField())) {
					m.invoke(userPrivacy, new Object[] {cmd.getPrivacy()} );
				}
			}  
			
			userPrivacy.update();
			
			JSONObject json = new JSONObject();
			String jsonString = json.serialize();
			resp.setContentType("text/json");
			Writer out = resp.getWriter();
			out.write(jsonString);
			out.close();
		}
		return null;
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private String field;
		private int privacy;
		
		public String getField() {
			return field;
		}
		public void setField(String field) {
			this.field = field;
		}
		public int getPrivacy() {
			return privacy;
		}
		public void setPrivacy(int privacy) {
			this.privacy = privacy;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setUserPrivacyFinder(UserPrivacyFinder userPrivacyFinder) {
		this.userPrivacyFinder = userPrivacyFinder;
	}
}