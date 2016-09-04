package com.era.community.communities.dao; 

import java.util.List;

interface CommunityMembershipDomainDao extends com.era.community.communities.dao.generated.CommunityMembershipDomainDaoBase, CommunityMembershipDomainFinder
{
    public List getDomainsForCommunity(Community comm) throws Exception;
}

