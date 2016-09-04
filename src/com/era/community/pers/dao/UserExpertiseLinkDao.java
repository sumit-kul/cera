package com.era.community.pers.dao; 

import java.util.List;

interface UserExpertiseLinkDao extends com.era.community.pers.dao.generated.UserExpertiseLinkDaoBase, UserExpertiseLinkFinder
{
    public List getExpertiseBeansForUserId(int id) throws Exception;
    public int[] getExpertiseIdsForUserId(int id) throws Exception;
    public void clearExpertise(int id) throws Exception;
    
}

