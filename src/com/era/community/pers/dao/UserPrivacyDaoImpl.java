package com.era.community.pers.dao;

public class UserPrivacyDaoImpl extends com.era.community.pers.dao.generated.UserPrivacyDaoBaseImpl implements UserPrivacyDao
{
	public UserPrivacy getUserPrivacyForUserId(int userId) throws Exception
	{
		return (UserPrivacy) getEntityWhere("userId = ? ", userId);
	}
}