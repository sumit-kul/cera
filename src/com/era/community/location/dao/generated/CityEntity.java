package com.era.community.location.dao.generated; 

public abstract class CityEntity extends support.community.framework.CommandBeanImpl
{
	private int CountryId;
	private int RegionId;
	private String CityName;
	private int Active;
	private int Id;
	private int Duplicate;
	private String Latitude;
	private String Longitude;
	public final String getCityName() { return CityName; }
	public final void setCityName(String v) {  CityName = v; }
	public final int getActive() { return Active; }
	public final void setActive(int v) {  Active = v; }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final int getCountryId() { return CountryId; }
	public final void setCountryId(int v) {  CountryId = v; }
	public final int getDuplicate() { return Duplicate; }
	public final void setDuplicate(int v) {  Duplicate = v; }
	public final int getRegionId() { return RegionId; }
	public final void setRegionId(int v) {  RegionId = v; }
	public final String getLatitude() { return Latitude; }
	public final void setLatitude(String v) {  Latitude = v; }
	public final String getLongitude() { return Longitude; }
	public final void setLongitude(String v) {  Longitude = v; }
}