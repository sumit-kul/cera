package com.era.community.pers.dao.generated; 

import com.era.community.pers.dao.UserActivity;

public interface UserActivityDaoBase extends UserActivityFinderBase
{
  public void store(UserActivity o) throws Exception;
  public void deleteUserActivityForId(int id) throws Exception;
  public void delete(UserActivity o) throws Exception;
}