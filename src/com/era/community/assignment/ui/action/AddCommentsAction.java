package com.era.community.assignment.ui.action;

import java.io.Writer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.assignment.dao.AssignmentComment;
import com.era.community.assignment.dao.AssignmentCommentFinder;
import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.User;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/assignment/addComments.ajx" 
 */
public class AddCommentsAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private AssignmentCommentFinder assignmentCommentFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command)data; 
		User currentUser = context.getCurrentUser();
		HttpServletResponse resp = contextManager.getContext().getResponse();
		JSONObject json = new JSONObject();
		
		if (currentUser != null && cmd.getComment() != null && !"".equals(cmd.getComment())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			String dt = sdf.format(now);
			Timestamp ts = Timestamp.valueOf(dt);
			AssignmentComment comment = assignmentCommentFinder.newAssignmentComment();
			comment.setAssignmentId(cmd.getEntryId());
			comment.setComment(cmd.getComment());
			comment.setPosterId(currentUser.getId());
			comment.setDatePosted(ts);		
			comment.update();
			json.put("id", comment.getId());
			
			
			if (comment.getId() > 0) {
				json.put("entryId", cmd.getEntryId());
				json.put("comment", cmd.getComment());
				json.put("datePosted", "now");
				json.put("posterId", currentUser.getId());
				json.put("posterName", currentUser.getFullName());
				json.put("photoPresent", currentUser.isPhotoPresent());
				
				String jsonString = json.serialize();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(jsonString);
				out.close();
			}
		}
		return null;
	}

	public class Command extends IndexCommandBeanImpl implements IndexCommandBean
	{
		private int entryId;
		private int communityId;
		private String comment = "";

		public int getEntryId() {
			return entryId;
		}
		public void setEntryId(int entryId) {
			this.entryId = entryId;
		}
		public int getCommunityId() {
			return communityId;
		}
		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setAssignmentCommentFinder(
			AssignmentCommentFinder assignmentCommentFinder) {
		this.assignmentCommentFinder = assignmentCommentFinder;
	}
}