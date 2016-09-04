package com.era.community.profile.ui.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.database.EntityWrapper;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.Option;
import support.community.util.StringHelper;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.communities.dao.Community;
import com.era.community.communities.ui.dto.CommunityDto;
import com.era.community.location.dao.Country;
import com.era.community.location.dao.CountryFinder;
import com.era.community.location.dao.Location;
import com.era.community.location.dao.LocationFinder;
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.DashBoardAlert;
import com.era.community.pers.dao.DashBoardAlertFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.dao.UserPrivacy;
import com.era.community.pers.dao.UserPrivacyFinder;
import com.era.community.pers.ui.dto.UserDto;
import com.era.community.profile.dao.ProfileVisit;
import com.era.community.profile.dao.ProfileVisitFinder;

/**
 * @spring.bean name="/pers/profileSettings.do"
 */
public class ProfileSettingsAction extends AbstractCommandAction
{
	public static final String REQUIRES_AUTHENTICATION = "";
	protected UserFinder userFinder; 
	protected CommunityEraContextManager contextManager; 
	protected LocationFinder locationFinder;
	protected ContactFinder contactFinder;
	protected ProfileVisitFinder profileVisitFinder;
	protected DashBoardAlertFinder dashBoardAlertFinder;
	protected CountryFinder countryFinder;
	protected UserPrivacyFinder userPrivacyFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		CommunityEraContext context = contextManager.getContext();
		User currentUser = context.getCurrentUser();

		Command cmd = (Command)data; 
		if (cmd.getId() == 0 && currentUser != null) {
			cmd.setId(currentUser.getId());
		}
		User usr = null;

		if (cmd.getProfileName() != null && !"".equals(cmd.getProfileName())) {
			try {
				usr = userFinder.getUserForProfileName(cmd.getProfileName());
			} catch (ElementNotFoundException e) {
				return new ModelAndView("/pageNotFound");
			}
		} else {
			try {
				usr = userFinder.getUserEntity(cmd.getId());
			} catch (ElementNotFoundException e) {
				return new ModelAndView("/pageNotFound");
			}
		}

		if (!usr.isValidated()) {
			return new ModelAndView("/pageNotFound");
		} else {
			cmd.setQueryText(usr.getFullName());
			cmd.setSearchType("People");
		}

		int profileVisitCount = profileVisitFinder.getUnseenProfileVisitCountForCurrentUser(usr.getId());
		cmd.setProfileVisitCount(profileVisitCount);
		cmd.copyPropertiesFrom(usr);
		try {
			Location location = locationFinder.getLocationByUserId(usr.getId());
			cmd.setLocation(location.getAddress());
		} catch (ElementNotFoundException e) {
		}

		/*if (usr.getDateOfBirth() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
			cmd.setDob(sdf.format(usr.getDateOfBirth()));
		}*/
		cmd.setUser(usr);

		if (!usr.isInactive()) {
			cmd.setExpertiseBeans(usr.getExpertiseBeans());
		}

		cmd.setReturnString(getConnectionInfo(currentUser, usr));

		// profile visiting...
		if (currentUser != null && usr.getId() != currentUser.getId()) {
			ProfileVisit profileVisit = null;
			try {
				profileVisit = profileVisitFinder.getProfileVisit(usr.getId(), currentUser.getId());
				if (profileVisit.getStatus() == 1) {
					profileVisit.setStatus(0);
					profileVisit.setVisitingCounter(1);
				} else {
					profileVisit.setVisitingCounter(profileVisit.getVisitingCounter() + 1);
				}
			} catch (ElementNotFoundException e) {
				profileVisit = profileVisitFinder.newProfileVisit();
				profileVisit.setProfileUserId(usr.getId());
				profileVisit.setVisitingUserId(currentUser.getId());
				profileVisit.setStatus(0);
				profileVisit.setVisitingCounter(1);
			}
			profileVisit.update();

			// update the dash board for recipients of this message
			DashBoardAlert dashBoardAlert;
			try {
				dashBoardAlert = dashBoardAlertFinder.getDashBoardAlertForUserId(usr.getId());
			} catch (ElementNotFoundException e) {
				dashBoardAlert = dashBoardAlertFinder.newDashBoardAlert();
				dashBoardAlert.setUserId(usr.getId());
				dashBoardAlert.setProfileVisitCount(0);
			}
			if (profileVisit.getVisitingCounter() == 1) {
				dashBoardAlert.setProfileVisitCount(dashBoardAlert.getProfileVisitCount() + 1);
			}
			dashBoardAlert.update();
		}

		cmd.setCountryList(stringToOptionList(countryFinder.getAllCountry()));
		/*try {
			Country country = countryFinder.getCountryForId(usr.getCuntryCodeId());
			cmd.setMobPhoneCode(country.getPhoneCountryCode());
			cmd.setPhoneCode(usr.getCuntryCodeId());
			cmd.setCuntryCodeId(usr.getCuntryCodeId());
		} catch (ElementNotFoundException e) {
		}*/

		UserPrivacy userPrivacy;
		try {
			userPrivacy = userPrivacyFinder.getUserPrivacyForUserId(usr.getId());
		} catch (Exception e) {
			userPrivacy = userPrivacyFinder.newUserPrivacy();
			userPrivacy.setUserId(usr.getId());
			userPrivacy.update();
		}
		cmd.setUserPrivacy(userPrivacy);
		return new ModelAndView("/pers/profileSettings");
	}

	private List stringToOptionList(List items)
	{
		List<Option> options = new ArrayList<Option>(items.size());
		for (int n=0; n<items.size(); n++) {
			Country lc = (Country)items.get(n);
			Option opt = new Option();
			opt.setLabel(lc.getCountryName() + " (" + lc.getPhoneCountryCode() + ")");
			opt.setValue(lc.getId());
			options.add(opt);
		}
		return options;
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
					returnString = returnString + "<a class='btnmain normalTip' onclick='sendMessageFromInfo("+profileUser.getId()+", \""+profileUser.getFullName()+"\", \""+profileUser.isPhotoPresent()+"\");' href='javascript:void(0);'" +
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
					returnString = returnString + "<a class='btnmain normalTip' onclick='sendMessageFromInfo("+profileUser.getId()+", \""+profileUser.getFullName()+"\", \""+profileUser.isPhotoPresent()+"\");' href='javascript:void(0);'" +
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

	public class Command extends UserDto implements CommandBean
	{
		private List expertiseBeans;
		private List communities = new ArrayList();
		private List connections = new ArrayList();
		private int communitiesSize;
		private int connectionSize;
		private User user;
		private boolean isBlogOwner;
		private String returnString = "";
		private List countryList = new ArrayList();
		private List relationshipList = new ArrayList();
		private String dob;
		private String location;
		private int phoneCode;
		private String mobPhoneCode;
		private int profileVisitCount;
		private UserPrivacy userPrivacy;

		public boolean isBlogOwner()
		{
			return isBlogOwner;
		}

		public void setBlogOwner(boolean isBlogOwner)
		{
			this.isBlogOwner = isBlogOwner;
		}

		public final List getCommunities()
		{
			return communities;
		}
		public final void setCommunities(List communities)
		{
			this.communities = communities;
		}
		public final List getExpertiseBeans()
		{
			return expertiseBeans;
		}
		public final void setExpertiseBeans(List expertiseBeans)
		{
			this.expertiseBeans = expertiseBeans;
		}

		public String getFormattedAbout()
		{
			return StringHelper.formatForDisplay(getAbout());
		}

		public boolean isContactionAllowed() throws Exception
		{
			User current=contextManager.getContext().getCurrentUser();

			if (current == null) return false;

			if (current.getId()==this.getId()) return false;

			return true;
		}

		public boolean isContactOfCurrentUser() throws Exception
		{
			User current=contextManager.getContext().getCurrentUser();

			if (current == null) return false;

			if (current.getId()==this.getId()) return false;

			try {
				Contact con = contactFinder.getContact(current.getId(), this.getId());
				if (con.getStatus() == 1) {
					return true;
				}
			} catch (ElementNotFoundException e) {
				return false;
			}
			return false;
		}

		public User getUser()
		{
			return user;
		}

		public void setUser(User user)
		{
			this.user = user;
		}

		/**
		 * @return the connections
		 */
		public List getConnections() {
			return connections;
		}

		/**
		 * @param connections the connections to set
		 */
		public void setConnections(List connections) {
			this.connections = connections;
		}

		/**
		 * @return the connectionSize
		 */
		public int getConnectionSize() {
			return connectionSize;
		}

		/**
		 * @param connectionSize the connectionSize to set
		 */
		public void setConnectionSize(int connectionSize) {
			this.connectionSize = connectionSize;
		}

		/**
		 * @return the communitiesSize
		 */
		public int getCommunitiesSize() {
			return communitiesSize;
		}

		/**
		 * @param communitiesSize the communitiesSize to set
		 */
		public void setCommunitiesSize(int communitiesSize) {
			this.communitiesSize = communitiesSize;
		}

		public String getReturnString() {
			return returnString;
		}

		public void setReturnString(String returnString) {
			this.returnString = returnString;
		}

		public List getCountryList() {
			return countryList;
		}

		public void setCountryList(List countryList) {
			this.countryList = countryList;
		}

		public String getDob() {
			return dob;
		}

		public void setDob(String dob) {
			this.dob = dob;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public int getPhoneCode() {
			return phoneCode;
		}

		public void setPhoneCode(int phoneCode) {
			this.phoneCode = phoneCode;
		}

		public String getMobPhoneCode() {
			return mobPhoneCode;
		}

		public void setMobPhoneCode(String mobPhoneCode) {
			this.mobPhoneCode = mobPhoneCode;
		}

		public int getProfileVisitCount() {
			return profileVisitCount;
		}

		public void setProfileVisitCount(int profileVisitCount) {
			this.profileVisitCount = profileVisitCount;
		}

		public UserPrivacy getUserPrivacy() {
			return userPrivacy;
		}

		public void setUserPrivacy(UserPrivacy userPrivacy) {
			this.userPrivacy = userPrivacy;
		}

		public List getRelationshipList() {
			List<Option> options = new ArrayList<Option>(8);

			Option opt = new Option();
			opt.setValue(0);
			opt.setLabel(" ");
			options.add(opt);

			Option opt1 = new Option();
			opt1.setValue(1);
			opt1.setLabel("Single");
			options.add(opt1);

			Option opt2 = new Option();
			opt2.setValue(2);
			opt2.setLabel("In a relationship");
			options.add(opt2);

			Option opt3 = new Option();
			opt3.setValue(3);
			opt3.setLabel("Engaged");
			options.add(opt3);

			Option opt4 = new Option();
			opt4.setValue(4);
			opt4.setLabel("Married");
			options.add(opt4);

			Option opt5 = new Option();
			opt5.setValue(5);
			opt5.setLabel("Separated");
			options.add(opt5);

			Option opt6 = new Option();
			opt6.setValue(6);
			opt6.setLabel("Divorced");
			options.add(opt6);

			Option opt7 = new Option();
			opt7.setValue(7);
			opt7.setLabel("Widowed");
			options.add(opt7);

			return options;
		}

		/*public String getRelationshipDisplay() {
			String relation = " ";
			if (getRelationship() == 1) {
				relation = "Single";
			} else if (getRelationship() == 2) {
				relation = "In a relationship";
			} else if (getRelationship() == 3) {
				relation = "Engaged";
			} else if (getRelationship() == 4) {
				relation = "Married";
			} else if (getRelationship() == 5) {
				relation = "Separated";
			} else if (getRelationship() == 6) {
				relation = "Divorced";
			} else if (getRelationship() == 7) {
				relation = "Widowed";
			}
			return relation;
		}*/

		public void setRelationshipList(List relationshipList) {
			this.relationshipList = relationshipList;
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

	public final void setContactFinder(ContactFinder contactFinder)
	{
		this.contactFinder = contactFinder;
	}

	public class RowBean extends CommunityDto implements EntityWrapper
	{
		private int resultSetIndex;
		private int level = 1;

		// Community entity
		private Community community;

		private boolean member;

		private boolean membershipRequested;

		private Date dateJoined;

		private String role;

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

		/**
		 * @return the dateJoined
		 */
		public Date getDateJoined() {
			return dateJoined;
		}

		/**
		 * @param dateJoined the dateJoined to set
		 */
		public void setDateJoined(Date dateJoined) {
			this.dateJoined = dateJoined;
		}

		/**
		 * @return the role
		 */
		public String getRole() {
			return role;
		}

		/**
		 * @param role the role to set
		 */
		public void setRole(String role) {
			this.role = role;
		}
	}

	public void setProfileVisitFinder(ProfileVisitFinder profileVisitFinder) {
		this.profileVisitFinder = profileVisitFinder;
	}

	public void setDashBoardAlertFinder(DashBoardAlertFinder dashBoardAlertFinder) {
		this.dashBoardAlertFinder = dashBoardAlertFinder;
	}

	public void setLocationFinder(LocationFinder locationFinder) {
		this.locationFinder = locationFinder;
	}

	public void setCountryFinder(CountryFinder countryFinder) {
		this.countryFinder = countryFinder;
	}

	public void setUserPrivacyFinder(UserPrivacyFinder userPrivacyFinder) {
		this.userPrivacyFinder = userPrivacyFinder;
	}
}