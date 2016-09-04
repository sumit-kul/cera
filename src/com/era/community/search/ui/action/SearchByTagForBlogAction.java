package com.era.community.search.ui.action;

import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.forum.dao.ForumItem;
import com.era.community.forum.dao.ForumItemFinder;
import com.era.community.tagging.dao.TagFinder;
import com.era.community.tagging.ui.dto.TagDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/search/searchByTagForBlog.do"
 * @spring.bean name="/search/searchByTagForBlog.ajx"
 */
public class SearchByTagForBlogAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected TagFinder tagFinder;
	private ForumItemFinder forumItemFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command)data;

		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}

		if (context.getRequest().getParameter("fTagList") != null 
				&& !"".equals(context.getRequest().getParameter("fTagList"))
						&& !"undefined".equals(context.getRequest().getParameter("fTagList"))) {
			cmd.setFilterTagList(context.getRequest().getParameter("fTagList"));
		}

		if (context.getRequest().getParameter("filterTag") != null 
				&& !"".equals(context.getRequest().getParameter("filterTag"))) {
			cmd.setFilterTag(context.getRequest().getParameter("filterTag"));
			cmd.addFilterTagList(cmd.getFilterTag());
		}
		
		if (context.getRequest().getParameter("submitType") != null 
				&& !"".equals(context.getRequest().getParameter("submitType"))) {
			cmd.setSubmitType(context.getRequest().getParameter("submitType"));
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

		QueryScroller scroller;
		scroller = tagFinder.getAllBlogEntriesByTagForPersonalBlog(cmd.getBpcId(), cmd.getFilterTagList());
		if (pageNum != null) {
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = null;
			if (pNumber > 0) {
				scroller.setBeanClass(RowBean.class, this);
				scroller.setPageSize(cmd.getPageSize());
				IndexedScrollerPage page = scroller.readPage(pNumber);
				json = page.toCommonJsonString(pNumber, cmd.getFilterTagList(), cmd.getSubmitType(), cmd.getToggleList());
				json.put("userSysAdmin", Boolean.toString(context.isUserSysAdmin()));
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
			return new ModelAndView("srch/searchByTag");
		}
	}

	public class RowBean 
	{
		private int id;
		private int consId;
		private int itemId;
		private String itemType;
		private String itemTitle;
		private String itemDesc;
		private String itemfilename;
		private Double avgRating;
		private Long commentCount;
		private String datePosted;
		private int posterid;
		private String firstName;
		private String lastName;
		private String photoPresent;
		private Long likeCount;

		public String getPostedOn() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			Date today = new Date();
			String sToday = fmter.format(today);

			try {

				Date date = formatter.parse(getDatePosted());
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date);

			} catch (ParseException e) {
				return getDatePosted();
			}
		}
		
		public String getTaggedKeywords(int pageNumber, String filterTag, String sortOption, String toggleList){
			String taggedKeywords = "";
			try {
				List tags = tagFinder.getTagsForParentTypeByPopularity(this.getItemId(), 0, 20, this.getItemType());
				String filterTagString = "";
				String sortOptionString = "";

				if (filterTag != null && !"".equals(filterTag))
					filterTagString = "&fTagList="+filterTag;

				for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
					TagDto tb = (TagDto) iterator.next();
					String tag = tb.getTagText().trim().toLowerCase();
					taggedKeywords += "<a href='search/searchByTagForBlog.do?filterTag="+tag+filterTagString+"&toggleList="+toggleList+" ' class='euInfoSelect' style='display: inline;' onmouseover='tip(this,&quot;Click to filter by tag &#39;"+tag+"&#39;&quot;)'>"+tag+"</a>";
					if (iterator.hasNext())taggedKeywords += " , ";
				}
			} catch (Exception e) {
				return taggedKeywords;
			}
			return taggedKeywords;
		}

		private int resultSetIndex;      

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

		public String getItemTitle() throws Exception
		{
			String responseTitle = itemTitle;
			if (this.getItemType().contains("Response")) {
				ForumItem topic = forumItemFinder.getForumItemForId(this.getItemId());
				return topic.getSubject() + " [<strong>" + responseTitle  + "</strong>]";
			}
			else
				return itemTitle;
		}

		public void setItemTitle(String itemTitle)
		{
			this.itemTitle = itemTitle;
		}

		public String getDisplayItemType() throws Exception
		{
			return "Blog";
		}

		public String getItemUrl() throws Exception
		{
			if (this.getItemType().endsWith("BlogEntry")) {
				return ("blog/blogEntry.do?id="+this.getItemId());
			}
			return "";
		}

		public String getItemBaseTitle() throws Exception
		{
			if (this.getItemType().endsWith("BlogEntry")) {
				return ("/blog/viewBlog.do?bid=");
			}
			return "";
		}

		public String getBodyDisplay() throws Exception
		{
			// The first 50 chars of the body display in the view. If these chars contain any links, just display the part before the link.
			if ( (this.getItemDesc() == null) || (this.getItemDesc().length() == 0)) return "";

			String sBody = this.getItemDesc();
			sBody = sBody.replaceAll("<p>","");
			sBody = sBody.replaceAll("</p>"," ");
			sBody = sBody.replaceAll("   ", "");
			
			if (sBody.contains("<")) {
    			sBody = sBody.substring(0, sBody.indexOf("<"));
    			if(sBody.length() >= 300)sBody.substring(0, 300);
    				sBody.concat("...");
    		} else if (sBody.length() >= 300) {
    			sBody = sBody.substring(0, 300).concat("...");
    		}
    		return sBody;
		}

		public boolean isPhotoPresent() {
			return "Y".equalsIgnoreCase(photoPresent) ? true : false;
		}

		public int getItemId() {
			return itemId;
		}

		public void setItemId(int itemId) {
			this.itemId = itemId;
		}

		public String getItemType() {
			return itemType;
		}

		public void setItemType(String itemType) {
			this.itemType = itemType;
		}

		public String getItemDesc() {
			return itemDesc;
		}

		public void setItemDesc(String itemDesc) {
			this.itemDesc = itemDesc;
		}

		public String getItemfilename() {
			return itemfilename;
		}

		public void setItemfilename(String itemfilename) {
			this.itemfilename = itemfilename;
		}

		public double getAvgRating() {
			return avgRating == null ? 0.0 : avgRating.doubleValue();
		}

		public void setAvgRating(Double avgRating) {
			this.avgRating = avgRating;
		}

		public long getCommentCount() {
			return commentCount == null ? 0 : commentCount.longValue();
		}

		public void setCommentCount(Long commentCount) {
			this.commentCount = commentCount;
		}

		public String getDatePosted() {
			return datePosted;
		}

		public void setDatePosted(Date datePosted) {
			this.datePosted = datePosted.toString();
		}

		public int getPosterid() {
			return posterid;
		}

		public void setPosterid(int posterid) {
			this.posterid = posterid;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public void setPhotoPresent(String photoPresent) {
			this.photoPresent = photoPresent;
		}

		public Long getLikeCount() {
			return likeCount == null ? 0 : likeCount.longValue();
		}

		public void setLikeCount(Long likeCount) {
			this.likeCount = likeCount;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getConsId() {
			return consId;
		}

		public void setConsId(int consId) {
			this.consId = consId;
		}
	}

	public static class Command extends IndexCommandBeanImpl
	{
		private String filterTagList = "";
		private int bpcId;
		private String submitType = "all";
		private String filterTag = "";
		
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
			if (type.startsWith("ForumItem/ForumTopic")) forumHitCount++;
			else if (type.startsWith("Document")) doclibHitCount++;
			else if (type.startsWith("WikiEntry")) wikiHitCount++;
			else if (type.startsWith("Event")) eventHitCount++;
			else if (type.startsWith("BlogEntry")) blogHitCount++;
			else if (type.startsWith("Community")) communityHitCount++;
			else if (type.startsWith("User")) userHitCount++;
			else throw new RuntimeException("invalid type "+type);
		}

		public String getFilterTagList() {
			return filterTagList;
		}

		public void setFilterTagList(String filterTagList) {
			this.filterTagList = filterTagList;
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
					returnHtmlTags += "<a href='search/searchByTagForBlog.ajx?fTagList="+filterList+"&rmFilterTag="+tag+"' class='euInfoRmTag' style='display: inline;' title='Remove "+tag+" filter'>"+tag+" <span style='color: rgb(137, 143, 156); font-size: 12px;' title='Remove "+tag+" filter' >X</span></a>";
				}
			}
			return returnHtmlTags;
		}

		public int getHitCount() {
			return hitCount;
		}

		public void setHitCount(int hitCount) {
			this.hitCount = hitCount;
		}

		public int getBlogHitCount() {
			return blogHitCount;
		}

		public void setBlogHitCount(int blogHitCount) {
			this.blogHitCount = blogHitCount;
		}

		public int getForumHitCount() {
			return forumHitCount;
		}

		public void setForumHitCount(int forumHitCount) {
			this.forumHitCount = forumHitCount;
		}

		public int getDoclibHitCount() {
			return doclibHitCount;
		}

		public void setDoclibHitCount(int doclibHitCount) {
			this.doclibHitCount = doclibHitCount;
		}

		public int getWikiHitCount() {
			return wikiHitCount;
		}

		public void setWikiHitCount(int wikiHitCount) {
			this.wikiHitCount = wikiHitCount;
		}

		public int getEventHitCount() {
			return eventHitCount;
		}

		public void setEventHitCount(int eventHitCount) {
			this.eventHitCount = eventHitCount;
		}

		public int getUserHitCount() {
			return userHitCount;
		}

		public void setUserHitCount(int userHitCount) {
			this.userHitCount = userHitCount;
		}

		public int getHitNumber() {
			return hitNumber;
		}

		public void setHitNumber(int hitNumber) {
			this.hitNumber = hitNumber;
		}

		public String getFilterTag() {
			return filterTag;
		}

		public void setFilterTag(String filterTag) {
			this.filterTag = filterTag;
		}

		public String getSubmitType() {
			return submitType;
		}

		public void setSubmitType(String submitType) {
			this.submitType = submitType;
		}

		public int getBpcId() {
			return bpcId;
		}

		public void setBpcId(int bpcId) {
			this.bpcId = bpcId;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setTagFinder(TagFinder tagFinder) {
		this.tagFinder = tagFinder;
	}

	public void setForumItemFinder(ForumItemFinder forumItemFinder) {
		this.forumItemFinder = forumItemFinder;
	}
}