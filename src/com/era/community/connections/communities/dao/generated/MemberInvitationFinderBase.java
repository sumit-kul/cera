package com.era.community.connections.communities.dao.generated; 
import com.era.community.connections.communities.dao.MemberInvitation;

public interface MemberInvitationFinderBase
{
    public MemberInvitation getMemberInvitationForId(int id) throws Exception;
    public MemberInvitation newMemberInvitation() throws Exception;
}