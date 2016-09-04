package com.era.community.jobs.dao.generated; 
import com.era.community.jobs.dao.ScheduledJob;

public interface ScheduledJobFinderBase
{
    public ScheduledJob getScheduledJobForId(int id) throws Exception;
    public ScheduledJob newScheduledJob() throws Exception;
}