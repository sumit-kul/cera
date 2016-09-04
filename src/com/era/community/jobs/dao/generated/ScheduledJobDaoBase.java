package com.era.community.jobs.dao.generated; 

import com.era.community.jobs.dao.ScheduledJob;

public interface ScheduledJobDaoBase extends ScheduledJobFinderBase
{
  public void store(ScheduledJob o) throws Exception;
  public void deleteScheduledJobForId(int id) throws Exception;
  public void delete(ScheduledJob o) throws Exception;
}