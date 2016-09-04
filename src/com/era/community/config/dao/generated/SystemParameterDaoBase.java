package com.era.community.config.dao.generated; 

import com.era.community.config.dao.*;

public interface SystemParameterDaoBase extends SystemParameterFinderBase
{
  public void store(SystemParameter o) throws Exception;
  public void deleteSystemParameterForId(int id) throws Exception;
  public void delete(SystemParameter o) throws Exception;
}