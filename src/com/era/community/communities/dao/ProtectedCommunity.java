package com.era.community.communities.dao;

import com.era.community.pers.dao.User;
import com.era.community.pers.dao.generated.UserEntity;

public class ProtectedCommunity extends Community
{
    public String getCommunityType()
    {
        return "Protected";
    }

    public boolean isPrivate()
    {
        return false;
    }
    
    public boolean isProtected()
    {
        return true;
    }
    
    public boolean isRestricted()
    {
        return true;
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