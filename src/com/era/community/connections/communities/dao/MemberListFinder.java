package com.era.community.connections.communities.dao; 

import java.util.List;

import com.era.community.communities.dao.Community;

public interface MemberListFinder extends com.era.community.connections.communities.dao.generated.MemberListFinderBase
{
    public MemberList getMemberListForCommunity(Community comm) throws Exception;
    
    public List getMemberListForCommunity(int communityId, int max) throws Exception;
}