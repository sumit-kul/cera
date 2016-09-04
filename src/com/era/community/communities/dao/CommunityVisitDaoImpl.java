package com.era.community.communities.dao; 

public class CommunityVisitDaoImpl extends com.era.community.communities.dao.generated.CommunityVisitDaoBaseImpl implements CommunityVisitDao
{
	public CommunityVisit getCommunityVisit(int communityId, int visitingUserId) throws Exception
	{
		CommunityVisit communityVisit= (CommunityVisit)getEntityWhere(" date(Created)  = date(current_timestamp) and CommunityId = ? and VisitingUserId = ? ", communityId, visitingUserId);
		return communityVisit;
	}

	public void deleteAllCommunityVisitsForCommunity(int communityId) throws Exception
	{
		String sql="delete from CommunityVisit where CommunityId = ?";
		getSimpleJdbcTemplate().update(sql, communityId);       
	}
	
	public void deleteCommunityVisitsForCommunityAndId(int communityId, int communityVisitId) throws Exception
	{
		String sql="delete from communityVisit where CommunityId = ? and ID = ?";
		getSimpleJdbcTemplate().update(sql, communityId, communityVisitId);       
	}
}