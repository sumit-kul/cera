package com.era.community.communities.dao;

import com.era.community.pers.dao.User;

public class PublicCommunity extends Community
{
    public String getCommunityType()
    {
        return "Public";
    }

    public boolean isPrivate()
    {
        return false;
    }
    
    public boolean isProtected()
    {
        return false;
    }
    
    public boolean userCanJoinWithoutApproval(User user) throws Exception
    {
        return true;
    }
}