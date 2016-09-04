package com.era.community.location.dao.generated; 

import java.math.BigDecimal;

public abstract class LocationEntity extends support.community.framework.CommandBeanImpl
{
	private int Id;
	private String Address;
	private String City;
	private String State;
	private long PostalCode;
	private String Country;
	private BigDecimal Latitude;
	private BigDecimal Longitude;
	private int UserId;
	private java.lang.String Modified;
	private java.lang.String Created;
	public final String getAddress() { return Address; }
	public final void setAddress(String v) {  Address = v; }
	public final String getCity() { return City; }
	public final void setCity(String v) {  City = v; }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final int getUserId() { return UserId; }
	public final void setUserId(int v) {  UserId = v; }
	public final String getState() { return State; }
	public final void setState(String v) {  State = v; }
	public final String getCountry() { return Country; }
	public final void setCountry(String v) {  Country = v; }
	public final BigDecimal getLatitude() { return Latitude; }
	public final void setLatitude(BigDecimal v) {  Latitude = v; }
	public final BigDecimal getLongitude() { return Longitude; }
	public final void setLongitude(BigDecimal v) {  Longitude = v; }
	public final long getPostalCode() { return PostalCode; }
	public final void setPostalCode(long v) {  PostalCode = v; }
	public final java.lang.String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final java.lang.String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
}