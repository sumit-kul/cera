package com.era.community.communities.dao; 

import java.util.List;

public class CommunityMembershipDomainDaoImpl extends com.era.community.communities.dao.generated.CommunityMembershipDomainDaoBaseImpl implements CommunityMembershipDomainDao
{
    public List getDomainsForCommunity(Community comm) throws Exception
    {
        return getBeanList("select D.* from CommunityMembershipDomain D where D.CommunityId = ? order by 1", CommunityMembershipDomain.class, comm.getId());
    }
    
}

