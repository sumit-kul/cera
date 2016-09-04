package com.era.community.pers.ui.action;

import java.io.Writer;
import java.math.BigDecimal;
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
import com.era.community.location.dao.Country;
import com.era.community.location.dao.CountryFinder;
import com.era.community.location.dao.Location;
import com.era.community.location.dao.LocationFinder;
import com.era.community.pers.dao.ExtraInfoUser;
import com.era.community.pers.dao.ExtraInfoUserFinder;
import com.era.community.pers.dao.MasterData;
import com.era.community.pers.dao.MasterDataFinder;
import com.era.community.pers.dao.SocialLink;
import com.era.community.pers.dao.SocialLinkFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.dao.generated.UserEntity;
import com.ibm.json.java.JSONObject;

/**
 *  @spring.bean name="/pers/saveProfileAction.ajx" 
 */
public class SaveProfileAction extends AbstractCommandAction
{
	private CommunityEraContextManager contextManager; 
	private UserFinder userFinder;
	private CountryFinder countryFinder;
	private ExtraInfoUserFinder extraInfoUserFinder;
	private SocialLinkFinder socialLinkFinder;
	private MasterDataFinder masterDataFinder;
	private LocationFinder locationFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		CommunityEraContext context = contextManager.getContext();
		HttpServletResponse resp = contextManager.getContext().getResponse();
		User currentUser = context.getCurrentUser();
		boolean isEdit = false;
		boolean isInfoEdit = false;
		JSONObject json = new JSONObject();
		if (currentUser != null) {
			ExtraInfoUser extraInfoUser = null;
			try {
				extraInfoUser = extraInfoUserFinder.getExtraInfoForUser(currentUser.getId());
			} catch (ElementNotFoundException e) {
				extraInfoUser = extraInfoUserFinder.newExtraInfoUser();
				extraInfoUser.setUserId(currentUser.getId());
				extraInfoUser.update();
			}
			if (cmd.getProfileName() != null && !"".equals(cmd.getProfileName())) {
				currentUser.setProfileName(cmd.getProfileName());
				isEdit = true;
			}
			if (cmd.getFirstName() != null && !"".equals(cmd.getFirstName())) {
				currentUser.setFirstName(cmd.getFirstName());
				isEdit = true;
			}
			if (cmd.getLastName() != null && !"".equals(cmd.getLastName())) {
				currentUser.setLastName(cmd.getLastName());
				isEdit = true;
			}
			if (cmd.getDob() != null && !"".equals(cmd.getDob())) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date dt = sdf.parse(cmd.getDob());
				Timestamp ts = new Timestamp(dt.getTime());
				extraInfoUser.setDateOfBirth(ts);
				isInfoEdit = true;
				
				SimpleDateFormat sdf2 = new SimpleDateFormat("MMM dd, yyyy");
				json.put("dob", sdf2.format(dt));
			}
			
			if (cmd.getGender() > 0) {
				extraInfoUser.setGender(cmd.getGender());
				if (extraInfoUser.getGender() == 401) {
					json.put("gender", "Female");
				} else if (extraInfoUser.getGender() == 402) {
					json.put("gender", "Male");
				} else if (extraInfoUser.getGender() == 403) {
					json.put("gender", "Custom");
				}
				isInfoEdit = true;
			}
			
			if (cmd.getCuntryCodeId() > 0 && cmd.getMobileNumber() != null && !"".equals(cmd.getMobileNumber())) {
				extraInfoUser.setCuntryCodeId(cmd.getCuntryCodeId());
				extraInfoUser.setMobileNumber(cmd.getMobileNumber());
				isInfoEdit = true;
				Country country = countryFinder.getCountryForId(cmd.getCuntryCodeId());
				json.put("mobNumber", country.getPhoneCountryCode() + " - " +cmd.getMobileNumber());
			}
			
			if (cmd.getRelationship() > 0) {
				extraInfoUser.setRelationship(cmd.getRelationship());
				isInfoEdit = true;
				if (extraInfoUser.getRelationship() == 501) {
					json.put("relationship", "Single");
				} else if (extraInfoUser.getRelationship() == 502) {
					json.put("relationship", "In a relationship");
				} else if (extraInfoUser.getRelationship() == 503) {
					json.put("relationship", "Engaged");
				} else if (extraInfoUser.getRelationship() == 504) {
					json.put("relationship", "Married");
				} else if (extraInfoUser.getRelationship() == 505) {
					json.put("relationship", "Separated");
				} else if (extraInfoUser.getRelationship() == 506) {
					json.put("relationship", "Divorced");
				} else if (extraInfoUser.getRelationship() == 507) {
					json.put("relationship", "Widowed");
				}
			}
			
			if (cmd.getAbout() != null && !"".equals(cmd.getAbout())) {
				extraInfoUser.setAbout(cmd.getAbout());
				json.put("about", cmd.getAbout());
				isInfoEdit = true;
			}
			
			String basicInfo = "";
			if (cmd.getOccupation1() > 0 || cmd.getOccupation2() > 0 || cmd.getOccupation3() > 0) {
				extraInfoUser.setOccupation1(0);
				extraInfoUser.setOccupation2(0);
				extraInfoUser.setOccupation3(0);
				String defaultBio = "";
				if (cmd.getOccupation1() > 0 && cmd.getOccupation2() > 0 && cmd.getOccupation3() > 0) {
					extraInfoUser.setOccupation1(cmd.getOccupation1());
					extraInfoUser.setOccupation2(cmd.getOccupation2());
					extraInfoUser.setOccupation3(cmd.getOccupation3());
					MasterData md1 = masterDataFinder.getMasterDataForId(cmd.getOccupation1());
					MasterData md2 = masterDataFinder.getMasterDataForId(cmd.getOccupation2());
					MasterData md3 = masterDataFinder.getMasterDataForId(cmd.getOccupation3());
					defaultBio += md1.getLabel() + ", " + md2.getLabel() + " and " + md3.getLabel();
					basicInfo += md1.getLabel() + ", " + md2.getLabel() + " and " + md3.getLabel();
				} else if (cmd.getOccupation1() > 0 && cmd.getOccupation2() > 0) {
					extraInfoUser.setOccupation1(cmd.getOccupation1());
					extraInfoUser.setOccupation2(cmd.getOccupation2());
					MasterData md1 = masterDataFinder.getMasterDataForId(cmd.getOccupation1());
					MasterData md2 = masterDataFinder.getMasterDataForId(cmd.getOccupation2());
					defaultBio += md1.getLabel() + " and " + md2.getLabel();
					basicInfo += md1.getLabel() + " and " + md2.getLabel();
				} else if (cmd.getOccupation1() > 0) {
					extraInfoUser.setOccupation1(cmd.getOccupation1());
					MasterData md1 = masterDataFinder.getMasterDataForId(cmd.getOccupation1());
					defaultBio += md1.getLabel();
					basicInfo += md1.getLabel();
				}
				json.put("basicInfo", basicInfo);
				isInfoEdit = true;
			}
			
			if (cmd.getCompany() != null && !"".equals(cmd.getCompany())) {
				extraInfoUser.setCompany(cmd.getCompany());
				isInfoEdit = true;
				json.put("company", extraInfoUser.getCompany());
				json.put("basicInfo", basicInfo);
			}
			
			if (cmd.getSpotlight() != null && !"".equals(cmd.getSpotlight())) {
				extraInfoUser.setSpotlight(cmd.getSpotlight());
				extraInfoUser.setSpotlightUrl("undefined".equals(cmd.getSpotlightUrl()) ? "" : cmd.getSpotlightUrl());
				isInfoEdit = true;
				json.put("spotlight", cmd.getSpotlight());
				json.put("spotlightUrl", cmd.getSpotlightUrl());
			}
			
			if (cmd.getSlName() != null && !"".equals(cmd.getSlName())) {
				SocialLink socialLink = null;
				try {
					socialLink = socialLinkFinder.getSocialLinkByNameAndUserId(cmd.getSlName(), currentUser.getId());
					socialLink.setLink(cmd.getSlLink());
				} catch (ElementNotFoundException e) {
					socialLink = socialLinkFinder.newSocialLink();
					socialLink.setName(cmd.getSlName());
					socialLink.setLink(cmd.getSlLink());
					socialLink.setUserId(currentUser.getId());
				}
				socialLink.update();
				json.put("iconname", cmd.getSlName().toLowerCase());
				json.put("link", cmd.getSlLink());
			}
			
			if (cmd.getAddress() != null && !"".equals(cmd.getAddress())) {
				Location location = null;
				try {
					location = locationFinder.getLocationByUserId(currentUser.getId());
				} catch (ElementNotFoundException e) {
					location = locationFinder.newLocation();
					location.setUserId(currentUser.getId());
				}
				location.setAddress(cmd.getAddress());
				location.setState(cmd.getCstate());
				location.setCity(cmd.getCity());
				location.setPostalCode(new Integer(cmd.getPostalCode()));
				location.setLatitude(new BigDecimal(cmd.getLatitude()));
				location.setLongitude(new BigDecimal(cmd.getLongitude()));
				location.update();
				
				json.put("caddress", cmd.getAddress());
			}
			
			if (isInfoEdit) {
				extraInfoUser.update();
			}
			
			if (isEdit) {
				currentUser.update();
			}
			String jsonString = json.serialize();
			resp.setContentType("text/json");
			Writer out = resp.getWriter();
			out.write(jsonString);
			out.close();
		}
		return null;
	}

	public class Command extends UserEntity implements CommandBean
	{
		private String dob;
		private String about;
		private int gender;
		private int cuntryCodeId;
		private String mobileNumber;
		private int relationship;
		private String Company;
		private int occupation1;
		private int occupation2;
		private int occupation3;
		private String spotlight;
		private String spotlightUrl;
		private String slName;
		private String slLink;
		
		private String address;
		private String city;
		private String venue;
		private String cstate;
		private String postalCode;
		private String country;
		private String latitude;
		private String longitude;

		public String getDob() {
			return dob;
		}

		public void setDob(String dob) {
			this.dob = dob;
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

		public int getOccupation1() {
			return occupation1;
		}

		public void setOccupation1(int occupation1) {
			this.occupation1 = occupation1;
		}

		public int getOccupation2() {
			return occupation2;
		}

		public void setOccupation2(int occupation2) {
			this.occupation2 = occupation2;
		}

		public int getOccupation3() {
			return occupation3;
		}

		public void setOccupation3(int occupation3) {
			this.occupation3 = occupation3;
		}

		public String getAbout() {
			return about;
		}

		public void setAbout(String about) {
			this.about = about;
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

		public String getSlName() {
			return slName;
		}

		public void setSlName(String slName) {
			this.slName = slName;
		}

		public String getSlLink() {
			return slLink;
		}

		public void setSlLink(String slLink) {
			this.slLink = slLink;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getVenue() {
			return venue;
		}

		public void setVenue(String venue) {
			this.venue = venue;
		}

		public String getCstate() {
			return cstate;
		}

		public void setCstate(String cstate) {
			this.cstate = cstate;
		}

		public String getPostalCode() {
			return postalCode;
		}

		public void setPostalCode(String postalCode) {
			this.postalCode = postalCode;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getLatitude() {
			return latitude;
		}

		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}

		public String getLongitude() {
			return longitude;
		}

		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}
	}

	public void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public UserFinder getUserFinder() {
		return userFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public void setCountryFinder(CountryFinder countryFinder) {
		this.countryFinder = countryFinder;
	}

	public void setExtraInfoUserFinder(ExtraInfoUserFinder extraInfoUserFinder) {
		this.extraInfoUserFinder = extraInfoUserFinder;
	}

	public void setSocialLinkFinder(SocialLinkFinder socialLinkFinder) {
		this.socialLinkFinder = socialLinkFinder;
	}

	public void setMasterDataFinder(MasterDataFinder masterDataFinder) {
		this.masterDataFinder = masterDataFinder;
	}

	public void setLocationFinder(LocationFinder locationFinder) {
		this.locationFinder = locationFinder;
	}
}