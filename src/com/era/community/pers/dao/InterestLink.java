package com.era.community.pers.dao;

import com.era.community.base.CecBaseEntity;
import com.era.community.pers.dao.generated.InterestEntity;

public class InterestLink extends CecBaseEntity
{
	private int InterestId;
	private int ProfileId;

	protected InterestLinkDao dao;

	public void update() throws Exception
	{
		dao.store(this); 
	}
	
	public void delete() throws Exception
	{
		dao.delete(this);
	}

	public boolean isReadAllowed(InterestEntity user)
	{
		return true;
	}

	public boolean isWriteAllowed(InterestEntity user)
	{
		return true;        
	}

	public void setDao(InterestLinkDao dao)
	{
		this.dao = dao;
	}

	public int getInterestId() {
		return InterestId;
	}

	public void setInterestId(int interestId) {
		InterestId = interestId;
	}

	public int getProfileId() {
		return ProfileId;
	}

	public void setProfileId(int profileId) {
		ProfileId = profileId;
	}
}