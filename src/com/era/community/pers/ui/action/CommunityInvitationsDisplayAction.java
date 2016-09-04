package com.era.community.pers.ui.action;

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
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.connections.communities.dao.MemberInvitation;
import com.era.community.connections.communities.dao.MemberInvitationFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.ui.dto.MembershipInvitationDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/communityInvitations.do" 
 *  @spring.bean name="/pers/communityInvitations.ajx"
 */
public class CommunityInvitationsDisplayAction extends AbstractCommandAction
{
	public static final String REQUIRES_AUTHENTICATION = "";

	private CommunityEraContextManager contextManager; 
	private CommunityFinder commFinder;
	private MemberInvitationFinder memberInvitationFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command)data; 
		User currUser = context.getCurrentUser();

		if (currUser == null ) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		} else{
			cmd.setUser(currUser);
			cmd.setQueryText(currUser.getFullName());
			cmd.setSearchType("People");
		}

		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}
		if ("".equals(cmd.getSortByOption()) || cmd.getSortByOption() == null) cmd.setSortByOption("Date Of Request");

		/* Get list of all communities invitations for current user */
		QueryScroller scroller = commFinder.listCommunityInvitationsForMember(currUser, cmd.getSortByOption());
		if (pageNum != null) {
			HttpServletResponse resp = contextManager.getContext().getResponse();
			JSONObject json = null;
			if (pNumber > 0) {
				scroller.setBeanClass(RowBean.class, this);
				scroller.setPageSize(cmd.getPageSize());
				IndexedScrollerPage page = scroller.readPage(pNumber);
				json = page.toJsonString(pNumber);
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
			scroller.setPageSize(cmd.getPageSize());
			cmd.setPageCount(scroller.readPageCount());
			cmd.setRowCount(scroller.readRowCount());
			return new ModelAndView("/pers/showCommunityInvitations");
		}
	}

	public class Command extends IndexCommandBeanImpl
	{
		private User user;
		private String returnString = "";
		private String sortByOption;

		public List getSortByOptionOptions() throws Exception
		{
			List sortByOptionList = new ArrayList();
			sortByOptionList.add("Date Of Invitation");
			sortByOptionList.add("Name Of Community");
			sortByOptionList.add("Name Of Invitor");
			return sortByOptionList;
		}

		public User getUser()
		{
			return user;
		}

		public void setUser(User user)
		{
			this.user = user;
		}

		public String getReturnString() {
			return returnString;
		}

		public void setReturnString(String returnString) {
			this.returnString = returnString;
		}

		public String getSortByOption() {
			return sortByOption;
		}

		public void setSortByOption(String sortByOption) {
			this.sortByOption = sortByOption;
		}
	}

	public final void setCommFinder(CommunityFinder commFinder)
	{
		this.commFinder = commFinder;
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public class RowBean extends MembershipInvitationDto implements EntityWrapper
	{
		private int resultSetIndex;
		private int level = 1;
		private Community community;

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
			User user = contextManager.getContext().getCurrentUser();
			return community.isMemberShipRequestPending(user);
		}

		public String getMemberCountString() throws Exception
		{
			int n = community.getMemberCount();
			if (n == 1)
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

		public String getRegistredDateOn() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy");
			Date today = new Date();
			String sToday = fmter.format(today);

			try {

				Date date = formatter.parse(this.getRequestDate().toString());
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date);

			} catch (ParseException e) {
				return this.getRequestDate().toString();
			}
		}

		public String getRequestInfo() throws Exception
		{
			String returnString = "";
			User current = contextManager.getContext().getCurrentUser();
			try {
				MemberInvitation inv = memberInvitationFinder.getMemberInvitationForId(this.getInvitationId());
				if (inv.getStatus() == 0) {
					returnString = "<a onClick='handleMemberInvitation("+this.getInvitationId()+ ", "+3+")' href='javascript:void(0);' "+
					"Delete membership invitation for "+this.getName()+"' class='btnmain normalTip' >Delete</a>"+
					"<a onClick='handleMemberInvitation("+this.getInvitationId()+ ", "+2+")' href='javascript:void(0);' "+
					"title='Reject membership invitation for "+this.getName()+"' class='btnmain normalTip' >Reject</a>"+
					"<a onClick='handleMemberInvitation("+this.getInvitationId()+ ", "+1+")' href='javascript:void(0);' " +
					"title='Accept membership invitation for "+this.getName()+"' class='btnmain normalTip' >Accept</a>";
				}
			} catch (ElementNotFoundException e) { 
			}
			return returnString;
		}

		public boolean isEvenRow()
		{
			return resultSetIndex % 2 == 0;
		}

		public boolean isOddRow()
		{
			return resultSetIndex % 2 == 1;
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
	}

	public void setMemberInvitationFinder(
			MemberInvitationFinder memberInvitationFinder) {
		this.memberInvitationFinder = memberInvitationFinder;
	}
}