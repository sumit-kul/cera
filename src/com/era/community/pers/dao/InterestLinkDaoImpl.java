package com.era.community.pers.dao; 


public class InterestLinkDaoImpl extends com.era.community.pers.dao.generated.InterestLinkDaoBaseImpl implements InterestLinkDao
{
	public InterestLink getInterestLinkForInterestIdAndProfileId(int interestId, int userId) throws Exception
	{
		String sql="select * from InterestLink where InterestId = ? and ProfileId = ? ";
		return getBean(sql, InterestLink.class, interestId, userId);
	}

	public void clearInterest(int profileId) throws Exception
	{
		String sql="delete from " + getTableName() + " where ProfileId=?";
		getSimpleJdbcTemplate().update(sql, profileId);
	}
}