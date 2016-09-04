package com.era.community.location.dao;

import com.era.community.base.CecBaseEntity;
import com.era.community.pers.dao.generated.UserEntity;

public class Region extends CecBaseEntity
{

	private int CountryId;
	private String RegionName;
	private int Active;
	private int Duplicate;

	protected RegionDao dao;
	
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

	public void setDao(RegionDao dao)
	{
		this.dao = dao;
	}

	public int getCountryId() {
		return CountryId;
	}

	public void setCountryId(int countryId) {
		CountryId = countryId;
	}

	public String getRegionName() {
		return RegionName;
	}

	public void setRegionName(String regionName) {
		RegionName = regionName;
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

	public RegionDao getDao() {
		return dao;
	}

}