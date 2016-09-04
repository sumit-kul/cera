package com.era.community.blog.ui.action;

import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.era.community.base.LinkBuilderContext;
import com.era.community.blog.dao.BlogAuthor;
import com.era.community.blog.dao.BlogAuthorFinder;
import com.era.community.blog.dao.BlogEntry;
import com.era.community.blog.dao.BlogEntryLikeFinder;
import com.era.community.blog.dao.PersonalBlog;
import com.era.community.blog.dao.PersonalBlogFinder;
import com.era.community.blog.ui.dto.BlogEntryDto;
import com.era.community.monitor.dao.PersonalBlogSubscription;
import com.era.community.monitor.dao.SubscriptionFinder;
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * Shows Blog with all the blog entries for that blog
 * @spring.bean name="/blog/viewBlog.do"
 */
public class ShowPersonalBlogAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected SubscriptionFinder subscriptionFinder;
	protected BlogEntryLikeFinder blogEntryLikeFinder;
	protected PersonalBlogFinder personalBlogFinder;
	protected BlogAuthorFinder blogAuthorFinder;
	protected UserFinder userFinder;
	private ContactFinder contactFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command)data;
		User currentUser = context.getCurrentUser();

		PersonalBlog bpc = null;
		if (cmd.getBid() > 0){
			try {
				bpc = personalBlogFinder.getPersonalBlogForId(cmd.getBid());
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

		cmd.setCons(bpc);
		
		QueryScroller scroller = null;
		scroller = bpc.listPersonalBlogEntries();
		
		if ("Title".equalsIgnoreCase(cmd.getSortByOption())) {
			scroller.addScrollKey("STEMP.Title", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		} else {
			scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}

		scroller.setBeanClass(RowBean.class, this);
		scroller.setPageSize(cmd.getPageSize());
		//pagination
		cmd.setPageCount(scroller.readPageCount());

		if (pageNum != null) {
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = null;
			if (pNumber > 0) {
				int userId = context.getCurrentUser() == null ? 0 : context.getCurrentUser().getId();
				scroller.setBeanClass(RowBean.class, this);
				scroller.setPageSize(cmd.getPageSize());
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
			scroller.setPageSize(cmd.getPageSize());
			cmd.setPageCount(scroller.readPageCount());
			cmd.setRowCount(scroller.readRowCount());
			cmd.setSearchType("Blog");
			cmd.setQueryText(bpc.getName());
			
			User blogOwner = userFinder.getUserEntity(bpc.getUserId());
			cmd.setOwner(blogOwner);

			Contact contact = null;
			try {
				if (currentUser != null) {
					contact = contactFinder.getContact(currentUser.getId(), blogOwner.getId());
					cmd.setReturnString(getConnectionInfo(currentUser, blogOwner, contact));
				}
			} catch (ElementNotFoundException e) { 
				String returnString = "<a class='btnmain normalTip' onclick='addConnection("+blogOwner.getId()+", \""+blogOwner.getFullName()+"\");' href='javascript:void(0);'" +
				"title='Add "+blogOwner.getFullName()+" to my connections'>Add to my connections</a>";
				cmd.setReturnString(returnString);
			}
			
			LinkBuilderContext linkBuilder = context.getLinkBuilder();
			BlogAuthor blogAuthor = null;
			try {
				if (currentUser != null) {
					blogAuthor = blogAuthorFinder.getPersonalBlogAuthorForBlogAndUser(bpc.getId(), currentUser.getId());
				}
			} catch (ElementNotFoundException e) {
			}
			
			if (blogAuthor != null && !bpc.isInactive()) {
				linkBuilder.addToolLink("New Blog Entry", context.getContextUrl()+"/blog/addEditBlog.do?bid="+cmd.getBid(), "Add new blog entry", "normalTip");
				//linkBuilder.addToolLink("View Authors", context.getContextUrl()+"/blog/blogAuthors.do?bid="+cmd.getBid(), "View authors list", "normalTip");
			}
			if (blogAuthor != null && blogAuthor.getRole() == 1) {
				linkBuilder.addToolLink("Manage Blog", context.getContextUrl()+"/blog/editBlog.do?bid="+bpc.getId(), "Manage your Blog", "normalTip");
				linkBuilder.addToolLink("Manage Authors", context.getContextUrl()+"/blog/manageBlogAuthors.do?bid="+cmd.getBid(), "Manage authors list", "normalTip");
			}
			
			return new ModelAndView("blog/showPersonalBlog");
		}
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

	public class Command extends IndexCommandBeanImpl implements IndexCommandBean
	{              
		private int bid;
		private String order;
		private int userId;
		private String blogOwnerName;
		private String sortByOption;
		private PersonalBlog cons;
		private User owner;
		private String returnString = "";
		private boolean commentAllowed = false;

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
				PersonalBlogSubscription subs = subscriptionFinder.getPersonalBlogSubscriptionForUser(cons.getId(), contextManager.getContext().getCurrentUser().getId());
				return true;
			}
			catch (ElementNotFoundException x) {
				return false;
			}
		}

		public boolean isContactionAllowed() throws Exception
		{
			User current=contextManager.getContext().getCurrentUser();

			if (current == null) return false;

			if (current.getId()==this.getUserId()) return false;

			return true;
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

		public String getSortByOption() {
			return sortByOption;
		}

		public void setSortByOption(String sortByOption) {
			this.sortByOption = sortByOption;
		}

		public PersonalBlog getCons() {
			return cons;
		}

		public void setCons(PersonalBlog cons) {
			this.cons = cons;
		}

		public int getBid() {
			return bid;
		}

		public void setBid(int bid) {
			this.bid = bid;
		}

		public User getOwner() {
			return owner;
		}

		public void setOwner(User owner) {
			this.owner = owner;
		}

		public String getReturnString() {
			return returnString;
		}

		public void setReturnString(String returnString) {
			this.returnString = returnString;
		}

		public boolean isCommentAllowed() {
			return cons.isInactive() ? false : cons.isDefaultAllowComments();
		}

		public void setCommentAllowed(boolean commentAllowed) {
			this.commentAllowed = commentAllowed;
		}
	}

	public class RowBean extends BlogEntryDto implements EntityWrapper
	{  
		private int resultSetIndex;        
		private int commentCount;
		private int viewCount;
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

		public int getCommentCount()
		{
			return commentCount;
		}

		public void setCommentCount(int commentCount)
		{
			this.commentCount = commentCount;
		}

		public final void setEntry(BlogEntry entry)
		{
			this.entry = entry;
		}

		public String getDisplayBody()
		{
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

		/**
		 * @return the viewCount
		 */
		public int getViewCount() {
			return viewCount;
		}

		/**
		 * @return the likeCount
		 */
		public int getLikeCount() throws Exception {
			return entry.getLikeCountForBlogEntry();
		}

		public boolean isLikeAllowed(int userID) throws Exception {
			return entry.getPosterId() != userID;
		}

		/**
		 * @return the alreadyLike
		 */
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
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
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

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public void setContactFinder(ContactFinder contactFinder) {
		this.contactFinder = contactFinder;
	}

	public void setPersonalBlogFinder(PersonalBlogFinder personalBlogFinder) {
		this.personalBlogFinder = personalBlogFinder;
	}
}