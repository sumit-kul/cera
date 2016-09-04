package com.era.community.pers.ui.action;

import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.EntityWrapper;
import support.community.database.IndexedScrollerPage;
import support.community.database.QueryScroller;
import support.community.framework.AbstractCommandAction;
import support.community.framework.IndexCommandBeanImpl;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.communities.ui.dto.CommunityDto;
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.tagging.ui.dto.TagDto;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/connectionCommunities.do" 
 *  @spring.bean name="/pers/connectionCommunities.ajx"
 */
public class ConnectionCommunitiesDisplayAction extends AbstractCommandAction
{

	public static final String REQUIRES_AUTHENTICATION = "";

	private UserFinder userFinder; 
	private CommunityEraContextManager contextManager; 
	private CommunityFinder commFinder;
	private ContactFinder contactFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		Command cmd = (Command)data; 
		User currUser = context.getCurrentUser();
		String pageNum = context.getRequest().getParameter("jPage");
		int pNumber = 0;
		if (pageNum != null && !"".equals(pageNum)) {
			pNumber = Integer.parseInt(pageNum);
		}

		if (cmd.getId() == 0 && currUser != null) {
			cmd.setId(currUser.getId());
		}
		User usr = null;
		try {
			usr = userFinder.getUserEntity(cmd.getId());
		} catch (ElementNotFoundException e) {
			return new ModelAndView("/pageNotFound");
		}

		if (!usr.isValidated()) {
			return new ModelAndView("/pageNotFound");
		}

		if (!usr.isInactive()) {
			cmd.setUser(usr);

			if (context.getRequest().getParameter("fTagList") != null 
					&& !"".equals(context.getRequest().getParameter("fTagList"))) {
				cmd.setFilterTagList(context.getRequest().getParameter("fTagList"));
			}

			if (context.getRequest().getParameter("filterTag") != null 
					&& !"".equals(context.getRequest().getParameter("filterTag"))) {
				cmd.addFilterTagList(context.getRequest().getParameter("filterTag"));
			}

			if (context.getRequest().getParameter("rmFilterTag") != null 
					&& !"".equals(context.getRequest().getParameter("rmFilterTag"))) {
				cmd.removeFilterTagList(context.getRequest().getParameter("rmFilterTag"));
			}

			if (context.getRequest().getParameter("toggleList") != null 
					&& !"".equals(context.getRequest().getParameter("toggleList"))
					&& "true".equals(context.getRequest().getParameter("toggleList"))) {
				cmd.setToggleList("true");
			}

			/* Get list of all communities this user is a member of */
			QueryScroller scroller = commFinder.listActiveCommunitiesForMemberByName(usr, currUser);
			if (pageNum != null) {
				HttpServletResponse resp = contextManager.getContext().getResponse();
				JSONObject json = null;
				if (pNumber > 0) {
					scroller.setBeanClass(RowBean.class, this);
					scroller.setPageSize(cmd.getPageSize());
					IndexedScrollerPage page = scroller.readPage(pNumber);
					json = page.toJsonString(pNumber, cmd.getFilterTagList(), null, null, cmd.getToggleList());
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
				cmd.setReturnString(getConnectionInfo(currUser, usr));
				scroller.setPageSize(cmd.getPageSize());
				cmd.setPageCount(scroller.readPageCount());
				cmd.setRowCount(scroller.readRowCount());
				return new ModelAndView("/pers/connectionCommunities");
			}
		} else {
			return new ModelAndView("redirect:/pers/connectionResult.do?id=" + cmd.getId());
		}
	}

	public String getConnectionInfo(User currentuser, User profileUser) throws Exception
	{
		String returnString = "";
		User current=contextManager.getContext().getCurrentUser();
		if (currentuser == null) // No action for User
			return "";
		if (current.getId() == profileUser.getId()) // No action for User
			return "";
		try {
			Contact contact = contactFinder.getContact(current.getId(), profileUser.getId());
			if (contact.getOwningUserId() == current.getId()) {
				if (contact.getStatus() == 0 || contact.getStatus() == 2 || contact.getStatus() == 3) {
					// connection request sent
					returnString = "<a class='btnmain normalTip' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+1+", \""+profileUser.getFullName()+"\")' href='javascript:void(0);'" +
					"title='Cancel connection request sent to "+profileUser.getFullName()+"' >Cancel Request</a>";
				} else if (contact.getStatus() == 1) {
					// Already connected
					returnString = "<a class='btnmain normalTip' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+1+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);' " +
					"title='Remove "+profileUser.getFullName()+" from you connections' >Disconnect</a>";
					if (contact.getFollowContact() == 1) { //pass 0 for Owner and 1 for Contact
						returnString = returnString + "<a class='btnmain normalTip' onclick='stopFollowing("+contact.getId()+", "+1+","+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
						"title='Stop Following'>Stop Following</a>";
					} else {
						returnString = returnString + "<a class='btnmain normalTip' onclick='startFollowing("+contact.getId()+", "+1+","+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
						"title='Start Following'>Follow</a>";
					}
					returnString = returnString + "<a class='btnmain normalTip' onclick='sendMessage("+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
					"title='Send message to "+profileUser.getFullName()+"'>Send Message</a>";
				} else if (contact.getStatus() == 4) {
					// user has spammed you and you have cancelled the request...
					returnString = "<a class='btnmain normalTip' onclick='addConnection("+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
					"title='Add "+profileUser.getFullName()+" to my connections'>Add to my connections</a>";
				}
			} else {
				if (contact.getStatus() == 0) {
					returnString = "<a class='btnmain normalTip' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+4+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);' " +
					"title='Confirm connection request from "+profileUser.getFullName()+"'>Confirm Request</a>" +
					//"<a onclick='updateConnection("+this.getId()+", "+contact.getId()+", "+5+", \""+this.getFullName()+"\");' href='javascript:void(0);' " +
					//"title='Hide this request. ("+this.getFullName()+" won't know)' class='search_btn right' style='font-size:12px;'>Not Now</a>";
					"<a class='btnmain normalTip' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+1+", \""+profileUser.getFullName()+"\")' href='javascript:void(0);' " +
					"title='Delete connection request from "+profileUser.getFullName()+"'>Delete</a>";
				} else if (contact.getStatus() == 1) {
					// Already connected
					returnString = "<a class='btnmain normalTip' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+1+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);' " +
					"title='Remove "+profileUser.getFullName()+" from you connections' style='float:right;'>Disconnect</a>";
					if (contact.getFollowOwner() == 1) { //pass 0 for Owner and 1 for Contact
						returnString = returnString + "<a class='btnmain normalTip' onclick='stopFollowing("+contact.getId()+", "+0+","+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
						"title='Stop Following'>Stop Following</a>";
					} else {
						returnString = returnString + "<a class='btnmain normalTip' onclick='startFollowing("+contact.getId()+", "+0+","+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
						"title='Start Following'>Follow</a>";
					}
					returnString = returnString + "<a class='btnmain normalTip' onclick='sendMessage("+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
					"title='Send message to "+profileUser.getFullName()+"'>Send Message</a>";
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
    				returnString = "<span>Spammed</span><a class='btnmain normalTip' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+3+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);' " +
    				"title='Undo spam to "+profileUser.getFullName()+"'>Undo</a>";
    			}
			}
		} catch (ElementNotFoundException e) { 
			// Add to my connections
			returnString = "<a class='btnmain normalTip' onclick='addConnection("+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
			"title='Add "+profileUser.getFullName()+" to my connections'>Add to my connections</a>";
		}
		return returnString;
	}

	public class Command extends IndexCommandBeanImpl
	{
		private int id;
		private User user;
		private boolean isBlogOwner;

		private int communitiesPendingApproval;
		private String communityType;
		private String communityOption;
		private String filterTagList = "";
		private String returnString = "";

		public boolean isContactOfCurrentUser() throws Exception
		{

			User current=contextManager.getContext().getCurrentUser();

			if (current == null) return false;

			if (current.getId()==this.getId()) return true;

			try {
				contactFinder.getContact(current.getId(), this.getId());
			} catch (ElementNotFoundException e) {
				return false;
			}
			return true;
		}

		public boolean isContactionAllowed() throws Exception
		{
			User current=contextManager.getContext().getCurrentUser();

			if (current == null) return false;

			if (current.getId()==this.getId()) return false;

			return true;
		}

		/**
		 * @param filterTagList the filterTagList to add as comma separated 
		 */
		 public void addFilterTagList(String filterTag) {
			if (filterTag != null && !"".equals(filterTag)) {
				if (this.filterTagList == null || this.filterTagList.equals("")) {
					this.filterTagList = filterTag;
				} else {
					StringTokenizer st = new StringTokenizer(getFilterTagList(), ",");
					boolean isAdditionAllowed = true;
					while (st.hasMoreTokens()) {
						String tag = st.nextToken().trim().toLowerCase();
						if (tag.equalsIgnoreCase(filterTag)) {
							isAdditionAllowed = false;
							break;
						}
					}
					if (isAdditionAllowed && filterTag != null && !"".equals(filterTag))
						this.filterTagList += ","+filterTag;
				}
			}
		 }

		 /**
		  * @param filterTagList the filterTagList to remove from the filterTag list 
		  */
		 public void removeFilterTagList(String rmfilterTag) {
			 String newFilterTagList = "";
			 if (getFilterTagList() != null && !"".equals(getFilterTagList())) {
				 StringTokenizer st = new StringTokenizer(getFilterTagList(), ",");
				 while (st.hasMoreTokens()) {
					 String tag = st.nextToken().trim().toLowerCase();
					 if (rmfilterTag != null && !rmfilterTag.equalsIgnoreCase(tag)) {
						 newFilterTagList += tag;
						 if (st.hasMoreTokens()) newFilterTagList += ",";
					 }
				 }
				 this.filterTagList = newFilterTagList;
			 }
		 }

		 public String getDisplayedFilterTag() {
			 String returnHtmlTags = "";
			 String filterList = getFilterTagList();
			 if (getFilterTagList() != null && !"".equals(filterList)) {
				 StringTokenizer st = new StringTokenizer(filterList, ",");
				 while (st.hasMoreTokens()) {
					 String tag = st.nextToken().trim().toLowerCase();
					 returnHtmlTags += "<a href='community/showCommunities.do?fTagList="+filterList+"&rmFilterTag="+tag+"' class='euInfoRmTag' style='display: inline;' title='Remove "+tag+" filter'>"+tag+" <span style='color: rgb(137, 143, 156); font-size: 12px;' title='Remove "+tag+" filter' >X</span></a>";
				 }
			 }
			 return returnHtmlTags;
		 }

		 public boolean isBlogOwner()
		 {
			 return isBlogOwner;
		 }

		 public void setBlogOwner(boolean isBlogOwner)
		 {
			 this.isBlogOwner = isBlogOwner;
		 }

		 public User getUser()
		 {
			 return user;
		 }

		 public void setUser(User user)
		 {
			 this.user = user;
		 }

		 public int getId() {
			 return id;
		 }

		 public void setId(int id) {
			 this.id = id;
		 }

		 public int getCommunitiesPendingApproval() {
			 return communitiesPendingApproval;
		 }

		 public void setCommunitiesPendingApproval(int communitiesPendingApproval) {
			 this.communitiesPendingApproval = communitiesPendingApproval;
		 }

		 public String getCommunityType() {
			 return communityType;
		 }

		 public void setCommunityType(String communityType) {
			 this.communityType = communityType;
		 }

		 public String getCommunityOption() {
			 return communityOption;
		 }

		 public void setCommunityOption(String communityOption) {
			 this.communityOption = communityOption;
		 }

		 public String getFilterTagList() {
			 return filterTagList;
		 }

		 public void setFilterTagList(String filterTagList) {
			 this.filterTagList = filterTagList;
		 }

		 public String getReturnString() {
			 return returnString;
		 }

		 public void setReturnString(String returnString) {
			 this.returnString = returnString;
		 }

	}

	public final void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public final void setCommFinder(CommunityFinder commFinder)
	{
		this.commFinder = commFinder;
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public class RowBean extends CommunityDto implements EntityWrapper
	{
		private int resultSetIndex;
		private int level = 1;
		private Community community;
		private boolean member;
		private boolean membershipRequested;
		private String firstName;
		private String lastName;

		public String getTaggedKeywords(int pageNumber, String filterTag, String commOption, String sortOption, String toggleList){
			String taggedKeywords = "";
			try {
				List tags = community.getTagsForOnlyCommunityByPopularity(20);
				String filterTagString = "";
				String commOptionString = "";
				String sortOptionString = "";

				if (filterTag != null && !"".equals(filterTag))
					filterTagString = "&fTagList="+filterTag;

				if (commOption != null && !"".equals(commOption))
					commOptionString = "&communityOption="+commOption;

				if (sortOption != null && !"".equals(sortOption))
					sortOptionString = "&sortByOption="+sortOption;

				for (Iterator iterator = tags.iterator(); iterator.hasNext();) {
					TagDto tb = (TagDto) iterator.next();
					String tag = tb.getTagText().trim().toLowerCase();
					taggedKeywords += "<a href='community/showCommunities.do?filterTag="+tag+filterTagString+commOptionString+sortOptionString+"&toggleList="+toggleList+" ' class='euInfoSelect normalTip' style='display: inline;' title='Click to filter by tag &#39;"+tag+"&#39;'>"+tag+"</a>";
					if (iterator.hasNext())taggedKeywords += " , ";
				}
			} catch (Exception e) {
				return taggedKeywords;
			}
			return taggedKeywords;
		}

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

		public String getCreatedBy()
		{
			return getFirstName() + " " + getLastName();
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

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
	}

	public void setContactFinder(ContactFinder contactFinder) {
		this.contactFinder = contactFinder;
	}
}