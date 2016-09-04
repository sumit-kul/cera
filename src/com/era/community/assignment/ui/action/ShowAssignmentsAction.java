package com.era.community.assignment.ui.action;

import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.springframework.web.servlet.ModelAndView;

import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;
import support.community.util.StringHelper;

import com.era.community.assignment.dao.AssignmentFeature;
import com.era.community.assignment.dao.Assignments;
import com.era.community.assignment.ui.dto.AssignmentDto;
import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.tagging.dao.TagFinder;
import com.era.community.tagging.ui.dto.TagDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/** 
 * @spring.bean name="/cid/[cec]/assignment/showAssignments.do"
 */
public class ShowAssignmentsAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected AssignmentFeature assignmentFeature ;
	protected TagFinder tagFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command) data;
		Assignments assignments = (Assignments)assignmentFeature.getFeatureForCurrentCommunity();

		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}

		if (context.getRequest().getParameter("sortByOption") != null 
				&& !"".equals(context.getRequest().getParameter("sortByOption"))) {
			cmd.setSortByOption(context.getRequest().getParameter("sortByOption"));
		} else {
			cmd.setSortByOption("Most Recent");
		}

		QueryScroller scroller; 
		scroller = assignments.listAssignments(cmd.getSortByOption());

		scroller.setBeanClass(RowBean.class, this);
		scroller.setPageSize(cmd.getPageSize());
		//pagination
		cmd.setPageCount(scroller.readPageCount());

		if (pageNum != null) {
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = null;
			if (pNumber > 0) {
				scroller.setBeanClass(RowBean.class, this);
				scroller.setPageSize(cmd.getPageSize());
				IndexedScrollerPage page = scroller.readPage(pNumber);
				json = page.toJsonString(pNumber);
			} else {
				json = new JSONObject();
				json.put("pageNumber", pNumber);
				JSONArray jData = new JSONArray();
				json.put("aData", jData);
			}
			String jsonString = json.serialize();
			resp.setContentType("text/json");
			Writer out = resp.getWriter();
			out.write(jsonString);
			out.close();
			return null;
		} else {
			scroller.setPageSize(cmd.getPageSize());
			cmd.setPageCount(scroller.readPageCount());
			cmd.setRowCount(scroller.readRowCount());
			cmd.setSearchType("Assignments");
	        cmd.setQueryText(assignments.getName());
			return new ModelAndView("assignment/showAssignments");
		}
	}

	public class Command extends IndexCommandBeanImpl implements IndexCommandBean
	{
		private String order;
		private String subjectHeaderClass;
		private String dateHeaderClass;
		private String authorHeaderClass;
		private String sortByOption;

		public boolean isMember() throws Exception
		{
			if (contextManager.getContext().getCurrentUser() == null ) return false;
			if (contextManager.getContext().isUserAuthenticated() == false) return false;
			Community community = contextManager.getContext().getCurrentCommunity();
			if (community == null) return false;
			int currentUser = contextManager.getContext().getCurrentUser().getId();
			return community.isMember(currentUser);
		}

		public List getSortByOptionOptions() throws Exception
		{
			List sortByOptionList = new ArrayList();
			sortByOptionList.add("Most Recent");
			sortByOptionList.add("Subject");
			sortByOptionList.add("Author");
			return sortByOptionList;
		}

		public Assignments getAssignments() throws Exception
		{
			return (Assignments)assignmentFeature.getFeatureForCurrentCommunity();
		}

		public void setOrder(String value) {
			order = value;
		}

		public String getOrder() {
			if (order == null || order.equals("")) return "date";
			return order;
		}

		public String getAuthorHeaderClass()
		{
			return authorHeaderClass;
		}

		public void setAuthorHeaderClass(String authorHeaderClass)
		{
			this.authorHeaderClass = authorHeaderClass;
		}

		public String getDateHeaderClass()
		{
			return dateHeaderClass;
		}

		public void setDateHeaderClass(String dateHeaderClass)
		{
			this.dateHeaderClass = dateHeaderClass;
		}

		public String getSubjectHeaderClass()
		{
			return subjectHeaderClass;
		}

		public void setSubjectHeaderClass(String subjectHeaderClass)
		{
			this.subjectHeaderClass = subjectHeaderClass;
		}

		/**
		 * @return the sortByOption
		 */
		 public String getSortByOption() {
			return sortByOption;
		}

		/**
		 * @param sortByOption the sortByOption to set
		 */
		 public void setSortByOption(String sortByOption) {
			 this.sortByOption = sortByOption;
		 }              
	}

	public class RowBean extends AssignmentDto
	{
		private int resultSetIndex;   
		private String Assigner;
		
		public boolean isAdsPosition()
		{
			return resultSetIndex%6==0;
		}
		
		public String getTaggedKeywords(){
			String taggedKeywords = "";
			try {
				List tags = tagFinder.getTagsForParentTypeByPopularity(this.getId(), 0, 20, "Assignment");
				for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
					TagDto tb = (TagDto) iterator.next();
					String tag = tb.getTagText().trim().toLowerCase();
					taggedKeywords += "<a href='"+contextManager.getContext().getCurrentCommunityUrl()+"/search/searchByTagInCommunity.do?filterTag="+tag+"' class='euInfoSelect normalTip' style='display: inline;' title='Click to filter by tag &#39;"+tag+"&#39;'>"+tag+"</a>";
					if (iterator.hasNext())taggedKeywords += ", ";
				}
			} catch (Exception e) {
				return taggedKeywords;
			}
			return taggedKeywords;
		}
		
		public String getDisplayBody() throws Exception
		{
			if ( (this.getBody()==null) || (this.getBody().length()==0)) return "";

			String sBody = this.getBody();
			sBody = Jsoup.parse(sBody).text();
			sBody = StringHelper.escapeHTML(sBody);

			if (sBody.length() >= 500) {
				sBody = sBody.substring(0, 497).concat("...");
			}
			return sBody;
		}
		
		public String getDisplayDueDate() throws Exception
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			try {

				Date date = formatter.parse(getDueDate());
				return fmt2.format(date);

			} catch (ParseException e) {
				return getDueDate();
			}
		}
		
		public final int getResultSetIndex()
		{
			return resultSetIndex;
		}

		public final void setResultSetIndex(int resultSetIndex)
		{
			this.resultSetIndex = resultSetIndex;
		}

		public String getAssigner() {
			return Assigner;
		}

		public void setAssigner(String assigner) {
			Assigner = assigner;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setTagFinder(TagFinder tagFinder) {
		this.tagFinder = tagFinder;
	}

	public void setAssignmentFeature(AssignmentFeature assignmentFeature) {
		this.assignmentFeature = assignmentFeature;
	}
}