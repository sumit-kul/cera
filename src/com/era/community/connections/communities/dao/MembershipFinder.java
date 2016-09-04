package com.era.community.connections.communities.dao; 

import java.util.Date;

public interface MembershipFinder extends com.era.community.connections.communities.dao.generated.MembershipFinderBase
{    
    public int getMemberCount(int groupId) throws Exception;
    public int getMemberCountAt(Date date, int groupId) throws Exception;
    public int getMembershipCount(int groupId) throws Exception;
    public Membership getMembershipForUserAndMemberList(int userId, int listId) throws Exception;
    public void deleteAllMembershipsForUser(int userId) throws Exception;
    public int getCommonMembershipCount(int user1, int user2) throws Exception;
 }

