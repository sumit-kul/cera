package com.era.community.blog.ui.action;

import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.EntityWrapper;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.blog.dao.BlogAuthorFinder;
import com.era.community.blog.dao.CommunityBlog;
import com.era.community.blog.dao.CommunityBlogFeature;
import com.era.community.communities.dao.Community;
import com.era.community.connections.communities.ui.dto.MemberInvitationDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/** 
 * @spring.bean name="/cid/[cec]/blog/showBlogAuthors.do"
 */
public class ShowBlogAuthorsAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected CommunityBlogFeature communityBlogFeature;
	protected BlogAuthorFinder blogAuthorFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		Community currComm = context.getCurrentCommunity();
		
		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}

		if (context.getRequest().getParameter("sortByOption") != null 
				&& !"".equals(context.getRequest().getParameter("sortByOption"))) {
			cmd.setSortByOption(context.getRequest().getParameter("sortByOption"));
		}
		
		CommunityBlog cons = (CommunityBlog)communityBlogFeature.getFeatureForCommunity(currComm);
		QueryScroller scroller =  blogAuthorFinder.getBlogAuthorsListForBlog(cons.getId(), cmd.getSortByOption());

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
			cmd.setCommunity(currComm);
			scroller.setPageSize(cmd.getPageSize());
			cmd.setPageCount(scroller.readPageCount());
			cmd.setRowCount(scroller.readRowCount());
			scroller.setBeanClass(RowBean.class, this);
			cmd.setScrollerPage(scroller.readPage(cmd.getPage()));
			cmd.setPage(cmd.getPage() + 1);
			return new ModelAndView("blog/showCommBlogAuthors");
		}

	}

	public class RowBean extends MemberInvitationDto implements EntityWrapper
	{      
		private int resultSetIndex;

		public RowBean()
		{
		}

		public boolean isEvenRow()
		{
			return resultSetIndex%2==0;
		}
		public boolean isOddRow()
		{
			return resultSetIndex%2==1;
		}

		public String getRequestDateDisplay() throws Exception
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy");
			try {
				Date date = formatter.parse(getRequestDate());
				return fmt2.format(date);

			} catch (ParseException e) {
				return getRequestDate();
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
	}

	public static class Command extends IndexCommandBeanImpl
	{
		private int numberOfInvitations;
		private String sortByOption = "Sent Date";
		private int communityId;
		private Community community;

		public List getSortByOptionOptions() throws Exception
		{
			List sortByOptionList = new ArrayList();
			sortByOptionList.add("Name");
			sortByOptionList.add("Sent Date");
			return sortByOptionList;
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

		public int getCommunityId() {
			return communityId;
		}

		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}

		public int getNumberOfInvitations() {
			return numberOfInvitations;
		}

		public void setNumberOfInvitations(int numberOfInvitations) {
			this.numberOfInvitations = numberOfInvitations;
		}

		public Community getCommunity() {
			return community;
		}

		public void setCommunity(Community community) {
			this.community = community;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setBlogAuthorFinder(BlogAuthorFinder blogAuthorFinder) {
		this.blogAuthorFinder = blogAuthorFinder;
	}

	public void setCommunityBlogFeature(CommunityBlogFeature communityBlogFeature) {
		this.communityBlogFeature = communityBlogFeature;
	}
}