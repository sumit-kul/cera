package com.era.community.pers.ui.action;

import java.text.SimpleDateFormat;
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
import com.era.community.connections.communities.dao.MembershipFinder;
import com.era.community.location.dao.Country;
import com.era.community.location.dao.CountryFinder;
import com.era.community.location.dao.Location;
import com.era.community.location.dao.LocationFinder;
import com.era.community.pers.dao.Contact;
import com.era.community.pers.dao.ContactFinder;
import com.era.community.pers.dao.DashBoardAlert;
import com.era.community.pers.dao.DashBoardAlertFinder;
import com.era.community.pers.dao.ExtraInfoUser;
import com.era.community.pers.dao.ExtraInfoUserFinder;
import com.era.community.pers.dao.InterestFinder;
import com.era.community.pers.dao.MasterDataFinder;
import com.era.community.pers.dao.SocialLink;
import com.era.community.pers.dao.SocialLinkFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.dao.UserPrivacy;
import com.era.community.pers.dao.UserPrivacyFinder;
import com.era.community.pers.ui.dto.UserDto;
import com.era.community.profile.dao.ProfileVisit;
import com.era.community.profile.dao.ProfileVisitFinder;

/**
 *  @spring.bean name="/pers/connectionResult.do" 
 */
public class ConnectionResultDisplayAction extends AbstractCommandAction
{

	public static final String REQUIRES_AUTHENTICATION = "";

	private CommunityEraContextManager contextManager; 
	private UserFinder userFinder;
	private LocationFinder locationFinder;
	private ContactFinder contactFinder;
	private UserPrivacyFinder userPrivacyFinder;
	private ProfileVisitFinder profileVisitFinder;
	private DashBoardAlertFinder dashBoardAlertFinder;
	private CountryFinder countryFinder;
	private MembershipFinder membershipFinder;
	private InterestFinder interestFinder;
	private ExtraInfoUserFinder extraInfoUserFinder;
	private MasterDataFinder masterDataFinder;
	private SocialLinkFinder socialLinkFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data;
		CommunityEraContext context = contextManager.getContext();
		User currUser = context.getCurrentUser();
		User user = null;
		if (cmd.getId() > 0) {
			try {
				user = userFinder.getUserForId(cmd.getId());
			} catch (ElementNotFoundException e) {
				return new ModelAndView("/pageNotFound");
			}
		} else if (cmd.getProfileName() != null && !"".equals(cmd.getProfileName())) {
			try {
				user = userFinder.getUserForProfileName(cmd.getProfileName());
				cmd.setId(user.getId());
			} catch (ElementNotFoundException e) {
				return new ModelAndView("/pageNotFound");
			}
		} else if (currUser != null) {
			cmd.setId(currUser.getId());
			user = currUser;
		} 
		if (!user.isValidated()) {
			return new ModelAndView("/pageNotFound");
		} else {
			cmd.setQueryText(user.getFullName());
			cmd.setUser(user);
		}

		ExtraInfoUser extraInfoUser = null;
		boolean isInfoEdit = false;
		try {
			extraInfoUser = extraInfoUserFinder.getExtraInfoForUser(cmd.getId());
			List<Option> occupationList = masterDataFinder.getOccupationList();
			cmd.setGender(extraInfoUser.getGender());
			cmd.setCuntryCodeId(extraInfoUser.getCuntryCodeId());
			cmd.setMobileNumber(extraInfoUser.getMobileNumber());
			cmd.setRelationship(extraInfoUser.getRelationship());
			cmd.setCompany(extraInfoUser.getCompany());
			cmd.setAbout(extraInfoUser.getAbout());
			cmd.setOccupation1(getOccupation(occupationList, extraInfoUser.getOccupation1()));
			cmd.setOccupation2(getOccupation(occupationList, extraInfoUser.getOccupation2()));
			cmd.setOccupation3(getOccupation(occupationList, extraInfoUser.getOccupation3()));
			cmd.setSpotlight(extraInfoUser.getSpotlight());
			cmd.setSpotlightUrl(extraInfoUser.getSpotlightUrl());
			if (extraInfoUser.getDateOfBirth() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
				cmd.setDob(sdf.format(extraInfoUser.getDateOfBirth()));
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
				cmd.setDobToEdit(sdf2.format(extraInfoUser.getDateOfBirth()));
			}
			try {
				Country country = countryFinder.getCountryForId(extraInfoUser.getCuntryCodeId());
				cmd.setMobPhoneCode(country.getPhoneCountryCode());
				cmd.setPhoneCode(extraInfoUser.getCuntryCodeId());
				cmd.setCuntryCodeId(extraInfoUser.getCuntryCodeId());
			} catch (ElementNotFoundException e) {}
		} catch (ElementNotFoundException e) {}
		
		try {
			Location location = locationFinder.getLocationByUserId(cmd.getId());
			cmd.setLocation(location.getAddress());
			cmd.setCity(location.getCity());
			cmd.setCountry(location.getCountry());
		} catch (ElementNotFoundException e) {}

		try {
			List intCatList = interestFinder.getInterestCategoryListForProfileId(user.getId());
			cmd.setIntCatList(intCatList);
		} catch (ElementNotFoundException e) {}
		
		try {
			List<SocialLink> slinks = socialLinkFinder.getSocialLinksByUserId(user.getId());
			cmd.setSocialLinks(slinks);
			cmd.setSociallinkcount(slinks.size());
		} catch (ElementNotFoundException e) {}

		cmd.copyPropertiesFrom(user);
		cmd.setConnectionCount(userFinder.countAllMyContacts(user.getId()));
		cmd.setCommunitiesSize(userFinder.countAllMyCommunities(user.getId()));
		
		List intList = null;
		int commonInterest = 0;
		intList = interestFinder.getInterestListForProfileId(cmd.getId(), currUser!= null ? currUser.getId(): 0);
		commonInterest = interestFinder.countForCommonInterest(cmd.getId(), currUser!= null ? currUser.getId(): 0);
		cmd.setInterestListSize(intList.size());
		cmd.setInterestList(intList);
		cmd.setCommonInterest(commonInterest);
		
		Contact contact = null;
		try {
			contact = contactFinder.getContact(currUser != null ? currUser.getId() : 0, cmd.getId());
			cmd.setReturnString(getConnectionInfo(currUser, user, contact));
		} catch (ElementNotFoundException e) { 
			String returnString = "<a class='buttonSecondary' onclick='addConnection("+user.getId()+", \""+user.getFullName()+"\");' href='javascript:void(0);'" +
			" >Add to my connections</a>";
			cmd.setReturnString(returnString);
		}
		
		//Set userprivacy level
		UserPrivacy userPrivacy = null;
		try {
			userPrivacy = userPrivacyFinder.getUserPrivacyForUserId(user.getId());
		} catch (ElementNotFoundException e) {
			userPrivacy = userPrivacyFinder.newUserPrivacy();
			userPrivacy.setUserId(user.getId());
			userPrivacy.update();
		}

		if (userPrivacy != null) {
			int countMembership = membershipFinder.getCommonMembershipCount(currUser != null ? currUser.getId() : 0, user.getId());
			int value = userPrivacy.getAddress();
			if (value == 0) {
				cmd.setAddressAccess(true);
			} else if (value == 1 && contact != null) {
				cmd.setAddressAccess(true);
			} else if (value == 2 && countMembership > 0) {
				cmd.setAddressAccess(true);
			} 

			value = userPrivacy.getDateOfBirth();
			if (value == 0) {
				cmd.setDateOfBirthAccess(true);
			} else if (value == 1 && contact != null) {
				cmd.setDateOfBirthAccess(true);
			} else if (value == 2 && countMembership > 0) {
				cmd.setDateOfBirthAccess(true);
			} 

			value = userPrivacy.getRelationship();
			if (value == 0) {
				cmd.setRelationshipAccess(true);
			} else if (value == 1 && contact != null) {
				cmd.setRelationshipAccess(true);
			} else if (value == 2 && countMembership > 0) {
				cmd.setRelationshipAccess(true);
			} 

			value = userPrivacy.getMobileNumber();
			if (value == 0) {
				cmd.setMobileNumberAccess(true);
			} else if (value == 1 && contact != null) {
				cmd.setMobileNumberAccess(true);
			} else if (value == 2 && countMembership > 0) {
				cmd.setMobileNumberAccess(true);
			} 

			value = userPrivacy.getEducation();
			if (value == 0) {
				cmd.setEducationAccess(true);
			} else if (value == 1 && contact != null) {
				cmd.setEducationAccess(true);
			} else if (value == 2 && countMembership > 0) {
				cmd.setEducationAccess(true);
			} 

			value = userPrivacy.getOccupation();
			if (value == 0) {
				cmd.setOccupationAccess(true);
			} else if (value == 1 && contact != null) {
				cmd.setOccupationAccess(true);
			} else if (value == 2 && countMembership > 0) {
				cmd.setOccupationAccess(true);
			} 

			value = userPrivacy.getGender();
			if (value == 0) {
				cmd.setGenderAccess(true);
			} else if (value == 1 && contact != null) {
				cmd.setGenderAccess(true);
			} else if (value == 2 && countMembership > 0) {
				cmd.setGenderAccess(true);
			} 
		}
		cmd.setUserPrivacy(userPrivacy);

		if (currUser != null && currUser.getId() == cmd.getId()) {
			cmd.setCountryList(stringToOptionList(countryFinder.getAllCountry()));
			// profile visiting...
			int profileVisitCount = profileVisitFinder.getUnseenProfileVisitCountForCurrentUser(cmd.getId());
			cmd.setProfileVisitCount(profileVisitCount);
		} else if (currUser != null) {
			ProfileVisit profileVisit = null;
			try {
				profileVisit = profileVisitFinder.getProfileVisit(cmd.getId(), currUser.getId());
				if (profileVisit.getStatus() == 1) {
					profileVisit.setStatus(0);
					profileVisit.setVisitingCounter(1);
				} else {
					profileVisit.setVisitingCounter(profileVisit.getVisitingCounter() + 1);
				}
			} catch (ElementNotFoundException e) {
				profileVisit = profileVisitFinder.newProfileVisit();
				profileVisit.setProfileUserId(cmd.getId());
				profileVisit.setVisitingUserId(currUser.getId());
				profileVisit.setStatus(0);
				profileVisit.setVisitingCounter(1);
			}
			profileVisit.update();

			// update the dash board for recipients of this message
			DashBoardAlert dashBoardAlert;
			try {
				dashBoardAlert = dashBoardAlertFinder.getDashBoardAlertForUserId(user.getId());
			} catch (ElementNotFoundException e) {
				dashBoardAlert = dashBoardAlertFinder.newDashBoardAlert();
				dashBoardAlert.setUserId(cmd.getId());
				dashBoardAlert.setProfileVisitCount(0);
			}
			if (profileVisit.getVisitingCounter() == 1) {
				dashBoardAlert.setProfileVisitCount(dashBoardAlert.getProfileVisitCount() + 1);
			}
			dashBoardAlert.update();
		}
		if (currUser != null && currUser.getId() == cmd.getId()) {
			return new ModelAndView("/pers/connectionResult");
		} else {
			return new ModelAndView("/pers/userProfile");
		}
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

	public String getConnectionInfo(User currentuser, User profileUser, Contact contact) throws Exception
	{
		String returnString = "";
		if (currentuser == null || contact == null) {
			return "<a class='buttonSecondary' onclick='addConnection("+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
			" >Add to my connections</a>";
		}
		if (contact.getOwningUserId() == currentuser.getId()) {
			if (contact.getStatus() == 0 || contact.getStatus() == 2 || contact.getStatus() == 3) {
				// connection request sent
				returnString = "" +
				"<a class='buttonSecondary' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+1+", \""+profileUser.getFullName()+"\")' href='javascript:void(0);'" +
				"title='Cancel connection request sent to "+profileUser.getFullName()+"' >Cancel Request</a>";
			} else if (contact.getStatus() == 1) {
				// Already connected
				returnString = "<a class='buttonSecondary' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+1+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);' " +
				"title='Remove "+profileUser.getFullName()+" from you connections' >Disconnect</a>";
				if (contact.getFollowContact() == 1) { //pass 0 for Owner and 1 for Contact
					returnString = returnString + "<a class='buttonSecondary' onclick='stopFollowing("+contact.getId()+", "+1+","+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
					"title='Stop Following'>Stop Following</a>";
				} else {
					returnString = returnString + "<a class='buttonSecondary' onclick='startFollowing("+contact.getId()+", "+1+","+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
					"title='Start Following'>Follow</a>";
				}
				returnString = returnString + "<a class='buttonSecondary' onclick='sendMessageFromInfo("+profileUser.getId()+", \""+profileUser.getFullName()+"\", \""+profileUser.isPhotoPresent()+"\");' href='javascript:void(0);'" +
				"title='Send message to "+profileUser.getFullName()+"'>Send Message</a>";
			} else if (contact.getStatus() == 4) {
				// user has spammed you and you have cancelled the request...
				returnString = "<a class='buttonSecondary' onclick='addConnection("+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
				"title='Add "+profileUser.getFullName()+" to my connections'>Add to my connections</a>";
			}
		} else {
			if (contact.getStatus() == 0) {
				returnString = "<a class='buttonSecondary' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+4+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);' " +
				"title='Confirm connection request from "+profileUser.getFullName()+"'>Confirm Request</a>" +
				//"<a onclick='updateConnection("+this.getId()+", "+contact.getId()+", "+5+", \""+this.getFullName()+"\");' href='javascript:void(0);' " +
				//"title='Hide this request. ("+this.getFullName()+" won't know)' class='search_btn right' style='font-size:12px;'>Not Now</a>";
				"<a class='buttonSecondary' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+1+", \""+profileUser.getFullName()+"\")' href='javascript:void(0);' " +
				"title='Delete connection request from "+profileUser.getFullName()+"'>Delete</a>";
			} else if (contact.getStatus() == 1) {
				// Already connected
				returnString = "<a class='buttonSecondary' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+1+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);' " +
				"title='Remove "+profileUser.getFullName()+" from you connections' >Disconnect</a>";
				if (contact.getFollowOwner() == 1) { //pass 0 for Owner and 1 for Contact
					returnString = returnString + "<a class='buttonSecondary' onclick='stopFollowing("+contact.getId()+", "+0+","+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
					"title='Stop Following'>Stop Following</a>";
				} else {
					returnString = returnString + "<a class='buttonSecondary' onclick='startFollowing("+contact.getId()+", "+0+","+profileUser.getId()+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);'" +
					"title='Start Following'>Follow</a>";
				}
				returnString = returnString + "<a class='buttonSecondary' onclick='sendMessageFromInfo("+profileUser.getId()+", \""+profileUser.getFullName()+"\", \""+profileUser.isPhotoPresent()+"\");' href='javascript:void(0);'" +
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
    				returnString = "<a class='buttonSecondary' onclick='updateConnection("+profileUser.getId()+", "+contact.getId()+", "+3+", \""+profileUser.getFullName()+"\");' href='javascript:void(0);' " +
    				"title='Undo spam to "+profileUser.getFullName()+"'>Undo</a>";
    			}
		}
		return returnString;
	}

	private String getOccupation(List<Option> occupationList, int occupationId) {
		if (occupationList != null && occupationList.size() > 0) {
			for (Option mData : occupationList) {
				Integer sss = (Integer)mData.getValue();
				if (sss.intValue() == occupationId) {
					return mData.getLabel();
				}
			}
		}
		return "";
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
		private String dob;
		private String dobToEdit;
		private String location;
		private String shortLocation;
		private int phoneCode;
		private String mobPhoneCode;
		private int profileVisitCount;

		private boolean addressAccess = false;
		private boolean dateOfBirthAccess = false;
		private boolean relationshipAccess = false;
		private boolean mobileNumberAccess = false;
		private boolean educationAccess = false;
		private boolean occupationAccess = false;
		private boolean genderAccess = false;

		private int interestListSize;
		private List interestList;
		private List intCatList = new ArrayList();
		private int commonInterest;
		private String occupation1;
		private String occupation2;
		private String occupation3;

		private int gender;
		private int cuntryCodeId;
		private String mobileNumber;
		private int relationship;
		private String Company;
		
		private UserPrivacy userPrivacy;

		private String Location;
		private String Country;
		private String Cstate;
		private String City;
		private String Address;
		private String PostalCode;
		
		private String spotlight;
		private String spotlightUrl;
		private List<SocialLink> socialLinks = new ArrayList<SocialLink>();
		private int sociallinkcount;

		public String getGenderTodisplay(){
			if (this.getGender() == 401) {
				return "Female";
			} else if (this.getGender() == 402) {
				return  "Male";
			} else if (this.getGender() == 403) {
				return  "Custom";
			}
			return  "";
		}

		public String getRelationshipDisplay(){
			if (this.getRelationship() == 501) {
				return "Single";
			} else if (this.getRelationship() == 502) {
				return  "In a relationship";
			} else if (this.getRelationship() == 503) {
				return  "Engaged";
			} else if (this.getRelationship() == 504) {
				return  "Married";
			} else if (this.getRelationship() == 505) {
				return  "Separated";
			} else if (this.getRelationship() == 506) {
				return  "Divorced";
			} else if (this.getRelationship() == 507) {
				return  "Widowed";
			}
			return  "";
		}

		public String getBasicInfo(){
			String basicInfo = "";
			if ((this.getOccupation1() != null && !"".equals(this.getOccupation1())) && (this.getOccupation2() != null && !"".equals(this.getOccupation2())) 
					&& (this.getOccupation3() != null && !"".equals(this.getOccupation3()))) {
				basicInfo += this.getOccupation1() + ", " + this.getOccupation2() + " and " + this.getOccupation3();
			} else if ((this.getOccupation1() != null && !"".equals(this.getOccupation1())) && (this.getOccupation2() != null && !"".equals(this.getOccupation2()))) {
				basicInfo += this.getOccupation1() + " and " + this.getOccupation2();
			} else if (this.getOccupation1() != null && !"".equals(this.getOccupation1())) {
				basicInfo += this.getOccupation1();
			}
			if ((this.getOccupation1() != null && !"".equals(this.getOccupation1())) && ((this.getCity() != null && !"".equals(this.getCity())) || 
					(this.getCountry() != null && !"".equals(this.getCountry())))) {
				basicInfo += " in ";
			} 
			if ((this.getCity() != null && !"".equals(this.getCity())) || (this.getCountry() != null && !"".equals(this.getCountry()))) {
				if (this.getCity() != null && !"".equals(this.getCity())) {
					basicInfo += this.getCity();
					if (this.getCountry() != null && !"".equals(this.getCountry())) {
						basicInfo += ", " + this.getCountry();
					}
				} else {
					basicInfo += this.getCountry();
				}
			}
			return basicInfo;
		}

		public String getBioInfo(){
			if (this.getAbout() != null && !"".equals(this.getAbout())) {
				return this.getAbout();
			} else {
				String defaultBio =  "Hey, I'm " + this.getFirstName()+". " ;
				if (((this.getOccupation1()!=null && !"".equals(this.getOccupation1())) || (this.getOccupation2() !=null && !"".equals(this.getOccupation2())) 
						|| (this.getOccupation3() !=null && !"".equals(this.getOccupation3()))) 
						|| (this.getCompany() != null && !"".equals(this.getCompany()))) {
					defaultBio += "I am";
				}
				if ((this.getOccupation1() != null && !"".equals(this.getOccupation1())) || (this.getOccupation2() !=null && !"".equals(this.getOccupation2())) 
						|| (this.getOccupation3() != null && !"".equals(this.getOccupation3()))) {
					defaultBio += " a ";
					if ((this.getOccupation1() != null && !"".equals(this.getOccupation1())) && (this.getOccupation2() != null && !"".equals(this.getOccupation2())) 
							&& (this.getOccupation3() != null && !"".equals(this.getOccupation3()))) {
						defaultBio += this.getOccupation1() + ", " + this.getOccupation2() + " and " + this.getOccupation3();
					} else if ((this.getOccupation1() != null && !"".equals(this.getOccupation1())) && (this.getOccupation2() != null && !"".equals(this.getOccupation2()))) {
						defaultBio += this.getOccupation1() + " and " + this.getOccupation2();
					} else if ((this.getOccupation1() != null && !"".equals(this.getOccupation1()))) {
						defaultBio += this.getOccupation1();
					}

					if (this.getCompany() != null && !"".equals(this.getCompany())) {
						defaultBio += " at " + this.getCompany();
					}
					if ((this.getCity() == null || "".equals(this.getCity())) && (this.getCountry() == null || "".equals(this.getCountry()))) {
						defaultBio += ". ";
					}
				} else {
					if (this.getCompany() != null && !"".equals(this.getCompany())) {
						defaultBio += " working at " + this.getCompany();
						if ((this.getCity() == null || "".equals(this.getCity())) && (this.getCountry() == null || "".equals(this.getCountry()))) {
							defaultBio += ". ";
						}
					}
				}
				if ((this.getCity() != null && !"".equals(this.getCity())) || (this.getCountry() != null && !"".equals(this.getCountry()))) {
					defaultBio += " currently living in ";
					if (this.getCity() != null && !"".equals(this.getCity())) {
						defaultBio += this.getCity();
						if (this.getCountry() != null && !"".equals(this.getCountry())) {
							defaultBio += ", " + this.getCountry() + ". ";
						}
					} else {
						if (this.getCountry() != null && !"".equals(this.getCountry())) {
							defaultBio += this.getCountry()+ ". ";
						}
					}
				}
				if (this.getIntCatList().size() == 1) {
					defaultBio += "I am interested in " + this.getIntCatList().get(0)+ ".";
				}
				if (this.getIntCatList().size() >= 2) {
					defaultBio += "My interests range from " + this.getIntCatList().get(0)+ " to " + this.getIntCatList().get(1)+ ".";
				} 
				if (this.getIntCatList().size() > 2) {
					defaultBio += " I am also interested in " ;
					for (int i = 0; i < this.getIntCatList().size(); i++) {
						if (i<2) {
							continue;
						} 
						defaultBio += this.getIntCatList().get(i);
						if (i < this.getIntCatList().size() -2) {
							defaultBio += ", ";
						} else if (i < this.getIntCatList().size() -1) {
							defaultBio += " and ";
						}
					}
					defaultBio += "." ;
				}
				
				if (this.getSpotlight() != null && !"".equals(this.getSpotlight())) {
					defaultBio += "<br /><br /> You can click the button below to " + this.getSpotlight().toLowerCase() + ".";
					 
				}
				if (this.getSociallinkcount()== 1) {
					SocialLink lnk = this.getSocialLinks().get(0);
					defaultBio += "If you’d like to get in touch, feel free to say hello through my "+lnk.getName()+" link below.";
				}
				if (this.getSociallinkcount()> 1) {
					defaultBio += "If you’d like to get in touch, feel free to say hello through any of the social links below.";
				}
				return defaultBio;
			}
		}

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

		public boolean isAddressAccess() {
			return addressAccess;
		}

		public void setAddressAccess(boolean addressAccess) {
			this.addressAccess = addressAccess;
		}

		public boolean isDateOfBirthAccess() {
			return dateOfBirthAccess;
		}

		public void setDateOfBirthAccess(boolean dateOfBirthAccess) {
			this.dateOfBirthAccess = dateOfBirthAccess;
		}

		public boolean isRelationshipAccess() {
			return relationshipAccess;
		}

		public void setRelationshipAccess(boolean relationshipAccess) {
			this.relationshipAccess = relationshipAccess;
		}

		public boolean isMobileNumberAccess() {
			return mobileNumberAccess;
		}

		public void setMobileNumberAccess(boolean mobileNumberAccess) {
			this.mobileNumberAccess = mobileNumberAccess;
		}

		public boolean isEducationAccess() {
			return educationAccess;
		}

		public void setEducationAccess(boolean educationAccess) {
			this.educationAccess = educationAccess;
		}

		public boolean isOccupationAccess() {
			return occupationAccess;
		}

		public void setOccupationAccess(boolean occupationAccess) {
			this.occupationAccess = occupationAccess;
		}

		public boolean isGenderAccess() {
			return genderAccess;
		}

		public void setGenderAccess(boolean genderAccess) {
			this.genderAccess = genderAccess;
		}

		public String getShortLocation() {
			return shortLocation;
		}

		public void setShortLocation(String shortLocation) {
			this.shortLocation = shortLocation;
		}

		public int getInterestListSize() {
			return interestListSize;
		}

		public void setInterestListSize(int interestListSize) {
			this.interestListSize = interestListSize;
		}

		public List getInterestList() {
			return interestList;
		}

		public void setInterestList(List interestList) {
			this.interestList = interestList;
		}

		public int getCommonInterest() {
			return commonInterest;
		}

		public void setCommonInterest(int commonInterest) {
			this.commonInterest = commonInterest;
		}

		public String getDobToEdit() {
			return dobToEdit;
		}

		public void setDobToEdit(String dobToEdit) {
			this.dobToEdit = dobToEdit;
		}

		public int getGender() {
			return gender;
		}

		public void setGender(int gender) {
			this.gender = gender;
		}

		public int getCuntryCodeId() {
			return cuntryCodeId;
		}

		public void setCuntryCodeId(int cuntryCodeId) {
			this.cuntryCodeId = cuntryCodeId;
		}

		public String getMobileNumber() {
			return mobileNumber;
		}

		public void setMobileNumber(String mobileNumber) {
			this.mobileNumber = mobileNumber;
		}

		public int getRelationship() {
			return relationship;
		}

		public void setRelationship(int relationship) {
			this.relationship = relationship;
		}

		public String getCompany() {
			return Company;
		}

		public void setCompany(String company) {
			Company = company;
		}

		public String getCountry() {
			return Country;
		}

		public void setCountry(String country) {
			Country = country;
		}

		public String getCstate() {
			return Cstate;
		}

		public void setCstate(String cstate) {
			Cstate = cstate;
		}

		public String getCity() {
			return City;
		}

		public void setCity(String city) {
			City = city;
		}

		public String getAddress() {
			return Address;
		}

		public void setAddress(String address) {
			Address = address;
		}

		public String getPostalCode() {
			return PostalCode;
		}

		public void setPostalCode(String postalCode) {
			PostalCode = postalCode;
		}

		public String getOccupation1() {
			return occupation1;
		}

		public void setOccupation1(String occupation1) {
			this.occupation1 = occupation1;
		}

		public String getOccupation2() {
			return occupation2;
		}

		public void setOccupation2(String occupation2) {
			this.occupation2 = occupation2;
		}

		public String getOccupation3() {
			return occupation3;
		}

		public void setOccupation3(String occupation3) {
			this.occupation3 = occupation3;
		}

		public List<String> getIntCatList() {
			return intCatList;
		}

		public String getSpotlight() {
			return spotlight;
		}

		public void setSpotlight(String spotlight) {
			this.spotlight = spotlight;
		}

		public String getSpotlightUrl() {
			return spotlightUrl;
		}

		public void setSpotlightUrl(String spotlightUrl) {
			this.spotlightUrl = spotlightUrl;
		}

		public void setIntCatList(List intCatList) {
			this.intCatList = intCatList;
		}

		public List<SocialLink> getSocialLinks() {
			return socialLinks;
		}

		public void setSocialLinks(List<SocialLink> socialLinks) {
			this.socialLinks = socialLinks;
		}

		public int getSociallinkcount() {
			return sociallinkcount;
		}

		public void setSociallinkcount(int sociallinkcount) {
			this.sociallinkcount = sociallinkcount;
		}

		public UserPrivacy getUserPrivacy() {
			return userPrivacy;
		}

		public void setUserPrivacy(UserPrivacy userPrivacy) {
			this.userPrivacy = userPrivacy;
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

		public Date getDateJoined() {
			return dateJoined;
		}

		public void setDateJoined(Date dateJoined) {
			this.dateJoined = dateJoined;
		}

		public String getRole() {
			return role;
		}

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

	public void setMembershipFinder(MembershipFinder membershipFinder) {
		this.membershipFinder = membershipFinder;
	}

	public void setInterestFinder(InterestFinder interestFinder) {
		this.interestFinder = interestFinder;
	}

	public void setExtraInfoUserFinder(ExtraInfoUserFinder extraInfoUserFinder) {
		this.extraInfoUserFinder = extraInfoUserFinder;
	}

	public void setMasterDataFinder(MasterDataFinder masterDataFinder) {
		this.masterDataFinder = masterDataFinder;
	}

	public void setSocialLinkFinder(SocialLinkFinder socialLinkFinder) {
		this.socialLinkFinder = socialLinkFinder;
	}
}