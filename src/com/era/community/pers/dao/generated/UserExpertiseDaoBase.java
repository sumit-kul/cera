package com.era.community.pers.dao.generated; 

import com.era.community.pers.dao.UserExpertise;

public interface UserExpertiseDaoBase extends UserExpertiseFinderBase
{
  public void store(UserExpertise o) throws Exception;
  public void deleteUserExpertiseForId(int id) throws Exception;
  public void delete(UserExpertise o) throws Exception;
}

