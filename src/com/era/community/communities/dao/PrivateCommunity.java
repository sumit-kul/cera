package com.era.community.communities.dao;

import com.era.community.pers.dao.User;
import com.era.community.pers.dao.generated.UserEntity;

public class PrivateCommunity extends Community
{
    public String getCommunityType()
    {
        return "Private";
    }

    public boolean isPrivate()
    {
        return true;
    }
    
    public boolean isProtected()
    {
        return false;
    }

    public boolean userCanJoinWithoutApproval(User user) throws Exception
    {
        return false;
    }
    
    public boolean isWriteAllowed(UserEntity user)
    {
        Community comm = getCommunityEraContext().getCurrentCommunity();
        try {
        	return comm.isAdminMember(user.getId());
		} catch (Exception e) {
			return false;
		}
    }
}