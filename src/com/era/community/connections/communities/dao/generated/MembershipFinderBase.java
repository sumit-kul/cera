package com.era.community.connections.communities.dao.generated; 

import com.era.community.connections.communities.dao.Membership;

public interface MembershipFinderBase
{
    public Membership getMembershipForId(int id) throws Exception;
    public Membership newMembership() throws Exception;
}

