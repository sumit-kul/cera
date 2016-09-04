package com.era.community.pers.dao.generated; 

public abstract class UserEntity extends support.community.framework.CommandBeanImpl
{
	private java.lang.String FirstName;
	private java.lang.String LastName;
	private java.lang.String EmailAddress;
	private java.lang.String Password;
	private java.lang.String DateRegistered;
	private boolean SystemAdministrator;
	private boolean Inactive;
	private boolean Validated;
	private boolean MsgAlert;
	private boolean SuperAdministrator;
	private java.lang.String DateLastVisit;
	private java.lang.String Modified;
	private java.lang.String Created;
	private java.lang.String DateDeactivated;
	private int Id;
	private int ConnectionCount;
	private boolean PhotoPresent;
	private int PhotoLength;
	private String PhotoContentType;
	private boolean CoverPresent;
	private int CoverLength;
	private String CoverContentType;
	private String ProfileName;
	private String FirstKey;
	private String ChangeKey;
	private int Width;
	private int Height;
	
	public final java.lang.String getFirstName() { return FirstName; }
	public final void setFirstName(java.lang.String v) {  FirstName = v; }
	public final java.lang.String getLastName() { return LastName; }
	public final void setLastName(java.lang.String v) {  LastName = v; }
	public final java.lang.String getEmailAddress() { return EmailAddress; }
	public final void setEmailAddress(java.lang.String v) {  EmailAddress = v; }
	public final java.lang.String getPassword() { return Password; }
	public final void setPassword(java.lang.String v) {  Password = v; }
	public final java.lang.String getDateRegistered() { return DateRegistered; }
	public final void setDateRegistered(java.util.Date v) {  DateRegistered = v.toString(); }
	public final boolean getSystemAdministrator() { return SystemAdministrator; }
	public final void setSystemAdministrator(boolean v) {  SystemAdministrator = v; }
	public final boolean getInactive() { return Inactive; }
	public final void setInactive(boolean v) {  Inactive = v; }
	public final boolean getValidated() { return Validated; }
	public final void setValidated(boolean v) {  Validated = v; }
	public final boolean getMsgAlert() { return MsgAlert; }
	public final void setMsgAlert(boolean v) {  MsgAlert = v; }
	public final boolean getSuperAdministrator() { return SuperAdministrator; }
	public final void setSuperAdministrator(boolean v) {  SuperAdministrator = v; }
	public final java.lang.String getDateLastVisit() { return DateLastVisit; }
	public final void setDateLastVisit(java.util.Date v) {  DateLastVisit = v.toString(); }
	public final java.lang.String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final java.lang.String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
	public final java.lang.String getDateDeactivated() { return DateDeactivated; }
	public final void setDateDeactivated(java.util.Date v) {  DateDeactivated = v.toString(); }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final int getConnectionCount() { return ConnectionCount; }
	public final void setConnectionCount(int v) {  ConnectionCount = v; }
	public final boolean getPhotoPresent() { return PhotoPresent; }
	public final void setPhotoPresent (boolean v) {  PhotoPresent = v; }
	public final int getPhotoLength() { return PhotoLength; }
	public final void setPhotoLength (int v) {  PhotoLength = v; }
	public final String getPhotoContentType() { return PhotoContentType; }
	public final void setPhotoContentType (String v) {  PhotoContentType = v; }
	
	public final boolean getCoverPresent() { return CoverPresent; }
	public final void setCoverPresent (boolean v) {  CoverPresent = v; }
	public final int getCoverLength() { return CoverLength; }
	public final void setCoverLength (int v) {  CoverLength = v; }
	public final String getCoverContentType() { return CoverContentType; }
	public final void setCoverContentType (String v) {  CoverContentType = v; }
	public final java.lang.String getProfileName() { return ProfileName; }
	public final void setProfileName(java.lang.String v) {  ProfileName = v; }

	public final java.lang.String getFirstKey() { return FirstKey; }
	public final void setFirstKey(java.lang.String v) {  FirstKey = v; }
	public final java.lang.String getChangeKey() { return ChangeKey; }
	public final void setChangeKey(java.lang.String v) {  ChangeKey = v; }
	public final int getWidth() { return Width; }
	public final void setWidth(int v) {  Width = v; }
	public final int getHeight() { return Height; }
	public final void setHeight(int v) {  Height = v; }
}