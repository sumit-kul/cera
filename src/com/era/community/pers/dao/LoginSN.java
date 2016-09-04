package com.era.community.pers.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.media.dao.PhotoFinder;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * 
 * @entity name="LoginSN"
 */
public class LoginSN extends CecAbstractEntity
{
	/**
	 * @column varchar(80) not null with default
	 */
	protected String FirstName;

	/**
	 * @column varchar(80) not null with default
	 */
	protected String LastName;
	
	/**
	 * @column varchar(160) not null with default
	 */
	protected String FullName;
	
	/**
	 * @column varchar(160) not null with default
	 */
	protected String Link;

	/**
	 * @column varchar(160) not null with default
	 */
	protected String Gender;
	
	/**
	 * @column varchar(160) not null with default
	 */
	protected String About;
	
	/**
	 * @column varchar(160) not null with default
	 */
	protected String Birthday;
	
	/**
	 * @column varchar(160) not null with default
	 */
	protected String cover;
	
	/**
	 * @column varchar(160) not null with default
	 */
	protected String website;
	
	/**
	 * @column varchar(160) not null with default
	 */
	protected String location;
	
	/**
	 * @column varchar(160) not null with default
	 */
	protected String photoUrl;
		
	/**
	 * @column varchar(120) not null with default
	 */
	protected String EmailAddress;

	/**
	 * @column char(1) not null with default
	 */
	protected boolean Inactive = false;
	
	/**
	 * @column char(1) not null with default
	 */
	protected boolean Login = false;
	
	/**
	 * @column int not null with default 0
	 */
	protected int SnType = 0;
	
	/**
	 * @column int not null with default 0
	 */
	protected String loginId;
	
	/**
	 * @column int not null with default 0
	 */
	protected int UserId;

	/*
	 * Injected dao references.
	 */
	protected LoginSNDao dao;
	protected UserFinder userFinder;
	protected PhotoFinder photoFinder;
	
	public boolean isReadAllowed(UserEntity user)
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user)
	{
		return true;
	}

	/**
	 * Delete this in the database
	 */
	public void delete() throws Exception
	{
		dao.delete(this);
	}
	
	public void update() throws Exception
	{
		dao.store(this);
	}

	public int getSnType() {
		return SnType;
	}

	public void setSnType(int snType) {
		this.SnType = snType;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getFullName() {
		return FullName;
	}

	public void setFullName(String fullName) {
		FullName = fullName;
	}

	public String getEmailAddress() {
		return EmailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		EmailAddress = emailAddress;
	}

	public boolean isInactive() {
		return Inactive;
	}

	public void setInactive(boolean inactive) {
		Inactive = inactive;
	}

	public void setDao(LoginSNDao dao) {
		this.dao = dao;
	}

	public boolean isLogin() {
		return Login;
	}

	public void setLogin(boolean login) {
		Login = login;
	}

	public String getLink() {
		return Link;
	}

	public void setLink(String link) {
		Link = link;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getAbout() {
		return About;
	}

	public void setAbout(String about) {
		About = about;
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

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getBirthday() {
		return Birthday;
	}

	public void setBirthday(String birthday) {
		this.Birthday = birthday;
	}
}