package com.era.community.accesslog.dao.generated; 

import com.era.community.accesslog.dao.*;

public interface AccessLogDaoBase extends AccessLogFinderBase
{
  public void store(AccessLog o) throws Exception;
  public void deleteAccessLogForId(int id) throws Exception;
  public void delete(AccessLog o) throws Exception;
}