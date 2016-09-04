package com.era.community.connections.communities.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import support.community.application.ElementNotFoundException;
import support.community.database.QueryScroller;

import com.era.community.base.CecAbstractEntity;
import com.era.community.communities.dao.CommunityRoleConstants;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * 
 * @entity name="MLST" 
 *
 * @entity.index name="01" unique="no" columns="CommunityId"   
 * 
 * @entity.foreignkey name="01" columns="CommunityId" target="COMM" ondelete="cascade"  
 */
public class MemberList extends CecAbstractEntity 
{
	/**
	 * @column integer not null
	 */
	private int CommunityId;

	/**
	 * @column varchar(150) not null with default
	 */
	protected String Name;

	/**
	 * @column char(1) not null with default
	 */
	protected boolean Inactive = false;



	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		return true;
	}
	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		return isAdminMember(user.getId()); 
	}

	UserFinder userFinder;
	MembershipDao membershipDao;

	/*
	 * Injected dao references.
	 */
	protected MemberListDao dao;     

	/**
	 * Update or insert this entity in the database.
	 */
	public void update() throws Exception
	{
		dao.store(this); 
	}

	public int getMemberCount() throws Exception
	{
		return membershipDao.getMembershipCount(this);
	}

	public int getMemberCountAt(Date date) throws Exception
	{
		return membershipDao.getMembershipCountAt(this, date);
	}

	public void addMember(User user) throws Exception
	{
		addMember(user, CommunityRoleConstants.MEMBER, null);
	}

	public void addMember(User user, User approver) throws Exception
	{
		addMember(user, CommunityRoleConstants.MEMBER, approver);
	}

	public void addMember(User user, String role) throws Exception
	{
		addMember(user, role, null);
	}

	public void addMember(User user, String role, User approver) throws Exception
	{
		if (isMember(user)) return;
		Membership mr = membershipDao.newMembership();
		mr.setMemberListId(getId());
		mr.setUserId(user.getId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);
		mr.setDateJoined(ts);
		mr.setRole(role);
		if (approver!=null) mr.setApproverId(approver.getId());
		mr.update();
	}
	
	public void addMember(int userId, String role, int approverId) throws Exception
	{
		if (isMember(userId)) return;
		Membership mr = membershipDao.newMembership();
		mr.setMemberListId(getId());
		mr.setUserId(userId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);
		mr.setDateJoined(ts);
		mr.setRole(role);
		if (approverId > 0) mr.setApproverId(approverId);
		mr.update();
	}

	public void setMemberRole(User user, String role) throws Exception
	{
		if (!isMember(user)) throw new Exception("User "+user.getEmailAddress()+" is not a member");
		Membership mr = membershipDao.getMembershipForUserId(this, user.getId());
		mr.setRole(role);
		mr.update();
	}


	public void removeMember(User user) throws Exception
	{
		removeMember(user.getId());
	}

	public void removeMember(int userId) throws Exception
	{
		if (!isMember(userId)) return;
		Membership mr = membershipDao.getMembershipForUserId(this, userId);
		mr.delete();

		// Get all the email subscriptions this user has for this community and delete them
	}


	public boolean isMember(User user) throws Exception
	{
		return isMember(user.getId());
	}

	public boolean isMember(int userId) throws Exception
	{
		try {
			membershipDao.getMembershipForUserId(this, userId);
			return true;
		}
		catch (ElementNotFoundException e) {
			return false;
		}
	}

	public boolean isAdminMember(int userId) throws Exception
	{
		try {
			Membership m = membershipDao.getMembershipForUserId(this, userId);
			return (m.getRole().equals(CommunityRoleConstants.ADMIN) || m.getRole().equals(CommunityRoleConstants.OWNER));
		}
		catch (ElementNotFoundException e) {
			return false;
		}
	}
	
	public boolean isCommunityOwner(int userId) throws Exception
	{
		try {
			Membership m = membershipDao.getMembershipForUserId(this, userId);
			return (m.getRole().equals(CommunityRoleConstants.OWNER));
		}
		catch (ElementNotFoundException e) {
			return false;
		}
	}

	public boolean isAdminMember(User user) throws Exception
	{
		return isAdminMember(user.getId());
	}

	public List getAdminMembers() throws Exception
	{
		return membershipDao.getAdminMembers(this);
	}

	public List getRecentMembers() throws Exception
	{
		return membershipDao.getMostRecentMembers(this);
	}
	
	public List getRecentMemberNames() throws Exception
	{
		return membershipDao.getMostRecentMemberNames(this);
	}

	public List getMembersByDateJoined() throws Exception
	{
		return membershipDao.getMembersByDateJoined(this);
	}

	public List getAdminByName() throws Exception
	{
		return membershipDao.getAdminByName(this);
	}

	public QueryScroller listMembersByName() throws Exception
	{
		return membershipDao.listMembersByName(this);
	}

	public QueryScroller listMembersByRole() throws Exception
	{
		return membershipDao.listMembersByRole(this);
	}

	public QueryScroller listMembersByOrganisation() throws Exception
	{
		return membershipDao.listMembersByOrganisation(this);
	}


	public QueryScroller listMembersByDateJoined() throws Exception
	{
		return membershipDao.listMembersByDateJoined(this);
	}


	public final void setDao(MemberListDao dao)
	{
		this.dao = dao;
	}    
	public final void setMembershipDao(MembershipDao membershipDao)
	{
		this.membershipDao = membershipDao;
	}
	public final void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}
	public final int getCommunityId()
	{
		return CommunityId;
	}
	public final void setCommunityId(int communityId)
	{
		CommunityId = communityId;
	}
	public final String getName()
	{
		return Name;
	}
	public final void setName(String name)
	{
		Name = name;
	}
	public final boolean isInactive()
	{
		return Inactive;
	}
	public final void setInactive(boolean inactive)
	{
		Inactive = inactive;
	}
}
