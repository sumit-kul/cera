package com.era.community.connections.communities.dao.generated; 

import com.era.community.connections.communities.dao.MemberInvitation;

public interface MemberInvitationDaoBase extends MemberInvitationFinderBase
{
  public void store(MemberInvitation o) throws Exception;
  public void deleteMemberInvitationForId(int id) throws Exception;
  public void delete(MemberInvitation o) throws Exception;
}