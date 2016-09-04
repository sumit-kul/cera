package com.era.community.assignment.dao;

import com.era.community.base.CecBaseEntity;
import com.era.community.pers.dao.generated.UserEntity;

public class AssignmentAssigneeLink extends CecBaseEntity
{

	/**
	 * @column int 
	 */
	private int UserId;

	/**
	 * @column int 
	 */    
	private int AssignmentId;
	
	
	protected AssignmentAssigneeLinkDao dao;

	public void update() throws Exception
	{
		dao.store(this); 
	}
	
	public void delete() throws Exception
	{
		dao.delete(this);
	}

	public int getAssignmentId()
	{
		return AssignmentId;
	}

	public boolean isReadAllowed(UserEntity user)
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user)
	{
		return true;        
	}

	public int getUserId()
	{
		return UserId;
	}

	public void setUserId(int userId)
	{
		UserId = userId;
	}

	public void setDao(AssignmentAssigneeLinkDao dao) {
		this.dao = dao;
	}

	public void setAssignmentId(int assignmentId) {
		AssignmentId = assignmentId;
	}
}