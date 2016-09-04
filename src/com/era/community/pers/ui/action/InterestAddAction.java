package com.era.community.pers.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.Interest;
import com.era.community.pers.dao.InterestFinder;
import com.era.community.pers.dao.InterestLink;
import com.era.community.pers.dao.InterestLinkFinder;
import com.era.community.pers.dao.User;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/addNewInterest.ajx" 
 */
public class InterestAddAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private InterestFinder interestFinder;
	private InterestLinkFinder interestLinkFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		CommunityEraContext context = contextManager.getContext();
		HttpServletResponse resp = context.getResponse();
		User currUser = context.getCurrentUser();
		if (currUser != null && cmd.getCategoryId() > 0 && cmd.getInterest() != null && !"".equals(cmd.getInterest())) {
			Interest interest = interestFinder.newInterest();
			interest.setInterest(cmd.getInterest());
			interest.setCategoryId(cmd.getCategoryId());
			interest.setCreatorId(currUser.getId());
			interest.setActive(1);
			interest.update();

			InterestLink intLink = interestLinkFinder.newInterestLink();
			intLink.setInterestId(interest.getId());
			intLink.setProfileId(currUser.getId());
			intLink.update();
			
			JSONObject json = new JSONObject();
			json.put("interest", interest.getInterest());
			json.put("interestId", interest.getId());
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
		private String interest;
		private int categoryId;

		public String getInterest() {
			return interest;
		}
		public void setInterest(String interest) {
			this.interest = interest;
		}
		public int getCategoryId() {
			return categoryId;
		}
		public void setCategoryId(int categoryId) {
			this.categoryId = categoryId;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setInterestFinder(InterestFinder interestFinder) {
		this.interestFinder = interestFinder;
	}

	public void setInterestLinkFinder(InterestLinkFinder interestLinkFinder) {
		this.interestLinkFinder = interestLinkFinder;
	}
}