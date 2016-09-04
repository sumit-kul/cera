package com.era.community.blog.ui.action;

import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.EntityWrapper;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.blog.dao.BlogAuthorFinder;
import com.era.community.blog.dao.BlogEntry;
import com.era.community.blog.dao.BlogEntryLikeFinder;
import com.era.community.blog.dao.CommunityBlog;
import com.era.community.blog.dao.CommunityBlogFeature;
import com.era.community.blog.dao.PersonalBlogFinder;
import com.era.community.blog.ui.dto.BlogEntryDto;
import com.era.community.communities.dao.Community;
import com.era.community.monitor.dao.CommunityBlogSubscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.UserFinder;
import com.era.community.tagging.dao.TagFinder;
import com.era.community.tagging.ui.dto.TagDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/cid/[cec]/blog/viewBlog.do"
 */
public class ShowCommunityBlogAction extends AbstractCommandAction
{
	protected CommunityBlogFeature communityBlogFeature;    
	protected CommunityEraContextManager contextManager;
	protected UserFinder userFinder;
	protected SubscriptionFinder subscriptionFinder;
	protected BlogEntryLikeFinder blogEntryLikeFinder;
	protected PersonalBlogFinder personalBlogFinder;
	protected BlogAuthorFinder blogAuthorFinder;
	protected TagFinder tagFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command)data;
		CommunityBlog bc = null;

		if (context.getCurrentCommunity() != null) {
			try {
				bc = (CommunityBlog)communityBlogFeature.getFeatureForCurrentCommunity();
			} catch (ElementNotFoundException ex) {
				return new ModelAndView("/pageNotFound");
			}
		} else {
			return new ModelAndView("/pageNotFound");
		}

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

		cmd.setCons(bc);
		QueryScroller scroller = null;

		scroller = bc.listBlogEntries();
		if ("Title".equalsIgnoreCase(cmd.getSortByOption())) {
			scroller.addScrollKey("STEMP.Title", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		} else {
			scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}

		if (pageNum != null) {
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = null;
			if (pNumber > 0) {
				int userId = context.getCurrentUser() == null ? 0 : context.getCurrentUser().getId();
				scroller.setBeanClass(RowBean.class, this);
				scroller.setPageSize(18);
				IndexedScrollerPage page = scroller.readPage(pNumber);
				json = page.toJsonString(pNumber, userId);
				json.put("userId", cmd.getUserId());
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
			cmd.setAuthorsCount(blogAuthorFinder.getBlogAuthorsCountForBlog(bc.getId()));
			cmd.setSearchType("Blog");
			cmd.setQueryText(bc.getName());
			scroller.setPageSize(18);
			cmd.setPageCount(scroller.readPageCount());
			cmd.setRowCount(scroller.readRowCount());
			scroller.setBeanClass(RowBean.class, this);
			cmd.setScrollerPage(scroller.readPage(1));
			return new ModelAndView("blog/showCommunityBlog");
		}
	}

	public class Command extends IndexCommandBeanImpl implements IndexCommandBean
	{              
		private int bid;
		private String order;
		private int userId;
		private String blogOwnerName;
		private String sortByOption;
		private CommunityBlog cons;
		private int authorsCount;
		private String filterTagList = "";

		public String getTaggedKeywords(){
			String taggedKeywords = "";
			try {
				List tags = tagFinder.getTagsForParentTypeByPopularity(cons.getId(), 0, 20, "CommunityBlog");
				for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
					TagDto tb = (TagDto) iterator.next();
					String tag = tb.getTagText().trim().toLowerCase();
					taggedKeywords += "<a href='blog/allBlogs.do?filterTag="+tag+"' class='normalTip euInfoSelect' style='display: inline;' title='Click to filter by tag &#39;"+tag+"&#39;'>"+tag+"</a>";
					if (iterator.hasNext())taggedKeywords += " , ";
				}
			} catch (Exception e) {
				return taggedKeywords;
			}
			return taggedKeywords;
		}

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
			sortByOptionList.add("Title");
			return sortByOptionList;
		}

		public boolean isCurrentUserSubscribed() throws Exception
		{
			try {
				if (contextManager.getContext().getCurrentUser() == null) return false;
				CommunityBlogSubscription subs = subscriptionFinder.getCommunityBlogSubscriptionForUser(cons.getId(), contextManager.getContext().getCurrentUser().getId());
				return true;
			}
			catch (ElementNotFoundException x) {
				return false;
			}
		}

		public String getOrder()
		{
			if (order == null || order.equals("")) {
				return "date";
			}
			return order;
		}

		public void setOrder(String order)
		{
			this.order = order;        
		}

		public int getUserId()
		{
			return userId;
		}

		public void setUserId(int userId)
		{
			this.userId = userId;
		}

		public String getBlogOwnerName()
		{
			return blogOwnerName;
		}

		public void setBlogOwnerName(String blogOwnerName)
		{
			this.blogOwnerName = blogOwnerName;
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

		public CommunityBlog getCons() {
			return cons;
		}

		public void setCons(CommunityBlog cons) {
			this.cons = cons;
		}

		public int getBid() {
			return bid;
		}

		public void setBid(int bid) {
			this.bid = bid;
		}

		public int getAuthorsCount() {
			return authorsCount;
		}

		public void setAuthorsCount(int authorsCount) {
			this.authorsCount = authorsCount;
		}

		public String getFilterTagList() {
			return filterTagList;
		}

		public void setFilterTagList(String filterTagList) {
			this.filterTagList = filterTagList;
		}
	}

	public class RowBean extends BlogEntryDto implements EntityWrapper
	{  
		private int resultSetIndex;        
		private int commentCount;
		private int viewCount;
		private int imageCount;
		private int likeCount;
		private BlogEntry entry;
		private boolean alreadyLike;
		private boolean likeAllowed;

		public String getLastVisitTime() throws Exception {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			Date today = new Date();
			String sToday = fmter.format(today);

			Date date = formatter.parse(getLastVisitorsTime());
			if (fmter.format(date).equals(sToday)) {
				return "Today " + fmt.format(date);
			}
			return fmt2.format(date);
		}

		public String getPostedOn() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy");
			Date today = new Date();
			String sToday = fmter.format(today);

			try {

				Date date = formatter.parse(getDatePosted());
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date);

			} catch (ParseException e) {
				return getCreated();
			}
		}

		public int getResultSetIndex()
		{
			return resultSetIndex; 
		}

		public void setResultSetIndex(int resultSetIndex)
		{
			this.resultSetIndex = resultSetIndex;
		}

		public boolean isEvenRow()
		{
			return resultSetIndex%2==0;
		}

		public boolean isOddRow()
		{
			return resultSetIndex%2==1;
		}

		public boolean isAdsPosition()
		{
			return resultSetIndex%3==0;
		}

		/*public String getFile3SizeString() throws Exception
		{
			long length = entry.getFile3().getLength();
			if (length == 0) return "";
			return (1+((length-1)/1024)) + "k";
		}*/

		public int getCommentCount()
		{
			return commentCount;
		}

		public void setCommentCount(Long commentCount)
		{
			this.commentCount = commentCount.intValue();
		}

		public final void setEntry(BlogEntry entry)
		{
			this.entry = entry;
		}

		public int getViewCount() {
			return viewCount;
		}

		public int getLikeCount() throws Exception {
			return entry.getLikeCountForBlogEntry();
		}

		public boolean isLikeAllowed(int userID) throws Exception {
			return entry.getPosterId() != userID;
		}

		public boolean isAlreadyLike(int userID) throws Exception {
			if (contextManager.getContext().getCurrentUser() == null ) return false;
			if (contextManager.getContext().isUserAuthenticated() == false) return false;
			int currentUserId = contextManager.getContext().getCurrentUser().getId();
			return entry.isAlreadyLike(currentUserId);
		}

		public void setLikeCount(int likeCount) {
			this.likeCount = likeCount;
		}

		public void setAlreadyLike(boolean alreadyLike) {
			this.alreadyLike = alreadyLike;
		}

		public void setLikeAllowed(boolean likeAllowed) {
			this.likeAllowed = likeAllowed;
		}

		public int getImageCount() {
			return imageCount;
		}

		public void setImageCount(Long imageCount) {
			this.imageCount = imageCount.intValue();
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public final void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
	{
		this.subscriptionFinder = subscriptionFinder;
	}

	public void setBlogEntryLikeFinder(BlogEntryLikeFinder blogEntryLikeFinder) {
		this.blogEntryLikeFinder = blogEntryLikeFinder;
	}

	public void setBlogAuthorFinder(BlogAuthorFinder blogAuthorFinder) {
		this.blogAuthorFinder = blogAuthorFinder;
	}

	public void setTagFinder(TagFinder tagFinder) {
		this.tagFinder = tagFinder;
	}

	public void setCommunityBlogFeature(CommunityBlogFeature communityBlogFeature) {
		this.communityBlogFeature = communityBlogFeature;
	}

	public void setPersonalBlogFinder(PersonalBlogFinder personalBlogFinder) {
		this.personalBlogFinder = personalBlogFinder;
	}
}