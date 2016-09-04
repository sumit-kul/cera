package com.era.community.communities.dao.generated; 
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.PrivateCommunity;
import com.era.community.communities.dao.ProtectedCommunity;
import com.era.community.communities.dao.PublicCommunity;

public interface CommunityFinderBase
{
    public Community getCommunityForId(int id) throws Exception;
    public PrivateCommunity newPrivateCommunity() throws Exception;
    public PublicCommunity newPublicCommunity() throws Exception;
    public ProtectedCommunity newProtectedCommunity() throws Exception;
}