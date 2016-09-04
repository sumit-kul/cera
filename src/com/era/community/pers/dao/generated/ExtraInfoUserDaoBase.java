package com.era.community.pers.dao.generated; 

import com.era.community.pers.dao.ExtraInfoUser;

public interface ExtraInfoUserDaoBase extends ExtraInfoUserFinderBase
{
  public void store(ExtraInfoUser o) throws Exception;
  public void deleteExtraInfoUserForId(int id) throws Exception;
  public void delete(ExtraInfoUser o) throws Exception;
}