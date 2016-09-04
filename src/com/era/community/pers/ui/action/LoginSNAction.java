package com.era.community.pers.ui.action;

import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.servlet.ModelAndView;

import support.community.application.ElementNotFoundException;
import support.community.framework.AbstractCommandAction;
import support.community.framework.CommandBean;
import support.community.framework.CommandBeanImpl;

import com.era.community.base.RandomString;
import com.era.community.pers.dao.ExtraInfoUser;
import com.era.community.pers.dao.ExtraInfoUserFinder;
import com.era.community.pers.dao.LoginSN;
import com.era.community.pers.dao.LoginSNFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 * @spring.bean name="/loginSN.ajx"
 */
public class LoginSNAction extends AbstractCommandAction 
{
	private LoginSNFinder loginSNFinder;
	private UserFinder userFinder;
	private ExtraInfoUserFinder extraInfoUserFinder;

	protected ModelAndView handle(Object data) throws Exception
	{
		Command cmd = (Command)data;

		LoginSN loginSN = null;
		User user = null;
		int type = 0;

		try {
			type = Integer.parseInt(cmd.getSnType());
		} catch (NumberFormatException e) {
		}
		
		try {
			loginSN = loginSNFinder.getLoginSNForEmailAddress(cmd.getEmail());
			updateLoginSN(loginSN, cmd);
			user = userFinder.getUserForEmailAddress(cmd.getEmail());
			updateUser(user, cmd);
		} catch (ElementNotFoundException e) {
			try {
				user = userFinder.getUserForEmailAddress(cmd.getEmail());
				updateUser(user, cmd);
			} catch (ElementNotFoundException ex) {
				user = userFinder.newUser();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date now = new Date();
				String dt = sdf.format(now);
				Timestamp ts = Timestamp.valueOf(dt);
				user.setDateRegistered(ts);
				String renKey = RandomString.nextString();
				user.setEmailAddress(cmd.getEmail());
				user.setFirstKey(renKey);
				user.setMsgAlert(true);
				user.setProfileName(RandomString.nextShortString());
				user.setPassword(RandomString.nextShortString());
				user.setValidated(true);
				updateUser(user, cmd);

				if (!"".equals(cmd.getPhotoUrl()) && !"undefined".equalsIgnoreCase(cmd.getPhotoUrl())) {
					if (cmd.getPhotoUrl().startsWith("http://") || cmd.getPhotoUrl().startsWith("https://")) {
						try {
							URL url = new URL(cmd.getPhotoUrl());
							URLConnection conn = url.openConnection();
							conn.setConnectTimeout(5000);
							conn.setReadTimeout(5000);
							conn.connect(); 
							user.storePhoto(conn.getInputStream());
						}
						catch (Exception exp)
						{
							exp.printStackTrace();
						}
					}
				}
			}

			loginSN = loginSNFinder.newLoginSN();
			loginSN.setUserId(user.getId());
			loginSN.setLoginId(cmd.getLoginId());
			loginSN.setSnType(type); // 1 for google & 2 for FB & 3 for Lin
			loginSN.setEmailAddress(cmd.getEmail());
			updateLoginSN(loginSN, cmd);
		}
		return new ModelAndView("redirect:/jlogin.do?j_username="+user.getEmailAddress()+"&j_password="+user.getPassword());
	}

	private void updateLoginSN(LoginSN loginSN, Command cmd) {
		try {
			loginSN.setLogin(true);
			loginSN.setAbout(cmd.getAbout());
			loginSN.setCover(cmd.getCover());
			loginSN.setFirstName(cmd.getFirstName());
			if ("female".equalsIgnoreCase(cmd.getGender())) {
				loginSN.setGender("F");
			} else {
				loginSN.setGender("M");
			}
			//loginSN.setGender(cmd.getGender());
			loginSN.setLastName(cmd.getLastName());
			loginSN.setLink(cmd.getLink());
			loginSN.setLocation(cmd.getLocation());
			loginSN.setPhotoUrl(cmd.getPhotoUrl());
			loginSN.setWebsite(cmd.getWebsite());
			loginSN.setBirthday(cmd.getBirthday());

			loginSN.update();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateUser(User user, Command cmd) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/DD/yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ExtraInfoUser extraInfoUser = null;
		boolean isInfoEdit = false;
		try {
			extraInfoUser = extraInfoUserFinder.getExtraInfoForUser(user.getId());
		} catch (ElementNotFoundException e) {
			extraInfoUser = extraInfoUserFinder.newExtraInfoUser();
			extraInfoUser.setUserId(user.getId());
			extraInfoUser.update();
		}
		
		try {
			if (user.getEmailAddress() == null || "".equals(user.getEmailAddress())) {
				user.setEmailAddress(cmd.getEmail());
			}
			if (user.getFirstName() == null || "".equals(user.getFirstName())) {
				user.setFirstName(cmd.getFirstName());
			}
			if (user.getLastName() == null || "".equals(user.getLastName())) {
				user.setLastName(cmd.getLastName());
			}
			if (extraInfoUser.getGender() > 0) {
				if ("female".equalsIgnoreCase(cmd.getGender())) {
					extraInfoUser.setGender(401);
				} else {
					extraInfoUser.setGender(402);
				}
				isInfoEdit = true;
			}
			if (extraInfoUser.getAbout() == null || "".equals(extraInfoUser.getAbout())) {
				if (!"undefined".equalsIgnoreCase(cmd.getAbout())) {
					extraInfoUser.setAbout(cmd.getAbout());
					isInfoEdit = true;
				}
			}

			if (extraInfoUser.getDateOfBirth() == null && cmd.getBirthday() != null && !"".equals(cmd.getBirthday()) && !"undefined".equals(cmd.getBirthday())) {
				Date dateB = sdf.parse(cmd.getBirthday());
				String dt = formatter.format(dateB);
				Timestamp ts = Timestamp.valueOf(dt);
				extraInfoUser.setDateOfBirth(ts);
				isInfoEdit = true;
			}
			
			if (isInfoEdit) {
				extraInfoUser.update();
			}
			
			user.setValidated(true);
			user.update();

			if (!"".equals(cmd.getPhotoUrl()) && !"undefined".equals(cmd.getPhotoUrl()) && !user.isPhotoPresent()) {
				if (cmd.getPhotoUrl().startsWith("http://") || cmd.getPhotoUrl().startsWith("https://")) {
					try {
						URL url = new URL(cmd.getPhotoUrl());
						URLConnection conn = url.openConnection();
						conn.setConnectTimeout(5000);
						conn.setReadTimeout(5000);
						conn.connect(); 
						user.storePhoto(conn.getInputStream());
					} catch (Exception exp) {
						exp.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getMiddleName(String[] middleName){
		StringBuilder builder = new StringBuilder();
		for (int i = 1; i < middleName.length-1; i++) {
			builder.append(middleName[i] + " ");
		}
		return builder.toString();
	}

	public class Command extends CommandBeanImpl implements CommandBean
	{
		private String name;
		private String firstName;
		private String lastName;
		private String email;
		private String link;
		private String photoUrl;
		private String gender;
		private String about;
		private String birthday;
		private String cover;
		private String website;
		private String location;
		private String loginId;
		private String snType;
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
		public String getLink() {
			return link;
		}
		public void setLink(String link) {
			this.link = link;
		}
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public String getAbout() {
			return about;
		}
		public void setAbout(String about) {
			this.about = about;
		}
		public String getBirthday() {
			return birthday;
		}
		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}
		public String getCover() {
			return cover;
		}
		public void setCover(String cover) {
			this.cover = cover;
		}
		public String getWebsite() {
			return website;
		}
		public void setWebsite(String website) {
			this.website = website;
		}
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		public String getLoginId() {
			return loginId;
		}
		public void setLoginId(String loginId) {
			this.loginId = loginId;
		}
		public String getSnType() {
			return snType;
		}
		public void setSnType(String snType) {
			this.snType = snType;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPhotoUrl() {
			return photoUrl;
		}
		public void setPhotoUrl(String photoUrl) {
			this.photoUrl = photoUrl;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}

	public final UserFinder getUserFinder()
	{
		return userFinder;
	}

	public final void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public void setLoginSNFinder(LoginSNFinder loginSNFinder) {
		this.loginSNFinder = loginSNFinder;
	}

	public void setExtraInfoUserFinder(ExtraInfoUserFinder extraInfoUserFinder) {
		this.extraInfoUserFinder = extraInfoUserFinder;
	}
}