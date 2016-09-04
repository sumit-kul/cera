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
import com.era.community.pers.dao.InterestLink;
import com.era.community.pers.dao.InterestLinkFinder;
import com.era.community.pers.dao.User;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/makeMyInterest.ajx" 
 */
public class MakeMyInterestAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private InterestLinkFinder interestLinkFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		CommunityEraContext context = contextManager.getContext();
		HttpServletResponse resp = context.getResponse();
		User currUser = context.getCurrentUser();
		if (currUser != null) {
			InterestLink intLink = null;
			if (cmd.getInterestId() > 0) {
				try {
					interestLinkFinder.getInterestLinkForInterestIdAndProfileId(cmd.getInterestId(), currUser.getId());
				} catch (ElementNotFoundException ex) {
					intLink = interestLinkFinder.newInterestLink();
					intLink.setInterestId(cmd.getInterestId());
					intLink.setProfileId(currUser.getId());
					intLink.update();
				}
			}
			String[] myJsonData = context.getRequest().getParameterValues("json[]");
			if(myJsonData != null && myJsonData.length > 0) {
				for (int i=0; i < myJsonData.length; ++i) {
					String intId = myJsonData[i];
					int interestId = Integer.parseInt(intId);
					try {
						interestLinkFinder.getInterestLinkForInterestIdAndProfileId(interestId, currUser.getId());
					} catch (ElementNotFoundException ex) {
						intLink = interestLinkFinder.newInterestLink();
						intLink.setInterestId(interestId);
						intLink.setProfileId(currUser.getId());
						intLink.update();
					}
				}
			}

			if (intLink != null) {
				JSONObject json = new JSONObject();
				json.put("addedInterestId", intLink.getId());
				String jsonString = json.serialize();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(jsonString);
				out.close();
			}
		}
		return null;
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int interestId;
		private String[] selectedInt;

		public int getInterestId() {
			return interestId;
		}

		public void setInterestId(int interestId) {
			this.interestId = interestId;
		}

		public String[] getSelectedInt() {
			return selectedInt;
		}

		public void setSelectedInt(String[] selectedInt) {
			this.selectedInt = selectedInt;
		}
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setInterestLinkFinder(InterestLinkFinder interestLinkFinder) {
		this.interestLinkFinder = interestLinkFinder;
	}
}