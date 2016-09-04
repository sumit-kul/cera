package com.era.community.pers.dao.generated; 

public abstract class ExtraInfoUserEntity extends support.community.framework.CommandBeanImpl
{
	private java.lang.String Modified;
	private java.lang.String Created;
	private java.lang.String About;
	private int Id;
	private int CuntryCodeId;
	private int UserId;
	private java.lang.String DateOfBirth;
	private int Relationship;
	private int Gender;
	private String MobileNumber;
	private String Education;
	private int Occupation1;
	private int Occupation2;
	private int Occupation3;
	private String Company;
	private String Spotlight;
	private String SpotlightUrl;
	
	public final java.lang.String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final java.lang.String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
	public final java.lang.String getAbout() { return About; }
	public final void setAbout(java.lang.String v) {  About = v; }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final java.lang.String getDateOfBirth() { return DateOfBirth; }
	public final void setDateOfBirth(java.util.Date v) { if (v != null) DateOfBirth = v.toString();}
	public final int getRelationship() { return Relationship; }
	public final void setRelationship(int v) {  Relationship = v; }
	public final int getGender() { return Gender; }
	public final void setGender(int v) {  Gender = v; }
	public final int getUserId() { return UserId; }
	public final void setUserId(int v) {  UserId = v; }
	public final int getCuntryCodeId() { return CuntryCodeId; }
	public final void setCuntryCodeId(int v) {  CuntryCodeId = v; }
	public final java.lang.String getMobileNumber() { return MobileNumber; }
	public final void setMobileNumber(java.lang.String v) {  MobileNumber = v; }
	public final java.lang.String getEducation() { return Education; }
	public final void setEducation(java.lang.String v) {  Education = v; }
	public final int getOccupation1() { return Occupation1; }
	public final void setOccupation1(int v) {  Occupation1 = v; }
	public final int getOccupation2() { return Occupation2; }
	public final void setOccupation2(int v) {  Occupation2 = v; }
	public final int getOccupation3() { return Occupation3; }
	public final void setOccupation3(int v) {  Occupation3 = v; }
	public final void setCompany(java.lang.String v) {  Company = v; }
	public final java.lang.String getCompany() { return Company; }
	public final void setSpotlight(java.lang.String v) {  Spotlight = v; }
	public final java.lang.String getSpotlight() { return Spotlight; }
	public final void setSpotlightUrl(java.lang.String v) {  SpotlightUrl = v; }
	public final java.lang.String getSpotlightUrl() { return SpotlightUrl; }
}