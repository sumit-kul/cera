package com.era.community.location.dao.generated; 

public abstract class CountryEntity extends support.community.framework.CommandBeanImpl
{
	private String CountryCode;
	private String CountryName;
	private String PhoneCountryCode;
	private int Active;
	private int Id;
	private int Duplicate;
	public final String getCountryName() { return CountryName; }
	public final void setCountryName(String v) {  CountryName = v; }
	public final String getCountryCode() { return CountryCode; }
	public final void setCountryCode(String v) {  CountryCode = v; }
	public final String getPhoneCountryCode() { return PhoneCountryCode; }
	public final void setPhoneCountryCode(String v) {  PhoneCountryCode = v; }
	public final int getActive() { return Active; }
	public final void setActive(int v) {  Active = v; }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final int getDuplicate() { return Duplicate; }
	public final void setDuplicate(int v) {  Duplicate = v; }
}