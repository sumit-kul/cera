package com.era.community.profile.ui.action;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.DashBoardAlert;
import com.era.community.pers.dao.DashBoardAlertFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.ui.dto.UserDto;
import com.era.community.profile.dao.ProfileVisitFinder;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/myContacts.do" 
 */
public class MyProfileVisitAction extends AbstractCommandAction
{

	public static final String REQUIRES_AUTHENTICATION = "";

	private CommunityEraContextManager contextManager;
	protected ContactFinder contactFinder;
	protected DashBoardAlertFinder dashBoardAlertFinder; 
	protected ProfileVisitFinder profileVisitFinder;
	private CommunityFinder communityFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		User currentUser = context.getCurrentUser();

		if (currentUser == null) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do");
		} else {
			cmd.setUser(currentUser);
		}

		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}

		if (context.getRequest().getParameter("filterOption") != null 
				&& !"".equals(context.getRequest().getParameter("filterOption"))) {
			cmd.setFilterOption(context.getRequest().getParameter("filterOption"));
		}
		
		if (context.getRequest().getParameter("sortByOption") != null 
				&& !"".equals(context.getRequest().getParameter("sortByOption"))) {
			cmd.setSortByOption(context.getRequest().getParameter("sortByOption"));
		}

		QueryScroller scroller = currentUser.getMyProfileVisitorList(cmd.getFilterOption(), cmd.getSortByOption());

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
			DashBoardAlert dashBoardAlert;
			try {
				dashBoardAlert = dashBoardAlertFinder.getDashBoardAlertForUserId(currentUser.getId());
				dashBoardAlert.setProfileVisitCount(0);
				dashBoardAlert.update();
			} catch (ElementNotFoundException e) {
				dashBoardAlert = dashBoardAlertFinder.newDashBoardAlert();
				dashBoardAlert.setUserId(currentUser.getId());
				dashBoardAlert.setProfileVisitCount(0);
				dashBoardAlert.update();
			}
			scroller.setPageSize(cmd.getPageSize());
			cmd.setPageCount(scroller.readPageCount());
			cmd.setRowCount(scroller.readRowCount());
			scroller.setBeanClass(RowBean.class, this);
			cmd.setScrollerPage(scroller.readPage(cmd.getPage()));
			cmd.setPage(cmd.getPage() + 1);
			return new ModelAndView("profile/profileVisitors");
		}
	}

	public class RowBean extends UserDto
	{              
		private int ProfileVisitId;
		private int resultSetIndex;
		private int status;
		
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
		public int getProfileVisitId() {
			return ProfileVisitId;
		}
		public void setProfileVisitId(int profileVisitId) {
			ProfileVisitId = profileVisitId;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		
	}

	public static class Command extends IndexCommandBeanImpl implements CommandBean 
	{
		private User user;
		private String filterOption;
		private String sortByOption;

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

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public String getFilterOption() {
			return filterOption;
		}

		public void setFilterOption(String filterOption) {
			this.filterOption = filterOption;
		}   
	}

	public void setContactFinder(ContactFinder contactFinder)
	{
		this.contactFinder = contactFinder;
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setDashBoardAlertFinder(DashBoardAlertFinder dashBoardAlertFinder) {
		this.dashBoardAlertFinder = dashBoardAlertFinder;
	}

	public void setProfileVisitFinder(ProfileVisitFinder profileVisitFinder) {
		this.profileVisitFinder = profileVisitFinder;
	}

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}
}