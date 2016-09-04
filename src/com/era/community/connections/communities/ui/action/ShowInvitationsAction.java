package com.era.community.connections.communities.ui.action;

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
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.connections.communities.dao.MemberInvitationFinder;
import com.era.community.connections.communities.ui.dto.MemberInvitationDto;
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/** 
 * List of community connections (members) - These connections can be added into My connection list.
 * 
 * @spring.bean name="/cid/[cec]/connections/showInvitations.do"
 */
public class ShowInvitationsAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;
	protected UserFinder userFinder;
	protected MemberInvitationFinder memberInvitationFinder;
	protected ContactFinder contactFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		Community currComm = context.getCurrentCommunity();
		
		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}

		if (context.getRequest().getParameter("sortByOption") != null 
				&& !"".equals(context.getRequest().getParameter("sortByOption"))) {
			cmd.setSortByOption(context.getRequest().getParameter("sortByOption"));
		}

		QueryScroller scroller =  memberInvitationFinder.getMemberInvitationsForCommunity(currComm != null ? currComm.getId() : 0, cmd.getSortByOption());

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
			cmd.setCommunity(currComm);
			scroller.setPageSize(cmd.getPageSize());
			cmd.setPageCount(scroller.readPageCount());
			cmd.setRowCount(scroller.readRowCount());
			scroller.setBeanClass(RowBean.class, this);
			cmd.setScrollerPage(scroller.readPage(cmd.getPage()));
			cmd.setPage(cmd.getPage() + 1);
			return new ModelAndView("connections/showInvitations");
		}

	}

	public class RowBean extends MemberInvitationDto implements EntityWrapper
	{      
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

		public String getRequestDateDisplay() throws Exception
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy");
			try {
				Date date = formatter.parse(getRequestDate());
				return fmt2.format(date);

			} catch (ParseException e) {
				return getRequestDate();
			}
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
		
		public final int getResultSetIndex()
		{
			return resultSetIndex;
		}

		public final void setResultSetIndex(int resultSetIndex)
		{
			this.resultSetIndex = resultSetIndex;
		}       
	}

	public static class Command extends IndexCommandBeanImpl
	{
		private int numberOfInvitations;
		private String sortByOption = "Sent Date";
		private int communityId;
		private Community community;

		public List getSortByOptionOptions() throws Exception
		{
			List sortByOptionList = new ArrayList();
			sortByOptionList.add("Name");
			sortByOptionList.add("Sent Date");
			return sortByOptionList;
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

		public int getCommunityId() {
			return communityId;
		}

		public void setCommunityId(int communityId) {
			this.communityId = communityId;
		}

		public int getNumberOfInvitations() {
			return numberOfInvitations;
		}

		public void setNumberOfInvitations(int numberOfInvitations) {
			this.numberOfInvitations = numberOfInvitations;
		}

		public Community getCommunity() {
			return community;
		}

		public void setCommunity(Community community) {
			this.community = community;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public void setMemberInvitationFinder(
			MemberInvitationFinder memberInvitationFinder) {
		this.memberInvitationFinder = memberInvitationFinder;
	}

	public void setContactFinder(ContactFinder contactFinder) {
		this.contactFinder = contactFinder;
	}
}