package com.era.community.pers.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.blog.dao.BlogAuthorFinder;
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.ContactFinder;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/connectionOptions.ajx" 
 */
public class ConnectionOptionsAction extends AbstractCommandAction
{
	public static final String REQUIRES_AUTHENTICATION = "";
	private CommunityEraContextManager contextManager; 
	private ContactFinder contactFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		HttpServletResponse resp = context.getResponse();
		Command cmd = (Command)data; 
		String info = "";
		if (cmd.getAuthId() > 0) {
			info = getAuthorRoleInfo(cmd.getAuthId(), cmd.getAuthRole());
		} else {
			info = getConnectionInfo(cmd.getCurrId(), cmd.getConnId(), cmd.getUserName(), cmd.isPhotoPresent());
		}
		
		JSONObject json = new JSONObject();
		json.put("optionInfo", info);
		String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
	}
	
	public String getAuthorRoleInfo(int authId, int authRole) throws Exception
	{
		String returnString = "<ul style='list-style: none outside none;'>";
		if (authRole == 1) {
			returnString = returnString  + "<li onClick='updateAuthorRole("+authId+", "+2+")' href='javascript:void(0);'>Author</li>";
		} else if (authRole == 2) {
			returnString = returnString  + "<li onClick='updateAuthorRole("+authId+", "+1+")' href='javascript:void(0);'>Owner</li>";
		}
		returnString = returnString + "</ul>";
		return returnString;
	}

	public String getConnectionInfo(int currId, int connId, String userName, boolean photoPresent) throws Exception
	{
		String returnString = "<ul style='list-style: none outside none;'>";
		if (currId == 0) // No action for User
			return "";
		if (currId == connId) // No action for User
			return "";
		try {
			Contact contact = contactFinder.getContact(currId, connId);
			if (contact.getOwningUserId() == currId) {
				if (contact.getStatus() == 0 || contact.getStatus() == 2 || contact.getStatus() == 3) {
					// connection request sent
					returnString = returnString  +
					"<li onClick='updateConnectionInner("+connId+", "+contact.getId()+", "+1+", &#39;"+userName+"&#39;)' href='javascript:void(0);'>Cancel Request</li>";
				} else if (contact.getStatus() == 1) {
					// Already connected
					returnString = returnString + "<li onClick='updateConnectionInner("+connId+", "+contact.getId()+", "+1+", &#39;"+userName+"&#39;);' href='javascript:void(0);' >Disconnect</li>";
					if (contact.getFollowContact() == 1) { //pass 0 for Owner and 1 for Contact
						returnString = returnString + "<li id='followId"+connId+"' onClick='stopFollowingInner("+contact.getId()+", "+1+","+connId+", &#39;"+userName+"&#39;);' href='javascript:void(0);'>Stop Following</li>";
					} else {
						returnString = returnString + "<li id='followId"+connId+"' onClick='startFollowingInner("+contact.getId()+", "+1+","+connId+", &#39;"+userName+"&#39;);' href='javascript:void(0);'>Follow</li>";
					}
					returnString = returnString + "<li onClick='sendMessageFromInfo("+connId+", &#39;"+userName+"&#39;, &#39;"+photoPresent+"&#39;);' href='javascript:void(0);' >Send Message</li>";
				} else if (contact.getStatus() == 4) {
					// user has spammed you and you have cancelled the request...
					returnString = returnString + "<li onClick='addConnectionInner("+connId+", &#39;"+userName+"&#39;);' href='javascript:void(0);'>Add to my connections</li>";
				}
			} else {
				if (contact.getStatus() == 0) {
					returnString = returnString + "<li onClick='updateConnectionInner("+connId+", "+contact.getId()+", "+4+", &#39;"+userName+"&#39;);' href='javascript:void(0);' >Confirm Request</li>" +
					//"<a onClick='updateConnection("+connId+", "+contact.getId()+", "+5+", \""+this.getFullName()+"\");' href='javascript:void(0);' " +
					//"title='Hide this request. ("+this.getFullName()+" won't know)' class='search_btn right' style='font-size:12px;'>Not Now</a>";
					"<li onClick='updateConnectionInner("+connId+", "+contact.getId()+", "+1+", "+userName+")' href='javascript:void(0);' >Delete</li>";
				} else if (contact.getStatus() == 1) {
					// Already connected
					returnString = returnString + "<li onClick='updateConnectionInner("+connId+", "+contact.getId()+", "+1+", &#39;"+userName+"&#39;);' href='javascript:void(0);' >Disconnect</li>";
					if (contact.getFollowOwner() == 1) { //pass 0 for Owner and 1 for Contact
						returnString = returnString + "<li id='followId"+connId+"' onClick='stopFollowingInner("+contact.getId()+", "+0+","+connId+", &#39;"+userName+"&#39;);' href='javascript:void(0);'>Stop Following</li>";
					} else {
						returnString = returnString + "<li id='followId"+connId+"' onClick='startFollowingInner("+contact.getId()+", "+0+","+connId+", &#39;"+userName+"&#39;);' href='javascript:void(0);'>Follow</li>";
					}
					returnString = returnString + "<li onClick='sendMessageFromInfo("+connId+", &#39;"+userName+"&#39;, &#39;"+photoPresent+"&#39; );' href='javascript:void(0);'>Send Message</li>";
				} else if (contact.getStatus() == 3 || contact.getStatus() == 4) {
					// Spammed case
					returnString = returnString + "<li onClick='updateConnectionInner("+connId+", "+contact.getId()+", "+3+", &#39;"+userName+"&#39;);' href='javascript:void(0);' >Undo</li>";
				}
			}
		} catch (ElementNotFoundException e) { 
			// Add to my connections
			returnString = returnString + "<li onClick='addConnectionInner("+connId+", &#39;"+userName+"&#39;);' href='javascript:void(0);' >Add to my connections</li>";
		}
		returnString = returnString + "</ul>";
		return returnString;
	}

	public class Command extends IndexCommandBeanImpl implements CommandBean 
	{
		private int connId;
		private int currId;
		private String userName;
		private boolean photoPresent;
		private int authId;
		private int authRole;

		public int getConnId() {
			return connId;
		}
		public void setConnId(int connId) {
			this.connId = connId;
		}
		public int getCurrId() {
			return currId;
		}
		public void setCurrId(int currId) {
			this.currId = currId;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public boolean isPhotoPresent() {
			return photoPresent;
		}
		public void setPhotoPresent(boolean photoPresent) {
			this.photoPresent = photoPresent;
		}
		public int getAuthId() {
			return authId;
		}
		public void setAuthId(int authId) {
			this.authId = authId;
		}
		public int getAuthRole() {
			return authRole;
		}
		public void setAuthRole(int authRole) {
			this.authRole = authRole;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setContactFinder(ContactFinder contactFinder)
	{
		this.contactFinder = contactFinder;
	}
}