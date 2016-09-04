package com.era.community.jobs.dao; 

public interface ScheduledJobFinder extends com.era.community.jobs.dao.generated.ScheduledJobFinderBase
{
	public ScheduledJob getLastScheduledJobForTask(String taskName) throws Exception;
}