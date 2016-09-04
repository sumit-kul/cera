package com.era.community.assignment.ui.action;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.assignment.dao.AssignmentAssigneeLink;
import com.era.community.assignment.dao.AssignmentAssigneeLinkFinder;
import com.era.community.assignment.ui.dto.AssignmentAssigneeDto;
import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.User;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/assignment/addAssignee.ajx"
 */
public class AddAssigneeAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected AssignmentAssigneeLinkFinder assignmentAssigneeLinkFinder;

	@Override
	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();

		HttpServletResponse resp = context.getResponse();
		User user = context.getCurrentUser();

		JSONObject json = new JSONObject();
		List<Integer> invList = new ArrayList();
		List<Integer> deletedList = new ArrayList();
		if (cmd.getAssignees() != null && !"".equals(cmd.getAssignees())) {
			StringTokenizer outer = new StringTokenizer(cmd.getAssignees(), ",");
			while (outer.hasMoreTokens()) {
				String assId = outer.nextToken().trim();
				if (assId != null && !"".equals(assId)) {
					try {
						invList.add(Integer.valueOf(assId));
					} catch (NumberFormatException e) {
						// Do nothing, process next record...
					}
				}
			}
		}

		List<AssignmentAssigneeDto> existings = assignmentAssigneeLinkFinder.getAssigneesForAssignment(cmd.getEntryId(), 0);
		for (AssignmentAssigneeDto dto : existings) {
			boolean deleted = true;
			for (Integer id : invList) {
				if (dto.getUserId() == id) {
					deleted = false;
					break;
				}
			}
			if (deleted) {
				deletedList.add(dto.getUserId());
			}
		}

		if (invList.size() > 0) {
			int guestCounter = 0;
			for (Integer addId : invList) {
				try {
					assignmentAssigneeLinkFinder.getAssignmentAssigneeLinkForAssignmentAndUser(cmd.getEntryId(), addId);
				} catch (ElementNotFoundException e) {
					AssignmentAssigneeLink link = assignmentAssigneeLinkFinder.newAssignmentAssigneeLink();
					link.setAssignmentId(cmd.getEntryId());
					link.setUserId(addId);
					link.update();
					guestCounter ++;
				}
			}
		}
		
		if (deletedList.size() > 0) {
			for (Integer delId : deletedList) {
				try {
					AssignmentAssigneeLink lnk = assignmentAssigneeLinkFinder.getAssignmentAssigneeLinkForAssignmentAndUser(cmd.getEntryId(), delId);
					lnk.delete();
				} catch (ElementNotFoundException e) {
				}
			}
		}

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
		row.put("photoPresant", assignee.isPhotoPresent());
		row.put("userId", assignee.getUserId());
		row.put("assigned", assignee.getAssigned());
		return row;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int entryId;
		private String assignees;
		public int getEntryId() {
			return entryId;
		}
		public void setEntryId(int entryId) {
			this.entryId = entryId;
		}
		public String getAssignees() {
			return assignees;
		}
		public void setAssignees(String assignees) {
			this.assignees = assignees;
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