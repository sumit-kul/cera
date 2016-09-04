package com.era.community.assignment.ui.action;

import java.io.Writer;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.assignment.dao.Assignment;
import com.era.community.assignment.dao.AssignmentEntry;
import com.era.community.assignment.dao.AssignmentFinder;
import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.ImageManipulation;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityActivity;
import com.era.community.communities.dao.CommunityActivityFinder;
import com.era.community.pers.dao.UserActivity;
import com.era.community.pers.dao.UserActivityFinder;
import com.era.community.tagging.dao.Tag;
import com.era.community.tagging.dao.TagFinder;
import com.ibm.json.java.JSONObject;

/** 
 * @spring.bean name="/cid/[cec]/assignment/addAssignment.ajx"
 */
public class AddAssignmentsEntryAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected AssignmentFinder assignmentFinder;
	protected TagFinder tagFinder;
	protected CommunityActivityFinder communityActivityFinder;
	protected UserActivityFinder userActivityFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		HttpServletResponse resp = context.getResponse();
		Command cmd = (Command) data;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);

		Assignment assignment = assignmentFinder.getAssignmentForId(cmd.getTaskId());
		Assignment parent = null;
		if (cmd.getParentId() > 0) {
			parent = assignmentFinder.getAssignmentForId(cmd.getParentId());
		}

		AssignmentEntry assignmentEntry = assignmentFinder.newAssignmentEntry();
		assignmentEntry.setTaskId(cmd.getTaskId());
		assignmentEntry.setAssignmentsId(assignment.getAssignmentsId());
		assignmentEntry.setTitle(cmd.getTitle());
		assignmentEntry.setCreatorId(context.getCurrentUser().getId());
		assignmentEntry.setParentId(cmd.getParentId());
		assignmentEntry.setModified(ts);
		assignmentEntry.setEntryType(cmd.getEntryType()); // 0 - entry & 1 for ToDo
		assignmentEntry.setDueDate(ts);
		assignmentEntry.setCompletedOn(ts);
		assignmentEntry.setBody(ImageManipulation.manageImages(context, cmd.getDescription(), cmd.getTitle(), context.getCurrentUser().getId(), assignmentEntry.getId(), "Assignment"));
		if (parent != null) {
			assignmentEntry.setLvl(parent.getLvl()+1);
		} else {
			assignmentEntry.setLvl(0);
		}
		if (cmd.getDdate() != null && !"".equals(cmd.getDdate())) {
			assignment.setDueDate(convertDateForEvent(cmd.getDdate()));
		}
		assignmentEntry.setRht(0);
		assignmentEntry.setLft(0);
		assignmentEntry.update();

		Community comm = context.getCurrentCommunity();
		comm.setCommunityUpdated(ts);
		comm.update();
		
		CommunityActivity cActivity = communityActivityFinder.newCommunityActivity();
		cActivity.setCommunityId(comm.getId());
		cActivity.setAssignmentId(assignmentEntry.getId());
		cActivity.setUserId(context.getCurrentUser().getId());
		cActivity.update();
		
		UserActivity uActivity = userActivityFinder.newUserActivity();
		uActivity.setUserId(context.getCurrentUser().getId());
		uActivity.setCommunityActivityId(cActivity.getId());
		uActivity.update();

		if (cmd.getParentId() > 0 && cmd.getTaskId() > 0) {
			assignmentFinder.updateForhierarchy(cmd.getParentId(), cmd.getTaskId(), assignmentEntry.getId(), 0);
		}

		int returnId = assignmentFinder.getLastSiblingId(cmd.getParentId(), cmd.getTaskId(), assignmentEntry.getId());
		if (returnId == 0) {
			returnId = cmd.getParentId();
		} 

		int depth = assignmentFinder.getDepthForItem(assignmentEntry.getId());

		//runInBackground(assignmentEntry, context.getCurrentUser(), context);

		if (cmd.getTags() != null) {

			if (!"".equals(cmd.getTags()))
				tagFinder.clearTagsForParentIdUserId(assignmentEntry.getTaskId(), context.getCurrentUser().getId());    

			StringTokenizer st = new StringTokenizer(cmd.getTags(), " ");
			while (st.hasMoreTokens()) {
				String tag = st.nextToken().trim().toLowerCase();
				Tag newTag = tagFinder.newTag();
				newTag.setCommunityId(context.getCurrentCommunity().getId());
				newTag.setTagText(tag);         
				newTag.setPosterId(context.getCurrentUser().getId());
				newTag.setParentId(assignmentEntry.getTaskId());
				newTag.setParentType("AssignmentEntry");
				newTag.update();            
			} 
		}

		JSONObject json = null;
		json = new JSONObject();
		json.put("lastSiblingId", returnId);
		json.put("photoPresent", context.getCurrentUser().isPhotoPresent());
		json.put("posterId", assignmentEntry.getCreatorId());
		json.put("entryId", assignmentEntry.getId());
		json.put("title", assignmentEntry.getTitle());
		json.put("posterName", context.getCurrentUser().getFullName());
		json.put("postedOn", getPostedOn(assignmentEntry.getDatePosted()));
		json.put("postedOnHover", getPostedOnHover(assignmentEntry.getDatePosted()));
		//json.put("likeCount", response.getLikeCountForForumItem());
		//json.put("likeAllowed", isLikeAllowed(response));
		//json.put("alreadyLike", isAlreadyLike(response));
		if (cmd.getParentId() > 0) {
			json.put("parentId", assignmentEntry.getParentId());
			json.put("parentAuthorId", parent.getCreatorId());
			json.put("parentAuthorPhotoPresent", parent.getPoster().isPhotoPresent());
			json.put("parentAuthorName", parent.getPoster().getFullName());
			json.put("parentPostDateOnHover", getPostedOnHover(parent.getDatePosted()));
			json.put("parentPostDateON", getPostedOn(parent.getDatePosted()));
		} else {
			json.put("parentId", 0);
			json.put("parentAuthorId", 0);
			json.put("parentAuthorPhotoPresent", false);
			json.put("parentAuthorName", "");
			json.put("parentPostDateOnHover", "");
			json.put("parentPostDateON", "");
		}

		json.put("body", assignmentEntry.getBody());
		json.put("depth", depth);
		json.put("level", assignmentEntry.getLvl() == null ? 1 : assignmentEntry.getLvl()+1);
		String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
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

	public String getPostedOn(Date datePosted) throws Exception
	{
		SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
		SimpleDateFormat fmt2 = new SimpleDateFormat("dd MMM, yy");
		Date today = new Date();
		String sToday = fmter.format(today);
		if (fmter.format(datePosted).equals(sToday)) {
			return "Today " + fmt.format(datePosted);
		}
		return fmt2.format(datePosted);
	}

	public String getPostedOnHover(Date datePosted) throws Exception
	{
		SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
		return fmt2.format(datePosted);
	}

	public class Command extends IndexCommandBeanImpl implements IndexCommandBean
	{
		private int taskId;
		private int parentId;
		private String title;
		private String description;
		private String tags;
		private int entryType;
		private String ddate;

		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getTags() {
			return tags;
		}
		public void setTags(String tags) {
			this.tags = tags;
		}
		public int getParentId() {
			return parentId;
		}
		public void setParentId(int parentId) {
			this.parentId = parentId;
		}
		public int getEntryType() {
			return entryType;
		}
		public void setEntryType(int entryType) {
			this.entryType = entryType;
		}
		public int getTaskId() {
			return taskId;
		}
		public void setTaskId(int taskId) {
			this.taskId = taskId;
		}
		public String getDdate() {
			return ddate;
		}
		public void setDdate(String ddate) {
			this.ddate = ddate;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setTagFinder(TagFinder tagFinder) {
		this.tagFinder = tagFinder;
	}

	public void setAssignmentFinder(AssignmentFinder assignmentFinder) {
		this.assignmentFinder = assignmentFinder;
	}

	public void setCommunityActivityFinder(
			CommunityActivityFinder communityActivityFinder) {
		this.communityActivityFinder = communityActivityFinder;
	}

	public void setUserActivityFinder(UserActivityFinder userActivityFinder) {
		this.userActivityFinder = userActivityFinder;
	}
}