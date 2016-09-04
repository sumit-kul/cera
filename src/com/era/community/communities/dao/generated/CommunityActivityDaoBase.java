package com.era.community.communities.dao.generated; 

import com.era.community.communities.dao.CommunityActivity;

public interface CommunityActivityDaoBase extends CommunityActivityFinderBase
{
  public void store(CommunityActivity o) throws Exception;
  public void deleteCommunityActivityForId(int id) throws Exception;
  public void delete(CommunityActivity o) throws Exception;
}