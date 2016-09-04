package com.era.community.pers.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.pers.dao.SocialLink;

public abstract class SocialLinkDaoBaseImpl extends AbstractJdbcDaoSupport implements SocialLinkDaoBase
{
	public void delete(SocialLink o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(SocialLink o) throws Exception
	{
		storeEntity(o);
	}
	
	public SocialLink newSocialLink() throws Exception
	{
		return (SocialLink)newEntity(SocialLink.class);
	}
}