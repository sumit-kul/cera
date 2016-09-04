package com.era.community.jobs.dao; 

public class ScheduledJobDaoImpl extends com.era.community.jobs.dao.generated.ScheduledJobDaoBaseImpl implements ScheduledJobDao
{
	public ScheduledJob getLastScheduledJobForTask(String taskName) throws Exception
    {
        String where = " TaskName = ? order by ID desc LIMIT 1 ";
        return (ScheduledJob)getEntityWhere(where, taskName);
    }
}