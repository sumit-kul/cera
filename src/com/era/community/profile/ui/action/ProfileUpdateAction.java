package com.era.community.profile.ui.action;

import java.io.Writer;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.EmailValidator;
import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;

import com.era.community.base.CommunityEraContext;
import com.era.community.base.CommunityEraContextManager;
import com.era.community.location.dao.Location;
import com.era.community.location.dao.LocationFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.ui.dto.UserDto;
import com.ibm.json.java.JSONObject;

/**
 * @spring.bean name="/pers/updateProfile.ajx"
 */
public class ProfileUpdateAction extends AbstractCommandAction
{
	protected UserFinder userFinder;
	protected CommunityEraContextManager contextManager; 
	protected LocationFinder locationFinder; 
	protected static EmailValidator emailValidator = EmailValidator.getInstance();

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data; 
		CommunityEraContext context = contextManager.getContext();
		HttpServletResponse resp = contextManager.getContext().getResponse();
		User currUser = context.getCurrentUser();
		JSONObject json = new JSONObject();
		boolean iserror = false;
		if (currUser != null) {
			if ("aboutMe".equalsIgnoreCase(cmd.getSection()) && !"".equals(cmd.getAbout())) {
				//currUser.setAbout(cmd.getAbout());
				json.put("caboutMe", cmd.getAbout().replace( "<br />", "\n"));
			}
			if ("personal".equalsIgnoreCase(cmd.getSection())) {
				try {
					if (!cmd.getProfileName().equalsIgnoreCase(currUser.getProfileName())) {
						userFinder.getUserForProfileName(cmd.getProfileName());
						iserror = true;
						json.put("profileNameError", "<p class='availableFail'>Sorry, the Profile Name that you are looking for is taken.</p>");
					} 
				} catch (ElementNotFoundException e) { 
					currUser.setProfileName(cmd.getProfileName());
				}

				if (cmd.getFirstName() == null || "".equals(cmd.getFirstName())) {
					iserror = true;
					json.put("firstNameError", "<p class='availableFail'>The First Name cannot be blank.</p>");
				} else {
					currUser.setFirstName(cmd.getFirstName());
					currUser.setLastName(cmd.getLastName());
				}

				if (cmd.getDob() != null && !"".equals(cmd.getDob()) && !"undefined".equals(cmd.getDob())) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date dt = sdf.parse(cmd.getDob());
					String ndate = sdf2.format(dt);
					Timestamp ts = Timestamp.valueOf(ndate);
					//currUser.setDateOfBirth(ts);
				}
				//currUser.setRelationship(cmd.getRelationship());				
			} 
			if ("additional".equalsIgnoreCase(cmd.getSection())) {
				Location location;
				try {
					location = locationFinder.getLocationByUserId(currUser.getId());
				} catch (ElementNotFoundException ex) {
					location = locationFinder.newLocation();
				}
				location.setCity(cmd.getCity());
				location.setCountry(cmd.getCountry());
				location.setState(cmd.getState());
				location.setAddress(cmd.getAddress());
				location.setUserId(currUser.getId());

				try {
					if (cmd.getPostalCode() != null && !"".equals(cmd.getPostalCode())) {
						location.setPostalCode(Long.parseLong(cmd.getPostalCode()));
					}
				} catch (Exception e) {
				}

				try {
					if (cmd.getLatlng() != null && !"".equals(cmd.getLatlng())) {
						String ll = cmd.getLatlng();
						ll = ll.replace("(", "");
						ll = ll.replace(")", "").trim();
						String [] sa = ll.split(",");
						BigDecimal lat = BigDecimal.valueOf(Double.parseDouble(sa[0]));
						BigDecimal lon = BigDecimal.valueOf(Double.parseDouble(sa[1]));
						location.setLatitude(lat);
						location.setLongitude(lon);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				location.update();
				/*currUser.setCuntryCodeId(cmd.getCuntryCodeId());
				currUser.setMobileNumber(cmd.getMobileNumber());
				currUser.setWebSiteAddress(cmd.getWebSiteAddress());*/
			}
			
			if ("account".equalsIgnoreCase(cmd.getSection())) {
				try {
					if (cmd.getEmailAddress() == null || cmd.getEmailAddress().equals("")) {
						iserror = true;
						json.put("emailError", "<p class='availableFail'>Please enter Email address</p>");
					} else if (cmd.getEmailAddress().toString()!=null && cmd.getEmailAddress().toString().trim().length()> 120) {
			        	iserror = true;
						json.put("emailError", "<p class='availableFail'>Email address length cannot exceed 120 characters</p>");
			        } else if (cmd.getEmailAddress().toString()!=null && cmd.getEmailAddress().toString().trim().length() <= 120) {
			        	EmailValidator emailValidator = EmailValidator.getInstance();
			        	if (!emailValidator.isValid(cmd.getEmailAddress().toString())) {
			        		iserror = true;
							json.put("emailError", "<p class='availableFail'>Email address is invalid</p>");
						}
			        } else if (!cmd.getEmailAddress().equalsIgnoreCase(currUser.getEmailAddress())) {
						userFinder.getUserForEmailAddress(cmd.getEmailAddress());
						iserror = true;
						json.put("emailError", "<p class='availableFail'>Sorry, Email address " + cmd.getEmailAddress() + " is already registered</p>");
					}
				} catch (ElementNotFoundException e) {
					currUser.setEmailAddress(cmd.getEmailAddress());
				}
				
				if (cmd.getPassword() != null && !cmd.getPassword().equals("")) {
					if (!cmd.getPassword().equals(cmd.getConfirmPassword())){
						iserror = true;
						json.put("passwordError", "<p class='availableFail'>Password and confirmation do not match</p>");
					} else if (cmd.getPassword().toString().length() < 6){
						iserror = true;
						json.put("passwordError", "<p class='availableFail'>Please enter a password of at least 6 characters</p>");
					} else if (cmd.getPassword().toString().length() > 12){
						iserror = true;
						json.put("passwordError", "<p class='availableFail'>Password length cannot exceed 12 characters</p>");
					} else {
						currUser.setPassword(cmd.getPassword());
					}
				}
			}
			if (!iserror) {
				currUser.update();
			} 
			json.put("iserror", iserror);
		}
		String jsonString = json.serialize();
		resp.setContentType("text/json");
		Writer out = resp.getWriter();
		out.write(jsonString);
		out.close();
		return null;
	}

	public class Command extends UserDto implements CommandBean
	{
		private String section;
		private String dob;

		private String address;
		private String city;
		private String state;
		private String postalCode;
		private String country;
		private String latlng;
		private String confirmPassword;

		public String getSection() {
			return section;
		}

		public void setSection(String section) {
			this.section = section;
		}

		public String getDob() {
			return dob;
		}

		public void setDob(String dob) {
			this.dob = dob;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
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

		public String getLatlng() {
			return latlng;
		}

		public void setLatlng(String latlng) {
			this.latlng = latlng;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getConfirmPassword() {
			return confirmPassword;
		}

		public void setConfirmPassword(String confirmPassword) {
			this.confirmPassword = confirmPassword;
		}
	}

	public final void setContextHolder(CommunityEraContextManager contextManager)
	{
		this.contextManager = contextManager;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public void setLocationFinder(LocationFinder locationFinder) {
		this.locationFinder = locationFinder;
	}
}