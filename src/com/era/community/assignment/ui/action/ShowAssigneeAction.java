package com.era.community.assignment.ui.action;

import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.assignment.dao.AssignmentAssigneeLinkFinder;
import com.era.community.assignment.ui.dto.AssignmentAssigneeDto;
import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/assignment/showAssignee.ajx"
 */
public class ShowAssigneeAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected AssignmentAssigneeLinkFinder assignmentAssigneeLinkFinder;

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
        CommunityEraContext context = contextManager.getContext();
        
        HttpServletResponse resp = context.getResponse();
        
        JSONObject json = new JSONObject();
        JSONArray bData = new JSONArray();
		List<AssignmentAssigneeDto> assignees = assignmentAssigneeLinkFinder.getAssigneesForAssignment(cmd.getEntryId(), 0);
		
		for (AssignmentAssigneeDto assignee : assignees) {
			bData.add(toJsonStringForUsers(assignee));
		}
		
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
		private int entryId;

		public int getEntryId() {
			return entryId;
		}

		public void setEntryId(int entryId) {
			this.entryId = entryId;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setAssignmentAssigneeLinkFinder(
			AssignmentAssigneeLinkFinder assignmentAssigneeLinkFinder) {
		this.assignmentAssigneeLinkFinder = assignmentAssigneeLinkFinder;
	}
}