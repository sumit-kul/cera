package com.era.community.pers.dao; 

import java.util.List;

interface InterestLinkDao extends com.era.community.pers.dao.generated.InterestLinkDaoBase, InterestLinkFinder
{
    public void clearInterest(int id) throws Exception;
}