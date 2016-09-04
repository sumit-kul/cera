package com.era.community.events.dao.generated; 

import java.math.BigDecimal;

public abstract class EventEntity extends support.community.framework.CommandBeanImpl
{
	private java.lang.String Name;
	private java.lang.String Description;
	private String StartDate;
	private String EndDate;
	private java.lang.String Location;
	private java.lang.String Address;
	private String Country;
	private String Cstate;
	private String Venue;
	private String City;
	private String PostalCode;
	private int PosterId;
	private int Status;
	private java.lang.String Weblink;
	private java.lang.String ContactName;
	private java.lang.String ContactTel;
	private java.lang.String ContactEmail;
	private int CalendarId;
	private String Modified;
	private String Created;
	private int Id;
	private int PhoneCodeId;
	private boolean PhotoPresent;
	private int PhotoLength;
	private String PhotoContentType;
	private int Invited;
	private int MayBe;
	private int Confirmed;
	private int Declined;
	private int HostCount;
	private String Rollup;
	private String Ticket;
	private double Latitude;
	private double Longitude;
	private int EventType;
	private int EventCategory;
	private int Width;
	private int Height;
	
	public final java.lang.String getName() { return Name; }
	public final void setName(java.lang.String v) {  Name = v; }
	public final java.lang.String getDescription() { return Description; }
	public final void setDescription(java.lang.String v) {  Description = v; }
	public final String getStartDate() { return StartDate; }
	public final void setStartDate(java.util.Date v) {  StartDate = v.toString(); }
	public final String getEndDate() { return EndDate; }
	public final void setEndDate(java.util.Date v) {  EndDate = v.toString(); }
	public final java.lang.String getLocation() { return Location; }
	public final void setLocation(java.lang.String v) {  Location = v; }
	public final java.lang.String getAddress() { return Address; }
	public final void setAddress(java.lang.String v) {  Address = v; }
	public final String getCstate() { return Cstate; }
	public final void setCstate(String v) {  Cstate = v; }
	public final String getCountry() { return Country; }
	public final void setCountry(String v) {  Country = v; }
	public final String getCity() { return City; }
	public final void setCity(String v) {  City = v; }
	public final String getPostalCode() { return PostalCode; }
	public final void setPostalCode(String v) {  PostalCode = v; }
	public final String getVenue() { return Venue; }
	public final void setVenue(String v) {  Venue = v; }
	public final int getPosterId() { return PosterId; }
	public final void setPosterId(int v) {  PosterId = v; }
	public final int getStatus() { return Status; }
	public final void setStatus(int v) {  Status = v; }
	public final java.lang.String getWeblink() { return Weblink; }
	public final void setWeblink(java.lang.String v) {  Weblink = v; }
	public final java.lang.String getContactName() { return ContactName; }
	public final void setContactName(java.lang.String v) {  ContactName = v; }
	public final java.lang.String getContactTel() { return ContactTel; }
	public final void setContactTel(java.lang.String v) {  ContactTel = v; }
	public final java.lang.String getContactEmail() { return ContactEmail; }
	public final void setContactEmail(java.lang.String v) {  ContactEmail = v; }
	public final java.lang.String getRollup() { return Rollup; }
	public final void setRollup(java.lang.String v) {  Rollup = v; }
	public final java.lang.String getTicket() { return Ticket; }
	public final void setTicket(java.lang.String v) {  Ticket = v; }
	public final int getCalendarId() { return CalendarId; }
	public final void setCalendarId(int v) {  CalendarId = v; }
	public final String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
	public final int getPhoneCodeId() { return PhoneCodeId; }
	public final void setPhoneCodeId(int v) {  PhoneCodeId = v; }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final boolean getPhotoPresent() { return PhotoPresent; }
	public final void setPhotoPresent (boolean v) {  PhotoPresent = v; }
	public final int getPhotoLength() { return PhotoLength; }
	public final void setPhotoLength (int v) {  PhotoLength = v; }
	public final String getPhotoContentType() { return PhotoContentType; }
	public final void setPhotoContentType (String v) {  PhotoContentType = v; }
	public final int getInvited() { return Invited; }
	public final void setInvited(Integer v) {  Invited = v; }
	public final int getMayBe() { return MayBe; }
	public final void setMayBe(Integer v) {  MayBe = v; }
	public final int getConfirmed() { return Confirmed; }
	public final void setConfirmed(Integer v) {  Confirmed = v; }
	public final int getDeclined() { return Declined; }
	public final void setDeclined(Integer v) {  Declined = v; }
	public final int getHostCount() { return HostCount; }
	public final void setHostCount(Integer v) {  HostCount = v; }
	public final double getLatitude() { return Latitude; }
	public final void setLatitude(BigDecimal v) {  Latitude = v.doubleValue(); }
	public final double getLongitude() { return Longitude; }
	public final void setLongitude(BigDecimal v) {  Longitude = v.doubleValue(); }
	public final int getEventCategory() { return EventCategory; }
	public final void setEventCategory(int v) {  EventCategory = v; }
	public final int getEventType() { return EventType; }
	public final void setEventType(int v) {  EventType = v; }
	public final int getWidth() { return Width; }
	public final void setWidth(int v) {  Width = v; }
	public final int getHeight() { return Height; }
	public final void setHeight(int v) {  Height = v; }
}

