package com.era.community.pers.ui.action;

import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.database.EntityWrapper;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.IndexCommandBean;
import support.community.framework.IndexCommandBeanImpl;
import support.community.util.StringFormat;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.DashBoardAlert;
import com.era.community.pers.dao.DashBoardAlertFinder;
import com.era.community.pers.dao.Message;
import com.era.community.pers.dao.MessageFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.MessageDto;
import com.ibm.json.java.JSONObject;

/**
 * Display a list of messages
 * 
 *  @spring.bean name="/pers/myMessages.do"
 */
public class MyMessagesAction extends AbstractCommandAction
{ 
	public static final String REQUIRES_AUTHENTICATION = "";
	public CommunityEraContextManager contextManager;      
	public MessageFinder msgFinder;
	protected UserFinder userFinder;
	protected DashBoardAlertFinder dashBoardAlertFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		User usr = context.getCurrentUser();
		Command cmd = (Command)data;
		
		if (usr == null ) {
			String reqUrl = context.getRequestUrl();
			if(reqUrl != null) {
				context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
			}
			return new ModelAndView("redirect:/login.do?redirect=" + StringFormat.escape(context.getRequestUrl()));
		} else {
			cmd.setUser(usr);
			cmd.setQueryText(usr.getFullName());
	    	cmd.setSearchType("People");
		}

		if (context.getRequest().getParameter("msgType") != null 
				&& !"".equals(context.getRequest().getParameter("msgType"))) {
			cmd.setMsgType(context.getRequest().getParameter("msgType"));
		} else {
			cmd.setMsgType(com.era.community.common.Constants.ALL);
		}
		
		if (context.getRequest().getParameter("order") != null 
				&& !"".equals(context.getRequest().getParameter("order"))) {
			cmd.setOrder(context.getRequest().getParameter("order"));
		} else {
			cmd.setOrder(com.era.community.common.Constants.DATE);
		}

		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
			cmd.setPageNumber(pNumber);
		}
		else{
			cmd.setPageNumber(1);
		}
		
		int dbMessageCount = -1;

		//If delete action has been called on Msg
		if (cmd.getActionType() == 5 || cmd.getActionType() == 6 || cmd.getActionType() == 7 || 
				cmd.getActionType() == 8 || cmd.getActionType() == 9 || cmd.getActionType() == 10) {
			if (cmd.getActionType() == 5) { // Delete SelectedMesage... only archive this time
				String[] myJsonData = context.getRequest().getParameterValues("json[]");
				if(myJsonData != null && myJsonData.length > 0) {
					for (int i=0; i < myJsonData.length; ++i) {
						String mId = myJsonData[i];
						int messageId = Integer.parseInt(mId);
						archiveSelectedMesage(messageId);
					}
				}
			} else if (cmd.getActionType() == 6) { // Delete AllMessage... only archive this time
				usr.archiveAllMessage();
				dbMessageCount = 0;
			} else if (cmd.getActionType() == 7) { // restoreSelectedMessage
				String[] myJsonData = context.getRequest().getParameterValues("json[]");
				if(myJsonData != null && myJsonData.length > 0) {
					for (int i=0; i < myJsonData.length; ++i) {
						String mId = myJsonData[i];
						int messageId = Integer.parseInt(mId);
						restoreSelectedMessage(messageId);
					}
				}
			} else if (cmd.getActionType() == 8) { // restoreAllMessage
				usr.restoreAllMessage();
			} else if (cmd.getActionType() == 9) { // deleteSelectedMesage
				String[] myJsonData = context.getRequest().getParameterValues("json[]");
				if(myJsonData != null && myJsonData.length > 0) {
					for (int i=0; i < myJsonData.length; ++i) {
						String mId = myJsonData[i];
						int messageId = Integer.parseInt(mId);
						deleteSelectedMesage(messageId);
					}
				}
			} else if (cmd.getActionType() == 10) { //deleteAllMessage
				usr.deleteAllMessage();
			}

			int messageCount = usr.getMessageCount(context.getRequest().getParameter("msgType"));
			if (messageCount > 0) {
				int expectedPageNumber = (messageCount%com.era.community.common.Constants.RECORDS_PER_PAGE) != 0 ? (messageCount/com.era.community.common.Constants.RECORDS_PER_PAGE)+1
						:messageCount/com.era.community.common.Constants.RECORDS_PER_PAGE; 	// We are showing 10 records per page
				if(pNumber > expectedPageNumber){
					pNumber = expectedPageNumber;
				}
				if (pNumber > 0) {
					HttpServletResponse resp = contextManager.getContext().getResponse();
					JSONObject json = new JSONObject();
					json.put("totalMessageCount", messageCount);
					json.put("pageNumber", pNumber);
					json.put("jPage", pageNum);
					json.put("actionFrom", context.getRequest().getParameter("actionFrom"));
					json.put("msgType", context.getRequest().getParameter("msgType"));
					json.put("order", context.getRequest().getParameter("order"));
					json.put("dbMessageCount", dbMessageCount);
					String jsonString = json.serialize();
					resp.setContentType("text/json");
					Writer out = resp.getWriter();
					out.write(jsonString);
					out.close();
					return null;
				} 
			} else {
				HttpServletResponse resp = contextManager.getContext().getResponse();
				JSONObject json = new JSONObject();
				json.put("totalMessageCount", 0);
				json.put("pageNumber", 1);
				json.put("jPage", 1);
				json.put("actionFrom", context.getRequest().getParameter("actionFrom"));
				json.put("msgType", context.getRequest().getParameter("msgType"));
				json.put("order", context.getRequest().getParameter("order"));
				json.put("dbMessageCount", dbMessageCount);
				String jsonString = json.serialize();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(jsonString);
				out.close();
				return null;
			}
		}

		QueryScroller scroller = getScroller(cmd);

		scroller.setPageSize(cmd.getPageSize());
		cmd.setPageCount(scroller.readPageCount());
		cmd.setRowCount(scroller.readRowCount());

		if (pageNum != null) {
			if (pNumber > 0) {
				IndexedScrollerPage page = scroller.readPage(pNumber);
				HttpServletResponse resp = contextManager.getContext().getResponse();
				JSONObject json = page.toJsonString(pNumber);
				String jsonString = json.serialize();
				resp.setContentType("text/json");
				Writer out = resp.getWriter();
				out.write(jsonString);
				out.close();
			}
			return null;
		} else {
			DashBoardAlert dashBoardAlert = dashBoardAlertFinder.getDashBoardAlertForUserId(usr.getId());
			if (dashBoardAlert.getMessageCount() > 0) {
				dashBoardAlert.setMessageCount(0);
				dashBoardAlert.update();
			}
			return new ModelAndView("/pers/myMessages");
		}
	}

	protected QueryScroller getScroller(IndexCommandBean data) throws Exception
	{
		Command cmd = (Command) data;
		CommunityEraContext context = contextManager.getContext();
		User usr = context.getCurrentUser();
		String strOrder = null;
		if (cmd.getOrder() != null && cmd.getOrder().trim().length() > 0) {
			strOrder = cmd.getOrder(); 
		}
		QueryScroller scroller = null;
		// Return different scroller depending upon view selected by user 
		if (cmd.getMsgType() != null) {
			if (cmd.getMsgType().equalsIgnoreCase(com.era.community.common.Constants.UNREAD)) {
				scroller = usr.listUnreadMessages(strOrder);
			} else if (cmd.getMsgType().equalsIgnoreCase(com.era.community.common.Constants.RECEIVED)) {
				scroller = usr.listReceivedMessages(strOrder);
			} else if (cmd.getMsgType().equalsIgnoreCase(com.era.community.common.Constants.SENT)) {
				scroller = usr.listSentMessages(strOrder);
			} else if (cmd.getMsgType().equalsIgnoreCase(com.era.community.common.Constants.ARCHIVED)) {
				scroller = usr.listArchivedMessages(strOrder);
			} else if (cmd.getMsgType().equalsIgnoreCase(com.era.community.common.Constants.ALL)) {
				scroller = usr.listMessages(strOrder);
			} else {
				scroller = usr.listMessages(strOrder);
			}
		} else {
			scroller = usr.listMessages(strOrder);
		}

		scroller.setBeanClass( RowBean.class, this );
		cmd.setTotalMsgCount( getScrollerSize( scroller ) );
		return scroller;
	}

	protected void archiveSelectedMesage (int messageId) throws Exception
	{        
		/*  Delete selected messages, only archive this time... */        
		Message msg = msgFinder.getMessageForId(messageId);
		msg.setDeleteFlag(com.era.community.common.Constants.FLAG_1); // For Archive Message
		msg.setAlreadyRead(true);
		msg.update();
	}

	protected void deleteSelectedMesage (int messageId) throws Exception
	{        
		/*  Delete selected messages, permanently delete this time... */        
		Message msg = msgFinder.getMessageForId(messageId);
		msg.delete();
	}

	protected void restoreSelectedMessage (int messageId) throws Exception
	{        
		/*  Restore selected messages from archive... */        
		Message msg = msgFinder.getMessageForId(messageId);
		msg.setDeleteFlag(com.era.community.common.Constants.FLAG_0); // For Archive Message
		msg.setAlreadyRead(false);
		msg.update();
	}

	private int getScrollerSize(QueryScroller scroller) throws Exception
	{
		return scroller.readRowCount();
	}

	public class RowBean extends MessageDto implements EntityWrapper
	{      
		private String displayName;
		private int resultSetIndex;
		private boolean photoPresent;
		private String toName;
		private int toId;
		private int toCount;

		public final int getResultSetIndex()
		{
			return resultSetIndex;
		}

		public final void setResultSetIndex(int resultSetIndex)
		{
			this.resultSetIndex = resultSetIndex;
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

		public String getCreatedOn() throws Exception
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
			Date today = new Date();

			try {

				Date date = formatter.parse(getCreated());
				if (date.equals(today)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date);

			} catch (ParseException e) {
				return getCreated();
			}
		}

		public String getToName() {
			String sb = "";
			if (getAddressLabel() != null && ! "".equals(getAddressLabel())) {
				StringTokenizer st = new StringTokenizer(getAddressLabel(),",");   
				int count = -1;
				while (st.hasMoreTokens()) {
					try {
						int id = Integer.parseInt(st.nextToken());
						User user = userFinder.getUserEntity(id);
						sb = user.getFullName();
						setToId(user.getId());
						count ++;
					}
					catch (Exception ex) {
						/* Do nothing, contact ID not recognized */
					}
				}
				setToCount(count);
			}
			return sb;
		}

		public int getToId() {
			return toId;
		}

		public void setToId(int toId) {
			this.toId = toId;
		}

		public int getToCount() {
			return toCount;
		}

		public void setToCount(int toCount) {
			this.toCount = toCount;
		}

		public void setToName(String toName) {
			this.toName = toName;
		}
	}

	public static class Command extends IndexCommandBeanImpl implements CommandBean 
	{
		private String order;
		private int totalMsgCount;
		private int unreadMsgCount; 

		private int actionType;
		private String msgType;
		private int pageNumber;

		private String subject;
		private String body;

		private int recipientCount;
		private int toUserId;
		private User user;

		@SuppressWarnings("unchecked")
		public List getOrderOptions() throws Exception
		{
			List orderOptionList = new ArrayList();
			orderOptionList.add(com.era.community.common.Constants.DATE);
			orderOptionList.add(com.era.community.common.Constants.NAME);
			return orderOptionList;
		}
		@SuppressWarnings("unchecked")
		public List getMsgTypeOptions() throws Exception
		{
			List orderOptionList = new ArrayList();
			orderOptionList.add(com.era.community.common.Constants.ALL);
			orderOptionList.add(com.era.community.common.Constants.UNREAD);
			orderOptionList.add(com.era.community.common.Constants.RECEIVED);
			orderOptionList.add(com.era.community.common.Constants.SENT);
			orderOptionList.add(com.era.community.common.Constants.ARCHIVED);
			return orderOptionList;
		}

		public int getTotalMsgCount()
		{
			return totalMsgCount;
		}

		public void setTotalMsgCount(int totalMsgCount)
		{
			this.totalMsgCount = totalMsgCount;
		}

		public int getUnreadMsgCount()
		{
			return unreadMsgCount;
		}

		public void setUnreadMsgCount(int unreadMsgCount)
		{
			this.unreadMsgCount = unreadMsgCount;
		}

		public String getOrder() {
			return order;
		}

		public void setOrder(String order) {
			this.order = order;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		public int getRecipientCount() {
			return recipientCount;
		}

		public void setRecipientCount(int recipientCount) {
			this.recipientCount = recipientCount;
		}

		public String getMsgType() {
			return msgType;
		}

		public void setMsgType(String msgType) {
			this.msgType = msgType;
		}

		public int getToUserId() {
			return toUserId;
		}

		public void setToUserId(int toUserId) {
			this.toUserId = toUserId;
		}

		public int getPageNumber() {
			return pageNumber;
		}

		public void setPageNumber(int pageNumber) {
			this.pageNumber = pageNumber;
		}
		public int getActionType() {
			return actionType;
		}
		public void setActionType(int actionType) {
			this.actionType = actionType;
		}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setMsgFinder(MessageFinder msgFinder)
	{
		this.msgFinder = msgFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public void setDashBoardAlertFinder(DashBoardAlertFinder dashBoardAlertFinder) {
		this.dashBoardAlertFinder = dashBoardAlertFinder;
	}
}