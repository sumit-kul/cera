package com.era.community.pers.dao; 

import java.util.List;

import support.community.database.QueryScroller;

interface UserDao extends com.era.community.pers.dao.generated.UserDaoBase, UserFinder
{
    public boolean isPhotoPresent(User user) throws Exception;
    public void clearPhoto(User user) throws Exception;
    
    public boolean isCoverPresent(User user) throws Exception;
    public void clearCover(User user) throws Exception;
    
    public boolean isAdminFor(User user, int id) throws Exception;
    
    public QueryScroller listUserSubscriptions(User user) throws Exception;
    public List listSubscriptionsForUser(User user) throws Exception;
    public QueryScroller listUserSubscriptionsForCommunity(User user, int communityId) throws Exception;
    
    public QueryScroller listContacts(User user) throws Exception;
    public QueryScroller listContactsByJob(User user) throws Exception;
    public QueryScroller listContactsByRegion(User user) throws Exception;
    public QueryScroller listContactsByOrg(User user) throws Exception;
    public List listAllContacts(User user) throws Exception;
    public boolean isConnectedWith(User user, int id) throws Exception;
    public void removeFromMemberLists(User user) throws Exception;
    public void removeFromContactLists(User user) throws Exception;
    public String getLocalAuthorityName(User user) throws Exception;

    
    public int getBlogCommentCountSinceLastLogin(User user) throws Exception;
    public int getMembershipCount(User user) throws Exception;
    
    public int countAllMyContacts(int userId) throws Exception;
}

