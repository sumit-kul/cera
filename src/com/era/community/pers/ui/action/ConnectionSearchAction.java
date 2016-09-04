package com.era.community.pers.ui.action;

import java.io.Writer;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.UserSearchDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/connectionSearch.ajx" 
 */
public class ConnectionSearchAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private UserFinder userFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command)data; 
		User currUser = context.getCurrentUser();
		HttpServletResponse resp = context.getResponse();
		try {
			if (currUser != null && cmd.getSearchString() != null && !"".equals(cmd.getSearchString())) {
				List<UserSearchDto> intList = userFinder.searchInAllConnections(currUser.getId(), cmd.getSearchString());
				JSONObject json = toJsonString(intList);
				String jsonString = json.serialize();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(jsonString);
				out.close();
			} 
		} catch (ElementNotFoundException e) {
		}
		return null;
	}
	
	private JSONObject toJsonString(List<UserSearchDto> dtos) throws Exception
	{
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();
		for (UserSearchDto usrdto : dtos) {
			JSONObject row = new JSONObject();
			for (Method m : usrdto.getClass().getMethods()) {
				String name = m.getName(); 
				if (name.startsWith("get")) {
					if (name.equals("getClass")) continue;
					row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(usrdto, new Object[] {}));
				}            
			}
			data.add(row);
		}
		json.put("aData", data);
		return json;
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private String searchString;

		public String getSearchString() {
			return searchString;
		}

		public void setSearchString(String searchString) {
			this.searchString = searchString;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}
}