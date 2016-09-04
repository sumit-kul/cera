package com.era.community.jobs.dao;

import java.util.Date;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

public class ScheduledJob extends CecAbstractEntity
{    
	protected String TaskName;
	protected Date Started;
	protected Date Completed;

	protected ScheduledJobDao dao;

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		return true;
	}

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		return true;
	}

	public void setDao(ScheduledJobDao dao) {
		this.dao = dao;
	}

	public String getTaskName() {
		return TaskName;
	}

	public void setTaskName(String taskName) {
		TaskName = taskName;
	}

	public Date getStarted() {
		return Started;
	}

	public void setStarted(Date started) {
		Started = started;
	}

	public Date getCompleted() {
		return Completed;
	}

	public void setCompleted(Date completed) {
		Completed = completed;
	}
}