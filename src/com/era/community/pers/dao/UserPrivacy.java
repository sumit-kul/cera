package com.era.community.pers.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * 
 * @entity name="UserPrivacy"
 */
public class UserPrivacy extends CecAbstractEntity
{
	protected int Address;
	protected int DateOfBirth;
	protected int Relationship;
	protected int MobileNumber;
	protected int Education;
	protected int Occupation;
	protected int Gender;
	protected int UserId;

	protected UserPrivacyDao dao;

	public boolean isReadAllowed(UserEntity user)
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user)
	{
		if (user == null)
			return false;
		if (user.getId() == getUserId())
			return true;
		return false;
	}

	public void update() throws Exception
	{
		dao.store(this);
	}

	public final void setDao(UserPrivacyDao dao)
	{
		this.dao = dao;
	}
	
	public String getAddressPrivacy() {
		return getFieldValue(getAddress());
	}
	
	public String getDateOfBirthPrivacy() {
		return getFieldValue(getDateOfBirth());
	}
	
	public String getRelationshipPrivacy() {
		return getFieldValue(getRelationship());
	}
	
	public String getMobileNumberPrivacy() {
		return getFieldValue(getMobileNumber());
	}
	
	public String getEducationPrivacy() {
		return getFieldValue(getEducation());
	}
	
	public String getOccupationPrivacy() {
		return getFieldValue(getOccupation());
	}
	
	public String getGenderPrivacy() {
		return getFieldValue(getGender());
	}
	
	private String getFieldValue(int pLevel) {
		String rt = "";
		if (pLevel == 0) {
			rt = "Public";
		} else if (pLevel == 1) {
			rt = "Connections";
		} else if (pLevel == 2) {
			rt = "Community Members";
		} else if (pLevel == 3) {
			rt = "Followers";
		} else if (pLevel == 4) {
			rt = "Only Me";
		} else if (pLevel == 5) {
			rt = "Custom";
		}
		return rt;
	}

	public int getAddress() {
		return Address;
	}

	public void setAddress(int address) {
		Address = address;
	}

	public int getDateOfBirth() {
		return DateOfBirth;
	}

	public void setDateOfBirth(int dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}

	public int getRelationship() {
		return Relationship;
	}

	public void setRelationship(int relationship) {
		Relationship = relationship;
	}

	public int getMobileNumber() {
		return MobileNumber;
	}

	public void setMobileNumber(int mobileNumber) {
		MobileNumber = mobileNumber;
	}

	public int getEducation() {
		return Education;
	}

	public void setEducation(int education) {
		Education = education;
	}

	public int getOccupation() {
		return Occupation;
	}

	public void setOccupation(int occupation) {
		Occupation = occupation;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public int getGender() {
		return Gender;
	}

	public void setGender(int gender) {
		Gender = gender;
	}
}