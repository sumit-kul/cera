package com.era.community.location.dao.generated; 

public abstract class RegionEntity extends support.community.framework.CommandBeanImpl
{
	private int CountryId;
	private String RegionName;
	private int Active;
	private int Id;
	private int Duplicate;
	public final String getRegionName() { return RegionName; }
	public final void setRegionName(String v) {  RegionName = v; }
	public final int getActive() { return Active; }
	public final void setActive(int v) {  Active = v; }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final int getCountryId() { return CountryId; }
	public final void setCountryId(int v) {  CountryId = v; }
	public final int getDuplicate() { return Duplicate; }
	public final void setDuplicate(int v) {  Duplicate = v; }
}