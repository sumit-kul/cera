package com.era.community.communities.dao.generated; 

import com.era.community.communities.dao.*;

public interface DeletedCommunityDaoBase extends DeletedCommunityFinderBase
{
  public void store(DeletedCommunity o) throws Exception;
  public void deleteDeletedCommunityForId(int id) throws Exception;
  public void delete(DeletedCommunity o) throws Exception;
}