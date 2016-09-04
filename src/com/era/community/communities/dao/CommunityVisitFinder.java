package com.era.community.communities.dao; 


public interface CommunityVisitFinder extends com.era.community.communities.dao.generated.CommunityVisitFinderBase
{
	public CommunityVisit getCommunityVisit(int communityId, int visitingUserId) throws Exception;
	public void deleteAllCommunityVisitsForCommunity(int communityId) throws Exception;
	public void deleteCommunityVisitsForCommunityAndId(int communityId, int communityVisitId) throws Exception;
}