package com.era.community.communities.ui.action;

import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.EntityWrapper;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.IndexCommandBeanImpl;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityJoiningRequest;
import com.era.community.communities.dao.CommunityJoiningRequestFinder;
import com.era.community.config.dao.MailMessageConfig;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.UserDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/** 
 * @spring.bean name="/cid/[cec]/connections/showJoiningRequests.do"
 * @spring.bean name="/cid/[cec]/connections/showJoiningRequests.ajx"
 */
public class ShowJoiningRequestsIndexAction extends AbstractCommandAction
{
	protected CommunityEraContextManager contextManager;     
	protected CommunityJoiningRequestFinder requestFinder;
	protected UserFinder userFinder;
	protected MailMessageConfig mailMessageConfig;

	public void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		Community comm = context.getCurrentCommunity();
		cmd.setCommunityLogoPresent(comm.isLogoPresent());

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
		if (cmd.getFilterOption() == null) cmd.setFilterOption("Unapproved Requests");
		if (cmd.getSortByOption() == null) cmd.setSortByOption("Date Of Request");


		QueryScroller scroller = comm.listUsersRequestingMembership(cmd.getFilterOption(), cmd.getSortByOption());
		
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
			scroller.setPageSize(cmd.getPageSize());
			cmd.setPageCount(scroller.readPageCount());
			cmd.setRowCount(scroller.readRowCount());
			scroller.setBeanClass(RowBean.class, this);
			cmd.setScrollerPage(scroller.readPage(1));
			cmd.setPage(cmd.getPage() + 1);
			//cmd.setCreated(comm.getCreated());
			//cmd.setCreatorId(comm.getCreatorId());
			//cmd.setPrivateCommunity(comm.isPrivate());
			return new ModelAndView("community/showJoiningRequests");
		}
	}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		Command cmd = (Command)data;
		SimpleMailMessage msg = null;
		CommunityEraContext context = contextManager.getContext();

		if (context.getCurrentUser() == null) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		}

		Community comm = contextManager.getContext().getCurrentCommunity();

		if (cmd.getSelectedIds() == null || cmd.getSelectedIds().length == 0) {
			return null;
		}

		/* Build string of selected user ids */
		StringBuffer buf = new StringBuffer();
		for (int i=0; i<cmd.getSelectedIds().length; i++) {
			if (buf.length() > 0) buf.append(",");
			buf.append(cmd.getSelectedIds()[i] + "");
		}
		cmd.setSelectedIdString(buf.toString());

		/*
		 * Parameters to substitute into the body text of the Email.
		 */
		Map<String, String> params = new HashMap<String, String>(11);
		String sLink = context.getContextUrl()+"/cid/"+comm.getId()+"/home.do";
		params.put("#communityName#", comm.getName());
		params.put("#communityLink#", sLink);
		params.put("#currentUserName#", context.getCurrentUser().getFullName());

		/* Set default message text */
		if ( cmd.getAcceptButton() != null ) {
			cmd.setActionType("Accept");
			/*
			 * Create the mail message.
			 */
			msg = mailMessageConfig.createMailMessage("membership-request-approval", params);
			cmd.setMessageText( msg.getText() );
		}
		else if ( cmd.getRejectButton() != null ) {
			cmd.setActionType("Reject");
			/*
			 * Create the mail message.
			 */
			msg = mailMessageConfig.createMailMessage("membership-request-rejection", params);
			cmd.setMessageText( msg.getText() );
		}        

		//  Delete selected membership requests
		else if ( cmd.getDeleteButton() != null ) {
			for (int userId : cmd.getSelectedIds()) {

				User u = userFinder.getUserEntity(userId);
				//CommunityJoiningRequest req = requestFinder.getRequestForUserAndCommunity(u, comm);
				//req.delete();    

			}
			return new ModelAndView("redirect:/cid/"+comm.getId()+"/connections/showJoiningRequests.do");

		}

		return new ModelAndView("comm/membership-process", "command", cmd );
	}

	public class RowBean extends UserDto implements EntityWrapper
	{      
		private int numberOfRequests;
		private String registredDate;
		private String requestedDate;
		private int resultSetIndex;
		private int requestId;
		
		public boolean isEvenRow()
		{
			return resultSetIndex%2==0;
		}
		public boolean isOddRow()
		{
			return resultSetIndex%2==1;
		}

		public String getRegistredDate() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy");
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

		public String getRequestedDate() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy");
			Date today = new Date();
			String sToday = fmter.format(today);

			String returnString = "";
			User current = contextManager.getContext().getCurrentUser();
			Community currentCommunity = contextManager.getContext().getCurrentCommunity();
			if (current != null) // No action for User
				try {
					CommunityJoiningRequest req = requestFinder.getRequestForUserAndCommunity(this.getId(), currentCommunity.getId());
						Date date = req.getRequestDate();
						if (fmter.format(date).equals(sToday)) {
							returnString = "Today " + fmt.format(date);
						}
						returnString = fmt2.format(date);
				}catch (ElementNotFoundException e) {
}
				return returnString;
		}

		public String getRequestInfo() throws Exception
		{
			String returnString = "";
			User current = contextManager.getContext().getCurrentUser();
			Community currentCommunity = contextManager.getContext().getCurrentCommunity();
			if (current != null) // No action for User
				try {
					CommunityJoiningRequest req = requestFinder.getRequestForUserAndCommunity(this.getId(), currentCommunity.getId());
					if (req.getStatus() == 0) {
						returnString = "<a onClick='handleMemberRequest("+req.getId()+ ", "+3+")' href='javascript:void(0);' "+
						"Delete membership request from "+this.getFullName()+"' class='btnmain normalTip' >Delete</a>"+
						"<a onClick='handleMemberRequest("+req.getId()+ ", "+2+")' href='javascript:void(0);' "+
						"title='Reject "+this.getFullName()+" as a member of this community' class='btnmain normalTip' >Reject</a>"+
						"<a onClick='handleMemberRequest("+req.getId()+ ", "+1+")' href='javascript:void(0);' " +
						"title='Accept "+this.getFullName()+" as a member of this community' class='btnmain normalTip' >Accept</a>";
					} else {
						if (req.getStatus() == 1) { //Accepted
							returnString = "<span style='float: right; font-size:14px; font-weight:bolder; color: #858681; margin-right: 20px;'>Accepted</span>";
						} else if (req.getStatus() == 2){ //Rejected
							returnString = "<span style='float: right; font-size:14px; font-weight:bolder; color: #858681; margin-right: 20px;'>Rejected</span>";
						}
					}

				} catch (ElementNotFoundException e) { 
				}
				return returnString;
		}

		public int getNumberOfRequests() {
			return numberOfRequests;
		}

		public void setNumberOfRequests(int numberOfRequests) {
			this.numberOfRequests = numberOfRequests;
		}
		public int getResultSetIndex() {
			return resultSetIndex;
		}
		public void setResultSetIndex(int resultSetIndex) {
			this.resultSetIndex = resultSetIndex;
		}
		public int getRequestId() {
			return requestId;
		}
		public void setRequestId(int requestId) {
			this.requestId = requestId;
		}
	}

	public class Command extends IndexCommandBeanImpl implements CommandBean
	{
		private int[] selectedIds;
		private String acceptButton;
		private String rejectButton;        
		private String deleteButton;
		private String messageText;
		private String actionType;
		private String selectedIdString;
		private boolean communityLogoPresent;
		private String filterOption;
		private String sortByOption;
		private boolean privateCommunity = false;
		private Date created;
		private int creatorId;

		public List getfilterOptions() throws Exception
		{
			List filterOptionList = new ArrayList();
			filterOptionList.add("Unapproved Requests");
			filterOptionList.add("Accepted Requests");
			filterOptionList.add("Rejected Requests");
			filterOptionList.add("All Requests");
			return filterOptionList;
		}

		public List getSortByOptionOptions() throws Exception
		{
			List sortByOptionList = new ArrayList();
			sortByOptionList.add("Date Of Request");
			sortByOptionList.add("Name Of Requester");
			return sortByOptionList;
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

		private int numberOfRequests;

		public int getNumberOfRequests()
		{
			return numberOfRequests;
		}
		public void setNumberOfRequests(int numberOfRequests)
		{
			this.numberOfRequests = numberOfRequests;
		}
		public String getSelectedIdString()
		{
			return selectedIdString;
		}
		public void setSelectedIdString(String selectedIdString)
		{
			this.selectedIdString = selectedIdString;
		}
		public String getActionType()
		{
			return actionType;
		}
		public void setActionType(String actionType)
		{
			this.actionType = actionType;
		}
		public final int[] getSelectedIds()
		{
			return selectedIds;
		}
		public final void setSelectedIds(int[] selectedIds)
		{
			this.selectedIds = selectedIds;
		}
		public String getAcceptButton()
		{
			return acceptButton;
		}
		public void setAcceptButton(String acceptButton)
		{
			this.acceptButton = acceptButton;
		}
		public String getRejectButton()
		{
			return rejectButton;
		}
		public void setRejectButton(String rejectButton)
		{
			this.rejectButton = rejectButton;
		}
		public String getMessageText()
		{
			return messageText;
		}
		public void setMessageText(String messageText)
		{
			this.messageText = messageText;
		}
		public String getDeleteButton()
		{
			return deleteButton;
		}
		public void setDeleteButton(String deleteButton)
		{
			this.deleteButton = deleteButton;
		}
		public boolean isCommunityLogoPresent() {
			return communityLogoPresent;
		}
		public void setCommunityLogoPresent(boolean communityLogoPresent) {
			this.communityLogoPresent = communityLogoPresent;
		}
		public String getSortByOption() {
			return sortByOption;
		}
		public void setSortByOption(String sortByOption) {
			this.sortByOption = sortByOption;
		}
		public String getFilterOption() {
			return filterOption;
		}
		public void setFilterOption(String filterOption) {
			this.filterOption = filterOption;
		}

		public boolean isPrivateCommunity() {
			return privateCommunity;
		}

		public void setPrivateCommunity(boolean privateCommunity) {
			this.privateCommunity = privateCommunity;
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
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setRequestFinder(CommunityJoiningRequestFinder requestFinder)
	{
		this.requestFinder = requestFinder;
	}

	public final void setMailMessageConfig(MailMessageConfig mailMessageConfig)
	{
		this.mailMessageConfig = mailMessageConfig;
	}
}