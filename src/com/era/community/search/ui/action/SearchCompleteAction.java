package com.era.community.search.ui.action;

import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.document.Document;
import org.springframework.web.servlet.ModelAndView;

import support.community.database.IndexedScrollerPage;
import support.community.database.QueryPaginator;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;
import support.community.lucene.search.EntitySearchScroller;
import support.community.lucene.search.EntitySearchScrollerCallback;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.blog.dao.BlogEntry;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.events.dao.Event;
import com.era.community.forum.dao.ForumTopic;
import com.era.community.jobs.dao.ScheduledJob;
import com.era.community.jobs.dao.ScheduledJobFinder;
import com.era.community.pers.dao.User;
import com.era.community.search.index.CecEntityIndex;
import com.era.community.search.index.CecEntitySearcher;
import com.era.community.wiki.dao.WikiEntry;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/search/searchComplete.do"
 * @spring.bean name="/search/searchComplete.ajx"
 */
public class SearchCompleteAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected CommunityFinder communityFinder;
	protected ScheduledJobFinder scheduledJobFinder;
	protected CecEntityIndex index;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command)data;

/*		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}

		if (context.getRequest().getParameter("searchType") != null 
				&& !"".equals(context.getRequest().getParameter("searchType"))) {
			cmd.setSearchType(context.getRequest().getParameter("searchType"));
		} else {
			cmd.setSearchType("All Content");
		}

		if (context.getRequest().getParameter("queryText") != null 
				&& !"".equals(context.getRequest().getParameter("queryText"))) {
			cmd.setQueryText(context.getRequest().getParameter("queryText"));
		}

		EntitySearchScroller scroller = (EntitySearchScroller)getScroller(cmd);

		if (pNumber > 0) {
			scroller.setPageSize(cmd.getPageSize());
			//pagination

			IndexedScrollerPage page = scroller.readPage(pNumber);
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = page.toJsonString(pNumber);
			String jsonString = json.serialize();
			resp.setContentType("text/json");
			Writer out = resp.getWriter();
			out.write(jsonString);
			out.close();
			return null;
		} else {
			scroller.setPageSize(cmd.getPageSize());
			int rowCount = scroller.readRowCount();
			cmd.setRowCount(rowCount);
			cmd.setPageCount(rowCount==0 ? 0 : ((int)((rowCount-1)/cmd.getPageSize()))+1);
			ScheduledJob job = scheduledJobFinder.getLastScheduledJobForTask("IndexerTask");
			cmd.setCompleted(job.getCompleted().toString());
			return new ModelAndView("srch/searchComplete");
		}*/
		return new ModelAndView("srch/searchComplete");
	}

	protected QueryPaginator getScroller(IndexCommandBean bean) throws Exception
	{
		final Command cmd = (Command)bean;

		if (cmd.getQueryText()==null||cmd.getQueryText().trim().length()==0)
			cmd.setErrorText("No query has been entered");

		CommunityEraContext context = contextManager.getContext();

		EntitySearchScroller scroller = getScrollerForEntity(cmd);

		if (context.getCurrentUser() != null) {
			scroller.setCurrentUser(context.getCurrentUser());
		}

		scroller.doForAllRows(new EntitySearchScrollerCallback() {
			public void handleRow(Document doc, float score, int rowNum) throws Exception
			{
				cmd.addHit(doc.get(CecEntityIndex.ENTITY_TYPE_FIELD));
			}
		});
		return scroller;
	}

	private EntitySearchScroller getScrollerForEntity(IndexCommandBean bean) throws Exception
	{
		Command cmd = (Command)bean;

		if (cmd.getQueryText()==null||cmd.getQueryText().trim().length()==0)
			throw new Exception("No search query has been entered");

		if (cmd.getSearchType().equals("All Content"))
			return searchAllContent(cmd);
		if (cmd.getSearchType().equals("Community"))
			return searchCommunity(cmd);
		else if (cmd.getSearchType().equals("People"))
			return searchUser(cmd);
		else if (cmd.getSearchType().equals("Forum"))
			return searchForum(cmd);
		else if (cmd.getSearchType().equals("File"))
			return searchDoclib(cmd);
		else if (cmd.getSearchType().equals("Wiki"))
			return searchWiki(cmd);
		else if (cmd.getSearchType().equals("Event"))
			return searchEvents(cmd);
		else if (cmd.getSearchType().equals("Blog"))
			return searchBlogs(cmd);
		else {
			cmd.setErrorText("Invalid search type ["+cmd.getSearchType()+"]");
			return null;
		}
	}

	private EntitySearchScroller searchAllContent(Command cmd) throws Exception
	{
		CecEntitySearcher searcher = (CecEntitySearcher)index.getIndexSearcher();
		return searcher.search(cmd.getQueryText(), CecEntitySearcher.DEFAULT_ENTITY_TYPES);
	}

	private EntitySearchScroller searchCommunity(Command cmd) throws Exception
	{
		CecEntitySearcher searcher = (CecEntitySearcher)index.getIndexSearcher();
		return searcher.search(cmd.getQueryText(), Community.class);
	}

	private EntitySearchScroller searchUser(Command cmd) throws Exception
	{
		CecEntitySearcher searcher = (CecEntitySearcher)index.getIndexSearcher();
		return searcher.search(cmd.getQueryText(), User.class);
	}

	private EntitySearchScroller searchForum(Command cmd) throws Exception
	{
		CecEntitySearcher searcher = (CecEntitySearcher)index.getIndexSearcher();
		return  searcher.search(cmd.getQueryText(), ForumTopic.class);
	}

	private EntitySearchScroller searchDoclib(Command cmd) throws Exception
	{
		CecEntitySearcher searcher = (CecEntitySearcher)index.getIndexSearcher();
		return searcher.search(cmd.getQueryText(), Document.class);
	}

	private EntitySearchScroller searchWiki(Command cmd) throws Exception
	{
		CecEntitySearcher searcher = (CecEntitySearcher)index.getIndexSearcher();
		return searcher.search(cmd.getQueryText(), WikiEntry.class);
	}

	private EntitySearchScroller searchEvents(Command cmd) throws Exception
	{
		CecEntitySearcher searcher = (CecEntitySearcher)index.getIndexSearcher();
		return searcher.search(cmd.getQueryText(), Event.class);
	}

	private EntitySearchScroller searchBlogs(Command cmd) throws Exception
	{
		CecEntitySearcher searcher = (CecEntitySearcher)index.getIndexSearcher();
		return searcher.search(cmd.getQueryText(), BlogEntry.class);
	}

	public static class RowBean implements Comparable<Object>
	{
		private Community community;
		private int hitCount = 0;
		private int communityHitCount = 0;
		private int blogHitCount = 0;
		private int forumHitCount = 0;
		private int doclibHitCount = 0;
		private int wikiHitCount = 0;
		private int eventHitCount = 0;
		private int userHitCount = 0;
		private int hitNumber;

		public final void setCommunity(Community community)
		{
			this.community = community;
		}

		void addHit(String type)
		{
			hitCount++;
			if (type.startsWith("ForumItem/ForumTopic") || type.startsWith("ForumItem/ForumResponse")) forumHitCount++;
			else if (type.startsWith("Document")) doclibHitCount++;
			else if (type.startsWith("WikiEntry")) wikiHitCount++;
			else if (type.startsWith("Event")) eventHitCount++;
			else if (type.startsWith("BlogEntry")) blogHitCount++;
			else if (type.startsWith("Community")) communityHitCount++;
			else if (type.startsWith("User")) userHitCount++;
			else throw new RuntimeException("invalid type "+type);
		}

		public String getHitDescription()
		{
			StringBuffer buf = new StringBuffer(128);
			if (forumHitCount>0) buf.append(" forum("+forumHitCount+")");
			if (doclibHitCount>0) buf.append(" documents("+doclibHitCount+")");
			if (wikiHitCount>0) buf.append(" wiki("+wikiHitCount+")");
			if (eventHitCount>0) buf.append(" events("+eventHitCount+")");
			if (blogHitCount>0) buf.append(" blogs("+blogHitCount+")"); //per community is difficult because of multiple entries for same blog item
			if (communityHitCount>0) buf.append(" communities("+blogHitCount+")");
			if (userHitCount>0) buf.append(" users("+blogHitCount+")");
			return buf.toString();
		}

		public int compareTo(Object o)
		{
			RowBean r = (RowBean)o;
			return r.hitCount - this.hitCount; 
		}

		public boolean isOdd()
		{
			return hitNumber%2 != 0;
		}

		public boolean isEven()
		{
			return hitNumber%2 == 0;
		}

		public final int getHitCount()
		{
			return hitCount;
		}
		public final void setHitCount(int hitCount)
		{
			this.hitCount = hitCount;
		}
		public final Community getCommunity()
		{
			return community;
		}
		public final int getBlogHitCount()
		{
			return blogHitCount;
		}
		public final int getDoclibHitCount()
		{
			return doclibHitCount;
		}
		public final int getEventHitCount()
		{
			return eventHitCount;
		}
		public final int getForumHitCount()
		{
			return forumHitCount;
		}
		public final int getWikiHitCount()
		{
			return wikiHitCount;
		}
		public final int getHitNumber()
		{
			return hitNumber;
		}
		public final void setHitNumber(int hitNumber)
		{
			this.hitNumber = hitNumber;
		}

		/**
		 * @return the communityHitCount
		 */
		 public int getCommunityHitCount() {
			return communityHitCount;
		}

		/**
		 * @return the userHitCount
		 */
		 public int getUserHitCount() {
			return userHitCount;
		}
	}

	public static class Command extends IndexCommandBeanImpl implements CommandBean
	{
		private String errorText;
		protected String Completed;

		private int hitCount = 0;
		private int communityHitCount = 0;
		private int blogHitCount = 0;
		private int forumHitCount = 0;
		private int doclibHitCount = 0;
		private int wikiHitCount = 0;
		private int eventHitCount = 0;
		private int userHitCount = 0;
		private int hitNumber;

		void addHit(String type)
		{
			hitCount++;
			if (type.startsWith("ForumItem/ForumTopic") || type.startsWith("ForumItem/ForumResponse")) forumHitCount++;
			else if (type.startsWith("Document")) doclibHitCount++;
			else if (type.startsWith("WikiEntry")) wikiHitCount++;
			else if (type.startsWith("Event")) eventHitCount++;
			else if (type.startsWith("BlogEntry")) blogHitCount++;
			else if (type.startsWith("Community")) communityHitCount++;
			else if (type.startsWith("User")) userHitCount++;
			else throw new RuntimeException("invalid type "+type);
		}
		
		public String getCompletedOn() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			Date today = new Date();
			String sToday = fmter.format(today);

			try {

				Date date = formatter.parse(getCompleted());
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date);

			} catch (ParseException e) {
				return getCompleted();
			}
		}

		/**
		 * @return the errorText
		 */
		public String getErrorText() {
			return errorText;
		}

		/**
		 * @param errorText the errorText to set
		 */
		public void setErrorText(String errorText) {
			this.errorText = errorText;
		}

		/**
		 * @return the hitCount
		 */
		public int getHitCount() {
			return hitCount;
		}

		/**
		 * @param hitCount the hitCount to set
		 */
		public void setHitCount(int hitCount) {
			this.hitCount = hitCount;
		}

		/**
		 * @return the communityHitCount
		 */
		public int getCommunityHitCount() {
			return communityHitCount;
		}

		/**
		 * @param communityHitCount the communityHitCount to set
		 */
		public void setCommunityHitCount(int communityHitCount) {
			this.communityHitCount = communityHitCount;
		}

		/**
		 * @return the blogHitCount
		 */
		public int getBlogHitCount() {
			return blogHitCount;
		}

		/**
		 * @param blogHitCount the blogHitCount to set
		 */
		public void setBlogHitCount(int blogHitCount) {
			this.blogHitCount = blogHitCount;
		}

		/**
		 * @return the forumHitCount
		 */
		public int getForumHitCount() {
			return forumHitCount;
		}

		/**
		 * @param forumHitCount the forumHitCount to set
		 */
		public void setForumHitCount(int forumHitCount) {
			this.forumHitCount = forumHitCount;
		}

		/**
		 * @return the doclibHitCount
		 */
		public int getDoclibHitCount() {
			return doclibHitCount;
		}

		/**
		 * @param doclibHitCount the doclibHitCount to set
		 */
		public void setDoclibHitCount(int doclibHitCount) {
			this.doclibHitCount = doclibHitCount;
		}

		/**
		 * @return the wikiHitCount
		 */
		public int getWikiHitCount() {
			return wikiHitCount;
		}

		/**
		 * @param wikiHitCount the wikiHitCount to set
		 */
		public void setWikiHitCount(int wikiHitCount) {
			this.wikiHitCount = wikiHitCount;
		}

		/**
		 * @return the eventHitCount
		 */
		public int getEventHitCount() {
			return eventHitCount;
		}

		/**
		 * @param eventHitCount the eventHitCount to set
		 */
		public void setEventHitCount(int eventHitCount) {
			this.eventHitCount = eventHitCount;
		}

		/**
		 * @return the userHitCount
		 */
		public int getUserHitCount() {
			return userHitCount;
		}

		/**
		 * @param userHitCount the userHitCount to set
		 */
		public void setUserHitCount(int userHitCount) {
			this.userHitCount = userHitCount;
		}

		/**
		 * @return the hitNumber
		 */
		public int getHitNumber() {
			return hitNumber;
		}

		/**
		 * @param hitNumber the hitNumber to set
		 */
		public void setHitNumber(int hitNumber) {
			this.hitNumber = hitNumber;
		}

		public String getCompleted() {
			return Completed;
		}

		public void setCompleted(String completed) {
			Completed = completed;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setIndex(CecEntityIndex index)
	{
		this.index = index;
	}

	public final void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}

	public void setScheduledJobFinder(ScheduledJobFinder scheduledJobFinder) {
		this.scheduledJobFinder = scheduledJobFinder;
	}
}