package com.era.community.wiki.ui.action;

import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.UserFinder;
import com.era.community.tagging.dao.TagFinder;
import com.era.community.tagging.ui.dto.TagDto;
import com.era.community.wiki.dao.WikiEntryFinder;
import com.era.community.wiki.dao.WikiFeature;
import com.era.community.wiki.dao.WikiFinder;
import com.era.community.wiki.ui.dto.WikiEntryDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/wiki/allWikis.do" 
 */
public class AllWikisAction extends AbstractCommandAction
{
	protected WikiFeature wikiFeature;
	protected WikiFinder wikiFinder;
	protected UserFinder userFinder;
	protected CommunityEraContextManager contextManager;
	protected SubscriptionFinder subscriptionFinder;
	protected WikiEntryFinder wikiEntryFinder;
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

		QueryScroller scroller;

		if (cmd.getSortByOption() != null && cmd.getSortByOption().equals("Title")) {
			scroller = wikiFinder.listAllWikiEntriesByTitle(cmd.getFilterTagList());
		} else {
			scroller = wikiFinder.listAllWikiEntriesByUpdateDate(cmd.getFilterTagList());                
		}

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
			return new ModelAndView("/wiki/allWikis");
		}
	}

	public class Command extends IndexCommandBeanImpl implements IndexCommandBean
	{           
		private String order;
		private int resultSetIndex;   
		private String sortByOption;
		private String filterTagList = "";

		public List getSortByOptionOptions() throws Exception
		{
			List sortByOptionList = new ArrayList();
			sortByOptionList.add("Latest updates");
			sortByOptionList.add("Title");
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
					returnHtmlTags += "<a href='wiki/allWikis.do?fTagList="+filterList+"&rmFilterTag="+tag+"&sortByOption="+this.getSortByOption()+"&toggleList="+this.getToggleList()+"' class='euInfoRmTag' style='display: inline;' title='Remove "+tag+" filter'>"+tag+" <span style='color: rgb(137, 143, 156); font-size: 12px;' title='Remove "+tag+" filter' >X</span></a>";
				}
			}
			return returnHtmlTags;
		}

		public final int getResultSetIndex()
		{
			return resultSetIndex;
		}
		public final void setResultSetIndex(int resultSetIndex)
		{
			this.resultSetIndex = resultSetIndex;
		}

		public String getOrder()
		{
			if (order == null || order.equals("")) {
				return "date";   
			}
			else {
				return order;    
			}            
		}
		public void setOrder(String order)
		{
			this.order = order;
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

		public String getFilterTagList() {
			return filterTagList;
		}

		public void setFilterTagList(String filterTagList) {
			this.filterTagList = filterTagList;
		}
	} 

	public class RowBean extends WikiEntryDto
	{  
		private int resultSetIndex;  
		private int communityId;
		private String communityName;
		private String  CommunitySysType;
		private int likeCount;
		private int imageCount;
		private int sectionImageCount;
		
		public boolean isAdsPosition()
		{
			return resultSetIndex%6==0;
		}

		public String getLatestPostOn() throws Exception
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

		public String getBodyDisplay() throws Exception
		{
			// The first 50 chars of the body display in the view. If these chars contain any links, just display the part before the link.
			if ( (this.getBody()==null) || (this.getBody().length()==0)) return "";

			String sBody = this.getBody();

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

		public String getTaggedKeywords(int pageNumber, String filterTag, String commOption, String sortOption, String toggleList){
			String taggedKeywords = "";
			try {
				List tags = tagFinder.getTagsForParentTypeByPopularity(this.getId(), 0, 20, "WikiEntry");
				String filterTagString = "";
				String sortOptionString = "";

				if (filterTag != null && !"".equals(filterTag))
					filterTagString = "&fTagList="+filterTag;

				if (sortOption != null && !"".equals(sortOption))
					sortOptionString = "&sortByOption="+sortOption;

				for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
					TagDto tb = (TagDto) iterator.next();
					String tag = tb.getTagText().trim().toLowerCase();
					taggedKeywords += "<a href='wiki/allWikis.do?filterTag="+tag+filterTagString+sortOptionString+"&toggleList="+toggleList+" ' class='euInfoSelect' style='display: inline;' title='Click to filter by tag &#39;"+tag+"&#39;'>"+tag+"</a>";
					if (iterator.hasNext())taggedKeywords += " , ";
				}
			} catch (Exception e) {
				return taggedKeywords;
			}
			return taggedKeywords;
		}

		public int getLikeCount() {
			return likeCount;
		}

		public void setLikeCount(int likeCount) {
			this.likeCount = likeCount;
		}

		public int getVersionCount() throws Exception {
			return wikiEntryFinder.getWikiEntryVersionCount(getEntryId());
		}

		public int getCommunityId() {
			return communityId;
		}

		public void setCommunityId(int communityId) {
			this.communityId = communityId;
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
			return resultSetIndex % 3 == 0;
		}

		public boolean isOddRow()
		{
			return resultSetIndex % 3 != 0;
		}
		
		public boolean isSecEvenRow()
		{
			return resultSetIndex % 2 == 0;
		}

		public boolean isSecOddRow()
		{
			return resultSetIndex % 2 == 1;
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

		public int getImageCount() {
			return imageCount;
		}

		public void setImageCount(Long imageCount) {
			this.imageCount = imageCount.intValue();
		}

		public int getSectionImageCount() {
			return sectionImageCount;
		}

		public void setSectionImageCount(Long sectionImageCount) {
			this.sectionImageCount = sectionImageCount.intValue();
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

	public void setWikiFeature(WikiFeature wikiFeature)
	{
		this.wikiFeature = wikiFeature;
	}

	public SubscriptionFinder getSubscriptionFinder()
	{
		return subscriptionFinder;
	}
	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder)
	{
		this.subscriptionFinder = subscriptionFinder;
	}

	public void setWikiFinder(WikiFinder wikiFinder) {
		this.wikiFinder = wikiFinder;
	}

	public void setWikiEntryFinder(WikiEntryFinder wikiEntryFinder) {
		this.wikiEntryFinder = wikiEntryFinder;
	}

	public void setTagFinder(TagFinder tagFinder) {
		this.tagFinder = tagFinder;
	}
}
