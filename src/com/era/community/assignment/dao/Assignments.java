package com.era.community.assignment.dao;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import support.community.database.QueryScroller;

import com.era.community.base.CecAbstractEntity;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * 
 * @entity name="ASNMTS" 
 *
 * @entity.index name="01" unique="no" columns="CommunityId"   
 * 
 * @entity.foreignkey name="01" columns="CommunityId" target="COMM" ondelete="cascade"  
 */
public class Assignments extends CecAbstractEntity
{
	/**
	 * @column integer not null
	 */
	protected int CommunityId;

	/**
	 * @column varchar(150) not null with default
	 */
	protected String Name;

	/**
	 * @column char(1) not null with default
	 */
	protected boolean Inactive = false;


	protected AssignmentsDao dao; 
	protected AssignmentDao assignmentDao;
	protected CommunityFinder communityFinder;
	
	public AssignmentTask newAssignmentTask() throws Exception
	{
		if(getCommunityEraContext().getCurrentUser() == null) return null;
		AssignmentTask task = assignmentDao.newAssignmentTask();
		task.setAssignmentsId(this.getId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);
		task.setDatePosted(ts);
		task.setCompletedOn(ts);
		task.setModified(ts);
		task.setLft(0);
		task.setRht(0);
		task.setLvl(0);
		task.setCreatorId(getCommunityEraContext().getCurrentUser().getId());
		return task;
	}

	public AssignmentEntry newAssignmentEntry(int type) throws Exception
	{
		if(getCommunityEraContext().getCurrentUser() == null) return null;
		AssignmentEntry entry = assignmentDao.newAssignmentEntry();
		entry.setAssignmentsId(this.getId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);
		entry.setDatePosted(ts);
		entry.setCompletedOn(ts);
		entry.setModified(ts);
		entry.setLft(0);
		entry.setRht(0);
		entry.setLvl(0);
		entry.setCreatorId(getCommunityEraContext().getCurrentUser().getId());
		return entry;
	}

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public final void setDao(AssignmentsDao dao)
	{
		this.dao = dao;
	} 

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		if (!getCommunity().isPrivate()) return true;
		if (user==null) return false;
		return getCommunity().isMember(user.getId());
	}    

	public Community getCommunity() throws Exception
	{
		return communityFinder.getCommunityForId(getCommunityId());
	}

	public Date getLatestPostDate() throws Exception
	{
		return dao.getLatestPostDate(this);
	}
	
	public QueryScroller listAssignments(String sortBy) throws Exception
	{
		return dao.listAssignmentsForCommunity(this, sortBy);
	}

	public final int getCommunityId()
	{
		return CommunityId;
	}
	
	public final void setCommunityId(int communityId)
	{
		CommunityId = communityId;
	}
	
	public final boolean isInactive()
	{
		return Inactive;
	}
	
	public final void setInactive(boolean inactive)
	{
		Inactive = inactive;
	}
	
	public final String getName()
	{
		return Name;
	}
	
	public final void setName(String name)
	{
		Name = name;
	}

	public void setCommunityFinder(CommunityFinder communityFinder) {
		this.communityFinder = communityFinder;
	}

	public void setAssignmentDao(AssignmentDao assignmentDao) {
		this.assignmentDao = assignmentDao;
	}
}