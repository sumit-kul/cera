package com.era.community.communities.dao.generated; 

import com.era.community.communities.dao.*;

public interface CommunityMembershipDomainDaoBase extends CommunityMembershipDomainFinderBase
{
  public void store(CommunityMembershipDomain o) throws Exception;
  public void deleteCommunityMembershipDomainForId(int id) throws Exception;
  public void delete(CommunityMembershipDomain o) throws Exception;
}