package com.era.community.connections.communities.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.connections.communities.dao.MemberList;

public abstract class MemberListDaoBaseImpl extends AbstractJdbcDaoSupport implements MemberListDaoBase
{
	/*
	 *
	 */
	public MemberList getMemberListForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (MemberList)getEntity(MemberList.class, keys);
	}

	/*
	 *
	 */
	public void deleteMemberListForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	/*
	 *
	 */
	public void delete(MemberList o) throws Exception
	{
		deleteEntity(o);
	}

	/*
	 *
	 */
	public void store(MemberList o) throws Exception
	{
		storeEntity(o);
	}

	/*
	 *
	 */
	public MemberList newMemberList() throws Exception
	{
		return (MemberList)newEntity(MemberList.class);
	}

}
