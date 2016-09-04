package com.era.community.communities.dao.generated; 

import support.community.database.*;
import com.era.community.communities.dao.*;

public abstract class CommunityMembershipDomainDaoBaseImpl extends AbstractJdbcDaoSupport implements CommunityMembershipDomainDaoBase
{
	/*
	 *
	 */
	public CommunityMembershipDomain getCommunityMembershipDomainForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (CommunityMembershipDomain)getEntity(CommunityMembershipDomain.class, keys);
	}

	/*
	 *
	 */
	public void deleteCommunityMembershipDomainForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(CommunityMembershipDomain o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(CommunityMembershipDomain o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public CommunityMembershipDomain newCommunityMembershipDomain() throws Exception
	{
		return (CommunityMembershipDomain)newEntity(CommunityMembershipDomain.class);
	}

}
