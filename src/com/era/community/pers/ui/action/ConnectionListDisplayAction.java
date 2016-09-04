package com.era.community.pers.ui.action;

import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.UserDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/connectionResult.do" 
 *  @spring.bean name="/pers/connectionResult.ajx"
 */
public class ConnectionListDisplayAction extends AbstractCommandAction
{
	public static final String REQUIRES_AUTHENTICATION = "";
	private UserFinder userFinder; 
	private CommunityEraContextManager contextManager; 
	private ContactFinder contactFinder;
	private CommunityFinder communityFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		User currentUser = context.getCurrentUser();
		Command cmd = (Command)data; 

		HttpServletRequest request = contextManager.getContext().getRequest(); 
		String retMess = (String)request.getSession().getAttribute("retMess");
		cmd.setRetMess(retMess);
		request.getSession().removeAttribute("retMess");

		if (cmd.getId() == 0 && currentUser != null) {
			cmd.setId(currentUser.getId());
		}

		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}
		try {
			User usr = userFinder.getUserEntity(cmd.getId());

			if (!usr.isInactive()) {
				cmd.setUser(usr);

				QueryScroller scroller = usr.listContacts(); 

				cmd.setPageCount(scroller.readPageCount());

				if (pageNum != null) {
					HttpServletResponse resp = contextManager.getContext().getResponse();
					JSONObject json = null;
					if (pNumber > 0) {
						scroller.setBeanClass(RowBean.class, this);
						scroller.setPageSize(cmd.getPageSize());
						IndexedScrollerPage page = scroller.readPage(pNumber);
						json = page.toJsonString(pNumber);
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
					cmd.setReturnString(getConnectionInfo(currentUser, usr));
					if (currentUser != null) {
						if (cmd.getId() != currentUser.getId()) {
							int commConnCnt = contactFinder.countCommonConnections(cmd.getId(), currentUser.getId());
							cmd.setCommonConnCount(commConnCnt);
							cmd.setCommonConnPageCount(commConnCnt == 0 ? 0 : (commConnCnt/2)+1);
						} else {
							int countReceived = contactFinder.countReceivedContactRequests(currentUser.getId());
							cmd.setCountReceived(countReceived);
							if (countReceived == 0 ) {
								int countSent = contactFinder.countSentContactRequests(currentUser.getId());
								cmd.setCountSent(countSent);
							}
						}
					}

					scroller.setPageSize(cmd.getPageSize());
					cmd.setPageCount(scroller.readPageCount());
					cmd.setRowCount(scroller.readRowCount());
					scroller.setBeanClass(RowBean.class, this);
					cmd.setScrollerPage(scroller.readPage(cmd.getPage()));
					cmd.setPage(cmd.getPage() + 1);
					return new ModelAndView("/pers/connectionList");
				}
			} else {
				return new ModelAndView("redirect:/pers/connectionResult.do?id=" + cmd.getId());
			}
		} catch (ElementNotFoundException e) {
			return new ModelAndView("redirect:/pers/connectionResult.do?id=" + cmd.getId());
		}
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private int id;
		private User user;
		private boolean isBlogOwner;
		private String returnString = "";
		private IndexedScrollerPage scrollerPage;
		private int commonConnCount;
		private int commonConnPageCount;
		private String filterOption;
		private String sortByOption;
		private int countReceived;
		private int countSent;
		private String retMess;

		public List getSortByOptionOptions() throws Exception
		{
			List sortByOptionList = new ArrayList();
			sortByOptionList.add("Visit Date");
			sortByOptionList.add("Name");
			return sortByOptionList;
		}

		public List getfilterOptions() throws Exception
		{
			List filterOptionList = new ArrayList();
			filterOptionList.add("Unseen Entries");
			filterOptionList.add("Seen Requests");
			filterOptionList.add("All Entries");
			return filterOptionList;
		}

		public boolean isBlogOwner()
		{
			return isBlogOwner;
		}

		public void setBlogOwner(boolean isBlogOwner)
		{
			this.isBlogOwner = isBlogOwner;
		}

		public boolean isContactionAllowed() throws Exception
		{
			User current=contextManager.getContext().getCurrentUser();

			if (current == null) return false;

			if (current.getId()==this.getId()) return false;

			return true;
		}

		public boolean isContactOfCurrentUser() throws Exception
		{

			User current=contextManager.getContext().getCurrentUser();

			if (current == null) return false;

			if (current.getId()==this.getId()) return true;

			try {
				contactFinder.getContact(current.getId(), this.getId());
			} catch (ElementNotFoundException e) {
				return false;
			}
			return true;
		}

		public User getUser()
		{
			return user;
		}

		public void setUser(User user)
		{
			this.user = user;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getReturnString() {
			return returnString;
		}

		public void setReturnString(String returnString) {
			this.returnString = returnString;
		}

		public IndexedScrollerPage getScrollerPage() {
			return scrollerPage;
		}

		public void setScrollerPage(IndexedScrollerPage scrollerPage) {
			this.scrollerPage = scrollerPage;
		}

		public int getCommonConnCount() {
			return commonConnCount;
		}

		public void setCommonConnCount(int commonConnCount) {
			this.commonConnCount = commonConnCount;
		}

		public int getCommonConnPageCount() {
			return commonConnPageCount;
		}

		public void setCommonConnPageCount(int commonConnPageCount) {
			this.commonConnPageCount = commonConnPageCount;
		}

		public String getFilterOption() {
			return filterOption;
		}

		public void setFilterOption(String filterOption) {
			this.filterOption = filterOption;
		}

		public String getSortByOption() {
			return sortByOption;
		}

		public void setSortByOption(String sortByOption) {
			this.sortByOption = sortByOption;
		}

		public int getCountReceived() {
			return countReceived;
		}

		public void setCountReceived(int countReceived) {
			this.countReceived = countReceived;
		}

		public int getCountSent() {
			return countSent;
		}

		public void setCountSent(int countSent) {
			this.countSent = countSent;
		}

		public String getRetMess() {
			return retMess;
		}

		public void setRetMess(String retMess) {
			this.retMess = retMess;
		}
	}

	public String getConnectionInfo(User currentuser, User profileUser) throws Exception
	{
		String returnString = "";
		User current=contextManager.getContext().getCurrentUser();
		if (currentuser == null) // No action for User
			return "";
		if (current.getId() == profileUser.getId()) // No action for User
			return "";
		try {
			Contact contact = contactFinder.getContact(current.getId(), profileUser.getId());
			if (contact.getOwningUserId() == current.getId()) {
				if (contact.getStatus() == 0 || contact.getStatus() == 2 || contact.getStatus() == 3) {
					// connection request sent
					returnString = "" +
					"<a class='btnmain normalTip' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+1+", \""+profileUser.getFullName()+"\")' href='javascript:void(0);'" +
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
    				returnString = "<a class='btnmain normalTip' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+3+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);' " +
    				"title='Undo spam to "+profileUser.getFullName()+"'>Undo</a>";
    			}
			}
		} catch (ElementNotFoundException e) { 
			// Add to my connections
			returnString = "<a class='btnmain normalTip' onclick='addConnection("+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
			"title='Add "+profileUser.getFullName()+" to my connections'>Add to my connections</a>";
		}
		return returnString;
	}  

	public final void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setContactFinder(ContactFinder contactFinder)
	{
		this.contactFinder = contactFinder;
	}

	public class RowBean extends UserDto
	{              
		private int resultSetIndex;
		private String DateConnection;

		public String getConnectionDate() throws Exception
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy");
			try {
				Date date = formatter.parse(getDateConnection());
				return fmt2.format(date);

			} catch (ParseException e) {
				return getDateConnection();
			}
		}

		public String getDateConnection() {
			return DateConnection;
		}
		public void setDateConnection(Date dateConnection) {
			DateConnection = dateConnection.toString();
		}

		public boolean isEvenRow()
		{
			return resultSetIndex%2==0;
		}
		public boolean isOddRow()
		{
			return resultSetIndex%2==1;
		}              

		public final int getResultSetIndex()
		{
			return resultSetIndex;
		}

		public final void setResultSetIndex(int resultSetIndex)
		{
			this.resultSetIndex = resultSetIndex;
		}

		public int getCommunityCount() throws Exception
		{
			User current = contextManager.getContext().getCurrentUser();
			int commCount = communityFinder.countCommunitiesForConnection(this.getId(), current != null ? current.getId() : 0);
			return commCount;
		}

		public String getConnectionInfo() throws Exception
		{
			String returnString = "";
			User current = contextManager.getContext().getCurrentUser();
			if (current == null) // No action for User
				return "";
			if (current.getId() == this.getId()) // No action for User
				return "";
			try {
				Contact contact = contactFinder.getContact(current.getId(), this.getId());
				if (contact.getOwningUserId() == current.getId()) {
					if (contact.getStatus() == 0 || contact.getStatus() == 2 || contact.getStatus() == 3) {
						// connection request sent
						returnString = "<span class='dynaDropDown' title='pers/connectionOptions.ajx?connId="+this.getId()+"&currId="+current.getId()+"&userName="+this.getFullName()+"'>Request Sent</span>";
					} else if (contact.getStatus() == 1) {
						// Already connected
						returnString = "<span class='dynaDropDown' title='pers/connectionOptions.ajx?connId="+this.getId()+"&currId="+current.getId()+"&userName="+this.getFullName()+"'>Connected</span>";
					} else if (contact.getStatus() == 4) {
						// user has spammed you and you have cancelled the request...
						returnString = "<a class='btnmain normalTip' onClick='addConnectionInner("+this.getId()+", \""+this.getFullName()+"\");' href='javascript:void(0);'" +
						"title='Add "+this.getFullName()+" to my connections' >Add to my connections</a>";
					}
				} else {
					if (contact.getStatus() == 0) {
						returnString = "<span class='dynaDropDown' title='pers/connectionOptions.ajx?connId="+this.getId()+"&currId="+current.getId()+"&userName="+this.getFullName()+"'>Request Received</span>";
					} else if (contact.getStatus() == 1) {
						// Already connected
						returnString = "<span class='dynaDropDown' title='pers/connectionOptions.ajx?connId="+this.getId()+"&currId="+current.getId()+"&userName="+this.getFullName()+"'>Connected</span>";
					} else if (contact.getStatus() == 3 || contact.getStatus() == 4) {
						// Spammed case
						returnString = "<span class='dynaDropDown' title='pers/connectionOptions.ajx?connId="+this.getId()+"&currId="+current.getId()+"&userName="+this.getFullName()+"'>Spammed</span>";
					}
				}
			} catch (ElementNotFoundException e) { 
				// Add to my connections
				returnString = "<a class='btnmain normalTip' onClick='addConnectionInner("+this.getId()+", \""+this.getFullName()+"\");' href='javascript:void(0);'" +
				"title='Add "+this.getFullName()+" to my connections'>Add to my connections</a>";
			}
			return returnString;
		}
	}

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}
}