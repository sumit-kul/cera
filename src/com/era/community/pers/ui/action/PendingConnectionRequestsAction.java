package com.era.community.pers.ui.action;

import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.era.community.pers.dao.DashBoardAlert;
import com.era.community.pers.dao.DashBoardAlertFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.ui.dto.UserDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/pendingConnectionRequests.do" 
 *  @spring.bean name="/pers/pendingConnectionRequests.ajx"
 */
public class PendingConnectionRequestsAction extends AbstractCommandAction
{
	public static final String REQUIRES_AUTHENTICATION = "";
	private CommunityEraContextManager contextManager; 
	private ContactFinder contactFinder;
	protected DashBoardAlertFinder dashBoardAlertFinder;
	private CommunityFinder communityFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		User currentUser = context.getCurrentUser();
		Command cmd = (Command)data; 
		cmd.setUser(currentUser);
		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}
		QueryScroller scroller = null;
		int countReceived = 0;
		int countSent = 0;
		String selectedType = "";
		if ("received".equalsIgnoreCase(cmd.getType())) {
			countReceived = contactFinder.countReceivedContactRequests(currentUser.getId());
			if (countReceived == 0) {
				countSent = contactFinder.countSentContactRequests(currentUser.getId());
				if (countSent == 0) {
					return new ModelAndView("redirect:/pers/connectionList.do?id="+currentUser.getId());
				} else {
					selectedType = "sent";
					cmd.setSelectedType(selectedType);
					cmd.setCountSent(countSent);
					scroller = contactFinder.listSentContactRequests(currentUser.getId());
				}
			} else {
				selectedType = "received";
				cmd.setSelectedType(selectedType);
				cmd.setCountReceived(countReceived);
				scroller = contactFinder.listReceivedContactRequests(currentUser.getId());
				countSent = contactFinder.countSentContactRequests(currentUser.getId());
				cmd.setCountSent(countSent);
			}
		} else {
			countSent = contactFinder.countSentContactRequests(currentUser.getId());
			if (countSent == 0) {
				countReceived = contactFinder.countReceivedContactRequests(currentUser.getId());
				if (countReceived == 0) {
					return new ModelAndView("redirect:/pers/connectionList.do?id="+currentUser.getId());
				} else {
					selectedType = "received";
					cmd.setSelectedType(selectedType);
					cmd.setCountReceived(countReceived);
					scroller = contactFinder.listReceivedContactRequests(currentUser.getId());
				}
			} else {
				selectedType = "sent";
				cmd.setSelectedType(selectedType);
				cmd.setCountSent(countSent);
				scroller = contactFinder.listSentContactRequests(currentUser.getId());
				countReceived = contactFinder.countReceivedContactRequests(currentUser.getId());
				cmd.setCountReceived(countReceived);
			}
		}

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
			cmd.setReturnString(getConnectionInfo(currentUser, currentUser));
			DashBoardAlert dashBoardAlert;
			try {
				dashBoardAlert = dashBoardAlertFinder.getDashBoardAlertForUserId(currentUser.getId());
				dashBoardAlert.setConnectionReceivedCount(0);
				dashBoardAlert.update();
			} catch (ElementNotFoundException e) {
				dashBoardAlert = dashBoardAlertFinder.newDashBoardAlert();
				dashBoardAlert.setUserId(currentUser.getId());
				dashBoardAlert.setConnectionReceivedCount(0);
				dashBoardAlert.update();
			}
			scroller.setPageSize(cmd.getPageSize());
			cmd.setPageCount(scroller.readPageCount());
			cmd.setRowCount(scroller.readRowCount());
			scroller.setBeanClass(RowBean.class, this);
			cmd.setScrollerPage(scroller.readPage(cmd.getPage()));
			cmd.setPage(cmd.getPage() + 1);
			return new ModelAndView("/pers/pendingConnectionRequests");
		}
	}

	public class Command extends CommandBeanImpl implements CommandBean 
	{
		private User user;
		private IndexedScrollerPage scrollerPage;
		private String type = "received";
		private boolean isBlogOwner;
		private String returnString = "";
		private int countReceived = 0;
		private int countSent = 0;
		private String selectedType = "";

		public boolean isBlogOwner()
		{
			return isBlogOwner;
		}

		public void setBlogOwner(boolean isBlogOwner)
		{
			this.isBlogOwner = isBlogOwner;
		}

		public String getReturnString() {
			return returnString;
		}

		public void setReturnString(String returnString) {
			this.returnString = returnString;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
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

		public String getSelectedType() {
			return selectedType;
		}

		public void setSelectedType(String selectedType) {
			this.selectedType = selectedType;
		}

		public IndexedScrollerPage getScrollerPage() {
			return scrollerPage;
		}

		public void setScrollerPage(IndexedScrollerPage scrollerPage) {
			this.scrollerPage = scrollerPage;
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
					returnString = returnString + "<a class='btnmain normalTip' onclick='sendMessage("+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
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
					returnString = returnString + "<a class='btnmain normalTip' onclick='sendMessage("+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
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
		} catch (ElementNotFoundException e) { 
			// Add to my connections
			returnString = "<a class='btnmain normalTip' onclick='addConnection("+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
			"title='Add "+profileUser.getFullName()+" to my connections'>Add to my connections</a>";
		}
		return returnString;
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

	public void setDashBoardAlertFinder(DashBoardAlertFinder dashBoardAlertFinder) {
		this.dashBoardAlertFinder = dashBoardAlertFinder;
	}
}