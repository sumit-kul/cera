package com.era.community.pers.ui.action;

import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.IndexCommandBeanImpl;
import support.community.util.StringFormat;
import support.community.util.StringHelper;

import com.era.community.announcement.dao.AnnouncementFinder;
import com.era.community.assignment.dao.AssignmentFinder;
import com.era.community.assignment.ui.dto.AssignmentHeaderDto;
import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
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
import com.era.community.media.dao.PhotoFinder;
import com.era.community.media.ui.dto.MediaHeaderDto;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.wiki.dao.WikiEntryFinder;
import com.era.community.wiki.dao.WikiEntryLike;
import com.era.community.wiki.dao.WikiEntryLikeFinder;
import com.era.community.wiki.ui.dto.WikiEntryHeaderDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * Home page action after login
 * @spring.bean name="/pers/myHome.do"
 * @spring.bean name="/pers/myHome.ajx"
 */
public class MyHomePageAction extends AbstractCommandAction
{

	public static final String REQUIRES_AUTHENTICATION = "";

	private CommunityEraContextManager contextManager;
	private AnnouncementFinder announcementFinder;
	private UserFinder userFinder;
	private DocumentLibraryFinder documentLibraryFinder;
	private CommunityFinder communityFinder;
	private BlogEntryFinder blogEntryFinder;
	private ForumItemFinder forumItemFinder;
	private WikiEntryFinder wikiEntryFinder;
	private DocumentFinder documentFinder;
	private PhotoFinder photoFinder;
	private EventFinder eventFinder;
	private AssignmentFinder assignmentFinder;
	private BlogEntryLikeFinder blogEntryLikeFinder;
	private WikiEntryLikeFinder wikiEntryLikeFinder;
	private DocumentLikeFinder documentLikeFinder;
	private ForumItemLikeFinder forumItemLikeFinder;
	private EventLikeFinder eventLikeFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		context.getRequest().getSession().removeAttribute("sucsFMyPassword");

		Command cmd = (Command)data;
		User usr = context.getCurrentUser();

		if (usr == null ) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		} else {
			cmd.setId(usr.getId());
			cmd.setUser(usr);
			cmd.setQueryText(usr.getFullName());
			cmd.setSearchType("People");
		}

		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}

		cmd.setNumberOfMemberships(usr.getMembershipCount());

		QueryScroller scroller = usr.listRecentUpdatedActiveCommunitiesForMember();

		if (pageNum != null) {
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = null;
			if (pNumber > 0) {
				scroller.setPageSize(20);
				scroller.setBeanClass(RowBean.class, this);
				try {
					IndexedScrollerPage page = scroller.readPage(pNumber);
					json = page.toJsonStringMyHome(pNumber);
				} catch (Exception e) {
					e.printStackTrace();
				}

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
			scroller.setPageSize(20);
			scroller.setBeanClass(RowBean.class, this);
			cmd.setPageCount(scroller.readPageCount());
			cmd.setRowCount(scroller.readRowCount());
			IndexedScrollerPage page = scroller.readPage(1);
			page.toJsonStringMyHome(1);
			cmd.setScrollerPage(page);
			cmd.setPage(2);
			return new ModelAndView("/pers/myHomePage");
		}
	}

	public class Command extends IndexCommandBeanImpl implements CommandBean
	{
		private String body;
		private String h2Heading;
		private int numberOfMemberships;
		private User user;
		private String profileName;
		private int id;

		private boolean isBlogOwner;

		private List announcementList = new ArrayList();
		private int announcementCount;
		private boolean liveAnnouncement;
		private String filterTagList = "";

		public String getCurrentDate() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("yyyy-MM-dd");
			Date today = new Date();
			return fmter.format(today);
		}

		public String getRegisteredOn() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			Date today = new Date();
			String sToday = fmter.format(today);

			try {

				Date date = this.user.getDateRegistered();
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date);

			} catch (Exception e) {
				return this.user.getDateRegistered().toString();
			}
		}

		public int getCountConnections()
		{
			int count;
			try {
				count = user.countAllMyContacts();
			} catch (Exception e) {
				return 0;
			}
			return count;
		}

		public int getNumberOfMemberships()
		{
			return numberOfMemberships;
		}

		public void setNumberOfMemberships(int numberOfMemberships)
		{
			this.numberOfMemberships = numberOfMemberships;
		}

		public String getH2Heading()
		{
			return h2Heading;
		}

		public void setH2Heading(String heading)
		{
			h2Heading = heading;
		}

		public final List getAnnouncementList()
		{
			return announcementList;
		}

		public final void setAnnouncementList(List announcementList)
		{
			this.announcementList = announcementList;
		}

		public int getAnnouncementCount()
		{
			return announcementCount;
		}

		public void setAnnouncementCount(int announcementCount)
		{
			this.announcementCount = announcementCount;
		}

		public boolean isLiveAnnouncement()
		{
			return liveAnnouncement;
		}

		public void setLiveAnnouncement(boolean liveAnnouncement)
		{
			this.liveAnnouncement = liveAnnouncement;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public String getFilterTagList() {
			return filterTagList;
		}

		public void setFilterTagList(String filterTagList) {
			this.filterTagList = filterTagList;
		}

		public String getProfileName() {
			return profileName;
		}

		public void setProfileName(String profileName) {
			this.profileName = profileName;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
	}

	public class RowBean
	{
		private int communityId;
		private int documentId;
		private int folderId;
		private int photoId;
		private int albumId;
		private int docGroupNumber;
		private int mediaGroupNumber;
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
		private String communityType;
		private String fileContentType;
		private String firstName;
		private String lastName;
		private boolean photoPresent;
		private String itemDesc;
		private Long imageCount;
		private String datePosted;
		private String communityName;
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

		private List<MediaHeaderDto> mediaList = new ArrayList<MediaHeaderDto>();
		private List<MediaHeaderDto> secondMediaList = new ArrayList<MediaHeaderDto>();
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

		public void setDatePosted(Date datePosted) {
			this.datePosted = datePosted.toString();
		}

		public void getPrepareRow() throws Exception {
			if (this.getCommunityId() > 0) {
				Community community = communityFinder.getCommunityForId(this.getCommunityId());
				this.setLogoPresent(community.isLogoPresent());
				this.setCommunityName(community.getName());
				this.setCommunityType(community.getCommunityType());
			} 
			if (this.getBlogEntryId() > 0) {
				BlogEntryHeaderDto blogEntry = blogEntryFinder.getBlogEntryForHeader(this.getBlogEntryId());
				this.setItemType("BlogEntry");
				this.setItemId(this.getBlogEntryId());
				this.setItemTitle(blogEntry.getTitle());
				this.setPosterid(blogEntry.getPosterId());
				this.setContentType("");
				this.setLikeCount(blogEntry.getLikeCount());
				this.setFirstName(blogEntry.getFirstName());
				this.setLastName(blogEntry.getLastName());
				this.setPhotoPresent(blogEntry.getPhotoPresent());
				this.setVisitors(blogEntry.getVisitors());
				this.setImageCount(blogEntry.getImageCount());
				this.setItemDesc(blogEntry.getItemDesc());
			} else if (this.getPersonalBlogEntryId() > 0) {
				BlogEntryHeaderDto blogEntry = blogEntryFinder.getBlogEntryForHeader(this.getPersonalBlogEntryId());
				this.setItemType("PersonalBlogEntry");
				this.setItemId(this.getPersonalBlogEntryId());
				this.setItemTitle(blogEntry.getTitle());
				this.setPosterid(blogEntry.getPosterId());
				this.setContentType("");
				this.setLikeCount(blogEntry.getLikeCount());
				this.setFirstName(blogEntry.getFirstName());
				this.setLastName(blogEntry.getLastName());
				this.setPhotoPresent(blogEntry.getPhotoPresent());
				this.setVisitors(blogEntry.getVisitors());
				this.setImageCount(blogEntry.getImageCount());
				this.setItemDesc(blogEntry.getItemDesc());
			} else if (this.getWikiEntryId() > 0) {
				WikiEntryHeaderDto wikiEntry = wikiEntryFinder.getWikiEntryForHeader(this.getWikiEntryId());
				this.setItemType("WikiEntry");
				this.setItemId(wikiEntry.getId());
				this.setItemTitle(wikiEntry.getTitle());
				this.setPosterid(wikiEntry.getPosterId());
				this.setContentType("");
				this.setLikeCount(wikiEntry.getLikeCount());
				this.setFirstName(wikiEntry.getFirstName());
				this.setLastName(wikiEntry.getLastName());
				this.setPhotoPresent(wikiEntry.getPhotoPresent());
				this.setVisitors(wikiEntry.getVisitors());
				this.setImageCount(wikiEntry.getImageCount());
				this.setItemDesc(wikiEntry.getItemDesc());
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
				this.setStartDate(event.getStartDate());
				this.setEndDate(event .getEndDate());
			} else if (this.getForumItemId() > 0) {
				ForumItemHeaderDto forumItem = forumItemFinder.getForumItemForHeader(this.getForumItemId());
				this.setItemType("ForumTopic");
				this.setItemId(this.getForumItemId());
				this.setItemTitle(forumItem.getSubject());
				this.setPosterid(forumItem.getAuthorId());
				this.setContentType("");
				this.setLikeCount(forumItem.getLikeCount());
				this.setFirstName(forumItem.getFirstName());
				this.setLastName(forumItem.getLastName());
				this.setPhotoPresent(forumItem.getPhotoPresent());
				this.setVisitors(forumItem.getCVisitors());
				this.setImageCount(forumItem.getImageCount());
				this.setItemDesc(forumItem.getItemDesc());
			} else if ((this.getFolderId() > 0 || this.getDocumentId() > 0) && this.getUserId() > 0 && this.getCommunityId() > 0) {
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

			} else if ((this.getPhotoId() > 0 || this.getAlbumId() > 0) && this.getUserId() > 0) {
				try {
					User poster = userFinder.getUserForId(this.getUserId());
					int docCount = photoFinder.countPhotoListForHeader(this.getAlbumId(), this.getUserId(), this.getMediaGroupNumber());
					List<MediaHeaderDto> temp = photoFinder.getPhotoListForHeader(this.getAlbumId(), this.getUserId(), this.getMediaGroupNumber());
					if (docCount == 1) {
						MediaHeaderDto dto = temp.get(0);
						this.setItemType("Media");
						this.setContentType("Single");
						this.setItemId(dto.getDocId());
						this.setFileContentType(dto.getPhotoContentType());
						this.setItemTitle(dto.getTitle());
						this.setPosterid(poster.getId());
						this.setFirstName(poster.getFirstName());
						this.setLastName(poster.getLastName());
						this.setPhotoPresent(poster.isPhotoPresent());
						this.setImageCount(Long.valueOf(docCount));
					} else {
						for (int i = 0; i < temp.size(); i++) {
							MediaHeaderDto dto = temp.get(i);
							if (i<5) {
								this.mediaList.add(temp.get(i));
							} else {
								this.secondMediaList.add(temp.get(i));
							}
						}
						if (docCount > 5) {
							this.setCountDoc(docCount-4);
							this.setLastItemNumber(4);
						} else {
							this.setCountDoc(0);
							this.setLastItemNumber(0);
						}
						this.setItemType("Media");
						this.setContentType("Group");
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
				this.setLikeCount(assignment.getLikeCount());
				this.setFirstName(assignment.getFirstName());
				this.setLastName(assignment.getLastName());
				this.setPhotoPresent(assignment.getPhotoPresent());
				this.setImageCount(assignment.getImageCount());
				this.setItemDesc(assignment.getItemDesc());
			} else {
				this.setItemType("");
			}
		}

		public String getItemUrl() throws Exception
		{
			if (this.getItemType().equalsIgnoreCase("Event")) {
				return ("cid/"+this.getCommunityId()+"/event/showEventEntry.do?id="+this.getItemId());
			}  
			if (this.getItemType().equalsIgnoreCase("ForumTopic")) {
				return ("cid/"+this.getCommunityId()+"/forum/forumThread.do?id="+this.getItemId());
			}
			if (this.getItemType().equalsIgnoreCase("WikiEntry")) {
				return ("cid/"+this.getCommunityId()+"/wiki/wikiDisplay.do?id="+this.getItemId());
			}
			if (this.getItemType().equalsIgnoreCase("BlogEntry")) {
				return ("cid/"+this.getCommunityId()+"/blog/blogEntry.do?id="+this.getItemId());
			}
			if (this.getItemType().equalsIgnoreCase("PersonalBlogEntry")) {
				return ("blog/blogEntry.do?id="+this.getPersonalBlogEntryId());
			}
			if (this.getItemType().equalsIgnoreCase("Photo")) {
				return ("cid/"+this.getCommunityId()+"/library/documentdisplay.do?id="+this.getItemId());
			}
			if (this.getItemType().equalsIgnoreCase("Document")) {
				return ("cid/"+this.getCommunityId()+"/library/showLibraryItems.do");
			}
			if (this.getItemType().equalsIgnoreCase("Member")) {
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
			if (this.getItemType().equalsIgnoreCase("Event")) {
				return ("/cid/"+this.getCommunityId()+"/event/showEvents.do");
			}  
			if (this.getItemType().equalsIgnoreCase("ForumTopic")) {
				return ("/cid/"+this.getCommunityId()+"/forum/showTopics.do");
			}
			if (this.getItemType().equalsIgnoreCase("WikiEntry")) {
				return ("/cid/"+this.getCommunityId()+"/wiki/showWikiEntries.do");
			}
			if (this.getItemType().equalsIgnoreCase("BlogEntry")) {
				return ("/cid/"+this.getCommunityId()+"/blog/viewBlog.do");
			}
			if (this.getItemType().equalsIgnoreCase("PersonalBlogEntry")) {
				return ("/blog/personalBlog.do?id="+this.getUserId());
			}
			if (this.getItemType().equalsIgnoreCase("Document")) {
				return ("/cid/"+this.getCommunityId()+"/library/showLibraryItems.do");
			}
			if (this.getItemType().equalsIgnoreCase("Member")) {
				return ("/cid/"+this.getCommunityId()+"/connections/showConnections.do");
			}
			if (this.getItemType().equalsIgnoreCase("Media")) {
				return ("/pers/mediaGallery.do?id="+this.getUserId());
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

		public void setCommunityId(Long communityId) {
			this.communityId = communityId.intValue();
		}

		public int getDocumentId() {
			return documentId;
		}

		public void setDocumentId(Long documentId) {
			this.documentId = documentId.intValue();
		}

		public int getFolderId() {
			return folderId;
		}

		public void setFolderId(Long folderId) {
			this.folderId = folderId.intValue();
		}

		public int getDocGroupNumber() {
			return docGroupNumber;
		}

		public void setDocGroupNumber(Long docGroupNumber) {
			this.docGroupNumber = docGroupNumber.intValue();
		}

		public int getBlogEntryId() {
			return blogEntryId;
		}

		public void setBlogEntryId(Long blogEntryId) {
			this.blogEntryId = blogEntryId.intValue();
		}

		public int getForumItemId() {
			return forumItemId;
		}

		public void setForumItemId(Long forumItemId) {
			this.forumItemId = forumItemId.intValue();
		}

		public int getWikiEntryId() {
			return wikiEntryId;
		}

		public void setWikiEntryId(Long wikiEntryId) {
			this.wikiEntryId = wikiEntryId.intValue();
		}

		public int getEventId() {
			return eventId;
		}

		public void setEventId(Long eventId) {
			this.eventId = eventId.intValue();
		}

		public int getAssignmentId() {
			return assignmentId;
		}

		public void setAssignmentId(Long assignmentId) {
			this.assignmentId = assignmentId.intValue();
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

		public int getPhotoId() {
			return photoId;
		}

		public void setPhotoId(int photoId) {
			this.photoId = photoId;
		}

		public int getAlbumId() {
			return albumId;
		}

		public void setAlbumId(int albumId) {
			this.albumId = albumId;
		}

		public int getMediaGroupNumber() {
			return mediaGroupNumber;
		}

		public void setMediaGroupNumber(int mediaGroupNumber) {
			this.mediaGroupNumber = mediaGroupNumber;
		}

		public String getFileContentType() {
			return fileContentType;
		}

		public void setFileContentType(String fileContentType) {
			this.fileContentType = fileContentType;
		}

		public List<MediaHeaderDto> getMediaList() {
			return mediaList;
		}

		public List<MediaHeaderDto> getSecondMediaList() {
			return secondMediaList;
		}

		public String getCommunityType() {
			return communityType;
		}

		public void setCommunityType(String communityType) {
			this.communityType = communityType;
		}

		public void setMediaList(List<MediaHeaderDto> mediaList) {
			this.mediaList = mediaList;
		}

		public void setSecondMediaList(List<MediaHeaderDto> secondMediaList) {
			this.secondMediaList = secondMediaList;
		}
	}

	public CommunityEraContextManager getContextHolder()
	{
		return contextManager;
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setAnnouncementFinder(AnnouncementFinder announcementFinder)
	{
		this.announcementFinder = announcementFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public void setForumItemFinder(ForumItemFinder forumItemFinder) {
		this.forumItemFinder = forumItemFinder;
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

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
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

	public void setDocumentFinder(DocumentFinder documentFinder) {
		this.documentFinder = documentFinder;
	}

	public void setAssignmentFinder(AssignmentFinder assignmentFinder) {
		this.assignmentFinder = assignmentFinder;
	}

	public void setEventLikeFinder(EventLikeFinder eventLikeFinder) {
		this.eventLikeFinder = eventLikeFinder;
	}

	public void setPhotoFinder(PhotoFinder photoFinder) {
		this.photoFinder = photoFinder;
	}

	public void setDocumentLibraryFinder(DocumentLibraryFinder documentLibraryFinder) {
		this.documentLibraryFinder = documentLibraryFinder;
	}
}