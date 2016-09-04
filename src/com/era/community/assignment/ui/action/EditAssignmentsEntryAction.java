package com.era.community.assignment.ui.action;

import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.assignment.dao.Assignment;
import com.era.community.assignment.dao.AssignmentFinder;
import com.era.community.assignment.ui.dto.AssignmentDto;
import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.ImageManipulation;
import com.era.community.pers.dao.User;
import com.ibm.json.java.JSONObject;

/** 
 * @spring.bean name="/cid/[cec]/assignment/addAssignment.ajx"
 */
public class EditAssignmentsEntryAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected AssignmentFinder assignmentFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		CommunityEraContext context = contextManager.getContext();
		HttpServletResponse resp = context.getResponse();
		User currUser = context.getCurrentUser();
		if (currUser != null) {
			Assignment assignment = assignmentFinder.getAssignmentForId(cmd.getId());
			if (assignment != null && assignment.getCreatorId() == currUser.getId()) {
				if (cmd.getDdate() != null && !"".equals(cmd.getDdate())) {
					assignment.setDueDate(convertDateForEvent(cmd.getDdate()));
				}
				assignment.setTitle(cmd.getTitle());
				assignment.setBody(ImageManipulation.manageImages(context, cmd.getBody(), cmd.getTitle(), context.getCurrentUser().getId(), assignment.getId(), "Assignment"));
				assignment.update();
			}
			JSONObject json = new JSONObject();
			String jsonString = json.serialize();
			resp.setContentType("text/json");
			Writer out = resp.getWriter();
			out.write(jsonString);
			out.close();
		}
		return null;
	}
	
	private Date convertDateForEvent(String inDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm a");
		String time = "00:00 AM";
		Date date;
		try {
			date = (Date)sdf.parse(inDate + " " + time);
		} catch (ParseException e) {
			return null;
		}
		return date;
	}

	public class Command extends AssignmentDto implements CommandBean
	{
		
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setAssignmentFinder(AssignmentFinder assignmentFinder) {
		this.assignmentFinder = assignmentFinder;
	}
}