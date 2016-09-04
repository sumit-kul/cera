package com.era.community.pers.dao; 

public interface InterestLinkFinder extends com.era.community.pers.dao.generated.InterestLinkFinderBase
{      
	public InterestLink getInterestLinkForInterestIdAndProfileId(int interestId, int userId) throws Exception;
}