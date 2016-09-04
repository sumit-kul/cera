package com.era.community.forum.ui.action;

import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.springframework.web.servlet.ModelAndView;

import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;
import support.community.util.StringHelper;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.forum.dao.Forum;
import com.era.community.forum.dao.ForumFeature;
import com.era.community.forum.dao.ForumFinder;
import com.era.community.forum.dao.ForumItem;
import com.era.community.forum.dao.ForumItemLikeFinder;
import com.era.community.forum.ui.dto.ForumItemDto;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.tagging.dao.TagFinder;
import com.era.community.tagging.ui.dto.TagDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/** 
 * @spring.bean name="/forum/allForums.do"
 */
public class AllForumsAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected ForumFeature forumFeature ;
	protected SubscriptionFinder subscriptionFinder;
	protected ForumFinder forumFinder;
	protected ForumItemLikeFinder forumItemLikeFinder;
	protected TagFinder tagFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command)data;

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

		if (context.getRequest().getParameter("fTagList") != null 
				&& !"".equals(context.getRequest().getParameter("fTagList"))) {
			cmd.setFilterTagList(context.getRequest().getParameter("fTagList"));
		}

		if (context.getRequest().getParameter("filterTag") != null 
				&& !"".equals(context.getRequest().getParameter("filterTag"))) {
			cmd.addFilterTagList(context.getRequest().getParameter("filterTag"));
		}

		if (context.getRequest().getParameter("rmFilterTag") != null 
				&& !"".equals(context.getRequest().getParameter("rmFilterTag"))) {
			cmd.removeFilterTagList(context.getRequest().getParameter("rmFilterTag"));
		}

		if (context.getRequest().getParameter("toggleList") != null 
				&& !"".equals(context.getRequest().getParameter("toggleList"))
				&& "true".equals(context.getRequest().getParameter("toggleList"))) {
			cmd.setToggleList("true");
		}

		QueryScroller scroller = forumFinder.listAllUnThemedTopicsByMostRecentResponse(cmd.getSortByOption(), cmd.getFilterTagList());

		if (pageNum != null) {
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = null;
			if (pNumber > 0) {
				scroller.setBeanClass(RowBean.class, this);
				scroller.setPageSize(18);
				IndexedScrollerPage page = scroller.readPage(pNumber);
				json = page.toJsonString(pNumber, cmd.getFilterTagList(), "", cmd.getSortByOption(), cmd.getToggleList());
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
			scroller.setPageSize(18);
			cmd.setPageCount(scroller.readPageCount());
			cmd.setRowCount(scroller.readRowCount());
			scroller.setBeanClass(RowBean.class, this);
			cmd.setScrollerPage(scroller.readPage(cmd.getPage()));
			cmd.setPage(cmd.getPage() + 1);
			return new ModelAndView("/forum/allForums");
		}
	}

	public class Command extends IndexCommandBeanImpl implements IndexCommandBean
	{
		private String sortByOption;
		private String filterTagList = "";

		public List getSortByOptionOptions() throws Exception
		{
			List sortByOptionList = new ArrayList();
			sortByOptionList.add("Most Recent");
			sortByOptionList.add("Topic");
			sortByOptionList.add("Author");
			return sortByOptionList;
		}
		
		public void addFilterTagList(String filterTag) {
			if (filterTag != null && !"".equals(filterTag)) {
				if (this.filterTagList == null || this.filterTagList.equals("")) {
					this.filterTagList = filterTag;
				} else {
					StringTokenizer st = new StringTokenizer(getFilterTagList(), ",");
					boolean isAdditionAllowed = true;
					while (st.hasMoreTokens()) {
						String tag = st.nextToken().trim().toLowerCase();
						if (tag.equalsIgnoreCase(filterTag)) {
							isAdditionAllowed = false;
							break;
						}
					}
					if (isAdditionAllowed && filterTag != null && !"".equals(filterTag))
						this.filterTagList += ","+filterTag;
				}
			}
		}

		public void removeFilterTagList(String rmfilterTag) {
			String newFilterTagList = "";
			if (getFilterTagList() != null && !"".equals(getFilterTagList())) {
				StringTokenizer st = new StringTokenizer(getFilterTagList(), ",");
				while (st.hasMoreTokens()) {
					String tag = st.nextToken().trim().toLowerCase();
					if (rmfilterTag != null && !rmfilterTag.equalsIgnoreCase(tag)) {
						newFilterTagList += tag;
						if (st.hasMoreTokens()) newFilterTagList += ",";
					}
				}
				this.filterTagList = newFilterTagList;
			}
		}

		public String getDisplayedFilterTag() {
			String returnHtmlTags = "";
			String filterList = getFilterTagList();
			if (getFilterTagList() != null && !"".equals(filterList)) {
				StringTokenizer st = new StringTokenizer(filterList, ",");
				while (st.hasMoreTokens()) {
					String tag = st.nextToken().trim().toLowerCase();
					returnHtmlTags += "<a href='"+contextManager.getContext().getContextUrl()+"/forum/allForums.do?fTagList="+filterList+"&rmFilterTag="+tag+"&sortByOption="+this.getSortByOption()+"&toggleList="+this.getToggleList()+"' class='euInfoRmTag' style='display: inline;' title='Remove "+tag+" filter'>"+tag+" <span style='color: rgb(137, 143, 156); font-size: 12px;' title='Remove "+tag+" filter' >X</span></a>";
				}
			}
			return returnHtmlTags;
		}

		public Forum getForum() throws Exception
		{
			return (Forum)forumFeature.getFeatureForCurrentCommunity();
		}

		public String getSortByOption() {
			return sortByOption;
		}

		public void setSortByOption(String sortByOption) {
			this.sortByOption = sortByOption;
		}

		public String getFilterTagList() {
			return filterTagList;
		}

		public void setFilterTagList(String filterTagList) {
			this.filterTagList = filterTagList;
		}              
	}

	public class RowBean extends ForumItemDto
	{
		private int resultSetIndex;
		private int communityId;
		private String communityName;
		private String logoPresent;
		private String  CommunitySysType;
		private int commentCount;
		private int topicLike;
		private int responseLike;
		private boolean alreadyLike;
		private ForumItem forumItem;
		private int imageCount;
		
		public boolean isAdsPosition()
		{
			return resultSetIndex%4==0;
		}
		
		public String getDisplayBody()
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

		public String getFirstPostDate() throws Exception
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			try {

				Date date = formatter.parse(getDatePosted());
				return fmt2.format(date);

			} catch (ParseException e) {
				return getDatePosted();
			}
		}
		
		public String getLastPostDate() throws Exception
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			try {

				Date date = formatter.parse(getLatestPostDate());
				return fmt2.format(date);

			} catch (ParseException e) {
				return getLatestPostDate();
			}
		}

		public boolean isAlreadyLike() throws Exception {
			if (contextManager.getContext().getCurrentUser() == null ) return false;
			if (contextManager.getContext().isUserAuthenticated() == false) return false;
			/*int currentUserId = contextManager.getContext().getCurrentUser().getId();
			try {
				ForumItemLike forumItemLike = forumItem.getLikeForForumItemAndUser(this.getId(), currentUserId);
				return true;
			}
			catch (ElementNotFoundException e) {
				return false;
			}*/ 
			return true;
		}

		public String getTaggedKeywords(int pageNumber, String filterTag, String commOption, String sortOption, String toggleList){
			String taggedKeywords = "";
			try {
				List tags = tagFinder.getTagsForParentTypeByPopularity(this.getId(), 0, 20, "ForumTopic");
				String filterTagString = "";
				String sortOptionString = "";

				if (filterTag != null && !"".equals(filterTag))
					filterTagString = "&fTagList="+filterTag;

				if (sortOption != null && !"".equals(sortOption))
					sortOptionString = "&sortByOption="+sortOption;

				for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
					TagDto tb = (TagDto) iterator.next();
					String tag = tb.getTagText().trim().toLowerCase();
					taggedKeywords += "<a href='"+contextManager.getContext().getContextUrl()+"/forum/allForums.do?filterTag="+tag+filterTagString+sortOptionString+"&toggleList="+toggleList+" ' class='euInfoSelect' style='display: inline;' title='Click to filter by tag &#39;"+tag+"&#39;'>"+tag+"</a>";
					if (iterator.hasNext())taggedKeywords += " , ";
				}
			} catch (Exception e) {
				return taggedKeywords;
			}
			return taggedKeywords;
		}

		public int getCommunityId() {
			return communityId;
		}

		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}

		public String getCommunityName() {
			return communityName;
		}

		public void setCommunityName(String communityName) {
			this.communityName = communityName;
		}

		public String getCommunitySysType() {
			return CommunitySysType;
		}

		public void setCommunitySysType(String communitySysType) {
			CommunitySysType = communitySysType;
		}

		public int getCommentCount() {
			return commentCount;
		}

		public void setCommentCount(int commentCount) {
			this.commentCount = commentCount;
		}

		public void setAlreadyLike(boolean alreadyLike) {
			this.alreadyLike = alreadyLike;
		}

		public ForumItem getForumItem() {
			return forumItem;
		}

		public void setForumItem(ForumItem forumItem) {
			this.forumItem = forumItem;
		}

		public int getTopicLike() {
			return topicLike;
		}

		public void setTopicLike(Long topicLike) {
			this.topicLike = topicLike.intValue();
		}

		public int getResponseLike() {
			return responseLike;
		}

		public void setResponseLike(Long responseLike) {
			this.responseLike = responseLike.intValue();
		}

		public int getResultSetIndex() {
			return resultSetIndex;
		}

		public void setResultSetIndex(int resultSetIndex) {
			this.resultSetIndex = resultSetIndex;
		}

		public int getImageCount() {
			return imageCount;
		}

		public void setImageCount(Long imageCount) {
			this.imageCount = imageCount.intValue();
		}

		public String getLogoPresent() {
			return logoPresent;
		}

		public void setLogoPresent(String logoPresent) {
			this.logoPresent = logoPresent;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}
	public final void setForumFeature(ForumFeature forumFeature)
	{
		this.forumFeature = forumFeature;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
	{
		this.subscriptionFinder = subscriptionFinder;
	}

	public final void setForumFinder(ForumFinder forumFinder)
	{
		this.forumFinder = forumFinder;
	}
	
	public void setForumItemLikeFinder(ForumItemLikeFinder forumItemLikeFinder) {
		this.forumItemLikeFinder = forumItemLikeFinder;
	}
	
	public void setTagFinder(TagFinder tagFinder) {
		this.tagFinder = tagFinder;
	}
}