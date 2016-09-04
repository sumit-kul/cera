package com.era.community.pers.dao.generated; 

public abstract class LoginSNEntity extends support.community.framework.CommandBeanImpl
{
	private java.lang.String FirstName;
	private java.lang.String LastName;
	private java.lang.String FullName;
	private java.lang.String EmailAddress;
	private java.lang.String Link;
	private java.lang.String Gender;
	private java.lang.String About;
	private java.lang.String Cover;
	private java.lang.String Website;
	private java.lang.String Location;
	private java.lang.String PhotoUrl;
	private java.lang.String Birthday;
	
	private boolean Inactive;
	private boolean Login;
	private java.lang.String Modified;
	private java.lang.String Created;
	private int Id;
	private int SnType;
	private String LoginId;
	private int UserId;
	
	public final java.lang.String getFirstName() { return FirstName; }
	public final void setFirstName(java.lang.String v) {  FirstName = v; }
	public final java.lang.String getLastName() { return LastName; }
	public final void setLastName(java.lang.String v) {  LastName = v; }
	public final java.lang.String getFullName() { return FullName; }
	public final void setFullName(java.lang.String v) {  FullName = v; }
	public final java.lang.String getEmailAddress() { return EmailAddress; }
	public final void setEmailAddress(java.lang.String v) {  EmailAddress = v; }
	public final boolean getInactive() { return Inactive; }
	public final void setInactive(boolean v) {  Inactive = v; }
	public final boolean getLogin() { return Login; }
	public final void setLogin(boolean v) {  Login = v; }
	public final java.lang.String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final java.lang.String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final int getSnType() { return SnType; }
	public final void setSnType(int v) {  SnType = v; }
	public final String getLoginId() { return LoginId; }
	public final void setLoginId(String v) {  LoginId = v; }
	public final int getUserId() { return UserId; }
	public final void setUserId(int v) {  UserId = v; }
	public final java.lang.String getLink() { return Link; }
	public final void setLink(java.lang.String v) {  Link = v; }
	public final java.lang.String getGender() { return Gender; }
	public final void setGender(java.lang.String v) {  Gender = v; }
	public final java.lang.String getAbout() { return About; }
	public final void setAbout(java.lang.String v) {  About = v; }
	public final java.lang.String getcover() { return Cover; }
	public final void setcover(java.lang.String v) {  Cover = v; }
	public final java.lang.String getwebsite() { return Website; }
	public final void setwebsite(java.lang.String v) {  Website = v; }
	public final java.lang.String getLocation() { return Location; }
	public final void setLocation(java.lang.String v) {  Location = v; }
	public final java.lang.String getPhotoUrl() { return PhotoUrl; }
	public final void setPhotoUrl(java.lang.String v) {  PhotoUrl = v; }
	public final java.lang.String getBirthday() { return Birthday; }
	public final void setBirthday(java.lang.String v) {  Birthday = v; }
}