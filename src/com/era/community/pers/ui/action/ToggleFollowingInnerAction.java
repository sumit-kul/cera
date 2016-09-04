package com.era.community.pers.ui.action;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.ContactDto;

/**
 * @spring.bean name="/pers/toggleFollowingInner.ajx"
 */
public class ToggleFollowingInnerAction extends AbstractCommandAction
{
    protected UserFinder userFinder;
    protected ContactFinder contactFinder;
    protected CommunityEraContextManager contextManager;

    protected ModelAndView handle(Object data) throws Exception    
    {
    	CommunityEraContext context = contextManager.getContext();

    	User currentUser = context.getCurrentUser();

    	if (currentUser == null) {
    		String reqUrl = context.getRequestUrl();

    		if(reqUrl != null) {
    			context.getRequest().getSession().setAttribute("url_prior_login", reqUrl);
    		}
    		return new ModelAndView("redirect:/login.do");
    	}

    	Command cmd = (Command)data;

    	String returnString = "";
    	try {
    		Contact contact = contactFinder.getContactForId(cmd.getContactId());
    		if (cmd.getActionType() == 1) { // 1 for start following
    			if (cmd.getActionFor() == 1) { // 1 for toggle for contact
    				contact.setFollowContact(1);
				} else { // 0 for toggle for owner
					contact.setFollowOwner(1);
				}
			} else { // 0 for stop following
				if (cmd.getActionFor() == 1) { // 1 for toggle for contact
    				contact.setFollowContact(0);
				} else { // 0 for toggle for owner
					contact.setFollowOwner(0);
				}
			}
    		contact.update();
    		
    		if (contact.getOwningUserId() == currentUser.getId()) {
    			
    			if (contact.getStatus() == 1) {
    				returnString = "";
    				if (contact.getFollowContact() == 1) { //pass 0 for Owner and 1 for Contact
						returnString = returnString + "<li id='followId"+cmd.getUserId()+"' class='normalTip' onClick='stopFollowingInner("+contact.getId()+", "+1+", "+cmd.getUserId()+", \""+cmd.getContactName()+"\");' href='javascript:void(0);'" +
    		    		"title='Stop Following'>Stop Following</li>";
					} else {
						returnString = returnString + "<li id='followId"+cmd.getUserId()+"' class='normalTip' onClick='startFollowingInner("+contact.getId()+", "+1+", "+cmd.getUserId()+", \""+cmd.getContactName()+"\");' href='javascript:void(0);'" +
    		    		"title='Start Following'>Follow</li>";
					}
				}
    		} else if (contact.getContactUserId() == currentUser.getId()){
    			if (contact.getStatus() == 1) {
    				returnString = "";
    				if (contact.getFollowOwner() == 1) { //pass 0 for Owner and 1 for Contact
						returnString = returnString + "<li id='followId"+cmd.getUserId()+"' class='normalTip' onClick='stopFollowingInner("+contact.getId()+", "+0+", "+cmd.getUserId()+", \""+cmd.getContactName()+"\");' href='javascript:void(0);'" +
    		    		"title='Stop Following'>Stop Following</li>";
					} else {
						returnString = returnString + "<li id='followId"+cmd.getUserId()+"' class='normalTip' onClick='startFollowingInner("+contact.getId()+", "+0+", "+cmd.getUserId()+", \""+cmd.getContactName()+"\");' href='javascript:void(0);'" +
    		    		"title='Start Following'>Follow</li>";
					}
				}
    		}
    		
    		
    	} catch (ElementNotFoundException e) {
    		// if everything is correct, this is a not possible case
    	}

    	HttpServletResponse resp = contextManager.getContext().getResponse();
    	resp.setContentType("text/html");
    	Writer out = resp.getWriter();
    	out.write(returnString);
    	out.close();
    	return null;
    }

    public static class Command extends ContactDto implements CommandBean
    {
        private int contactId;
        private int actionFor;
        private int actionType;
        private int userId;
        private String contactName;
        
		public int getContactId() {
			return contactId;
		}
		public void setContactId(int contactId) {
			this.contactId = contactId;
		}
		public int getActionFor() {
			return actionFor;
		}
		public void setActionFor(int actionFor) {
			this.actionFor = actionFor;
		}
		public int getActionType() {
			return actionType;
		}
		public void setActionType(int actionType) {
			this.actionType = actionType;
		}
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public String getContactName() {
			return contactName;
		}
		public void setContactName(String contactName) {
			this.contactName = contactName;
		}
    }

    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

    public void setUserFinder(UserFinder userFinder)
    {
        this.userFinder = userFinder;
    }

    public void setContactFinder(ContactFinder contactFinder)
    {
        this.contactFinder = contactFinder;
    }
}
