package com.era.community.blog.ui.action;

import java.io.Writer;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.EntityWrapper;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;
import support.community.framework.Option;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.blog.dao.CommunityBlogFinder;
import com.era.community.blog.dao.PersonalBlogFinder;
import com.era.community.blog.ui.dto.BlogEntryDto;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.tagging.dao.TagFinder;
import com.era.community.tagging.ui.dto.TagDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * Shows all the blog entries irrespective to any community
 * @spring.bean name="/blog/allBlogs.do"
 */
public class AllBlogsIndexAction extends AbstractCommandAction
{

	public static final String REQUIRES_AUTHENTICATION = "";

	protected CommunityEraContextManager contextManager;
	protected UserFinder userFinder;
	protected TagFinder tagFinder; 
	protected SubscriptionFinder subscriptionFinder;
	protected CommunityBlogFinder communityBlogFinder;
	protected PersonalBlogFinder personalBlogFinder;
	protected CommunityFinder communityFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command)data;
		User currentUser = context.getCurrentUser();

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

		QueryScroller scroller = communityBlogFinder.listAllBlogsForHeader(cmd.getSortByOption(), cmd.getFilterTagList());

		if (pageNum != null) {
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = null;
			if (pNumber > 0) {
				scroller.setBeanClass(RowBean.class, this);
				scroller.setPageSize(18);
				IndexedScrollerPage page = scroller.readPage(pNumber);
				json = toJsonStringForAllBlogs(pNumber, cmd.getFilterTagList(), "", cmd.getSortByOption(), cmd.getToggleList(), page);
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
			scroller.setPageSize(18);
			cmd.setPageCount(scroller.readPageCount());
			cmd.setRowCount(scroller.readRowCount());
			scroller.setBeanClass(RowBean.class, this);
			cmd.setScrollerPage(scroller.readPage(1));
			cmd.setPage(2);
			return new ModelAndView("blog/allBlogsIndex");
		}
	}

	public JSONObject toJsonStringForAllBlogs(int pageNumber, String filterTag, String commOption, String sortOption, String toggleList, IndexedScrollerPage page) throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("rowCount", page.getRowCount());
		json.put("pageCount", page.getPageCount());
		json.put("pageSize", page.getScrollerPageSize());
		json.put("pageNumber", page.getCurrentPageNumber());

		JSONArray data = new JSONArray();
		for (Object o : page) {
			JSONObject row = new JSONObject();
			for (Method m : o.getClass().getMethods()) {
				String name = m.getName(); 
				if (!(name.startsWith("get") || name.startsWith("is"))) continue;
				if (name.equals("getClass")) continue;
				if ("getLatestEntries".equalsIgnoreCase(name)) {
					JSONArray entryData = new JSONArray();
					List<BlogEntryDto> entries = (List<BlogEntryDto>)m.invoke(o, new Object[] {});
					for (BlogEntryDto entry : entries) {
						JSONObject entryName = new JSONObject();
						for (Method im : entry.getClass().getMethods()) {
							String iName = im.getName(); 
							if (!iName.startsWith("get")) continue;
							if (iName.equals("getClass")) continue;
							entryName.put(iName.substring(3,4).toLowerCase()+iName.substring(4), im.invoke(entry, new Object[] {}));
						}
						entryData.add(entryName);
					}
					row.put("entryData", entryData);
				} else {
					if (name.startsWith("get")) {
						if (name.equals("getTaggedKeywords")) {
							Object[] obj = new Object[] {pageNumber, filterTag, commOption, sortOption, toggleList};
							row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(o, obj));  
						} else {
							row.put(name.substring(3,4).toLowerCase()+name.substring(4), m.invoke(o, new Object[] {}));
						}
					}
					if (name.startsWith("is"))
						row.put(name.substring(2,3).toLowerCase()+name.substring(3), m.invoke(o, new Object[] {}));
				}
			}            
			data.add(row);
		}
		json.put("aData", data);
		return json;
	}

	public static class Command extends IndexCommandBeanImpl implements IndexCommandBean
	{              
		private int userId;
		private String blogOwnerName;
		private String sortByOption;
		private String filterTagList = "";

		public List getSortByOptionOptions() throws Exception
		{
			List<Option> sortByOptionList = new ArrayList<Option>(5);
			Option opt1 = new Option();
			opt1.setLabel("Most Recent");
			opt1.setValue(1);
			sortByOptionList.add(opt1);

			Option opt2 = new Option();
			opt2.setLabel("Title");
			opt2.setValue(2);
			sortByOptionList.add(opt2);

			/*Option opt3 = new Option();
			opt3.setLabel("Most Liked");
			opt3.setValue(3);
			sortByOptionList.add(opt3);

			Option opt4 = new Option();
			opt4.setLabel("Most Commented");
			opt4.setValue(4);
			sortByOptionList.add(opt4);*/

			/*Option opt5 = new Option();
			opt5.setLabel("Most Visited");
			opt5.setValue(5);
			sortByOptionList.add(opt5);*/

			return sortByOptionList;
		}

		/**
		 * @param filterTagList the filterTagList to add as comma separated 
		 */
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

		/**
		 * @param filterTagList the filterTagList to remove from the filterTag list 
		 */
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
					returnHtmlTags += "<a href='blog/allBlogs.do?fTagList="+filterList+"&rmFilterTag="+tag+"&sortByOption="+this.getSortByOption()+"&toggleList="+this.getToggleList()+"' class='normalTip euInfoRmTag' style='display: inline;' title='Remove &#39;"+tag+"&#39; filter'>"+tag+" <span style='color: rgb(137, 143, 156); font-size: 12px;' title='Remove "+tag+" filter' >X</span></a>";
				}
			}
			return returnHtmlTags;
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

	public class RowBean extends BlogEntryDto implements EntityWrapper
	{  
		private int commId;
		private int commentCount;
		private int likeCount;
		private String communityName;
		private int imageCount;
		private int resultSetIndex;
		
		public boolean isEvenRow()
		{
			return resultSetIndex % 2 == 0;
		}

		public boolean isOddRow()
		{
			return resultSetIndex % 2 == 1;
		}
		
		public boolean isAdsPosition()
		{
			return resultSetIndex%4==0;
		}
		
		public String getDisplayShortName() {
			if (this.getDisplayName().length() > 14) {
				return this.getDisplayName().substring(0, 14);
			} else {
				return this.getDisplayName();
			}
		}

		public String getCreatedOn() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			Date today = new Date();
			String sToday = fmter.format(today);

			try {

				Date date = formatter.parse(getCreated());
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date);

			} catch (ParseException e) {
				return getCreated();
			}
		}

		public String getTaggedKeywords(int pageNumber, String filterTag, String commOption, String sortOption, String toggleList){
			String taggedKeywords = "";
			try {
				List tags = null;
				if (commId > 0) {
					tags = tagFinder.getTagsForParentTypeByPopularity(this.getCommunityBlogId(), commId, 20, "CommunityBlog");
				} else {
					tags = tagFinder.getTagsForParentTypeByPopularity(this.getPersonalBlogId(), 0, 20, "PersonalBlog");
				}
				String filterTagString = "";
				String sortOptionString = "";

				if (filterTag != null && !"".equals(filterTag))
					filterTagString = "&fTagList="+filterTag;

				if (sortOption != null && !"".equals(sortOption))
					sortOptionString = "&sortByOption="+sortOption;

				for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
					TagDto tb = (TagDto) iterator.next();
					String tag = tb.getTagText().trim().toLowerCase();
					taggedKeywords += "<a href='blog/allBlogs.do?filterTag="+tag+filterTagString+sortOptionString+"&toggleList="+toggleList+" ' class='normalTip euInfoSelect' style='display: inline;' title='Click to filter by tag &#39;"+tag+"&#39;'>"+tag+"</a>";
					if (iterator.hasNext())taggedKeywords += " , ";
				}
			} catch (Exception e) {
				return taggedKeywords;
			}
			return taggedKeywords;
		}

		public boolean isMember() throws Exception
		{
			try {
				Community comm = communityFinder.getCommunityForId(this.getCommId());
				if (comm != null && comm.isPrivate()) {
					if (contextManager.getContext().getCurrentUser() == null ) return false;
					if (contextManager.getContext().isUserAuthenticated() == false) return false;
					if (this.getCommId() == 0) return false;
					int currentUser = contextManager.getContext().getCurrentUser().getId();
					return comm.isMember(currentUser);
				} else {
					return true;
				}
			} catch (ElementNotFoundException ex) {
				return true;
			}

		}

		public int getCommentCount() {
			return commentCount;
		}

		public void setCommentCount(Long commentCount) {
			this.commentCount = commentCount.intValue();
		}

		public int getLikeCount() {
			return likeCount;
		}

		public void setLikeCount(Long likeCount) {
			this.likeCount = likeCount.intValue();
		}

		public String getCommunityName() {
			return communityName;
		}

		public void setCommunityName(String communityName) {
			this.communityName = communityName;
		}

		public int getCommId() {
			return commId;
		}

		public void setCommId(Long commId) {
			this.commId = commId.intValue();
		}

		public int getImageCount() {
			return imageCount;
		}

		public void setImageCount(Long imageCount) {
			this.imageCount = imageCount.intValue();
		}

		public int getResultSetIndex() {
			return resultSetIndex;
		}

		public void setResultSetIndex(int resultSetIndex) {
			this.resultSetIndex = resultSetIndex;
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

	public void setTagFinder(TagFinder tagFinder) {
		this.tagFinder = tagFinder;
	}

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}

	public void setCommunityBlogFinder(CommunityBlogFinder communityBlogFinder) {
		this.communityBlogFinder = communityBlogFinder;
	}

	public void setPersonalBlogFinder(PersonalBlogFinder personalBlogFinder) {
		this.personalBlogFinder = personalBlogFinder;
	}
}