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
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.UserDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/showConnections.do" 
 */
public class ShowConnectionsAction extends AbstractCommandAction
{

    public static final String REQUIRES_AUTHENTICATION = "";
    
    /* Injected references */
    private CommunityEraContextManager contextManager;
    private UserFinder userFinder;
    protected ContactFinder contactFinder;
       
    public void setContactFinder(ContactFinder contactFinder)
    {
        this.contactFinder = contactFinder;
    }
    
    @Override
    protected ModelAndView handle(Object data) throws Exception
    {
    	Command cmd = (Command) data;
    	CommunityEraContext context = contextManager.getContext();
        User user = null;
        if (cmd.getConnectionId() > 0) {
        	user = userFinder.getUserEntity(cmd.getConnectionId());
            cmd.copyPropertiesFrom(user);
            cmd.setUser(user);
        } else {
        	user = contextManager.getContext().getCurrentUser();
        }
        
        if (user == null ) return null;
        
    	
    	String pageNum = context.getRequest().getParameter("jPage");
        int pNumber = 0;
        if (pageNum != null && !"".equals(pageNum)) {
        	pNumber = Integer.parseInt(pageNum);
		}
        
        if (context.getRequest().getParameter("sortByOption") != null 
        		&& !"".equals(context.getRequest().getParameter("sortByOption"))) {
        	cmd.setSortByOption(context.getRequest().getParameter("sortByOption"));
		}
    	
    	QueryScroller scroller = null;
    	if (cmd.getSortByOption() == null || cmd.getSortByOption().equalsIgnoreCase("name")) {
            scroller = user.listContacts(); 
        } else {
        	scroller = user.listContacts();
        }
    	    	
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
			return new ModelAndView("pers/showConnections");
		}
    }
       
    public class RowBean extends UserDto
    {              
    	private int resultSetIndex;
    	private String DateConnection;

    	public String getConnectionDate() throws Exception
    	{
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy");
    		try {
    			Date date = formatter.parse(getDateConnection());
    			return fmt2.format(date);

    		} catch (ParseException e) {
    			return getDateConnection();
    		}
    	}
    	public String getDateConnection() {
    		return DateConnection;
    	}
    	public void setDateConnection(Date dateConnection) {
    		DateConnection = dateConnection.toString();
    	}

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

    	public String getConnectionInfo() throws Exception
    	{
    		String returnString = "";
    		User current=contextManager.getContext().getCurrentUser();
    		if (current == null) // No action for User
    			return "";
    		if (current.getId() == this.getId()) // No action for User
    			return "";
    		try {
    			Contact contact = contactFinder.getContact(current.getId(), this.getId());
    			if (contact.getOwningUserId() == current.getId()) {
    				if (contact.getStatus() == 0 || contact.getStatus() == 2 || contact.getStatus() == 3) {
        				// connection request sent
        				returnString = "<a onClick='updateConnection("+this.getId()+", "+contact.getId()+", "+1+", \""+this.getFullName()+"\")' href='javascript:void(0);'" +
        				"title='Cancel connection request sent to "+this.getFullName()+"'>Cancel Request</a>";
        			} else if (contact.getStatus() == 1) {
        				// Already connected
        				returnString = "<a onClick='updateConnection("+this.getId()+", "+contact.getId()+", "+1+", \""+this.getFullName()+"\");' href='javascript:void(0);' " +
        				"title='Remove "+this.getFullName()+" from you connections'>Disconnect</a><br />";
        				if (contact.getFollowContact() == 1) { //pass 0 for Owner and 1 for Contact
    						returnString = returnString + "<a onClick='stopFollowing("+contact.getId()+", "+1+", "+this.getId()+", \""+this.getFullName()+"\");' href='javascript:void(0);'" +
        		    		"title='Stop Following'>Stop Following</a>";
						} else {
							returnString = returnString + "<a onClick='startFollowing("+contact.getId()+", "+1+", "+this.getId()+", \""+this.getFullName()+"\");' href='javascript:void(0);'" +
	    		    		"title='Start Following'>Follow</a>";
						}
    		    		returnString = returnString + "<a onClick='sendMessage("+this.getId()+", \""+this.getFullName()+"\");' href='javascript:void(0);'" +
    		    		"title='Send message to "+this.getFullName()+"'>Send Message</a>";
        			} else if (contact.getStatus() == 4) {
        				// user has spammed you and you have cancelled the request...
        				returnString = "<div><a onClick='addConnection("+this.getId()+", \""+this.getFullName()+"\");' href='javascript:void(0);'" +
        				"title='Add "+this.getFullName()+" to my connections'>Add to my connections</a>";
        			}
    			} else {
    				if (contact.getStatus() == 0) {
    					returnString = "<a onclick='updateConnection("+this.getId()+", "+contact.getId()+", "+4+", \""+this.getFullName()+"\");' href='javascript:void(0);' " +
    					"title='Confirm connection request from "+this.getFullName()+"'>Confirm Request</a>" +
    					//"<a onclick='updateConnection("+this.getId()+", "+contact.getId()+", "+5+", \""+this.getFullName()+"\");' href='javascript:void(0);' " +
    					//"title='Hide this request. ("+this.getFullName()+" won't know)' class='search_btn right' style='font-size:12px;'>Not Now</a>";
    					"<a onclick='updateConnection("+this.getId()+", "+contact.getId()+", "+1+", \""+this.getFullName()+"\")' href='javascript:void(0);' " +
    					"title='Delete connection request from "+this.getFullName()+"'>Delete</a>";
    				} else if (contact.getStatus() == 1) {
    					// Already connected
    					returnString = "<a onclick='updateConnection("+this.getId()+", "+contact.getId()+", "+1+", \""+this.getFullName()+"\");' href='javascript:void(0);' " +
    					"title='Remove "+this.getFullName()+" from you connections'>Disconnect</a><br />";
    					if (contact.getFollowOwner() == 1) { //pass 0 for Owner and 1 for Contact
    						returnString = returnString + "<a onclick='stopFollowing("+contact.getId()+", "+0+","+this.getId()+", \""+this.getFullName()+"\");' href='javascript:void(0);'" +
        		    		"title='Stop Following'>Stop Following</a>";
						} else {
							returnString = returnString + "<a onclick='startFollowing("+contact.getId()+", "+0+","+this.getId()+", \""+this.getFullName()+"\");' href='javascript:void(0);'" +
	    		    		"title='Start Following'>Follow</a>";
						}
    		    		returnString = returnString + "<a onclick='sendMessage("+this.getId()+", \""+this.getFullName()+"\");' href='javascript:void(0);'" +
    		    		"title='Send message to "+this.getFullName()+"'>Send Message</a>";
    				} /*if (contact.getStatus() == 2) {
        				// Not now case
        				returnString = "<a onclick='updateConnection("+this.getId()+", "+contact.getId()+", "+4+", \""+this.getFullName()+"\");' href='javascript:void(0);' " +
        				"title='Confirm connection request from "+this.getFullName()+"' class='search_btn right' style='font-size:12px;'>Confirm Request</a>" +
        				"<a onclick='updateConnection("+this.getId()+", "+contact.getId()+", "+1+", \""+this.getFullName()+"\");' href='javascript:void(0);' " +
        				"title='Delete connection request from "+this.getFullName()+"' class='search_btn right' style='font-size:12px;'>Delete</a>" +
        				"<a onclick='updateConnection("+this.getId()+", "+contact.getId()+", "+2+", \""+this.getFullName()+"\");' href='javascript:void(0);' " +
        				"title='Mark spam to "+this.getFullName()+". You won't get connection request from "+this.getFullName()+" anymore.' class='search_btn right' style='font-size:12px;'>Mark Spamm</a>";
        			}*/ else if (contact.getStatus() == 3 || contact.getStatus() == 4) {
        				// Spammed case
        				returnString = "<span>Spammed</span><a onclick='updateConnection("+this.getId()+", "+contact.getId()+", "+3+", \""+this.getFullName()+"\");' href='javascript:void(0);' " +
        				"title='Undo spam to "+this.getFullName()+"'>Undo</a>";
        			}
    			}
    		} catch (ElementNotFoundException e) { 
    			// Add to my connections
    			returnString = "<a onclick='addConnection("+this.getId()+", \""+this.getFullName()+"\");' href='javascript:void(0);'" +
    			"title='Add "+this.getFullName()+" to my connections'>Add to my connections</a>";
    		}
    		return returnString;
    	}
    }
    
    public static class Command extends IndexCommandBeanImpl implements CommandBean 
    {
        private int connectionId;
        private User user;
        private String sortByOption;
        
        public List getSortByOptionOptions() throws Exception
        {
        	List sortByOptionList = new ArrayList();
        	sortByOptionList.add("Name");
        	sortByOptionList.add("Role");
        	sortByOptionList.add("Joined");
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

		/**
		 * @return the connectionId
		 */
		public int getConnectionId() {
			return connectionId;
		}

		/**
		 * @param connectionId the connectionId to set
		 */
		public void setConnectionId(int connectionId) {
			this.connectionId = connectionId;
		}

		/**
		 * @return the user
		 */
		public User getUser() {
			return user;
		}

		/**
		 * @param user the user to set
		 */
		public void setUser(User user) {
			this.user = user;
		}
    }
    
    public final void setUserFinder(UserFinder userFinder) 
    {
        this.userFinder = userFinder;
    }
    
    public final void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }
}