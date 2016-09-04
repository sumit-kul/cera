package com.era.community.location.dao;

import com.era.community.base.CecBaseEntity;
import com.era.community.pers.dao.generated.UserEntity;

public class City extends CecBaseEntity
{
	private int CountryId;
	private int RegionId;
	private String CityName;
	private String latitude;
	private String longitude;
	private int Active;
	private int Duplicate;

	protected CityDao dao;
	
	public void update() throws Exception
    {
       dao.store(this); 
    }

    public void delete() throws Exception
    {
        dao.delete(this);
    } 

	public boolean isReadAllowed(UserEntity user)
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user)
	{
		return true;        
	}

	public void setDao(CityDao dao)
	{
		this.dao = dao;
	}

	public int getCountryId() {
		return CountryId;
	}

	public void setCountryId(int countryId) {
		CountryId = countryId;
	}

	public int getRegionId() {
		return RegionId;
	}

	public void setRegionId(int regionId) {
		RegionId = regionId;
	}

	public String getCityName() {
		return CityName;
	}

	public void setCityName(String cityName) {
		CityName = cityName;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public int getActive() {
		return Active;
	}

	public void setActive(int active) {
		Active = active;
	}

	public int getDuplicate() {
		return Duplicate;
	}

	public void setDuplicate(int duplicate) {
		Duplicate = duplicate;
	}
}