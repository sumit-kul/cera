package com.era.community.communities.ui.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.servlet.ModelAndView;

import support.community.framework.AbstractFormAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;
import support.community.framework.CommandValidator;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.communities.dao.CommunityJoiningRequest;
import com.era.community.communities.dao.CommunityJoiningRequestFinder;
import com.era.community.communities.ui.validator.CommunityJoiningRequestValidator;
import com.era.community.config.dao.MailMessageConfig;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 * @spring.bean name="/community/inviteAFriend.do"
 * TODO need to add later
 */
public class InviteAFriendAction extends AbstractFormAction
{
	protected CommunityEraContextManager contextManager;
	protected CommunityFinder communityFinder; 
	protected CommunityJoiningRequestFinder joiningRequestFinder;
	protected MailSender mailSender;
	protected MailMessageConfig mailMessageConfig;

	protected CommandValidator createValidator()
	{
		return new Validator();
	}

	protected String getView()
	{
		return "community/inviteAFriend";
	}

	protected void onDisplay(Object data) throws Exception
	{/*
        Command cmd = (Command)data;
        Community comm = communityFinder.getCommunityForId(cmd.getId());
        cmd.setCommunity(comm);
        cmd.buildAutoCompleteList(userFinder, context.getCurrentUser());

        if (cmd.getToUserId() != 0 ) {
        	 Check for single ID of recipient        
        	User user = userFinder.getUserForId(cmd.getToUserId());
            cmd.setToNames(getDisplayNameForId(cmd, user.getId()));	
        }
        else if (cmd.getToIdList() != null && !cmd.getToIdList().trim().equals("")) {                        
        	 Check for list of IDs         
        	StringTokenizer st = new StringTokenizer(cmd.getToIdList(),",");        	
        	StringBuffer sb = new StringBuffer();
        	while (st.hasMoreTokens()) {
        		try {
        			int id = Integer.parseInt(st.nextToken());
        			User user = userFinder.getUserForId(id);
        			if (sb.length() > 0) sb.append(", ");
        			 Get display name for this user         			
        			sb.append(getDisplayNameForId(cmd, user.getId()));
        		}
        		catch (NumberFormatException ex) {
        			 Do nothing, contact ID not recognized 
        		}
        	}
        	cmd.setToNames(sb.toString());
        }
	 */}

	protected ModelAndView onSubmit(Object data) throws Exception
	{
		Command cmd = (Command)data;
		Community comm = communityFinder.getCommunityForId(cmd.getId());
		CommunityJoiningRequest request = null;
		cmd.setCommunity(comm);

		User user = contextManager.getContext().getCurrentUser();
		String redirect;

		if (cmd.isApprovalRequired()) {
			request = joiningRequestFinder.newCommunityJoiningRequest();
			request.setCommunityId(comm.getId());
			request.setUserId(user.getId());
			request.setOptionalComment(cmd.getOptionalComment());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			String dt = sdf.format(now);
			Timestamp ts = Timestamp.valueOf(dt);
			request.setRequestDate(ts);

			try {
				request.update();
			}
			catch (DataIntegrityViolationException e) { 
				e.printStackTrace();
			}  

			// send email to the community admins
			mailApprovers(comm,user,cmd);


			/* Display a confirmation screen */       
			CommunityEraContext context = contextManager.getContext();

			// We could pass a bean to the jsp that carries the Community name
			return new ModelAndView("comm/join-request-confirm",  "command", cmd );


			/*       
            redirect = cmd.getReferer();
            if (redirect==null||redirect.trim().length()==0) redirect = "/";    
			 */    
		}
		else {
			comm.getMemberList().addMember(user);
			redirect = cmd.getRedirect();
			if (redirect==null||redirect.trim().length()==0) redirect = "/cid/"+comm.getId()+"/home.do";        
		}


		return new ModelAndView("redirect:"+redirect);

	}


	protected Map referenceData(Object command) throws Exception
	{
		return new HashMap();
	}


	/*
	 * Mail the Community Administrators to request membership approval
	 */
	private void mailApprovers(Community comm, User user, Command cmd) throws Exception
	{

		CommunityEraContext context = contextManager.getContext(); 
		if (!user.isSuperAdministrator()) {
			Map<String, String> params = new HashMap<String, String>(11);
			String sLink = context.getContextUrl()+"/cid/"+comm.getId()+"/connections/showJoiningRequests.do";
			String sQuestion = cmd.getOptionalComment();
			if(sQuestion == null) sQuestion = "";

			params.put("#communityName#", comm.getName());
			params.put("#communityLink#", sLink);
			params.put("#question#", sQuestion);
			params.put("#userName#", user.getFullName());
			params.put("#emailAddress#", user.getEmailAddress());

			SimpleMailMessage msg = mailMessageConfig.createMailMessage("membership-request", params);
			msg.setTo(comm.getAdminMemberEmailAddresses());
			try { mailSender.send(msg); } 
			catch (Exception x) {
				logger.error("Mail Community Admins = mail send error ", x);
			}
		}
	}

	public static class Command extends CommandBeanImpl implements CommandBean
	{
		private int id;
		private boolean approvalRequired;
		private boolean alreadyRequested;
		private boolean requestExists;                             // There is an existing request to join this communitys
		private Community community;
		private String redirect;
		private String referer;
		private String optionalComment;

		public Community getCommunity()
		{
			return community;
		}
		public void setCommunity(Community community)
		{
			this.community = community;
		}
		public int getId()
		{
			return id;
		}
		public void setId(int id)
		{
			this.id = id;
		}
		public final String getRedirect()
		{
			return redirect;
		}
		public final void setRedirect(String redirect)
		{
			this.redirect = redirect;
		}
		public final boolean isApprovalRequired()
		{
			return approvalRequired;
		}
		public final void setApprovalRequired(boolean requiresApproval)
		{
			this.approvalRequired = requiresApproval;
		}
		public final boolean isAlreadyRequested()
		{
			return alreadyRequested;
		}
		public final void setAlreadyRequested(boolean alreadyRequested)
		{
			this.alreadyRequested = alreadyRequested;
		}
		public final String getReferer()
		{
			return referer;
		}
		public final void setReferer(String referer)
		{
			this.referer = referer;
		}
		public final String getOptionalComment()
		{
			return optionalComment;
		}
		public final void setOptionalComment(String optionalComment)
		{
			this.optionalComment = optionalComment;
		}

		public void buildAutoCompleteList(UserFinder userFinder, User curuser) throws Exception 
		{/*

		 * Build autocomplete JS array based on contact list and any user id specificed directly 
		 * Plus, 2 HashMaps:  
		 * One containing key = user full name, value = count (number of users with same full name)
		 * One containing key = user display name (inc count if user is a duplicate), value = user ID
		 *     	
        	HashMap<String, Integer> userIndex = new HashMap<String, Integer>();
        	HashMap<String, Integer> displayUsers = new HashMap<String, Integer>();        	
        	TreeMap<String, UserBean> myContacts = new TreeMap<String, UserBean>();        	        	  

        	 Process ids passed into action 
        	if (getToUserId() != 0 ) {
             	User user = userFinder.getUserForId(getToUserId());
             	userIndex.put(user.getFullName(), new Integer(1));
             	displayUsers.put(user.getFullName(), new Integer(user.getId()));
            }
        	else if (getToIdList() != null && !getToIdList().trim().equals("")) {
        		StringTokenizer st = new StringTokenizer(getToIdList(),",");        	         	
             	while (st.hasMoreTokens()) {
             		try {
             			int id = Integer.parseInt(st.nextToken());
             			User user = userFinder.getUserForId(id);
             			if (userIndex.containsKey(user.getFullName())) {
             				 Increment count if this is a different user with the same name 
             				if (displayUsers.get(user.getFullName()).intValue() != user.getId()) {
             					int count = userIndex.get(user.getFullName()).intValue();
                 				userIndex.put(user.getFullName(), count++);
                 				displayUsers.put(user.getFullName() + " (" +  count + ")" , new Integer(user.getId()));	
             				}         				
             			}
             			else {
             				userIndex.put(user.getFullName(), new Integer(1));
             	         	displayUsers.put(user.getFullName(), new Integer(user.getId()));
             			}
             		}
             		catch (NumberFormatException ex) {
             			// Do nothing, contact ID not recognized
             		}    		     		 
             	}
        	}

        	 Process 'my contacts' 
        	QueryScroller contacts = curuser.listContacts();
        	contacts.setPageSize(9999);
            List contactList = contacts.readPage(1);                                
            for (int i=0; i<contactList.size(); i++) {
            	UserBean contact = (UserBean) contactList.get(i);
            	if (userIndex.containsKey(contact.getFullName())) {
     				 Increment count if this is a different user with the same name 
     				if (displayUsers.get(contact.getFullName()).intValue() != contact.getId()) {
     					int count = userIndex.get(contact.getFullName()).intValue();
         				userIndex.put(contact.getFullName(), count++);
         				displayUsers.put(contact.getFullName() + " (" +  count + ")" , new Integer(contact.getId()));
         				myContacts.put(contact.getFullName() + " (" +  count + ")", contact);
     				}         				
     			}
     			else {
     				userIndex.put(contact.getFullName(), new Integer(1));
     	         	displayUsers.put(contact.getFullName(), new Integer(contact.getId()));
     	         	myContacts.put(contact.getFullName(), contact);
     			}    

            }	

             Build autocomplete array 
            StringBuffer buf = new StringBuffer();
            for (String key : displayUsers.keySet()) {        	
            	if (buf.length()>0) buf.append(",");
            	buf.append("'" + key + "'");        	
            }

             Sort using UserBean's 'natural order' (lastname) 
            TreeSet<Map.Entry> set = new TreeSet<Map.Entry>(new Comparator<Map.Entry>() {
                public int compare(Map.Entry obj, Map.Entry obj1) {
                	return ((UserBean) obj.getValue()).compareTo((UserBean) obj1.getValue());                	                   
                }
            });            

            set.addAll(myContacts.entrySet());

            setUserIndex(userIndex);
            setDisplayUsers(displayUsers);
            setMyContacts(set);
            setAutoCompleteJSArray(buf.toString());

		 */}    
	}

	public static class Validator extends CommunityJoiningRequestValidator
	{

	}

	public void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public final void setJoiningRequestFinder(CommunityJoiningRequestFinder joiningRequestFinder)
	{
		this.joiningRequestFinder = joiningRequestFinder;
	}

	public void setMailSender(MailSender mailSender)
	{
		this.mailSender = mailSender;
	}

	public CommunityEraContextManager getContextHolder()
	{
		return contextManager;
	}

	public final void setMailMessageConfig(MailMessageConfig mailMessageConfig)
	{
		this.mailMessageConfig = mailMessageConfig;
	}
}