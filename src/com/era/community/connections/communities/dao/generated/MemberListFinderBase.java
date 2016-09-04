package com.era.community.connections.communities.dao.generated; 

import com.era.community.connections.communities.dao.MemberList;

public interface MemberListFinderBase
{
    public MemberList getMemberListForId(int id) throws Exception;
    public MemberList newMemberList() throws Exception;
}

