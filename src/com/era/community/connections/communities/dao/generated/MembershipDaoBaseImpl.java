package com.era.community.connections.communities.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.connections.communities.dao.Membership;

public abstract class MembershipDaoBaseImpl extends AbstractJdbcDaoSupport implements MembershipDaoBase
{
	/*
	 *
	 */
	public Membership getMembershipForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (Membership)getEntity(Membership.class, keys);
	}

	/*
	 *
	 */
	public void deleteMembershipForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(Membership o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(Membership o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public Membership newMembership() throws Exception
	{
		return (Membership)newEntity(Membership.class);
	}

}
