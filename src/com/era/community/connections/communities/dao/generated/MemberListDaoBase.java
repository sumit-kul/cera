package com.era.community.connections.communities.dao.generated; 

import com.era.community.connections.communities.dao.MemberList;

public interface MemberListDaoBase extends MemberListFinderBase
{
  public void store(MemberList o) throws Exception;
  public void deleteMemberListForId(int id) throws Exception;
  public void delete(MemberList o) throws Exception;
}

