package com.era.community.connections.communities.ui.action;

import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.EntityWrapper;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.connections.communities.dao.MemberInvitationFinder;
import com.era.community.connections.communities.dao.MemberList;
import com.era.community.connections.communities.dao.MemberListFeature;
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.UserDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/** 
 * List of community connections (members) - These connections can be added into My connection list.
 * 
 * @spring.bean name="/cid/[cec]/connections/showConnections.do"
 */
public class ShowConnectionsAction extends AbstractCommandAction
{
	protected MemberListFeature feature;
	protected CommunityEraContextManager contextManager;
	protected ContactFinder contactFinder; 
	protected UserFinder userFinder;
	protected CommunityFinder communityFinder;
	protected MemberInvitationFinder memberInvitationFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		Community currComm = context.getCurrentCommunity();
		MemberList mlist =  (MemberList)feature.getFeatureForCommunity(currComm);

		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}

		if (context.getRequest().getParameter("sortByOption") != null 
				&& !"".equals(context.getRequest().getParameter("sortByOption"))) {
			cmd.setSortByOption(context.getRequest().getParameter("sortByOption"));
		}

		QueryScroller scroller = null;

		if  (cmd.getSortByOption() != null && cmd.getSortByOption().equals("Name"))  {
			scroller = mlist.listMembersByName();
		}
		else if (cmd.getSortByOption() != null && cmd.getSortByOption().equalsIgnoreCase("Role") ) {
			scroller = mlist.listMembersByRole();
		}
		else if (cmd.getSortByOption() != null && cmd.getSortByOption().equalsIgnoreCase("Joined") ) {
			scroller = mlist.listMembersByDateJoined();
		}
		else {
			scroller = mlist.listMembersByDateJoined();
			cmd.setSortByOption("Joined");
		}

		scroller.setBeanClass(RowBean.class, this);
		scroller.setPageSize(cmd.getPageSize());
		//pagination
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
			int invCount = memberInvitationFinder.countMemberInvitationsForCommunity(currComm != null ? currComm.getId() : 0);
			cmd.setInvitationCount(invCount);
			cmd.setCommunity(currComm);
			cmd.setMember(isMember(currComm));
			cmd.setAdminMember(isAdminMember(currComm));
			scroller.setPageSize(cmd.getPageSize());
			cmd.setPageCount(scroller.readPageCount());
			cmd.setRowCount(scroller.readRowCount());
			scroller.setBeanClass(RowBean.class, this);
			cmd.setScrollerPage(scroller.readPage(cmd.getPage()));
			cmd.setPage(cmd.getPage() + 1);
			cmd.setSearchType("Community");
	        cmd.setQueryText(currComm.getName());
			return new ModelAndView("connections/showConnections");
		}
	}
	
	private boolean isAdminMember(Community community) throws Exception
	{
		if (contextManager.getContext().getCurrentUser() == null ) return false;
		if (contextManager.getContext().isUserAuthenticated() == false) return false;
		return community.isAdminMember(contextManager.getContext().getCurrentUser());
	}
	
	private boolean isMember(Community community) throws Exception
	{
		if (contextManager.getContext().getCurrentUser() == null ) return false;
		if (contextManager.getContext().isUserAuthenticated() == false) return false;
		int currentUser = contextManager.getContext().getCurrentUser().getId();
		return community.isMember(currentUser);
	}

	public class RowBean extends UserDto implements EntityWrapper
	{      
		private String dateJoined;
		private String role;
		private int resultSetIndex;

		public RowBean()
		{
		}

		public boolean isEvenRow()
		{
			return resultSetIndex%2==0;
		}
		public boolean isOddRow()
		{
			return resultSetIndex%2==1;
		}

		public boolean isNewMember()
		{
			if (dateJoined==null) return false;
			GregorianCalendar cal = (GregorianCalendar)GregorianCalendar.getInstance();
			DateFormat df = DateFormat.getDateInstance();
			try {
				cal.setTime(df.parse(dateJoined));
			} catch (ParseException e) {
				return false;
			}
			cal.roll(Calendar.DAY_OF_YEAR, 7);
			return cal.after(Calendar.getInstance());
		}
		
		public String getConnectionDate() throws Exception
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy");
			try {
				Date date = formatter.parse(getDateJoined());
				return fmt2.format(date);

			} catch (ParseException e) {
				return getDateJoined();
			}
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
		
		public final String getRole()
		{
			return role;
		}

		public final void setRole(String communityRole)
		{
			this.role = communityRole;
		}

		public final String getDateJoined()
		{
			return dateJoined;
		}

		public final void setDateJoined(Date dateJoined)
		{
			this.dateJoined = dateJoined.toString();
		}

		public final int getResultSetIndex()
		{
			return resultSetIndex;
		}

		public final void setResultSetIndex(int resultSetIndex)
		{
			this.resultSetIndex = resultSetIndex;
		}       

		public String getCommunityType() 
		{
			if ( getClass().equals( "PrivateCommunity" ) ) {
				return "Private";
			}
			else {
				return "Public";    
			}            
		}
	}

	public static class Command extends IndexCommandBeanImpl
	{
		private int numberOfRequests;
		private String sortByOption;
		private int communityId;
		private int invitationCount;
		private boolean adminMember = false;
		private boolean member = false;
		private Community community;

		public List getSortByOptionOptions() throws Exception
		{
			List sortByOptionList = new ArrayList();
			sortByOptionList.add("Name");
			sortByOptionList.add("Role");
			sortByOptionList.add("Joined");
			return sortByOptionList;
		}

		public String getSortByOption() {
			return sortByOption;
		}

		public void setSortByOption(String sortByOption) {
			this.sortByOption = sortByOption;
		}

		public int getNumberOfRequests() {
			return numberOfRequests;
		}

		public void setNumberOfRequests(int numberOfRequests) {
			this.numberOfRequests = numberOfRequests;
		}

		public int getCommunityId() {
			return communityId;
		}

		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}

		public int getInvitationCount() {
			return invitationCount;
		}

		public void setInvitationCount(int invitationCount) {
			this.invitationCount = invitationCount;
		}

		public boolean isAdminMember() {
			return adminMember;
		}

		public void setAdminMember(boolean adminMember) {
			this.adminMember = adminMember;
		}

		public Community getCommunity() {
			return community;
		}

		public void setCommunity(Community community) {
			this.community = community;
		}

		public boolean isMember() {
			return member;
		}

		public void setMember(boolean member) {
			this.member = member;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setFeature(MemberListFeature feature)
	{
		this.feature = feature;
	}

	public void setContactFinder(ContactFinder contactFinder)
	{
		this.contactFinder = contactFinder;
	}

	public UserFinder getUserFinder()
	{
		return userFinder;
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}

	public void setMemberInvitationFinder(
			MemberInvitationFinder memberInvitationFinder) {
		this.memberInvitationFinder = memberInvitationFinder;
	}
}