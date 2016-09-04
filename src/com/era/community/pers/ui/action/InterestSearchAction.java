package com.era.community.pers.ui.action;

import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.Interest;
import com.era.community.pers.dao.InterestFinder;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/search/searchRegisteredUser.ajx" 
 */
public class InterestSearchAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private InterestFinder interestFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		HttpServletResponse resp = contextManager.getContext().getResponse();
		JSONObject json = new JSONObject();
		JSONArray jData = new JSONArray();
		try {
			if (cmd.getSearchString() != null && !"".equals(cmd.getSearchString())) {
				List<Interest> intList = interestFinder.searchInterestsForInput(cmd.getSearchString());
				if (intList != null && intList.size() > 0) {
					for (Iterator iterator = intList.iterator(); iterator.hasNext();) {
						Interest interest = (Interest) iterator.next();
						JSONObject name = new JSONObject();
						name.put("categoryId", interest.getCategoryId());
						name.put("interest", interest.getInterest());
						name.put("interestId", interest.getId());
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

	public void setInterestFinder(InterestFinder interestFinder) {
		this.interestFinder = interestFinder;
	}
}