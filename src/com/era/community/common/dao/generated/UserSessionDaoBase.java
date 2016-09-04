package com.era.community.common.dao.generated; 

import com.era.community.common.dao.UserSession;

public interface UserSessionDaoBase extends UserSessionFinderBase
{
  public void store(UserSession o) throws Exception;
  public void deleteUserSessionForId(int id) throws Exception;
  public void delete(UserSession o) throws Exception;
}