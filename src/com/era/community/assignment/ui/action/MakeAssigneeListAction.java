package com.era.community.assignment.ui.action;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.assignment.ui.dto.AssignmentAssigneeDto;
import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/assignment/makeAssigneeList.ajx"
 */
public class MakeAssigneeListAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected CommunityFinder communityFinder; 
	protected UserFinder userFinder;

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
        CommunityEraContext context = contextManager.getContext();
        
        HttpServletResponse resp = context.getResponse();
        User user = context.getCurrentUser();
        
        JSONObject json = new JSONObject();
        JSONArray bData = new JSONArray();
		Community comm = communityFinder.getCommunityForId(cmd.getCommunityId());
		
		List<AssignmentAssigneeDto> assignees = new ArrayList();
		if (comm != null) {
			assignees = userFinder.getAssigneeListForCommunityEssignment(comm.getId(), user.getId(), 0);
		}

		for (AssignmentAssigneeDto assignee : assignees) {
			bData.add(toJsonStringForUsers(assignee));
		}
		
		json.put("commName", comm.getName());
		json.put("commId", comm.getId());
		json.put("photoPresent", comm.isLogoPresent());
		json.put("bData", bData);
		String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
	}
	
	private JSONObject toJsonStringForUsers(AssignmentAssigneeDto assignee) throws Exception
    {
    	JSONObject row = new JSONObject();
    	row.put("name", assignee.getFirstName() + " " + assignee.getLastName());
    	row.put("photoPresent", assignee.isPhotoPresent());
    	row.put("userId", assignee.getUserId());
    	row.put("assigned", assignee.getAssigned());
    	return row;
    }

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int userId;
		private int communityId;
		private int eventId;

		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public int getCommunityId() {
			return communityId;
		}
		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}
		public int getEventId() {
			return eventId;
		}
		public void setEventId(int eventId) {
			this.eventId = eventId;
		}
	}

	public void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public UserFinder getUserFinder() {
		return userFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}
}