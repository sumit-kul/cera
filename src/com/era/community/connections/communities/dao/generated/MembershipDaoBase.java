package com.era.community.connections.communities.dao.generated; 

import com.era.community.connections.communities.dao.Membership;

public interface MembershipDaoBase extends MembershipFinderBase
{
  public void store(Membership o) throws Exception;
  public void deleteMembershipForId(int id) throws Exception;
  public void delete(Membership o) throws Exception;
}

