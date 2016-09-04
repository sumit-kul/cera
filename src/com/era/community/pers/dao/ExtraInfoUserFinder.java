package com.era.community.pers.dao; 

public interface ExtraInfoUserFinder extends com.era.community.pers.dao.generated.ExtraInfoUserFinderBase
{
	public ExtraInfoUser getExtraInfoForUser(int userId) throws Exception;
}