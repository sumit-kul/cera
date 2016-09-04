package com.era.community.assignment.dao;

import com.era.community.pers.dao.User;

/**
 *
 *  @entity name="ASNMTENT"
 *  @entity.longtext name="Body"
 * 
 */
public class AssignmentEntry extends Assignment
{
	protected Integer TaskId;
	protected User currentUser;
	
	public Integer getTaskId() {
		return TaskId;
	}
	
	public void setTaskId(Integer taskId) {
		TaskId = taskId;
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	@Override
	public AssignmentTask getTask() throws Exception {
		return (AssignmentTask)dao.getAssignmentForId(getTaskId().intValue());
	}
}