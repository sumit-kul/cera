package com.era.community.communities.dao.generated; 

import com.era.community.communities.dao.CommunityVisit;

public interface CommunityVisitDaoBase extends CommunityVisitFinderBase
{
  public void store(CommunityVisit o) throws Exception;
  public void deleteCommunityVisitForId(int id) throws Exception;
  public void delete(CommunityVisit o) throws Exception;
}