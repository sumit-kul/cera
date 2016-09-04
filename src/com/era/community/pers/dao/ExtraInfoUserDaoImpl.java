package com.era.community.pers.dao;

public class ExtraInfoUserDaoImpl extends com.era.community.pers.dao.generated.ExtraInfoUserDaoBaseImpl implements ExtraInfoUserDao
{
	public ExtraInfoUser getExtraInfoForUser(int userId) throws Exception {
		return (ExtraInfoUser) getEntityWhere("UserID = ? ", userId);
	}
}