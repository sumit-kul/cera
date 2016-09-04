package com.era.community.communities.ui.action;

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

import support.community.database.EntityWrapper;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.IndexCommandBeanImpl;
import support.community.framework.Option;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.communities.ui.dto.CommunityDto;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.tagging.ui.dto.TagDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/community/showCommunities.do"
 */
public class AllCommunityIndexAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected CommunityFinder communityFinder;
	protected UserFinder userFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		User currentUser = context.getCurrentUser();
		Command cmd = (Command)data;

		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
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

		QueryScroller scroller = null;
		if (currentUser != null && ("2".equals(cmd.getCommunityOption()) || "3".equals(cmd.getCommunityOption()))) {
			scroller = communityFinder.listActiveCommunitiesForMember(currentUser, cmd.getFilterTagList(), cmd.getCommunityOption(), cmd.getSortByOption());
		} else if (currentUser != null && "4".equals(cmd.getCommunityOption())) {
			scroller = communityFinder.listActiveCommunitiesForFollower(currentUser, cmd.getFilterTagList(), cmd.getSortByOption());
		} else {
			scroller = communityFinder.listActiveCommunities(cmd.getFilterTagList(), cmd.getCommunityOption(), cmd.getSortByOption());
		}

		if (pageNum != null) {
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = null;
			if (pNumber > 0) {
				scroller.setBeanClass(RowBean.class, this);
				scroller.setPageSize(18);
				IndexedScrollerPage page = scroller.readPage(pNumber);
				json = page.toJsonString(pNumber, cmd.getFilterTagList(), cmd.getCommunityOption(), cmd.getSortByOption(), cmd.getToggleList());
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
			scroller.setPageSize(18);
			cmd.setPageCount(scroller.readPageCount());
			cmd.setRowCount(scroller.readRowCount());
			cmd.setHeaderLabel(this.getHeaderLabel(cmd));
			scroller.setBeanClass(RowBean.class, this);
			cmd.setScrollerPage(scroller.readPage(1));
			cmd.setPage(2);
			return new ModelAndView("common/allCommunity");
		}
	}

	public class RowBean extends CommunityDto implements EntityWrapper
	{
		private int resultSetIndex;
		private int level = 1;
		private Community community;
		private boolean member;
		private boolean membershipRequested;
		private String firstName;
		private String lastName;

		public String getTaggedKeywords(int pageNumber, String filterTag, String commOption, String sortOption, String toggleList){
			String taggedKeywords = "";
			try {
				List tags = community.getTagsForOnlyCommunityByPopularity(20);
				String filterTagString = "";
				String commOptionString = "";
				String sortOptionString = "";

				if (filterTag != null && !"".equals(filterTag))
					filterTagString = "&fTagList="+filterTag;

				if (commOption != null && !"".equals(commOption))
					commOptionString = "&communityOption="+commOption;

				if (sortOption != null && !"".equals(sortOption))
					sortOptionString = "&sortByOption="+sortOption;

				for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
					TagDto tb = (TagDto) iterator.next();
					String tag = tb.getTagText().trim().toLowerCase();
					taggedKeywords += "<a href='community/showCommunities.do?filterTag="+tag+filterTagString+commOptionString+sortOptionString+"&toggleList="+toggleList+" ' class='euInfoSelect normalTip' style='display: inline;' title='Click to filter by tag &#39;"+tag+"&#39;'>"+tag+"</a>";
					if (iterator.hasNext())taggedKeywords += ", ";
				}
			} catch (Exception e) {
				return taggedKeywords;
			}
			return taggedKeywords;
		}

		public String getLogoPresent(){
			return Boolean.toString(this.getCommunityLogoPresent());
		}

		public String getType () throws Exception
		{
			return community.getCommunityType();
		}

		public boolean isMember() throws Exception
		{
			if (contextManager.getContext().getCurrentUser() == null ) return false;
			if (contextManager.getContext().isUserAuthenticated() == false) return false;
			int currentUser = contextManager.getContext().getCurrentUser().getId();
			return community.isMember(currentUser);
		}

		public boolean isMembershipRequested() throws Exception
		{
			if (contextManager.getContext().getCurrentUser() == null ) return false;
			if (contextManager.getContext().isUserAuthenticated() == false) return false;
			if (community.getCommunityType().equals("Public")) return false;
			User user = contextManager.getContext().getCurrentUser();
			return community.isMemberShipRequestPending(user);
		}

		public String getMemberCountString() throws Exception
		{
			int n = community.getMemberCount();
			if (n <= 1)
				return n + " member";
			else
				return n + " members";
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

		public String getBodyForDisplay()
		{
			if ( (this.getWelcomeText()==null) || (this.getWelcomeText().length()==0)) return "";

			String sBody = this.getWelcomeText();

			sBody = sBody.replaceAll("<p>","");
			sBody = sBody.replaceAll("</p>"," ");
			sBody = sBody.replaceAll("   ", " ");

			if (sBody.contains("<")) {
				sBody = sBody.substring(0, sBody.indexOf("<"));
				if(sBody.length() >= 300)sBody.substring(0, 300);
				sBody.concat("...");
			} else if (sBody.length() >= 300) {
				sBody = sBody.substring(0, 300).concat("...");
			}
			return sBody;
		}

		public String getCreatedBy()
		{
			return getFirstName() + " " + getLastName();
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
		
		public boolean isAdsPosition()
		{
			return resultSetIndex%6==0;
		}

		public final int getResultSetIndex()
		{
			return resultSetIndex;
		}

		public final void setResultSetIndex(int resultSetIndex)
		{
			this.resultSetIndex = resultSetIndex;
		}

		public void setCommunity(Community community)
		{
			this.community = community;
		}

		public int getLevel()
		{
			return level;
		}

		public void setLevel(int level)
		{
			this.level = level;
		}

		/**
		 * @return the firstName
		 */
		public String getFirstName() {
			return firstName;
		}

		/**
		 * @param firstName the firstName to set
		 */
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		/**
		 * @return the lastName
		 */
		public String getLastName() {
			return lastName;
		}

		/**
		 * @param lastName the lastName to set
		 */
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
	}
	
	private String getHeaderLabel(Command cmd) throws Exception {
		//return ((Option)getCommunityOptions().get(Integer.parseInt(getCommunityOption())- 1)).getLabel();
		if ("1".equals(cmd.getCommunityOption())) {
			return "All Communities";
		} else if ("2".equals(cmd.getCommunityOption())) {
			return "Public Communities";
		} else if ("3".equals(cmd.getCommunityOption())) {
			return "Private Communities";
		} else if ("4".equals(cmd.getCommunityOption())) {
			return "Communities I Own";
		} else if ("5".equals(cmd.getCommunityOption())) {
			return "Communities I'm a Member";
		} else if ("6".equals(cmd.getCommunityOption())) {
			return "Communities I'm Following";
		}
		return "Communities";
	}

	public class Command extends IndexCommandBeanImpl
	{
		private int communitiesPendingApproval;
		private int parentId = 0;
		private String communityType;
		private String headerLabel;
		private String communityOption = "1";
		private String sortByOption = "1";
		private String filterTagList = "";

		public List getCommunityOptions() throws Exception
		{
			User current = contextManager.getContext().getCurrentUser();
			int size = current == null ? 1 : 4;
			List<Option> communityOptionList = new ArrayList<Option>(size);
			Option opt1 = new Option();
			opt1.setLabel("All Communities");
			opt1.setValue(1);
			communityOptionList.add(opt1);

			/*Option opt2 = new Option();
			opt2.setLabel("Public Communities");
			opt2.setValue(2);
			communityOptionList.add(opt2);

			Option opt3 = new Option();
			opt3.setLabel("Private Communities");
			opt3.setValue(3);
			communityOptionList.add(opt3);*/

			if (current != null) {
				Option opt2 = new Option();
				opt2.setLabel("I'm an Owner");
				opt2.setValue(2);
				communityOptionList.add(opt2);

				Option opt3 = new Option();
				opt3.setLabel("I'm a Member");
				opt3.setValue(3);
				communityOptionList.add(opt3);

				Option opt4 = new Option();
				opt4.setLabel("I'm Following");
				opt4.setValue(4);
				communityOptionList.add(opt4);
			}
			return communityOptionList;
		}

		public List getSortByOptionOptions() throws Exception
		{
			List<Option> sortByOptionList = new ArrayList<Option>(2);
			Option opt1 = new Option();
			opt1.setLabel("Most Popular");
			opt1.setValue(1);
			sortByOptionList.add(opt1);

			Option opt2 = new Option();
			opt2.setLabel("Most Recent");
			opt2.setValue(2);
			sortByOptionList.add(opt2);
			
			Option opt3 = new Option();
			opt3.setLabel("Name");
			opt3.setValue(3);
			sortByOptionList.add(opt3);
			
			return sortByOptionList;
		}

		public String getCommunityType() {
			return communityType;
		}

		public void setCommunityType(String communityType) {
			this.communityType = communityType;
		}

		public int getParentId()
		{
			return parentId;
		}

		public void setParentId(int parentId)
		{
			this.parentId = parentId;
		}

		public int getCommunitiesPendingApproval()
		{
			return communitiesPendingApproval;
		}

		public void setCommunitiesPendingApproval(int communitiesPendingApproval)
		{
			this.communitiesPendingApproval = communitiesPendingApproval;
		}

		public String getCommunityOption() {
			return communityOption;
		}

		public void setCommunityOption(String communityOption) {
			this.communityOption = communityOption;
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
					returnHtmlTags += "<a href='community/showCommunities.do?fTagList="+filterList+"&rmFilterTag="+tag+"&communityOption="+this.getCommunityOption()+"&sortByOption="+this.getSortByOption()+"&toggleList="+this.getToggleList()+"' class='euInfoRmTag' style='display: inline;' >"+tag+" <span style='color: rgb(137, 143, 156); font-size: 12px;' class='normalTip' title='Remove "+tag+" filter' >X</span></a>";
				}
			}
			return returnHtmlTags;
		}

		public void setHeaderLabel(String headerLabel) {
			this.headerLabel = headerLabel;
		}

		public String getHeaderLabel() {
			return headerLabel;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}
}