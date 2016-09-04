package com.era.community.communities.dao; 

import java.util.List;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.pers.dao.User;

public class CommunityJoiningRequestDaoImpl extends com.era.community.communities.dao.generated.CommunityJoiningRequestDaoBaseImpl implements CommunityJoiningRequestDao
{
	public CommunityJoiningRequest getRequestForUserAndCommunity(int userId, int communityId) throws Exception
	{
		String where = "UserId = ? and CommunityId = ? LIMIT 1 ";
		return (CommunityJoiningRequest)getEntityWhere(where, userId, communityId);
	}

	public List getPendingJoiningRequestForCommunity(int communityId, int max) throws Exception
	{
		String query = "select distinct U.* from User U, CommunityJoiningRequest JR " 
			+ " where U.ID = JR.UserId and U.Inactive = 'N' and JR.Status = 0 and JR.CommunityId = ? ";

		if (max != 0) {
			query += " LIMIT " + max + " ";
		}
		return getBeanList(query, User.class, communityId);
	}

	public int countPendingJoiningRequestForCommunity(int communityId) throws Exception
	{
		String query = "select count(*) from CommunityJoiningRequest JR where JR.Status = 0 and JR.CommunityId = ? ";
		return getSimpleJdbcTemplate().queryForInt(query, communityId);
	}

	public QueryScroller listJoiningRequestForAdmin(User user, String sortBy) throws Exception
	{
		String query = "select JR.ID RequestId ,JR.UserId RequestorId, JR.RequestDate, JR.OptionalComment, CONCAT_WS(' ',U.FirstName,U.LastName) as Requester, C.* "
			+ " from CommunityJoiningRequest JR, Community C, User U, Membership M "
			+ " where JR.CommunityId = C.ID and JR.UserId = U.ID and M.MemberListId = C.ID and JR.Status = 0 "
			+ " and (M.Role = 'Owner' or M.Role = 'Community Admin') and M.UserId = ? and C.Status = " + Community.STATUS_ACTIVE;

		QueryScroller scroller = getQueryScroller(query, null, user.getId());

		if (sortBy != null && sortBy.equalsIgnoreCase("Date Of Request")) {
			scroller.addScrollKey("STEMP.RequestDate", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		} else if (sortBy != null && sortBy.equalsIgnoreCase("Name Of Community")) {
			scroller.addScrollKey("STEMP.Name", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		} else {
			scroller.addScrollKey("STEMP.FirstName", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		}
		return scroller;
	}

	public QueryScroller listMySentJoiningRequest(User user, String sortBy) throws Exception
	{
		String query = "select JR.ID RequestId ,JR.UserId RequestorId, JR.RequestDate, JR.OptionalComment, C.* "
			+ " from CommunityJoiningRequest JR, Community C "
			+ " where JR.CommunityId = C.ID and JR.UserId = ? and JR.Status = 0 "
			+ " and C.Status = " + Community.STATUS_ACTIVE;

		QueryScroller scroller = getQueryScroller(query, null, user.getId());

		if (sortBy != null && sortBy.equalsIgnoreCase("Community Name") ) {
			scroller.addScrollKey("STEMP.Name", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		} else  {
			scroller.addScrollKey("STEMP.RequestDate", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}
		return scroller;
	}
}