package com.era.community.blog.ui.action;

import java.io.Writer;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.EntityWrapper;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.IndexCommandBeanImpl;
import support.community.util.StringHelper;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.base.FileManager;
import com.era.community.base.ImageManipulation;
import com.era.community.base.LinkBuilderContext;
import com.era.community.base.Taggable;
import com.era.community.blog.dao.BlogEntry;
import com.era.community.blog.dao.BlogEntryComment;
import com.era.community.blog.dao.BlogEntryCommentFinder;
import com.era.community.blog.dao.BlogEntryFinder;
import com.era.community.blog.dao.BlogEntryLikeFinder;
import com.era.community.blog.dao.CommunityBlog;
import com.era.community.blog.dao.CommunityBlogFeature;
import com.era.community.blog.dao.PersonalBlog;
import com.era.community.blog.dao.PersonalBlogFinder;
import com.era.community.blog.dao.generated.BlogEntryDaoBase;
import com.era.community.blog.ui.dto.BlogEntryCommentDto;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.tagging.dao.TagFinder;
import com.era.community.tagging.dao.generated.TagEntity;
import com.era.community.upload.dao.ImageFinder;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/blog/blogEntry.do"
 * @spring.bean name="/cid/[cec]/blog/blogEntry.do"
 */
public class BlogConsolDisplayEntryAction extends AbstractCommandAction
{

	public static final String REQUIRES_AUTHENTICATION = "";

	protected BlogEntryFinder blogEntryFinder;
	protected BlogEntryCommentFinder blogEntryCommentFinder;
	protected SubscriptionFinder subscriptionFinder;
	protected UserFinder userFinder;
	protected CommunityFinder communityFinder;
	protected ImageFinder imageFinder;
	protected TagFinder tagFinder;
	protected ContactFinder contactFinder;
	protected CommunityEraContextManager contextManager;
	protected BlogEntryLikeFinder blogEntryLikeFinder;
	protected BlogEntryDaoBase dao;
	protected CommunityBlogFeature communityBlogFeature;
	protected PersonalBlogFinder personalBlogFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();

		if (cmd.getIsSubmit() != null && context.getCurrentUser() == null) {
			String reqUrl = context.getRequestUrl();

			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do");
		}

		String beID = context.getRequest().getParameter("beID");
		int cmdId = 0;
		if (cmd.getId() == 0 && beID != null && !"".equals(beID)&& !"undefined".equals(beID)) {
			cmdId = Integer.parseInt(beID);
			cmd.setId(cmdId);
		} 

		if (cmd.getIsSubmit() != null) {
			if(cmd.getComment()!=null && cmd.getComment().length()>0) {

				BlogEntryComment comment = blogEntryCommentFinder.newBlogEntryComment();
				comment.setPosterId( context.getCurrentUser().getId() );
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date now = new Date();
				String dt = sdf.format(now);
				Timestamp ts = Timestamp.valueOf(dt);
				comment.setDatePosted(ts);
				comment.setBlogEntryId( cmd.getId() );
				comment.update();
				
				comment.setComment( ImageManipulation.manageImages(context, cmd.getComment(), cmd.getTitle(), context.getCurrentUser().getId(), comment.getId(), "BlogEntryComment") );
				comment.update();
				
				cmd.setComment(null);
				cmd.setErrorMsg(null);
			} else {
				cmd.setErrorMsg("No posting to add. Please fill in a value.");
			}
		}

		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}

		BlogEntry entry = null;
		try {
			entry = blogEntryFinder.getBlogEntryForId(cmd.getId());
		} catch (ElementNotFoundException e) {
			return new ModelAndView("/pageNotFound");
		}

		processData(entry, cmd);
		QueryScroller scroller = entry.listComments();
		scroller.setBeanClass( RowBean.class );
		scroller.setPageSize(cmd.getPageSize());
		//pagination
		cmd.setPageCount(scroller.readPageCount());
		int cnt = scroller.readRowCount();
		cmd.setCommentCount(cnt);
		cmd.setRowCount(cnt);

		if (pNumber > 0) {
			int userId = context.getCurrentUser() == null ? 0 : context.getCurrentUser().getId();
			cmd.setIsSubmit("true");
			IndexedScrollerPage page = scroller.readPage(pNumber);
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = page.toJsonString(pNumber, userId);
			json.put("userId", cmd.getPosterId());
			String jsonString = json.serialize();
			resp.setContentType("text/json");
			Writer out = resp.getWriter();
			out.write(jsonString);
			out.close();
			return null;
		} else {
			if (context.getCurrentUser() == null || context.getCurrentUser().getId() != entry.getPosterId()) {
				HttpServletRequest request = contextManager.getContext().getRequest(); 
				Object sessionId = request.getSession().getAttribute("BlogEntry-"+entry.getId());
				if (sessionId == null) {
					request.getSession().setAttribute("BlogEntry-"+entry.getId(), entry.getId());
					int visitors = entry.getVisitors()+1;
					entry.setVisitors(visitors);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date now = new Date();
					String dt = sdf.format(now);
					Timestamp ts = Timestamp.valueOf(dt);
					entry.setLastVisitorsTime(ts);
					dao.store(entry, true);
					cmd.setVisitCount(entry.getVisitors());
					cmd.setLastVisitTime(entry.getLastVisitorsTime());
				}
			}
			cmd.setImageCount(imageFinder.countImageForItemType(entry.getId(), "BlogEntry"));
			cmd.setSearchType("Blog");
			cmd.setQueryText(entry.getTitle());
			if (context.getCurrentCommunity() != null) {
				return new ModelAndView("/blog/communityBlogConsolEntry");
			} else {
				return new ModelAndView("/blog/personalBlogConsolEntry");
			}
		}
	}

	private void processData(BlogEntry entry, Command cmd) throws Exception {
		cmd.copyPropertiesFrom(entry);
		CommunityEraContext context = contextManager.getContext();
		LinkBuilderContext navcontext = context.getLinkBuilder();
		User user = userFinder.getUserEntity( entry.getPosterId() );
		cmd.setDisplayName( user.getFirstName() + " " + user.getLastName() );
		cmd.setPhotoPresent(user.isPhotoPresent());
		cmd.setCoverPresent(user.isCoverPresent());
		int commentCount = blogEntryCommentFinder.getCommentCountForBlogEntry(entry.getId());
		cmd.setCommentCount(commentCount);
		int likeCount = blogEntryLikeFinder.getLikeCountForBlogEntry(entry.getId());
		cmd.setLikeCount(likeCount);
		cmd.setAlreadyLike(isAlreadyLike(entry));
		cmd.setLikeAllowed(isLikeAllowed(entry));
		cmd.setVisitCount(entry.getVisitors());
		cmd.setLastVisitTime(entry.getLastVisitorsTime());

		if (context.getCurrentCommunity() != null) {
			try {
				CommunityBlog bc = (CommunityBlog)communityBlogFeature.getFeatureForCurrentCommunity();
				cmd.setConsName(bc.getName());
				cmd.setBlogId(bc.getId());
				cmd.setBlogActive(!bc.isInactive());
			} catch (ElementNotFoundException ex) {
			}
		} else {
			PersonalBlog bpc = personalBlogFinder.getPersonalBlogForId(entry.getPersonalBlogId());
			cmd.setConsName(bpc.getName());
			cmd.setDescription(bpc.getDescription());
			cmd.setBlogId(bpc.getId());
			cmd.setBlogActive(!bpc.isInactive());
			Contact contact = null;
			if (context.getCurrentUser() != null) {
				try {
					contact = contactFinder.getContact(context.getCurrentUser().getId(), user.getId());
					cmd.setReturnString(getConnectionInfo(context.getCurrentUser(), user, contact));
				} catch (ElementNotFoundException e) { 
					String returnString = "<a class='btnmain normalTip' onclick='addConnection("+user.getId()+", \""+user.getFullName()+"\");' href='javascript:void(0);'" +
					"title='Add "+user.getFullName()+" to my connections'>Add to my connections</a>";
					cmd.setReturnString(returnString);
				}
			}
		}

		if (context.getCurrentUser() == null || context.getCurrentUser().getId() != entry.getPosterId()) {
			navcontext.addToolLink("View "+cmd.getDisplayName()+"'s blog",  context.getContextUrl()+"/blog/personalBlog.do?userId=" + entry.getPosterId(), "View "+cmd.getDisplayName()+"'s blog", "normalTip");
		}
		cmd.setWriteAllowed(entry.isWriteAllowed( context.getCurrentUserDetails()));
	}

	private boolean isAlreadyLike(BlogEntry entry) throws Exception {
		if (contextManager.getContext().getCurrentUser() == null ) return false;
		if (contextManager.getContext().isUserAuthenticated() == false) return false;
		int currentUserId = contextManager.getContext().getCurrentUser().getId();
		return entry.isAlreadyLike(currentUserId);
	}

	private boolean isLikeAllowed(BlogEntry entry) throws Exception {
		if (contextManager.getContext().getCurrentUser() == null ) return true;
		if (contextManager.getContext().isUserAuthenticated() == false) return true;
		int currentUserId = contextManager.getContext().getCurrentUser().getId();
		return entry.getPosterId() != currentUserId;
	}

	public String getConnectionInfo(User currentuser, User profileUser, Contact contact) throws Exception
	{
		String returnString = "";
		if (contact.getOwningUserId() == currentuser.getId()) {
			if (contact.getStatus() == 0 || contact.getStatus() == 2 || contact.getStatus() == 3) {
				// connection request sent
				returnString = "<a class='btnmain normalTip' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+1+", \""+profileUser.getFullName()+"\")' href='javascript:void(0);'" +
				"title='Cancel connection request sent to "+profileUser.getFullName()+"' >Cancel Request</a>";
			} else if (contact.getStatus() == 1) {
				// Already connected
				returnString = "<a class='btnmain normalTip' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+1+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);' " +
				"title='Remove "+profileUser.getFullName()+" from you connections' >Disconnect</a>";
				if (contact.getFollowContact() == 1) { //pass 0 for Owner and 1 for Contact
					returnString = returnString + "<a class='btnmain normalTip' onclick='stopFollowing("+contact.getId()+", "+1+","+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
					"title='Stop Following'>Stop Following</a>";
				} else {
					returnString = returnString + "<a class='btnmain normalTip' onclick='startFollowing("+contact.getId()+", "+1+","+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
					"title='Start Following'>Follow</a>";
				}
				returnString = returnString + "<a class='btnmain normalTip' onclick='sendMessageFromInfo("+profileUser.getId()+", \""+profileUser.getFullName()+"\", \""+profileUser.isPhotoPresent()+"\");' href='javascript:void(0);'" +
				"title='Send message to "+profileUser.getFullName()+"'>Send Message</a>";
			} else if (contact.getStatus() == 4) {
				// user has spammed you and you have cancelled the request...
				returnString = "<a class='btnmain normalTip' onclick='addConnection("+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
				"title='Add "+profileUser.getFullName()+" to my connections'>Add to my connections</a>";
			}
		} else {
			if (contact.getStatus() == 0) {
				returnString = "<a class='btnmain normalTip' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+4+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);' " +
				"title='Confirm connection request from "+profileUser.getFullName()+"'>Confirm Request</a>" +
				//"<a onclick='updateConnection("+this.getId()+", "+contact.getId()+", "+5+", \""+this.getFullName()+"\");' href='javascript:void(0);' " +
				//"title='Hide this request. ("+this.getFullName()+" won't know)' class='search_btn right' style='font-size:12px;'>Not Now</a>";
				"<a class='btnmain normalTip' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+1+", \""+profileUser.getFullName()+"\")' href='javascript:void(0);' " +
				"title='Delete connection request from "+profileUser.getFullName()+"'>Delete</a>";
			} else if (contact.getStatus() == 1) {
				// Already connected
				returnString = "<a class='btnmain normalTip' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+1+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);' " +
				"title='Remove "+profileUser.getFullName()+" from you connections' style='float:right;'>Disconnect</a>";
				if (contact.getFollowOwner() == 1) { //pass 0 for Owner and 1 for Contact
					returnString = returnString + "<a class='btnmain normalTip' onclick='stopFollowing("+contact.getId()+", "+0+","+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
					"title='Stop Following'>Stop Following</a>";
				} else {
					returnString = returnString + "<a class='btnmain normalTip' onclick='startFollowing("+contact.getId()+", "+0+","+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
					"title='Start Following'>Follow</a>";
				}
				returnString = returnString + "<a class='btnmain normalTip' onclick='sendMessageFromInfo("+profileUser.getId()+", \""+profileUser.getFullName()+"\", \""+profileUser.isPhotoPresent()+"\");' href='javascript:void(0);'" +
				"title='Send message to "+profileUser.getFullName()+"'>Send Message</a>";
			} /*if (contact.getStatus() == 2) {
    				// Not now case
    				returnString = "<a onclick='updateConnection("+this.getId()+", "+contact.getId()+", "+4+", \""+this.getFullName()+"\");' href='javascript:void(0);' " +
    				"title='Confirm connection request from "+this.getFullName()+"' class='search_btn right' style='font-size:12px;'>Confirm Request</a>" +
    				"<a onclick='updateConnection("+this.getId()+", "+contact.getId()+", "+1+", \""+this.getFullName()+"\");' href='javascript:void(0);' " +
    				"title='Delete connection request from "+this.getFullName()+"' class='search_btn right' style='font-size:12px;'>Delete</a>" +
    				"<a onclick='updateConnection("+this.getId()+", "+contact.getId()+", "+2+", \""+this.getFullName()+"\");' href='javascript:void(0);' " +
    				"title='Mark spam to "+this.getFullName()+". You won't get connection request from "+this.getFullName()+" anymore.' class='search_btn right' style='font-size:12px;'>Mark Spamm</a>";
    			}*/ else if (contact.getStatus() == 3 || contact.getStatus() == 4) {
    				// Spammed case
    				returnString = "<span>Spammed</span><a class='btnmain normalTip' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+3+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);' " +
    				"title='Undo spam to "+profileUser.getFullName()+"'>Undo</a>";
    			}
		}
		return returnString;
	}

	public class Command extends IndexCommandBeanImpl implements CommandBean, Taggable
	{              
		private String comment;        
		private int id;
		private int blogId;
		private String title;
		private String trimmedTitle;
		private String body;
		private Date datePosted;
		private int posterId;
		private String displayName;
		private boolean file1Present;
		private boolean file2Present;
		private boolean file3Present;
		private String fileName1;
		private String fileName2;
		private String fileName3;
		private String file1ContentType;
		private String file2ContentType;
		private String file3ContentType;
		private long  sizeInBytes1;
		private long sizeInBytes2;
		private long sizeInBytes3;
		private String displayBody;
		private String tags;
		private TreeMap popularTags;
		private boolean photoPresent;
		private String about;
		private boolean coverPresent;
		private int commentCount;
		private int imageCount;
		private int likeCount;
		private int visitCount;
		private String lastVisitTime;
		private boolean alreadyLike;
		private boolean likeAllowed;
		private String isSubmit;
		private String errorMsg;
		private boolean addTagAllowed;
		private String communityNames;
		private String consName;
		private String description;
		private String returnString = "";
		private boolean writeAllowed;
		private boolean blogActive;
		private String displayDescription;
		
		public String getDisplayDescription()
		{
			if ( (this.getBody()==null) || (this.getBody().length()==0)) return "";

			String sBody = this.getBody();
			sBody = Jsoup.parse(sBody).text();

			if (sBody.length() >= 200) {
				sBody = sBody.substring(0, 197).concat("...");
			}
			return sBody;
		}
		
		public String getMarkupBody()
		{
			if ( (this.getBody()==null) || (this.getBody().length()==0)) return "";

			String sBody = this.getBody();
			sBody = Jsoup.parse(sBody).text();
			sBody = StringHelper.escapeHTML(sBody);
			return sBody;
		}
		
		public String getMarkupImage()
		{
			String url = "";
			try {
				if (contextManager.getContext().getCurrentCommunity() != null) {
					if (this.getImageCount() > 0) {
						url = contextManager.getContext().getContextUrl()+ "/common/showImage.img?showType=t&type=BlogEntry&itemId=" + this.getId();
					} else if (contextManager.getContext().getCurrentCommunity().isLogoPresent()) {
						url = contextManager.getContext().getContextUrl()+ "/commLogoDisplay.img?communityId=" + contextManager.getContext().getCurrentCommunity().getId();
					} else {
						url = contextManager.getContext().getContextUrl()+ "/img/community_Image.png";
					}
				} else {
					if (this.getImageCount() > 0) {
						url = contextManager.getContext().getContextUrl()+ "/common/showImage.img?showType=t&type=BlogEntry&itemId=" + this.getId();
					} else if (this.isPhotoPresent()) {
						url = contextManager.getContext().getContextUrl()+ "/pers/userPhoto.img?id=" + this.getPosterId();
					} else {
						url = contextManager.getContext().getContextUrl()+ "/img/user_icon.png";
					}
				}
			} catch (Exception e) {
			}
			return url;
		}

		public String getTaggedKeywords(){
			String taggedKeywords = "";
			try {
				List tags = tagFinder.getTagsForParentTypeByPopularity(this.getId(), 0, 15, "BlogEntry");
				String prt = "";
				if (contextManager.getContext().getCurrentCommunity() != null) {
					prt = contextManager.getContext().getCurrentCommunityUrl();
				} else {
					prt = contextManager.getContext().getContextUrl();
				}
				for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
					TagEntity tb = (TagEntity) iterator.next();
					String tag = tb.getTagText().trim().toLowerCase();
					taggedKeywords += "<a href='"+prt+"/search/searchByTagInCommunity.do?filterTag="+tag+" ' class='normalTip euInfoSelect' style='display: inline;' title='Click to filter by tag &#39;"+tag+"&#39; all items in the community'>"+tag+"</a>";
					if (iterator.hasNext())taggedKeywords += " , ";
				}
			} catch (Exception e) {
				return taggedKeywords;
			}
			return taggedKeywords;
		}
		
		public String getKeywords(){
			String keywords = "";
			try {
				List tags = tagFinder.getTagsForParentTypeByPopularity(this.getId(), 0, 20, "BlogEntry");
				
				for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
					TagEntity tb = (TagEntity) iterator.next();
					String tag = tb.getTagText().trim().toLowerCase();
					keywords += tag;
					if (iterator.hasNext())keywords += " , ";
				}
			} catch (Exception e) {
				return keywords;
			}
			return keywords;
		}

		public String getPostedOn() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");     
			Date today = new Date();
			String sToday = fmter.format(today);

			Date date = getDatePosted();
			if (fmter.format(date).equals(sToday)) {
				return "Today " + fmt.format(date);
			}
			return fmt2.format(date);
		}
		
		public String getIsoPostedOn() throws Exception
		{
			TimeZone tz = TimeZone.getTimeZone("UTC");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
			df.setTimeZone(tz);
			String nowAsISO = df.format(getDatePosted());
			return nowAsISO;
		}

		public void setLastVisitTime(Date lastVisitorsTime) throws Exception {
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			Date today = new Date();
			String sToday = fmter.format(today);

			Date date = lastVisitorsTime != null ? lastVisitorsTime : fmter.parse(getLastVisitTime());
			if (fmter.format(date).equals(sToday)) {
				lastVisitTime =  "Today " + fmt.format(date);
			}
			lastVisitTime =  fmt2.format(date);
		}

		public boolean isCurrentUserSubscribed() throws Exception
		{
			try {
				User currentUser = contextManager.getContext().getCurrentUser();
				Community currentCommunity = contextManager.getContext().getCurrentCommunity();
				if (currentUser == null) return false;
				if (currentCommunity != null) {
					subscriptionFinder.getCommunityBlogEntrySubscriptionForUser(this.getId(), currentUser.getId(), currentCommunity.getId());
					return true;
				} else {
					subscriptionFinder.getPersonalBlogEntrySubscriptionForUser(this.getId(), currentUser.getId());
					return true;
				}
			}
			catch (ElementNotFoundException x) {
				return false;
			}
		}

		public boolean isContactionAllowed() throws Exception
		{
			User current=contextManager.getContext().getCurrentUser();

			if (current == null) return false;

			if (current.getId()==this.getId()) return false;

			return true;
		}

		public String getLastVisitTime() throws Exception
		{
			return lastVisitTime;
		}

		public String getTags() {
			return tags;
		}

		public void setTags(String tags) {
			this.tags = tags;
		}

		public String getComment()
		{
			return comment;
		}

		public void setComment(String comment)
		{
			this.comment = comment;
		}

		public String getBody()
		{
			return body;
		}

		public void setBody(String body)
		{
			this.body = body;
		}

		public Date getDatePosted()
		{
			return datePosted;
		}

		public void setDatePosted(Date datePosted)
		{
			this.datePosted = datePosted;
		}

		public int getId()
		{
			return id;
		}

		public void setId(int id)
		{
			this.id = id;
		}

		public String getDisplayName()
		{
			return displayName;
		}

		public void setDisplayName(String displayName)
		{
			this.displayName = displayName;
		}                    

		public int getPosterId()
		{
			return posterId;
		}

		public void setPosterId(int posterId)
		{
			this.posterId = posterId;
		}

		public String getTitle()
		{
			return title;
		}

		public void setTitle(String title)
		{
			this.title = title;
		}

		public String getDisplayBody()
		{

			return StringHelper.formatForDisplay(this.getBody());
		}

		public String getFile1ContentType()
		{
			return file1ContentType;
		}

		public void setFile1ContentType(String file1ContentType)
		{
			this.file1ContentType = file1ContentType;
		}

		public boolean isFile1Present()
		{
			return file1Present;
		}

		public void setFile1Present(boolean file1Present)
		{
			this.file1Present = file1Present;
		}

		public String getFile2ContentType()
		{
			return file2ContentType;
		}

		public void setFile2ContentType(String file2ContentType)
		{
			this.file2ContentType = file2ContentType;
		}

		public boolean isFile2Present()
		{
			return file2Present;
		}

		public void setFile2Present(boolean file2Present)
		{
			this.file2Present = file2Present;
		}

		public String getFile3ContentType()
		{
			return file3ContentType;
		}

		public void setFile3ContentType(String file3ContentType)
		{
			this.file3ContentType = file3ContentType;
		}

		public boolean isFile3Present()
		{
			return file3Present;
		}

		public void setFile3Present(boolean file3Present)
		{
			this.file3Present = file3Present;
		}

		public String getFileName1()
		{
			return fileName1;
		}

		public void setFileName1(String fileName1)
		{
			this.fileName1 = fileName1;
		}

		public String getFileName2()
		{
			return fileName2;
		}

		public void setFileName2(String fileName2)
		{
			this.fileName2 = fileName2;
		}

		public String getFileName3()
		{
			return fileName3;
		}

		public void setFileName3(String fileName3)
		{
			this.fileName3 = fileName3;
		}

		public String getIconImage1() {
			return FileManager.computeIconImage( getFile1ContentType() );
		}

		public String getIconImage2() {
			return FileManager.computeIconImage( getFile2ContentType() );
		}

		public String getIconImage3() {
			return FileManager.computeIconImage( getFile3ContentType() );
		}

		public long getSizeInBytes1()
		{
			return sizeInBytes1;
		}

		public void setSizeInBytes1(long sizeInBytes1)
		{
			this.sizeInBytes1 = sizeInBytes1;
		}

		public long getSizeInBytes2()
		{
			return sizeInBytes2;
		}

		public void setSizeInBytes2(long sizeInBytes2)
		{
			this.sizeInBytes2 = sizeInBytes2;
		}

		public long getSizeInBytes3()
		{
			return sizeInBytes3;
		}

		public void setSizeInBytes3(long sizeInBytes3)
		{
			this.sizeInBytes3 = sizeInBytes3;
		}

		public String getSizeInKb1() {
			return FileManager.getSizeInKb( getSizeInBytes1() );
		}

		public String getSizeInKb2() {
			return FileManager.getSizeInKb( getSizeInBytes2() );
		}

		public String getSizeInKb3() {
			return FileManager.getSizeInKb( getSizeInBytes3() );
		}

		public TreeMap getPopularTags()
		{
			return popularTags;
		}

		public void setPopularTags(TreeMap popularTags)
		{
			this.popularTags = popularTags;
		}

		/**
		 * @return the photoPresent
		 */
		public boolean isPhotoPresent() {
			return photoPresent;
		}

		/**
		 * @param photoPresent the photoPresent to set
		 */
		public void setPhotoPresent(boolean photoPresent) {
			this.photoPresent = photoPresent;
		}

		/**
		 * @return the commentCount
		 */
		public int getCommentCount() {
			return commentCount;
		}

		/**
		 * @param commentCount the commentCount to set
		 */
		public void setCommentCount(int commentCount) {
			this.commentCount = commentCount;
		}

		/**
		 * @return the isSubmit
		 */
		public String getIsSubmit() {
			return isSubmit;
		}

		/**
		 * @param isSubmit the isSubmit to set
		 */
		public void setIsSubmit(String isSubmit) {
			this.isSubmit = isSubmit;
		}

		/**
		 * @return the errorMsg
		 */
		public String getErrorMsg() {
			return errorMsg;
		}

		/**
		 * @param errorMsg the errorMsg to set
		 */
		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}

		public int getLikeCount() {
			return likeCount;
		}

		public void setLikeCount(int likeCount) {
			this.likeCount = likeCount;
		}

		public boolean isAlreadyLike() {
			return alreadyLike;
		}

		public void setAlreadyLike(boolean alreadyLike) {
			this.alreadyLike = alreadyLike;
		}

		public int getVisitCount() {
			return visitCount;
		}

		public void setVisitCount(int visitCount) {
			this.visitCount = visitCount;
		}

		public boolean isLikeAllowed() {
			return likeAllowed;
		}

		public void setLikeAllowed(boolean likeAllowed) {
			this.likeAllowed = likeAllowed;
		}

		public boolean isAddTagAllowed() {
			try {
				if (contextManager.getContext().getCurrentUser() == null ) return false;
				if (contextManager.getContext().isUserAuthenticated() == false) return false;
				User user = contextManager.getContext().getCurrentUser();

				return user != null && user.getId() == getPosterId();
			} catch (Exception exp) {
				return false;
			}
		}

		public void setAddTagAllowed(boolean addTagAllowed) {
			this.addTagAllowed = addTagAllowed;
		}

		public String getTrimmedTitle() {
			if (this.getTitle().length() >= 80) {
				return this.getTitle().substring(0, 80).concat("...");
			}
			return this.getTitle();
		}

		public void setTrimmedTitle(String trimmedTitle) {
			this.trimmedTitle = trimmedTitle;
		}

		public String getCommunityNames() {
			return communityNames;
		}

		public void setCommunityNames(String communityNames) {
			this.communityNames = communityNames;
		}

		public int getBlogId() {
			return blogId;
		}

		public void setBlogId(int blogId) {
			this.blogId = blogId;
		}

		public String getConsName() {
			return consName;
		}

		public void setConsName(String consName) {
			this.consName = consName;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getReturnString() {
			return returnString;
		}

		public void setReturnString(String returnString) {
			this.returnString = returnString;
		}

		public boolean isCoverPresent() {
			return coverPresent;
		}

		public void setCoverPresent(boolean coverPresent) {
			this.coverPresent = coverPresent;
		}

		public String getAbout() {
			return about;
		}

		public void setAbout(String about) {
			this.about = about;
		}

		public boolean isWriteAllowed() {
			return writeAllowed;
		}

		public void setWriteAllowed(boolean writeAllowed) {
			this.writeAllowed = writeAllowed;
		}

		public boolean isBlogActive() {
			return blogActive;
		}

		public void setBlogActive(boolean blogActive) {
			this.blogActive = blogActive;
		}

		public int getImageCount() {
			return imageCount;
		}

		public void setImageCount(int imageCount) {
			this.imageCount = imageCount;
		}
	}

	public static class RowBean extends BlogEntryCommentDto implements EntityWrapper
	{  
		private String displayName;
		private boolean photoPresent;
		private int likeCommentCount;
		private BlogEntryComment entry;
		private boolean alreadyCommentLike;
		private boolean commentLikeAllowed;

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
				return getCreated();
			}
		}

		public boolean isAlreadyCommentLike(int userID) throws Exception {
			return entry.isAlreadyLike(userID);
		}

		public boolean isCommentLikeAllowed(int userID) throws Exception {
			return entry.getPosterId() != userID;
		}

		public String getDisplayName()
		{
			return displayName;
		}

		public void setDisplayName(String displayName)
		{
			this.displayName = displayName;
		}

		public boolean isPhotoPresent() {
			return photoPresent;
		}

		public void setPhotoPresent(boolean photoPresent) {
			this.photoPresent = photoPresent;
		}

		public int getLikeCommentCount() {
			return likeCommentCount;
		}

		public void setLikeCommentCount(Long likeCommentCount) {
			this.likeCommentCount = likeCommentCount.intValue();
		}

		public void setAlreadyCommentLike(boolean alreadyCommentLike) {
			this.alreadyCommentLike = alreadyCommentLike;
		}

		public void setEntry(BlogEntryComment entry) {
			this.entry = entry;
		}
	}

	public void setBlogEntryCommentFinder(
			BlogEntryCommentFinder blogEntryCommentFinder)
	{
		this.blogEntryCommentFinder = blogEntryCommentFinder;
	}

	public void setBlogEntryFinder(BlogEntryFinder blogEntryFinder)
	{
		this.blogEntryFinder = blogEntryFinder;
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public void setBlogEntryLikeFinder(BlogEntryLikeFinder blogEntryLikeFinder) {
		this.blogEntryLikeFinder = blogEntryLikeFinder;
	}

	public void setDao(BlogEntryDaoBase dao) {
		this.dao = dao;
	}

	public void setTagFinder(TagFinder tagFinder) {
		this.tagFinder = tagFinder;
	}

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}

	public void setSubscriptionFinder(SubscriptionFinder subscriptionFinder) {
		this.subscriptionFinder = subscriptionFinder;
	}

	public void setContactFinder(ContactFinder contactFinder) {
		this.contactFinder = contactFinder;
	}

	public void setImageFinder(ImageFinder imageFinder) {
		this.imageFinder = imageFinder;
	}

	public void setContextManager(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setCommunityBlogFeature(CommunityBlogFeature communityBlogFeature) {
		this.communityBlogFeature = communityBlogFeature;
	}

	public void setPersonalBlogFinder(PersonalBlogFinder personalBlogFinder) {
		this.personalBlogFinder = personalBlogFinder;
	}
}