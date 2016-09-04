package com.era.community.profile.dao.generated; 

import com.era.community.profile.dao.ProfileVisit;

public interface ProfileVisitDaoBase extends ProfileVisitFinderBase
{
  public void store(ProfileVisit o) throws Exception;
  public void deleteProfileVisitForId(int id) throws Exception;
  public void delete(ProfileVisit o) throws Exception;
}

