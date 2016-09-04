package com.era.community.location.dao;

import java.math.BigDecimal;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

public class Location extends CecAbstractEntity
{
	private String Address;
	private String City;
	private String State;
	private long PostalCode;
	private String Country;
	private BigDecimal Latitude;
	private BigDecimal Longitude;
	private int UserId;

	protected LocationDao dao;
	
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

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public long getPostalCode() {
		return PostalCode;
	}

	public void setPostalCode(long postalCode) {
		PostalCode = postalCode;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public BigDecimal getLatitude() {
		return Latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		Latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return Longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		Longitude = longitude;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public void setDao(LocationDao dao) {
		this.dao = dao;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}
}