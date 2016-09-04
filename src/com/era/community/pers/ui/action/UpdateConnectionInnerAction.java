package com.era.community.pers.ui.action;

import java.io.Writer;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.DashBoardAlert;
import com.era.community.pers.dao.DashBoardAlertFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.ContactDto;

/**
 * @spring.bean name="/pers/updateConnectionInner.ajx"
 */
public class UpdateConnectionInnerAction extends AbstractCommandAction
{
    protected UserFinder userFinder;
    protected ContactFinder contactFinder;
    protected DashBoardAlertFinder dashBoardAlertFinder;
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
    		Contact contact = contactFinder.getContactForId(cmd.getId());
    		if (contact.getOwningUserId() == currentUser.getId()) {
    			
    			User contactUser = userFinder.getUserEntity(contact.getContactUserId());
    			int connectionCountForContact = 0;
    			int connectionCountForCurrent = 0;
    			
    			// if request is for cancel or delete NewStatus = 1
    			if (cmd.getNewStatus() == 1) {
    				if (contact.getStatus() == 3) {
    					contact.setStatus(4); // owner can not cancel this request and status will be STATUS_SPAMMED_CANCELLED
    					contact.update();
    				} else if (contact.getStatus() == 3) {
    					// do nothing
    				} else {
    					if (contact.getStatus() == 1) {
    						connectionCountForContact = contactUser.getConnectionCount();
    						connectionCountForContact = connectionCountForContact -1;
        					contactUser.setConnectionCount(connectionCountForContact);
        					contactUser.update();
        					
        					connectionCountForCurrent = currentUser.getConnectionCount();
        					connectionCountForCurrent = connectionCountForCurrent -1;
        					currentUser.setConnectionCount(connectionCountForCurrent);
        					currentUser.update();
    					}
    					contact.delete();
    				}
    				returnString = "<a class='btnmain normalTip' onClick='addConnectionInner("+cmd.getUserId()+", \""+cmd.getUserName()+"\")'  href='javascript:void(0);'" +
    				"title='Add "+cmd.getUserName()+" to my connections' >Add to my Connections</a>";
				}
    		} else if (contact.getContactUserId() == currentUser.getId()){
    			User owningUser = userFinder.getUserEntity(contact.getOwningUserId());
    			int connectionCountForOwner = 0;
    			int connectionCountForCurrent = 0;
    			if (cmd.getNewStatus() == 1) { // if request is for cancel or delete, NewStatus = 1
    				if (contact.getStatus() == 1) {
    					connectionCountForOwner = owningUser.getConnectionCount();
    					connectionCountForOwner = connectionCountForOwner -1;
    					owningUser.setConnectionCount(connectionCountForOwner);
    					owningUser.update();
    					
    					connectionCountForCurrent = currentUser.getConnectionCount();
    					connectionCountForCurrent = connectionCountForCurrent -1;
    					currentUser.setConnectionCount(connectionCountForCurrent);
    					currentUser.update();
					}
    				contact.delete();
    				returnString = "<a class='btnmain normalTip' onClick='addConnectionInner("+cmd.getUserId()+", \""+cmd.getUserName()+"\")'  href='javascript:void(0);'" +
    				"title='Add "+cmd.getUserName()+" to my connections' >Add to my Connections</a>";
    			} else if (cmd.getNewStatus() == 2) { // if request is for do spam, NewStatus = 2
    				contact.setStatus(3);
    				contact.update();
    				returnString = "<span class='dynaDropDown' title='pers/connectionOptions.ajx?connId="+cmd.getUserId()+"&currId="+currentUser.getId()+"'>Spammed</span>";
    			} else if (cmd.getNewStatus() == 3) { // if request is for undo spam, NewStatus = 3
    				if (contact.getStatus() == 3) {
    					contact.setStatus(0);
    					contact.update();
    					returnString = "<a class='btnmain normalTip' onClick='updateConnectionInner("+cmd.getUserId()+", "+contact.getId()+", "+4+", \""+cmd.getUserName()+"\")' href='javascript:void(0);' " +
        				"title='Confirm connection request from "+cmd.getUserName()+"' >Confirm Request</a>" +
        						//"<a onClick='updateConnectionInner("+cmd.getUserId()+", "+contact.getId()+", "+5+", \""+cmd.getUserName()+"\")' href='javascript:void(0);' " +
        				//"title='Hide this request. ("+cmd.getUserName()+" won't know)' class='search_btn right' style='font-size:12px;'>Not Now</a>";
        				"<a class='btnmain' onClick='updateConnectionInner("+cmd.getUserId()+", "+contact.getId()+", "+1+", \""+cmd.getUserName()+"\")' href='javascript:void(0);' " +
            			"title='Delete connection request from "+cmd.getUserName()+"'>Delete</a>";
					} else if (contact.getStatus() == 4) { 
						contact.delete();
						returnString = "<a class='btnmain normalTip' onClick='addConnectionInner("+cmd.getUserId()+", \""+cmd.getUserName()+"\")'  href='javascript:void(0);'" +
	    				"title='Add "+cmd.getUserName()+" to my connections'>Add to My Connections</a>";
					}
    			} else if (cmd.getNewStatus() == 4) { // if request is for confirmation, NewStatus = 4
    				contact.setStatus(1); // marked approved
    				contact.setFollowOwner(1); // contact is following Owner
    				contact.setFollowContact(1); // Owner is following Contact
    				
    				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		    	Date now = new Date();
    		    	String dt = sdf.format(now);
    		    	Timestamp ts = Timestamp.valueOf(dt);
    				contact.setDateConnection(ts);
					contact.update();
					
					connectionCountForOwner = owningUser.getConnectionCount();
					connectionCountForOwner = connectionCountForOwner + 1;
					owningUser.setConnectionCount(connectionCountForOwner);
					owningUser.update();
					
					connectionCountForCurrent = currentUser.getConnectionCount();
					connectionCountForCurrent = connectionCountForCurrent + 1;
					currentUser.setConnectionCount(connectionCountForCurrent);
					currentUser.update();
					
					// update the dash board for recipients of this message
			        DashBoardAlert dashBoardAlert;
			        try {
			        	dashBoardAlert = dashBoardAlertFinder.getDashBoardAlertForUserId(owningUser.getId());
					} catch (ElementNotFoundException e) {
						dashBoardAlert = new DashBoardAlert();
						dashBoardAlert.setUserId(owningUser.getId());
						dashBoardAlert.setConnectionApprovedCount(1);
					}
					dashBoardAlert.setConnectionApprovedCount(dashBoardAlert.getConnectionApprovedCount() + 1);
					dashBoardAlert.update();
					
					returnString = "<span class='dynaDropDown' title='pers/connectionOptions.ajx?connId="+cmd.getUserId()+"&currId="+currentUser.getId()+"'>Connected</span>";
    			} 
    			/*else if (cmd.getNewStatus() == 5) { // if request is for not now, NewStatus = 5
    				contact.setStatus(2); // marked hide
					contact.update();
					returnString = "<a onClick='updateConnectionInner("+cmd.getUserId()+", "+contact.getId()+", "+4+", \""+cmd.getUserName()+"\")' href='javascript:void(0);' " +
    				"title='Confirm connection request from "+cmd.getUserName()+"' class='search_btn right' style='font-size:12px;'>Confirm Request</a>" +
    					"<a onClick='updateConnectionInner("+cmd.getUserId()+", "+contact.getId()+", "+1+", \""+cmd.getUserName()+"\")' href='javascript:void(0);' " +
        			"title='Delete connection request from "+cmd.getUserName()+"' class='search_btn right' style='font-size:12px;'>Delete</a>";
    			}*/
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
        private String userName;
        private int newStatus;
        private int userId;
        private String inner;

        public String getUserName()
        {
            return userName;
        }

        public void setUserName(String userName)
        {
            this.userName = userName;
        }

		public int getNewStatus() {
			return newStatus;
		}

		public void setNewStatus(int newStatus) {
			this.newStatus = newStatus;
		}

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public String getInner() {
			return inner;
		}

		public void setInner(String inner) {
			this.inner = inner;
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

	public void setDashBoardAlertFinder(DashBoardAlertFinder dashBoardAlertFinder) {
		this.dashBoardAlertFinder = dashBoardAlertFinder;
	}
}
