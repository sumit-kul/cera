package com.era.community.connections.communities.dao; 

import java.util.List;

import com.era.community.pers.dao.User;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

public class MemberInvitationDaoImpl extends com.era.community.connections.communities.dao.generated.MemberInvitationDaoBaseImpl implements MemberInvitationDao
{
    public MemberInvitation getMemberInvitationForUserAndCommunity(int userId, int communityId) throws Exception
    {
        String where = "UserId = ? and CommunityId = ? LIMIT 1 ";
        return (MemberInvitation)getEntityWhere(where, userId, communityId);
    }
    
    public int countMemberInvitationsForCommunity(int communityId) throws Exception
    {
    	String query = "select count(*) from MemberInvitation MI where MI.Status = 0 and MI.CommunityId = ?";
		return getSimpleJdbcTemplate().queryForInt(query, communityId);
    }
    
    public QueryScroller getMemberInvitationsForCommunity(int communityId, String sortBy) throws Exception
    {
    	String query = "select U.*, MI.RequestDate, CONCAT_WS(' ',I.FirstName,I.LastName) as InvitorName from MemberInvitation MI, User U, User I " +
    		" where MI.UserId = U.ID and MI.InvitorId = I.ID and MI.Status = 0 and MI.CommunityId = ?";
    	QueryScroller scroller = getQueryScroller(query, null, communityId);
    	if ("Name".equalsIgnoreCase(sortBy)) {
    		scroller.addScrollKey("STEMP.FirstName", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		} else {
			scroller.addScrollKey("STEMP.RequestDate", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}
		return scroller;
    }
    
    public List getMemberInvitationsForCommunity(int communityId, int max) throws Exception
	{
		String query = "select distinct U.* from MemberInvitation MI, User U, User I " +
    		" where MI.UserId = U.ID and MI.InvitorId = I.ID and MI.Status = 0 and MI.CommunityId = ? order by MI.RequestDate desc ";

		if (max != 0) {
			query += " LIMIT " + max + " ";
		}
		return getBeanList(query, User.class, communityId);
	}
}