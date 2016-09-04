package com.era.community.connections.communities.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.connections.communities.dao.MemberInvitation;

public abstract class MemberInvitationDaoBaseImpl extends AbstractJdbcDaoSupport implements MemberInvitationDaoBase
{
	public MemberInvitation getMemberInvitationForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (MemberInvitation)getEntity(MemberInvitation.class, keys);
	}

	public void deleteMemberInvitationForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(MemberInvitation o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(MemberInvitation o) throws Exception
	{
		storeEntity(o);
	}

	public MemberInvitation newMemberInvitation() throws Exception
	{
		return (MemberInvitation)newEntity(MemberInvitation.class);
	}
}