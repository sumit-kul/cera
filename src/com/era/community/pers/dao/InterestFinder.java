package com.era.community.pers.dao; 

import java.util.List;

import support.community.database.QueryScroller;

public interface InterestFinder extends com.era.community.pers.dao.generated.InterestFinderBase
{
    public List getInterestList(int categoryId, int offset, int rows) throws Exception;
    public List searchInterestsForInput(String input) throws Exception;
    public List getInterestListForProfileId(int userId) throws Exception;
    public List getInterestListForProfileId(int profileId, int currentUserId) throws Exception;
    public int countForCommonInterest(int profileId, int currentUserId) throws Exception;
    public QueryScroller getInterestsForProfileId(int profileId, int currentUserId) throws Exception;
    public int countInterestsPerCategory(int categoryId) throws Exception;
    public List getInterestCategoryListForProfileId(int profileId) throws Exception;
}