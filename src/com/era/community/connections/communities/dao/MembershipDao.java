package com.era.community.connections.communities.dao; 

import java.util.Date;
import java.util.List;

import support.community.database.QueryScroller;

interface MembershipDao extends com.era.community.connections.communities.dao.generated.MembershipDaoBase, MembershipFinder
{
    public int getMembershipCount(MemberList mlist) throws Exception;
    public int getMembershipCountAt(MemberList mlist, Date date) throws Exception;
    public Membership getMembershipForUserId(MemberList mlist, int userId) throws Exception;
    
    public QueryScroller listMembersByName(MemberList mlist) throws Exception;
    public QueryScroller listMembersByDateJoined(MemberList mlist) throws Exception;
    public QueryScroller listMembersByRole(MemberList mlist) throws Exception;
    public QueryScroller listMembersByOrganisation(MemberList mlist) throws Exception;
    
    public List getMostRecentMembers(MemberList mlist) throws Exception;
    public List getMostRecentMemberNames(MemberList mlist) throws Exception;
    public List getAdminMembers(MemberList mlist) throws Exception;
    public List getMembersByDateJoined(MemberList mlist) throws Exception;
    public List getAdminByName(MemberList mlist) throws Exception;
}

