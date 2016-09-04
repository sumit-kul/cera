package com.era.community.location.dao;

import com.era.community.base.CecBaseEntity;
import com.era.community.pers.dao.generated.UserEntity;

public class Country extends CecBaseEntity
{
	private String CountryCode;
	private String CountryName;
	private String PhoneCountryCode;
	private int Active;
	private int Duplicate;

	protected CountryDao dao;
	
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

	public void setDao(CountryDao dao)
	{
		this.dao = dao;
	}

	public String getCountryCode() {
		return CountryCode;
	}

	public void setCountryCode(String countryCode) {
		CountryCode = countryCode;
	}

	public String getCountryName() {
		return CountryName;
	}

	public void setCountryName(String countryName) {
		CountryName = countryName;
	}

	public String getPhoneCountryCode() {
		return PhoneCountryCode;
	}

	public void setPhoneCountryCode(String phoneCountryCode) {
		PhoneCountryCode = phoneCountryCode;
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