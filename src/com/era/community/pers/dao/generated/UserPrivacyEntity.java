package com.era.community.pers.dao.generated; 

public abstract class UserPrivacyEntity extends support.community.framework.CommandBeanImpl
{
	private int Id;
	private int Address;
	private int DateOfBirth;
	private int Relationship;
	private int MobileNumber;
	private int Education;
	private int Occupation;
	private int Gender;
	private int UserId;
	private java.lang.String Modified;
	private java.lang.String Created;
	public final int getId() {
		return Id;
	}
	public final void setId(int id) {
		Id = id;
	}
	public final int getAddress() {
		return Address;
	}
	public final void setAddress(int address) {
		Address = address;
	}
	public final int getDateOfBirth() {
		return DateOfBirth;
	}
	public final void setDateOfBirth(int dateOfBirth) {
		DateOfBirth = dateOfBirth;
	}
	public final int getRelationship() {
		return Relationship;
	}
	public final void setRelationship(int relationship) {
		Relationship = relationship;
	}
	public final int getMobileNumber() {
		return MobileNumber;
	}
	public final void setMobileNumber(int mobileNumber) {
		MobileNumber = mobileNumber;
	}
	public final int getEducation() {
		return Education;
	}
	public final void setEducation(int education) {
		Education = education;
	}
	public final int getOccupation() {
		return Occupation;
	}
	public final void setOccupation(int occupation) {
		Occupation = occupation;
	}
	public final int getGender() {
		return Gender;
	}
	public final void setGender(int gender) {
		Gender = gender;
	}
	public final int getUserId() {
		return UserId;
	}
	public final void setUserId(int userId) {
		UserId = userId;
	}
	public final java.lang.String getModified() {
		return Modified;
	}
	public final void setModified(java.util.Date modified) {
		Modified = modified.toString();
	}
	public final java.lang.String getCreated() {
		return Created;
	}
	public final void setCreated(java.util.Date created) {
		Created = created.toString();
	}

}