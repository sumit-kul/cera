package com.era.community.pers.ui.action;

import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.DashBoardAlert;
import com.era.community.pers.dao.DashBoardAlertFinder;
import com.era.community.pers.dao.Message;
import com.era.community.pers.dao.MessageFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/pers/messageUpdate.do"
 */
public class MessageUpdateAction extends AbstractCommandAction 
{
	protected CommunityEraContextManager contextManager;
	public MessageFinder msgFinder;
	protected UserFinder userFinder;
	protected DashBoardAlertFinder dashBoardAlertFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		User user = context.getCurrentUser();
		if (user != null) {
			Command cmd = (Command) data;
			HttpServletResponse resp = context.getResponse();
			JSONObject json = new JSONObject();
			JSONArray aData = new JSONArray();
			if (context.getRequest().getParameter("messageId") != null 
					&& !"".equals(context.getRequest().getParameter("messageId"))) { // read/unread a single message
				int id = Integer.parseInt(context.getRequest().getParameter("messageId"));
				cmd.setMessageId(id);
				JSONObject res = processMessage(id, cmd, user);
				aData.add(res);
				json.put("aData", aData);
			}

			if (cmd.getActionType() == 3 || cmd.getActionType() == 4) { // read/unread a selected list of messages
				List <Message> msgs = user.listAllMessagesToReadUnread();
				for (Message message : msgs) {
					int messageId = message.getId();
					JSONObject res = processMessage(messageId, cmd, user);
					aData.add(res);
					json.put("aData", aData);
				}
			} else if (cmd.getActionType() == 1 || cmd.getActionType() == 2) {  // read/unread a selected list of messages
				String[] myJsonData = context.getRequest().getParameterValues("json[]");
				if(myJsonData != null && myJsonData.length > 0) {
					for (int i=0; i < myJsonData.length; ++i) {
						String mId = myJsonData[i];
						int messageId = Integer.parseInt(mId);
						JSONObject res = processMessage(messageId, cmd, user);
						aData.add(res);
						json.put("aData", aData);
					}
				}
			}
			resp.setContentType("text/json");
			Writer out = resp.getWriter();
			out.write(json.serialize());
			out.close();
		}
		return null;
	}

	private JSONObject processMessage(int messageId, Command cmd, User user) throws Exception {
		Message msg = msgFinder.getMessageForId(messageId);
		JSONObject json = new JSONObject();
		String returnString = "";
		String isMessageRead = "";
		boolean read = true;
		boolean readFlag = false;
		int dbMessageCount = 0;
		if ( msg.getToUserId() == user.getId()){  
			if(!msg.isAlreadyRead() && (cmd.getActionType() == 0 || cmd.getActionType() == 1 || cmd.getActionType() == 3)) 
			{ //Mark as read 
				read = true;
				msg.setAlreadyRead(read);
				if (msg.getReadFlag() == 0) { // unread for dashboard
					DashBoardAlert dashBoardAlert = dashBoardAlertFinder.getDashBoardAlertForUserId(user.getId());
					if (dashBoardAlert.getMessageCount() > 0) {
						dashBoardAlert.setMessageCount(dashBoardAlert.getMessageCount() - 1);
						dbMessageCount = dashBoardAlert.getMessageCount();
						dashBoardAlert.update();
					}
					readFlag = true;
					msg.setReadFlag(1);
				}
				msg.update();
				isMessageRead = isMessageRead + com.era.community.common.Constants.UNREAD;
				returnString = "<a class='commIndexRight' style='padding: 0px 30px 0px;font-weight: bold;' href='javascript:void(0);' " +
				"onClick='return updateMessage("+cmd.getMessageId()+")'>"+isMessageRead+"</a>";
				json.put("retMessId", msg.getId());
				json.put("returnString", returnString);
				json.put("alreadyRead", read);
				json.put("readFlag", readFlag);
				json.put("dbMessageCount", dbMessageCount);
			} else if (msg.isAlreadyRead() && (cmd.getActionType() == 0 || cmd.getActionType() == 2 || cmd.getActionType() == 4)){ //Mark as unread 
				read = false;
				msg.setAlreadyRead(read);
				msg.update();
				isMessageRead = isMessageRead + com.era.community.common.Constants.READ;
				returnString = "<a class='commIndexRight' style='padding: 0px 30px 0px;font-weight: bold;' href='javascript:void(0);' " +
				"onClick='return updateMessage("+cmd.getMessageId()+")'>"+isMessageRead+"</a>";
				json.put("retMessId", msg.getId());
				json.put("returnString", returnString);
				json.put("alreadyRead", read);
				json.put("readFlag", readFlag);
				json.put("dbMessageCount", dbMessageCount);
			}
		}
		return json;
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int messageId;
		private int actionType; // 1- read, 2-unread, 3-archieve, 4-restore, 5-delete permanently

		public int getMessageId() {
			return messageId;
		}

		public void setMessageId(int messageId) {
			this.messageId = messageId;
		}

		public int getActionType() {
			return actionType;
		}

		public void setActionType(int actionType) {
			this.actionType = actionType;
		}
	}

	public void setMsgFinder(MessageFinder msgFinder)
	{
		this.msgFinder = msgFinder;
	}

	public void setContextHolder(CommunityEraContextManager contextManager) {
		this.contextManager = contextManager;
	}

	public void setDashBoardAlertFinder(DashBoardAlertFinder dashBoardAlertFinder) {
		this.dashBoardAlertFinder = dashBoardAlertFinder;
	}
}