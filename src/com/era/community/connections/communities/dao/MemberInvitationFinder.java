package com.era.community.connections.communities.dao; 

import java.util.List;

import support.community.database.QueryScroller;

public interface MemberInvitationFinder extends com.era.community.connections.communities.dao.generated.MemberInvitationFinderBase
{
    public MemberInvitation getMemberInvitationForUserAndCommunity(int userId, int communityId) throws Exception;
    public int countMemberInvitationsForCommunity(int communityId) throws Exception;
    public QueryScroller getMemberInvitationsForCommunity(int communityId, String sortBy) throws Exception;
    public List getMemberInvitationsForCommunity(int communityId, int max) throws Exception;
}