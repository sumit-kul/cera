package com.era.community.search.ui.action;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.UserSearchDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/search/searchRegisteredUser.ajx" 
 */
public class RegisteredUserSearchAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private UserFinder userFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		HttpServletResponse resp = contextManager.getContext().getResponse();
		JSONObject json = new JSONObject();
		JSONArray jData = new JSONArray();
		try {
			if (cmd.getSearchString() != null && !"".equals(cmd.getSearchString())) {
				List<UserSearchDto> usrList = new ArrayList<UserSearchDto>();
				if (cmd.getPconsId() > 0) {
					usrList = userFinder.searchAuthorsForInput(cmd.getSearchString(), cmd.getPconsId());
				} else {
					usrList = userFinder.searchUsersForInput(cmd.getSearchString(), cmd.getCommunityId());
				}
				
				if (usrList != null && usrList.size() > 0) {
					for (UserSearchDto dto : usrList) {
						JSONObject name = new JSONObject();
						name.put("userId", dto.getId());
						name.put("firstName", dto.getFirstName());
						name.put("lastName", dto.getLastName());
						name.put("emailAddress", dto.getEmailAddress());
						name.put("photoPresent", dto.getPhotoPresent());
						jData.add(name);
					}
				}
			} 
		} catch (ElementNotFoundException e) {
		}
		json.put("bData", jData);
		String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private String searchString;
		private int communityId;
		private int pconsId;

		public String getSearchString() {
			return searchString;
		}

		public void setSearchString(String searchString) {
			this.searchString = searchString;
		}

		public int getCommunityId() {
			return communityId;
		}

		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}

		public int getPconsId() {
			return pconsId;
		}

		public void setPconsId(int pconsId) {
			this.pconsId = pconsId;
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