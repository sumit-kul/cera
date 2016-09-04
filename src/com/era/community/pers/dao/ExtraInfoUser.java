package com.era.community.pers.dao;

import java.sql.Timestamp;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * 
 * @entity name="ExtraInfoUser"
 */
public class ExtraInfoUser extends CecAbstractEntity
{
	protected int UserId;
	
	protected String About = "";

	protected Timestamp DateOfBirth;

	/**
	 * @column varchar(80) not null with default
	 */
	protected int Relationship;

	protected int Gender;
	/**
	 * @column varchar(80) not null with default
	 */
	protected int CuntryCodeId;
	/**
	 * @column varchar(80) not null with default
	 */
	protected String MobileNumber;
	/**
	 * @column varchar(80) not null with default
	 */
	protected String Education;
	/**
	 * @column varchar(80) not null with default
	 */
	protected int Occupation1;
	protected int Occupation2;
	protected int Occupation3;
	protected String Company;
	
	protected String Spotlight;
	protected String SpotlightUrl;

	/*
	 * Injected dao references.
	 */
	protected ExtraInfoUserDao dao;

	public boolean isReadAllowed(UserEntity user)
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user)
	{
		return true;
	}

	/**
	 * Update or insert the object in the database.
	 */
	public void update() throws Exception
	{
		dao.store(this);
	}

	/**
	 * Delete this in the database
	 */
	public void delete() throws Exception
	{
		dao.delete(this);
	}
	
	public final void setDao(ExtraInfoUserDao dao)
	{
		this.dao = dao;
	}

	public Timestamp getDateOfBirth() {
		return DateOfBirth;
	}

	public void setDateOfBirth(Timestamp dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}

	public int getRelationship() {
		return Relationship;
	}

	public String getRelationshipDisplay() {
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
	}

	public void setRelationship(int relationship) {
		Relationship = relationship;
	}

	public String getMobileNumber() {
		return MobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		MobileNumber = mobileNumber;
	}

	public String getEducation() {
		return Education;
	}

	public void setEducation(String education) {
		Education = education;
	}

	public String getAbout() {
		return About;
	}

	public void setAbout(String about) {
		About = about;
	}

	public int getCuntryCodeId() {
		return CuntryCodeId;
	}

	public void setCuntryCodeId(int cuntryCodeId) {
		CuntryCodeId = cuntryCodeId;
	}

	public int getGender() {
		return Gender;
	}

	public void setGender(int gender) {
		Gender = gender;
	}

	public String getCompany() {
		return Company;
	}

	public void setCompany(String company) {
		Company = company;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public int getOccupation1() {
		return Occupation1;
	}

	public void setOccupation1(int occupation1) {
		Occupation1 = occupation1;
	}

	public int getOccupation2() {
		return Occupation2;
	}

	public void setOccupation2(int occupation2) {
		Occupation2 = occupation2;
	}

	public int getOccupation3() {
		return Occupation3;
	}

	public void setOccupation3(int occupation3) {
		Occupation3 = occupation3;
	}

	public String getSpotlight() {
		return Spotlight;
	}

	public void setSpotlight(String spotlight) {
		Spotlight = spotlight;
	}

	public String getSpotlightUrl() {
		return SpotlightUrl;
	}

	public void setSpotlightUrl(String spotlightUrl) {
		SpotlightUrl = spotlightUrl;
	}
}