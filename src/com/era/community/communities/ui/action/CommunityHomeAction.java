package com.era.community.communities.ui.action;

import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.BlobData;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.IndexCommandBeanImpl;
import support.community.util.StringFormat;
import support.community.util.StringHelper;

import com.era.community.assignment.dao.AssignmentFinder;
import com.era.community.assignment.ui.dto.AssignmentHeaderDto;
import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.FileManager;
import com.era.community.blog.dao.BlogEntryFinder;
import com.era.community.blog.dao.BlogEntryLike;
import com.era.community.blog.dao.BlogEntryLikeFinder;
import com.era.community.blog.ui.dto.BlogEntryHeaderDto;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.doclib.dao.DocumentFinder;
import com.era.community.doclib.dao.DocumentLibrary;
import com.era.community.doclib.dao.DocumentLibraryFinder;
import com.era.community.doclib.dao.DocumentLike;
import com.era.community.doclib.dao.DocumentLikeFinder;
import com.era.community.doclib.ui.dto.DocumentHeaderDto;
import com.era.community.events.dao.EventFinder;
import com.era.community.events.dao.EventLike;
import com.era.community.events.dao.EventLikeFinder;
import com.era.community.events.ui.dto.EventHeaderDto;
import com.era.community.forum.dao.ForumItemFinder;
import com.era.community.forum.dao.ForumItemLike;
import com.era.community.forum.dao.ForumItemLikeFinder;
import com.era.community.forum.ui.dto.ForumItemHeaderDto;
import com.era.community.monitor.dao.CommunitySubscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.tagging.ui.dto.TagDto;
import com.era.community.wiki.dao.WikiEntryFinder;
import com.era.community.wiki.dao.WikiEntryLike;
import com.era.community.wiki.dao.WikiEntryLikeFinder;
import com.era.community.wiki.ui.dto.WikiEntryHeaderDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/** 
 * Action used to display a community's homepage.  
 * @spring.bean name="/cid/[cec]/home.do"
 */
public class CommunityHomeAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager;
	private CommunityFinder communityFinder;
	private ForumItemFinder forumItemFinder;
	private UserFinder userFinder;
	private DocumentLibraryFinder documentLibraryFinder;
	private SubscriptionFinder subscriptionFinder;
	private DocumentFinder documentFinder;
	private BlogEntryFinder blogEntryFinder;
	private WikiEntryFinder wikiEntryFinder;
	private EventFinder eventFinder;
	private AssignmentFinder assignmentFinder;
	private BlogEntryLikeFinder blogEntryLikeFinder;
	private WikiEntryLikeFinder wikiEntryLikeFinder;
	private DocumentLikeFinder documentLikeFinder;
	private ForumItemLikeFinder forumItemLikeFinder;
	private EventLikeFinder eventLikeFinder;

	protected ModelAndView handle(Object data) throws Exception
	{        
		Command cmd = (Command) data;  
		CommunityEraContext context = contextManager.getContext();
		Community comm = context.getCurrentCommunity();  

		if (context.getCurrentUser() == null && comm.isPrivate()) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}

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

		QueryScroller scroller = communityFinder.getItemsForCommunityHome(comm) ;

		if (pageNum != null) {
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = null;
			if (pNumber > 0) {
				scroller.setBeanClass(RowBean.class, this);
				scroller.setPageSize(20);
				IndexedScrollerPage page = scroller.readPage(pNumber);
				json = page.toJsonStringMyHome(pNumber);
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
			BlobData logo= comm.getCommunityLogo();
			cmd.setSizeInBytes(logo.getLength());
			cmd.setIconImage(FileManager.computeIconImage(logo.getContentType()));

			int mediaCount = documentFinder.getMediaCountForCommunity(context.getCurrentCommunity());
			cmd.setMediaCount(mediaCount);

			User creator = userFinder.getUserEntity(comm.getCreatorId());
			cmd.setCreator(creator);
			cmd.setCreated(comm.getCreated());


			cmd.setPrivateCommunity(comm.isPrivate());
			QueryScroller requestsScroller = comm.listUsersRequestingMembership("Unapproved Requests", null);
			cmd.setNumberOfMembershipRequests(requestsScroller.readRowCount());

			cmd.setPrivateCommunity(comm.isPrivate());
			cmd.setCommunityId(comm.getId());
			cmd.setMember(isMember(comm));
			cmd.setOwner(isOwner(comm));
			cmd.setFollower(isFollower(comm));
			cmd.setAdminMember(isAdminMember(comm));
			cmd.setCommunityLogoPresent(comm.isLogoPresent());

			scroller.setPageSize(20);
			scroller.setBeanClass(RowBean.class, this);
			cmd.setPageCount(scroller.readPageCount());
			cmd.setRowCount(scroller.readRowCount());
			IndexedScrollerPage page = scroller.readPage(1);
			page.toJsonStringMyHome(1);
			cmd.setScrollerPage(page);
			cmd.setPage(2);
			
			cmd.setCommunity(comm);
			cmd.setSearchType("Community");
			cmd.setQueryText(comm.getName());
			return new ModelAndView("/community/communityHome");
		}
	}

	public boolean isMember(Community community) throws Exception
	{
		if (contextManager.getContext().getCurrentUser() == null ) return false;
		if (contextManager.getContext().isUserAuthenticated() == false) return false;
		int currentUser = contextManager.getContext().getCurrentUser().getId();
		return community.isMember(currentUser);
	}

	public boolean isOwner(Community community) throws Exception
	{
		if (contextManager.getContext().getCurrentUser() == null ) return false;
		if (contextManager.getContext().isUserAuthenticated() == false) return false;
		return community.isCommunityOwner(contextManager.getContext().getCurrentUser());
	}

	public boolean isAdminMember(Community community) throws Exception
	{
		if (contextManager.getContext().getCurrentUser() == null ) return false;
		if (contextManager.getContext().isUserAuthenticated() == false) return false;
		return community.isAdminMember(contextManager.getContext().getCurrentUser());
	}

	public boolean isFollower(Community community) throws Exception
	{
		if (contextManager.getContext().getCurrentUser() == null ) return false;
		if (contextManager.getContext().isUserAuthenticated() == false) return false;
		int currentUser = contextManager.getContext().getCurrentUser().getId();
		try {
			CommunitySubscription subscriptions = subscriptionFinder.getCommunitySubscriptionForUser(community.getId(), currentUser);
			if (subscriptions != null) {
				return true;
			} else {
				return false;
			}
		} catch (ElementNotFoundException e) {
			return false; 
		}
	}

	public class Command extends IndexCommandBeanImpl
	{     
		private boolean membershipRequested;
		private int mediaCount;
		private User creator;    
		private MultipartFile logo;
		private long sizeInBytes;       
		private String iconImage;
		private IndexedScrollerPage items;
		private int pageCount = 0;
		private String toggleList = "false";
		private boolean member = false;
		private boolean owner = false;
		private boolean adminMember = false;
		private boolean follower = false;
		private boolean privateCommunity = false;
		private Date created;
		private int creatorId;
		private boolean communityLogoPresent;
		private int communityId;
		private String filterTagList = "";
		private Community community;
		
		public String getMarkupImage()
		{
			String url = "";
			try {
				if (contextManager.getContext().getCurrentCommunity() != null) {
					if (this.isCommunityLogoPresent()) {
						url = contextManager.getContext().getContextUrl()+ "/commLogoDisplay.img?communityId=" + this.getCommunityId();
					} else {
						url = contextManager.getContext().getContextUrl()+ "/img/community_Image.png";
					}
				}
			} catch (Exception e) {
			}
			return url;
		}
		
		public String getIsoPostedOn() throws Exception
		{
			TimeZone tz = TimeZone.getTimeZone("UTC");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
			df.setTimeZone(tz);
			String nowAsISO = df.format(getCreated());
			return nowAsISO;
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

				Date date = formatter.parse(getCreated().toString());
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date);

			} catch (ParseException e) {
				return getCreated().toString();
			}
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
					returnHtmlTags += "<a href='community/showCommunities.do?fTagList="+filterList+"&rmFilterTag="+tag+"&toggleList="+this.getToggleList()+"' class='euInfoRmTag' style='display: inline;' title='Remove "+tag+" filter'>"+tag+" <span style='color: rgb(137, 143, 156); font-size: 12px;' title='Remove "+tag+" filter' >X</span></a>";
				}
			}
			return returnHtmlTags;
		}

		public String getCreatedBy()
		{
			try {
				User creator = userFinder.getUserEntity(getCreatorId());
				return creator.getFirstName() +" "+ creator.getLastName();
			} catch (Exception e) {
				return "";
			}
		}

		public String getTaggedKeywords(){
			String taggedKeywords = "";
			try {
				List tags = community.getTagsForOnlyCommunityByPopularity(20);

				for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
					TagDto tb = (TagDto) iterator.next();
					String tag = tb.getTagText().trim().toLowerCase();
					taggedKeywords += "<a href='community/showCommunities.do?filterTag="+tag+" ' class='normalTip euTagSelect' style='display: inline;' title='Click to filter by tag &#39;"+tag+"&#39;'>"+tag+"</a>";
					if (iterator.hasNext())taggedKeywords += " , ";
				}
			} catch (Exception e) {
				return taggedKeywords;
			}
			return taggedKeywords;
		}

		private List activeUsers; 

		private ArrayList<String> pollOptionList; // The available options for the poll 
		private ArrayList<String> pollResultArray; // The results the poll (shown when user has already voted
		private int voteCount; // Number of votes cast
		private String pollVote; // The choice made by a user who has voted
		private boolean voteAlreadyPresent; // True if current user has already voted

		private int NumberOfMembershipRequests;

		public int getNumberOfMembershipRequests()
		{
			return NumberOfMembershipRequests;
		}
		public void setNumberOfMembershipRequests(int numberOfMembershipRequests)
		{
			NumberOfMembershipRequests = numberOfMembershipRequests;
		}
		public int getVoteCount() {
			return voteCount;
		}
		public void setVoteCount(int voteCount) {
			this.voteCount = voteCount;
		}						
		public ArrayList<String> getPollResultArray() {
			return pollResultArray;
		}
		public void setPollResultArray(ArrayList<String> pollResultArray) {
			this.pollResultArray = pollResultArray;
		}
		public boolean isVoteAlreadyPresent() {
			return voteAlreadyPresent;
		}
		public void setVoteAlreadyPresent(boolean voteAlreadyPresent) {
			this.voteAlreadyPresent = voteAlreadyPresent;
		}
		public String getPollVote() {
			return pollVote;
		}
		public void setPollVote(String pollVote) {
			this.pollVote = pollVote;
		}
		public ArrayList getPollOptionList() {
			return pollOptionList;
		}
		public void setPollOptionList(ArrayList<String> pollOptionList) {
			this.pollOptionList = pollOptionList;
		}
		public final String getIconImage()
		{
			return iconImage;
		}
		public final void setIconImage(String iconImage)
		{
			this.iconImage = iconImage;
		}
		public final MultipartFile getLogo()
		{
			return logo;
		}
		public final void setLogo(MultipartFile logo)
		{
			this.logo = logo;
		}
		public final long getSizeInBytes()
		{
			return sizeInBytes;
		}
		public final void setSizeInBytes(long sizeInBytes)
		{
			this.sizeInBytes = sizeInBytes;
		}
		public final List getActiveUsers()
		{
			return activeUsers;
		}
		public final void setActiveUsers(List activeUsers)
		{
			this.activeUsers = activeUsers;
		}
		public IndexedScrollerPage getItems() {
			return items;
		}
		public void setItems(IndexedScrollerPage items) {
			this.items = items;
		}

		/**
		 * @return the pageCount
		 */
		public int getPageCount() {
			return pageCount;
		}

		/**
		 * @param pageCount the pageCount to set
		 */
		public void setPageCount(int pageCount) {
			this.pageCount = pageCount;
		}

		/**
		 * @return the toggleList
		 */
		public String getToggleList() {
			return toggleList;
		}

		/**
		 * @param toggleList the toggleList to set
		 */
		public void setToggleList(String toggleList) {
			this.toggleList = toggleList;
		}

		/**
		 * @return the member
		 */
		public boolean isMember() {
			return member;
		}

		/**
		 * @param member the member to set
		 */
		public void setMember(boolean member) {
			this.member = member;
		}

		/**
		 * @return the follower
		 */
		public boolean isFollower() {
			return follower;
		}

		/**
		 * @param follower the follower to set
		 */
		public void setFollower(boolean follower) {
			this.follower = follower;
		}

		/**
		 * @return the privateCommunity
		 */
		public boolean isPrivateCommunity() {
			return privateCommunity;
		}

		/**
		 * @param privateCommunity the privateCommunity to set
		 */
		public void setPrivateCommunity(boolean privateCommunity) {
			this.privateCommunity = privateCommunity;
		}

		public int getCommunityId() {
			return communityId;
		}

		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}

		public String getFilterTagList() {
			return filterTagList;
		}

		public void setFilterTagList(String filterTagList) {
			this.filterTagList = filterTagList;
		}

		public boolean isOwner() {
			return owner;
		}

		public void setOwner(boolean owner) {
			this.owner = owner;
		}

		public Community getCommunity() {
			return community;
		}

		public void setCommunity(Community community) {
			this.community = community;
		}

		public boolean isAdminMember() {
			return adminMember;
		}

		public void setAdminMember(boolean adminMember) {
			this.adminMember = adminMember;
		}

		public boolean isMembershipRequested() {
			return membershipRequested;
		}

		public void setMembershipRequested(boolean membershipRequested) {
			this.membershipRequested = membershipRequested;
		}

		public int getMediaCount() {
			return mediaCount;
		}

		public void setMediaCount(int mediaCount) {
			this.mediaCount = mediaCount;
		}

		public User getCreator() {
			return creator;
		}

		public void setCreator(User creator) {
			this.creator = creator;
		}

		public Date getCreated() {
			return created;
		}

		public void setCreated(Date created) {
			this.created = created;
		}

		public int getCreatorId() {
			return creatorId;
		}

		public void setCreatorId(int creatorId) {
			this.creatorId = creatorId;
		}

		public boolean isCommunityLogoPresent() {
			return communityLogoPresent;
		}

		public void setCommunityLogoPresent(boolean communityLogoPresent) {
			this.communityLogoPresent = communityLogoPresent;
		}
	}

	public class RowBean 
	{
		private int communityId;
		private int documentId;
		private int folderId;
		private int docGroupNumber;
		private int blogEntryId;
		private int forumItemId;
		private int wikiEntryId;
		private int eventId;
		private int assignmentId;
		private int userId;
		private int personalBlogEntryId;

		private int itemId;
		private String itemType;
		private String itemTitle;
		private boolean logoPresent;
		private int posterid;
		private String contentType;
		private String fileContentType;
		private String firstName;
		private String lastName;
		private boolean photoPresent;
		private String itemDesc;
		private Long imageCount;
		private String datePosted;
		private String communityName;
		private String creatorName;
		private int creatorId;
		private boolean creatorPhotoPresent;
		private String dateCreation;
		private Long likeCount;
		private int visitors;
		private String venue;
		private String location;
		private String address;
		private String startDate;
		private String endDate;
		private Long commentCount;
		private boolean eventPhotoPresent;
		private int countDoc;
		
		private List<DocumentHeaderDto> photoList = new ArrayList<DocumentHeaderDto>();
		private List<DocumentHeaderDto> secondPhotoList = new ArrayList<DocumentHeaderDto>();
		private int lastItemNumber;

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
		
		public boolean isAdsPosition()
		{
			return resultSetIndex%4==0;
		}

		public String getDatePosted() {
			return datePosted;
		}

		public void setDatePosted(String datePosted) {
			this.datePosted = datePosted;
		}

		public void getPrepareRow() throws Exception {
			if (this.getCommunityId() > 0) {
				Community community = communityFinder.getCommunityForId(this.getCommunityId());
				User creator = userFinder.getUserForId(community.getCreatorId());
				this.setLogoPresent(community.isLogoPresent());
				this.setCommunityName(community.getName());
				this.setCreatorId(community.getCreatorId());
				this.setCreatorName(creator.getFullName());
				this.setCreatorPhotoPresent(creator.isPhotoPresent());
			} 
			if (this.getBlogEntryId() > 0) {
				BlogEntryHeaderDto blogEntry = blogEntryFinder.getBlogEntryForHeader(this.getBlogEntryId());
				this.setItemType("BlogEntry");
				this.setItemId(this.getBlogEntryId());
				this.setItemTitle(blogEntry.getTitle());
				this.setPosterid(blogEntry.getPosterId());
				this.setContentType("");
				this.setDatePosted(blogEntry.getDatePosted());
				this.setLikeCount(blogEntry.getLikeCount());
				this.setFirstName(blogEntry.getFirstName());
				this.setLastName(blogEntry.getLastName());
				this.setPhotoPresent(blogEntry.getPhotoPresent());
				this.setVisitors(blogEntry.getVisitors());
				this.setImageCount(blogEntry.getImageCount());
				this.setItemDesc(blogEntry.getItemDesc());
				this.setDatePosted(blogEntry.getDatePosted());
			} else if (this.getWikiEntryId() > 0) {
				WikiEntryHeaderDto wikiEntry = wikiEntryFinder.getWikiEntryForHeader(this.getWikiEntryId());
				this.setItemType("WikiEntry");
				this.setItemId(wikiEntry.getId());
				this.setItemTitle(wikiEntry.getTitle());
				this.setPosterid(wikiEntry.getPosterId());
				this.setContentType("");
				this.setDatePosted(wikiEntry.getDatePosted());
				this.setLikeCount(wikiEntry.getLikeCount());
				this.setFirstName(wikiEntry.getFirstName());
				this.setLastName(wikiEntry.getLastName());
				this.setPhotoPresent(wikiEntry.getPhotoPresent());
				this.setVisitors(wikiEntry.getVisitors());
				this.setImageCount(wikiEntry.getImageCount());
				this.setItemDesc(wikiEntry.getItemDesc());
				this.setDatePosted(wikiEntry.getDatePosted());
			} else if (this.getEventId() > 0) {
				EventHeaderDto event = eventFinder.getEventForHeader(this.getEventId());
				this.setItemType("Event");
				this.setItemId(this.getEventId());
				this.setItemTitle(event.getName());
				this.setPosterid(event.getPosterId());
				this.setContentType("");
				this.setLikeCount(event.getLikeCount());
				this.setFirstName(event.getFirstName());
				this.setLastName(event.getLastName());
				this.setPhotoPresent(event.getUserPhotoPresent());
				this.setEventPhotoPresent(event.getPhotoPresent());
				//this.setVisitors(event.getVisitors());
				this.setImageCount(event.getImageCount());
				this.setItemDesc(event.getItemDesc());
				this.setDatePosted(event.getDatePosted());
				this.setStartDate(event.getStartDate());
				this.setEndDate(event .getEndDate());
			} else if (this.getForumItemId() > 0) {
				ForumItemHeaderDto forumItem = forumItemFinder.getForumItemForHeader(this.getForumItemId());
				this.setItemType("ForumTopic");
				this.setItemId(this.getForumItemId());
				this.setItemTitle(forumItem.getSubject());
				this.setPosterid(forumItem.getAuthorId());
				this.setContentType("");
				this.setDatePosted(forumItem.getDatePosted().toString());
				this.setLikeCount(forumItem.getLikeCount());
				this.setFirstName(forumItem.getFirstName());
				this.setLastName(forumItem.getLastName());
				this.setPhotoPresent(forumItem.getPhotoPresent());
				this.setVisitors(forumItem.getCVisitors());
				this.setImageCount(forumItem.getImageCount());
				this.setItemDesc(forumItem.getItemDesc());
				this.setDatePosted(forumItem.getDatePosted());
			} else if ((this.getFolderId() > 0 || this.getDocumentId() > 0) && this.getUserId() > 0) {
				boolean isNotImage = false;
				try {
					User poster = userFinder.getUserForId(this.getUserId());
					DocumentLibrary lib = documentLibraryFinder.getDocumentLibraryForCommunityId(this.getCommunityId());
					int docCount = documentFinder.countPhotoListForHeader(lib.getId(), this.getFolderId(), this.getUserId(), this.getDocGroupNumber());
					List<DocumentHeaderDto> temp = documentFinder.getPhotoListForHeader(lib.getId(),this.getFolderId(), this.getUserId(), this.getDocGroupNumber());
					if (docCount == 1) {
						DocumentHeaderDto dto = temp.get(0);
						if(dto.getFileContentType().contains("image")){
							this.setItemType("Photo");
							this.setContentType("Single");
						} else {
							this.setItemType("Document");
							this.setContentType("Single");
						}
						this.setItemId(dto.getDocId());
						this.setFileContentType(dto.getFileContentType());
						this.setItemTitle(dto.getTitle());
						this.setPosterid(poster.getId());
						this.setFirstName(poster.getFirstName());
						this.setLastName(poster.getLastName());
						this.setPhotoPresent(poster.isPhotoPresent());
						this.setImageCount(Long.valueOf(docCount));
					} else {
						for (int i = 0; i < temp.size(); i++) {
							DocumentHeaderDto dto = temp.get(i);
							if (!dto.getFileContentType().contains("image")) {
								isNotImage = true;
							}
							if (i<5) {
								this.photoList.add(temp.get(i));
							} else {
								this.secondPhotoList.add(temp.get(i));
							}
						}
						if (docCount > 5) {
							this.setCountDoc(docCount-4);
							this.setLastItemNumber(4);
						} else {
							this.setCountDoc(0);
							this.setLastItemNumber(0);
						}
						if (isNotImage) {
							this.setItemType("Document");
							this.setContentType("Group");
						} else {
							this.setItemType("Photo");
							this.setContentType("Group");
						}
						this.setPosterid(poster.getId());
						this.setFirstName(poster.getFirstName());
						this.setLastName(poster.getLastName());
						this.setPhotoPresent(poster.isPhotoPresent());
						this.setImageCount(Long.valueOf(docCount));
					}
				} catch (ElementNotFoundException e) {
					this.setPosterid(this.getUserId());
				}
			} else if (this.getAssignmentId() > 0) {
				AssignmentHeaderDto assignment = assignmentFinder.getAssignmentForHeader(this.getAssignmentId());
				this.setItemType("Assignment");
				this.setItemId(this.getAssignmentId());
				this.setItemTitle(assignment.getTitle());
				this.setPosterid(assignment.getCreatorId());
				this.setContentType("");
				this.setDatePosted(assignment.getDatePosted().toString());
				this.setLikeCount(assignment.getLikeCount());
				this.setFirstName(assignment.getFirstName());
				this.setLastName(assignment.getLastName());
				this.setPhotoPresent(assignment.getPhotoPresent());
				this.setImageCount(assignment.getImageCount());
				this.setItemDesc(assignment.getItemDesc());
				this.setDatePosted(assignment.getDatePosted());
			} else {
				this.setItemType("");
			}
		}

		public String getItemUrl() throws Exception
		{
			if (this.getItemType().contains("Event")) {
				return ("cid/"+this.getCommunityId()+"/event/showEventEntry.do?id="+this.getItemId());
			}  
			if (this.getItemType().contains("ForumTopic")) {
				return ("cid/"+this.getCommunityId()+"/forum/forumThread.do?id="+this.getItemId());
			}
			if (this.getItemType().contains("WikiEntry")) {
				return ("cid/"+this.getCommunityId()+"/wiki/wikiDisplay.do?id="+this.getItemId());
			}
			if (this.getItemType().endsWith("BlogEntry")) {
				return ("cid/"+this.getCommunityId()+"/blog/blogEntry.do?id="+this.getItemId());
			}
			if (this.getItemType().endsWith("Document")) {
				return ("cid/"+this.getCommunityId()+"/library/documentdisplay.do?id="+this.getItemId());
			}
			if (this.getItemType().contains("Member")) {
				return ("cid/"+this.getCommunityId()+"/members/member-display.do?id="+this.getItemId());
			}
			return "";
		}
		
		public String getIconType() {
	    	if (this.getFileContentType() != null) {
	    		if (getFileContentType().contains("application/vnd.ms-excel") || getFileContentType().contains("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
	        		return "xls";
	    		} else if (getFileContentType().contains("application/x-tar") || getFileContentType().contains("application/zip")) {
	    			return "zip";
	    		} else if (getFileContentType().contains("application/pdf")) {
	    			return "pdf";
	    		} else if (getFileContentType().contains("application/xml")) {
	    			return "xml";
	    		} else if (getFileContentType().contains("image/jpeg")) {
	    			return "img";
	    		} else if (getFileContentType().contains("text/richtext") || getFileContentType().contains("application/msword") || getFileContentType().contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
	    			return "doc";
	    		} else if (getFileContentType().contains("application/vnd.ms-powerpoint") || getFileContentType().contains("application/vnd.openxmlformats-officedocument.presentationml.presentation")) {
	    			return "ppt";
	    		} 
	    		return "file";
			} else {
				return "file";
			}
	    }

		public String getItemBaseTitle() throws Exception
		{
			if (this.getItemType().contains("Event")) {
				return ("/cid/"+this.getCommunityId()+"/event/showEvents.do");
			}  
			if (this.getItemType().contains("ForumTopic")) {
				return ("/cid/"+this.getCommunityId()+"/forum/showTopics.do");
			}
			if (this.getItemType().contains("WikiEntry")) {
				return ("/cid/"+this.getCommunityId()+"/wiki/showWikiEntries.do");
			}
			if (this.getItemType().endsWith("BlogEntry")) {
				return ("/cid/"+this.getCommunityId()+"/blog/viewBlog.do");
			}
			if (this.getItemType().endsWith("Document")) {
				return ("/cid/"+this.getCommunityId()+"/library/showLibraryItems.do");
			}
			if (this.getItemType().contains("Member")) {
				return ("/cid/"+this.getCommunityId()+"/connections/showConnections.do");
			}
			return "";
		}

		public int getItemId() {
			return itemId;
		}

		public String getBodyDisplay() throws Exception
		{
			if ( (this.getItemDesc() == null) || (this.getItemDesc().length() == 0)) return "";
			String sBody = this.getItemDesc();
			sBody = Jsoup.parse(sBody).text();
			sBody = StringHelper.escapeHTML(sBody);
			if (this.getImageCount() > 0) {
				if (sBody.length() >= 500) {
					sBody = sBody.substring(0, 496).concat("...").concat("  <a class='rMore' href='"+contextManager.getContext().getContextUrl()+"/"+this.getItemUrl()+"' target='_blank'>read more</a>");
				}
			} else {
				if (sBody.length() >= 699) {
					sBody = sBody.substring(0, 695).concat("...").concat("  <a class='rMore' href='"+contextManager.getContext().getContextUrl()+"/"+this.getItemUrl()+"' target='_blank'>read more</a>");
				}
			}
			return sBody;
		}

		public String getPostedOn() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			Date today = new Date();
			String sToday = fmter.format(today);

			try {

				if (getDatePosted() != null) {
					Date date = formatter.parse(getDatePosted());
					if (fmter.format(date).equals(sToday)) {
						return "Today " + fmt.format(date);
					}
					return " on "+fmt2.format(date);
				} else {
				}
				return "";
			} catch (ParseException e) {
				return getDatePosted();
			}
		}

		public String getDateStarted() throws Exception
		{
			if (this.getEventId() > 0) {
				SimpleDateFormat fmter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
				SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
				SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd");   
				Date today = new Date();
				String sToday = fmter.format(today);
				try {
					Date date = fmter.parse(this.getStartDate());
					if (fmter.format(date).equals(sToday)) {
						return "Today " + fmt.format(date);
					}
					return fmt2.format(date) + " at " + fmt.format(date);
				} catch (Exception e) {
					return "";
				}
			} else {
				return "";
			}
		}

		public String getDateEnded() throws Exception
		{
			if (this.getEventId() > 0) {
				SimpleDateFormat fmter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
				SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
				SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd");      
				Date today = new Date();
				String sToday = fmter.format(today);
				try {
					Date date = fmter.parse(this.getEndDate());
					if (fmter.format(date).equals(sToday)) {
						return "Today " + fmt.format(date);
					}
					return fmt2.format(date) + " at " + fmt.format(date);
				} catch (Exception e) {
					return "";
				}
			} else {
				return "";
			}
		}

		public String getContentType() {
			if (contentType == null || contentType.equals("")) {
				return "";
			} else if(contentType.contains("image")){
				return "Photo";
			}
			return contentType;
		}

		public String getLikeString(){
			try {
				User cUser = contextManager.getContext().getCurrentUser();
				if (cUser == null) {
					return "Like";
				} else {
					try {
						if ("WikiEntry".equalsIgnoreCase(this.getItemType())) {
							WikiEntryLike wikiEntryLike = wikiEntryLikeFinder.getLikeForWikiEntryAndUser(this.getItemId(), cUser.getId());
						} else if ("BlogEntry".equalsIgnoreCase(this.getItemType())) {
							BlogEntryLike blogEntryLike = blogEntryLikeFinder.getLikeForBlogEntryAndUser(this.getItemId(), cUser.getId());
						} else if ("Document".equalsIgnoreCase(this.getItemType())) {
							DocumentLike documentLike = documentLikeFinder.getLikeForDocumentAndUser(this.getItemId(), cUser.getId());
						} else if ("ForumTopic".equalsIgnoreCase(this.getItemType()) || "ForumResponse".equalsIgnoreCase(this.getItemType())) {
							ForumItemLike forumItemLike = forumItemLikeFinder.getLikeForForumItemAndUser(this.getItemId(), cUser.getId());
						} else if ("Event".equalsIgnoreCase(this.getItemType())) {
							EventLike eventLike = eventLikeFinder.getLikeForEventAndUser(this.getItemId(), cUser.getId());
						}
						return "Unlike";
					}
					catch (ElementNotFoundException e) {
						return "Like";
					}
				}
			} catch (Exception e) {
				return "Like";
			}
		}

		public Long getLikeCount() {
			return likeCount == null ? 0 : likeCount.longValue();
		}

		public void setLikeCount(Long likeCount) {
			this.likeCount = likeCount;
		}

		public long getCommentCount() {
			return commentCount == null ? 0 : commentCount.longValue();
		}

		public void setCommentCount(Long commentCount) {
			this.commentCount = commentCount;
		}

		public void setContentType(String contentType) {
			this.contentType = contentType;
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

		public void setPhotoPresent(boolean photoPresent) {
			this.photoPresent = photoPresent;
		}

		public int getCommunityId() {
			return communityId;
		}

		public void setCommunityId(Integer communityId) {
			this.communityId = communityId;
		}

		public int getDocumentId() {
			return documentId;
		}

		public void setDocumentId(int documentId) {
			this.documentId = documentId;
		}

		public int getFolderId() {
			return folderId;
		}

		public void setFolderId(int folderId) {
			this.folderId = folderId;
		}

		public int getDocGroupNumber() {
			return docGroupNumber;
		}

		public void setDocGroupNumber(int docGroupNumber) {
			this.docGroupNumber = docGroupNumber;
		}

		public int getBlogEntryId() {
			return blogEntryId;
		}

		public void setBlogEntryId(int blogEntryId) {
			this.blogEntryId = blogEntryId;
		}

		public int getForumItemId() {
			return forumItemId;
		}

		public void setForumItemId(int forumItemId) {
			this.forumItemId = forumItemId;
		}

		public int getWikiEntryId() {
			return wikiEntryId;
		}

		public void setWikiEntryId(int wikiEntryId) {
			this.wikiEntryId = wikiEntryId;
		}

		public int getEventId() {
			return eventId;
		}

		public void setEventId(int eventId) {
			this.eventId = eventId;
		}

		public int getAssignmentId() {
			return assignmentId;
		}

		public void setAssignmentId(int assignmentId) {
			this.assignmentId = assignmentId;
		}

		public int getPersonalBlogEntryId() {
			return personalBlogEntryId;
		}

		public void setPersonalBlogEntryId(int personalBlogEntryId) {
			this.personalBlogEntryId = personalBlogEntryId;
		}

		public boolean getLogoPresent() {
			return logoPresent;
		}

		public void setLogoPresent(boolean logoPresent) {
			this.logoPresent = logoPresent;
		}

		public String getItemTitle() {
			return itemTitle;
		}

		public void setItemTitle(String itemTitle) {
			this.itemTitle = itemTitle;
		}

		public int getPosterid() {
			return posterid;
		}

		public void setPosterid(int posterid) {
			this.posterid = posterid;
		}

		public String getItemDesc() {
			return itemDesc;
		}

		public void setItemDesc(String itemDesc) {
			this.itemDesc = itemDesc;
		}

		public Long getImageCount() {
			return imageCount;
		}

		public void setImageCount(Long imageCount) {
			this.imageCount = imageCount;
		}

		public String getCommunityName() {
			return communityName;
		}

		public void setCommunityName(String communityName) {
			this.communityName = communityName;
		}

		public int getVisitors() {
			return visitors;
		}

		public void setVisitors(int visitors) {
			this.visitors = visitors;
		}

		public String getVenue() {
			return venue;
		}

		public void setVenue(String venue) {
			this.venue = venue;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate.toString();
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate.toString();
		}

		public boolean getPhotoPresent() {
			return photoPresent;
		}

		public boolean isEventPhotoPresent() {
			return eventPhotoPresent;
		}

		public void setEventPhotoPresent(boolean eventPhotoPresent) {
			this.eventPhotoPresent = eventPhotoPresent;
		}

		public List<DocumentHeaderDto> getPhotoList() {
			return photoList;
		}

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public int getCountDoc() {
			return countDoc;
		}

		public void setCountDoc(int countDoc) {
			this.countDoc = countDoc;
		}

		public int getLastItemNumber() {
			return lastItemNumber;
		}

		public void setLastItemNumber(int lastItemNumber) {
			this.lastItemNumber = lastItemNumber;
		}

		public List<DocumentHeaderDto> getSecondPhotoList() {
			return secondPhotoList;
		}

		public void setSecondPhotoList(List<DocumentHeaderDto> secondPhotoList) {
			this.secondPhotoList = secondPhotoList;
		}

		public String getFileContentType() {
			return fileContentType;
		}

		public void setFileContentType(String fileContentType) {
			this.fileContentType = fileContentType;
		}

		public String getCreatorName() {
			return creatorName;
		}

		public void setCreatorName(String creatorName) {
			this.creatorName = creatorName;
		}

		public int getCreatorId() {
			return creatorId;
		}

		public void setCreatorId(int creatorId) {
			this.creatorId = creatorId;
		}

		public boolean isCreatorPhotoPresent() {
			return creatorPhotoPresent;
		}

		public void setCreatorPhotoPresent(boolean creatorPhotoPresent) {
			this.creatorPhotoPresent = creatorPhotoPresent;
		}

		public void setPhotoList(List<DocumentHeaderDto> photoList) {
			this.photoList = photoList;
		}

		public String getDateCreation() {
			return dateCreation;
		}

		public void setDateCreation(String dateCreation) {
			this.dateCreation = dateCreation;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}

	public void setForumItemFinder(ForumItemFinder forumItemFinder) {
		this.forumItemFinder = forumItemFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
	}

	public void setDocumentFinder(DocumentFinder documentFinder) {
		this.documentFinder = documentFinder;
	}

	public void setBlogEntryLikeFinder(BlogEntryLikeFinder blogEntryLikeFinder) {
		this.blogEntryLikeFinder = blogEntryLikeFinder;
	}

	public void setWikiEntryLikeFinder(WikiEntryLikeFinder wikiEntryLikeFinder) {
		this.wikiEntryLikeFinder = wikiEntryLikeFinder;
	}

	public void setDocumentLikeFinder(DocumentLikeFinder documentLikeFinder) {
		this.documentLikeFinder = documentLikeFinder;
	}

	public void setForumItemLikeFinder(ForumItemLikeFinder forumItemLikeFinder) {
		this.forumItemLikeFinder = forumItemLikeFinder;
	}

	public void setBlogEntryFinder(BlogEntryFinder blogEntryFinder) {
		this.blogEntryFinder = blogEntryFinder;
	}

	public void setWikiEntryFinder(WikiEntryFinder wikiEntryFinder) {
		this.wikiEntryFinder = wikiEntryFinder;
	}

	public void setEventFinder(EventFinder eventFinder) {
		this.eventFinder = eventFinder;
	}

	public void setAssignmentFinder(AssignmentFinder assignmentFinder) {
		this.assignmentFinder = assignmentFinder;
	}

	public void setEventLikeFinder(EventLikeFinder eventLikeFinder) {
		this.eventLikeFinder = eventLikeFinder;
	}

	public void setDocumentLibraryFinder(DocumentLibraryFinder documentLibraryFinder) {
		this.documentLibraryFinder = documentLibraryFinder;
	}
}