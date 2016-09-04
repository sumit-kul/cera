package com.era.community.connections.communities.dao; 

import java.util.List;

import com.era.community.communities.dao.Community;
import com.era.community.pers.dao.User;

public class MemberListDaoImpl extends com.era.community.connections.communities.dao.generated.MemberListDaoBaseImpl implements MemberListDao
{
    public MemberList getMemberListForCommunity(Community comm) throws Exception
    {
        return (MemberList)getEntityWhere("CommunityId=?", comm.getId() );
    }
    
    public List getMemberListForCommunity(int communityId, int max) throws Exception
	{
		String query = "select distinct U.* from User U, MemberList ML, Membership MS " 
			+ " where U.ID = MS.UserId and U.Inactive = 'N' and ML.Inactive = 'N' and MS.MemberListId = ML.Id and ML.CommunityId = ? ";

		if (max != 0) {
			query += " LIMIT " + max + " ";
		}
		return getBeanList(query, User.class, communityId);
	}
    
}