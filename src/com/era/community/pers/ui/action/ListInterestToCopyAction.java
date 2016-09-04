package com.era.community.pers.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.EntityWrapper;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.InterestFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.generated.InterestEntity;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/listInterestToCopy.ajx" 
 */
public class ListInterestToCopyAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private InterestFinder interestFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command)data; 
		User currUser = context.getCurrentUser(); 
		String jsonString = "";
		HttpServletResponse resp = context.getResponse();
		JSONObject json = null;
		try {
			if (cmd.getProfileId() > 0) {
				QueryScroller scroller = interestFinder.getInterestsForProfileId(cmd.getProfileId(), currUser.getId());
				scroller.setBeanClass(RowBean.class, this);
				scroller.setPageSize(999);
				IndexedScrollerPage page = scroller.readPage(1);
				json = page.toJsonString(1);
				jsonString = json.serialize();
			} 
		} catch (ElementNotFoundException e) {
		}
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int profileId;

		public int getProfileId() {
			return profileId;
		}

		public void setProfileId(int profileId) {
			this.profileId = profileId;
		}
	}
	
	public class RowBean extends InterestEntity implements EntityWrapper
	{
		private int resultSetIndex;
		private int selected;
		
		public boolean isEvenRow()
		{
			return resultSetIndex % 2 == 0;
		}

		public boolean isOddRow()
		{
			return resultSetIndex % 2 == 1;
		}

		public final int getResultSetIndex()
		{
			return resultSetIndex;
		}

		public final void setResultSetIndex(int resultSetIndex)
		{
			this.resultSetIndex = resultSetIndex;
		}

		public int getSelected() {
			return selected;
		}

		public void setSelected(Long selected) {
			this.selected = selected.intValue();
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