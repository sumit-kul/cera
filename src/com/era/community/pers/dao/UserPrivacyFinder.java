package com.era.community.pers.dao; 

public interface UserPrivacyFinder extends com.era.community.pers.dao.generated.UserPrivacyFinderBase
{
	public UserPrivacy getUserPrivacyForUserId(int userId) throws Exception;
}