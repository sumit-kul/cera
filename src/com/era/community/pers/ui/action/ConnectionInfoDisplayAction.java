package com.era.community.pers.ui.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.location.dao.Location;
import com.era.community.location.dao.LocationFinder;
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.UserDto;

/**
 *  @spring.bean name="/pers/connectionResult.do" 
 */
public class ConnectionInfoDisplayAction extends AbstractCommandAction
{
    private CommunityEraContextManager contextManager; 
    private UserFinder userFinder;
    private ContactFinder contactFinder;
    private LocationFinder locationFinder;

    protected ModelAndView handle(Object data) throws Exception
    {
    	Command cmd = (Command)data; 
    	CommunityEraContext context = contextManager.getContext();
    	User usr = null;
    	try {
			usr = userFinder.getUserEntity(cmd.getId());
	    	cmd.setUser(usr);
	    	try {
	    		Location location = locationFinder.getLocationByUserId(usr.getId());
	    		cmd.setAddress(location.getAddress());
			} catch (ElementNotFoundException e) {
			}
	    	
	    	if (context.getCurrentUser() != null && usr.getId() != context.getCurrentUser().getId()) {
	    		String returnString = "";
	    		try {
	    			Contact contact = contactFinder.getContact(context.getCurrentUser().getId(), usr.getId());
	    			cmd.setConnectionDate(getConnectionDate(contact));
	    			cmd.setReturnString(getConnectionInfo(contact, context.getCurrentUser(), usr));
				} catch (ElementNotFoundException e) { 
		    		// Add to my connections
		    		returnString = "<a class='btnmain normalTip' onclick='addConnection("+usr.getId()+", \""+usr.getFullName()+"\");' href='javascript:void(0);'" +
					"title='Add "+usr.getFullName()+" to my connections'>Add to my connections</a>";
		    		cmd.setReturnString(returnString);
		    	}
	    	}
				
		} catch (ElementNotFoundException e) {
		}
    	return new ModelAndView("/pers/connectionInfo");
    }
    
    public String getConnectionDate(Contact contact) throws Exception
	{
		SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy");
			Date date = contact.getDateConnection();
			return fmt2.format(date);
	}
    
    public String getConnectionInfo(Contact contact, User current, User conUsr) throws Exception
    {
    	String returnString = "";
    	if (current == null) // No action for User
    		return "";
    	if (current.getId() == conUsr.getId()) // No action for User
    		return "";
    		if (contact.getOwningUserId() == current.getId()) {
    			if (contact.getStatus() == 0 || contact.getStatus() == 2 || contact.getStatus() == 3) {
    				// connection request sent
    				returnString = "<a class='btnmain normalTip' onclick='updateConnection("+conUsr.getId()+", "+contact.getId()+", "+1+", \""+conUsr.getFullName()+"\")' href='javascript:void(0);'" +
    				"title='Cancel connection request sent to "+conUsr.getFullName()+"' >Cancel Request</a>";
    			} else if (contact.getStatus() == 1) {
    				// Already connected
    				returnString = "<a class='btnmain normalTip' onclick='updateConnection("+conUsr.getId()+", "+contact.getId()+", "+1+", \""+conUsr.getFullName()+"\");' href='javascript:void(0);' " +
    				"title='Remove "+conUsr.getFullName()+" from you connections' >Disconnect</a>";
    				if (contact.getFollowContact() == 1) { //pass 0 for Owner and 1 for Contact
						returnString = returnString + "<a class='btnmain normalTip' onclick='stopFollowing("+contact.getId()+", "+1+","+conUsr.getId()+", \""+conUsr.getFullName()+"\");' href='javascript:void(0);'" +
    		    		"title='Stop Following'>Stop Following</a>";
					} else {
						returnString = returnString + "<a class='btnmain normalTip' onclick='startFollowing("+contact.getId()+", "+1+","+conUsr.getId()+", \""+conUsr.getFullName()+"\");' href='javascript:void(0);'" +
    		    		"title='Start Following'>Follow</a>";
					}
    				returnString = returnString + "<a class='btnmain normalTip' onclick='sendMessageFromInfo("+conUsr.getId()+", \""+conUsr.getFullName()+"\", \""+conUsr.isPhotoPresent()+"\");' href='javascript:void(0);'" +
		    		"title='Send message to "+conUsr.getFullName()+"'>Send Message</a>";
    			} else if (contact.getStatus() == 4) {
    				// user has spammed you and you have cancelled the request...
    				returnString = "<a class='btnmain normalTip' onclick='addConnection("+conUsr.getId()+", \""+conUsr.getFullName()+"\");' href='javascript:void(0);'" +
    				"title='Add "+conUsr.getFullName()+" to my connections'>Add to my connections</a>";
    			}
    		} else {
    			if (contact.getStatus() == 0) {
    				returnString = "<a class='btnmain normalTip' onclick='updateConnection("+conUsr.getId()+", "+contact.getId()+", "+4+", \""+conUsr.getFullName()+"\");' href='javascript:void(0);' " +
    				"title='Confirm connection request from "+conUsr.getFullName()+"'>Confirm Request</a>" +
    						//"<a onclick='updateConnection("+this.getId()+", "+contact.getId()+", "+5+", \""+this.getFullName()+"\");' href='javascript:void(0);' " +
    				//"title='Hide this request. ("+this.getFullName()+" won't know)' class='search_btn right' style='font-size:12px;'>Not Now</a>";
    				"<a class='btnmain normalTip' onclick='updateConnection("+conUsr.getId()+", "+contact.getId()+", "+1+", \""+conUsr.getFullName()+"\")' href='javascript:void(0);' " +
        			"title='Delete connection request from "+conUsr.getFullName()+"'>Delete</a>";
    			} else if (contact.getStatus() == 1) {
    				// Already connected
    				returnString = "<a class='btnmain normalTip' onclick='updateConnection("+conUsr.getId()+", "+contact.getId()+", "+1+", \""+conUsr.getFullName()+"\");' href='javascript:void(0);' " +
    				"title='Remove "+conUsr.getFullName()+" from you connections' style='float:right;'>Disconnect</a>";
    				if (contact.getFollowOwner() == 1) { //pass 0 for Owner and 1 for Contact
						returnString = returnString + "<a class='btnmain normalTip' onclick='stopFollowing("+contact.getId()+", "+0+","+conUsr.getId()+", \""+conUsr.getFullName()+"\");' href='javascript:void(0);'" +
    		    		"title='Stop Following'>Stop Following</a>";
					} else {
						returnString = returnString + "<a class='btnmain normalTip' onclick='startFollowing("+contact.getId()+", "+0+","+conUsr.getId()+", \""+conUsr.getFullName()+"\");' href='javascript:void(0);'" +
    		    		"title='Start Following'>Follow</a>";
					}
    				returnString = returnString + "<a class='btnmain normalTip' onclick='sendMessageFromInfo("+conUsr.getId()+", \""+conUsr.getFullName()+"\", \""+conUsr.isPhotoPresent()+"\");' href='javascript:void(0);'" +
		    		"title='Send message to "+conUsr.getFullName()+"'>Send Message</a>";
    			} else if (contact.getStatus() == 3 || contact.getStatus() == 4) {
    				// Spammed case
    				returnString = "<span>Spammed</span><a class='btnmain normalTip' onclick='updateConnection("+conUsr.getId()+", "+contact.getId()+", "+3+", \""+conUsr.getFullName()+"\");' href='javascript:void(0);' " +
    				"title='Undo spam to "+conUsr.getFullName()+"'>Undo</a>";
    			}
    		}
    	
    	return returnString;
    }
    
    public class Command extends UserDto implements CommandBean
    {
        private User user;
        private String connectionDate = "";
        private String returnString = "";
        private String phoneCode = "";
        private String address = "";
        private String registeredOn;
        
        public String getRegisteredOn() throws Exception
		{
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy");
			Date today = new Date();
			String sToday = fmter.format(today);

			try {

				//Date date = formatter.format(user.getDateRegistered());
				if (formatter.format(user.getDateRegistered()).equals(sToday)) {
					return "Today " + fmt.format(user.getDateRegistered());
				}
				return fmt2.format(user.getDateRegistered());

			} catch (Exception e) {
				return getDateRegistered();
			}
		}
        
        public User getUser()
        {
            return user;
        }

        public void setUser(User user)
        {
            this.user = user;
        }

		public String getConnectionDate() {
			return connectionDate;
		}

		public void setConnectionDate(String connectionDate) {
			this.connectionDate = connectionDate;
		}

		public String getReturnString() {
			return returnString;
		}

		public void setReturnString(String returnString) {
			this.returnString = returnString;
		}

		public String getPhoneCode() {
			return phoneCode;
		}

		public void setPhoneCode(String phoneCode) {
			this.phoneCode = phoneCode;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public void setRegisteredOn(String registeredOn) {
			this.registeredOn = registeredOn;
		}
    }
    
    public void setUserFinder(UserFinder userFinder)
    {
        this.userFinder = userFinder;
    }
    public void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }

	public void setContactFinder(ContactFinder contactFinder) {
		this.contactFinder = contactFinder;
	}

	public void setLocationFinder(LocationFinder locationFinder) {
		this.locationFinder = locationFinder;
	}
}