package com.era.community.pers.dao; 

import java.util.Date;
import java.util.List;

import com.era.community.pers.ui.dto.UserSearchDto;

import support.community.database.QueryScroller;

public interface UserFinder extends com.era.community.pers.dao.generated.UserFinderBase
{
    public User getUserForEmailAddress(String address) throws Exception;
    public User getUserForProfileName(String profileName) throws Exception;
    public <T> T getUserDataForEmailAddress(String address, Class<T> beanClass) throws Exception;
    
    public QueryScroller listActiveUsersByName() throws Exception;
    public QueryScroller listAllActiveUsersByName(Class beanClass) throws Exception;
    
    public QueryScroller listUsersByNameForAdmin() throws Exception;
    public QueryScroller listUsersByNameForAdmin(Class beanClass) throws Exception;
    
    public QueryScroller listAdministratorsByNameForAdmin() throws Exception;
    public QueryScroller listAdministratorsByNameForAdmin(Class beanClass) throws Exception;
    
    public QueryScroller listAdministratorsByEmailForAdmin() throws Exception;
    public QueryScroller listAdministratorsByEmailForAdmin(Class beanClass) throws Exception;
    
    public QueryScroller listAdministratorsByLastVisitDateForAdmin() throws Exception;
    public QueryScroller listAdministratorsByLastVisitDateForAdmin(Class beanClass) throws Exception;
    
    public QueryScroller listUnvalidatedUsers() throws Exception;
    public QueryScroller listUnvalidatedUsers(Class beanClass) throws Exception;
   
    public QueryScroller listUsersByEmailForAdmin() throws Exception;
    public QueryScroller listUsersByEmailForAdmin(Class beanClass) throws Exception;
    
    public QueryScroller listUsersByDateRegisteredForAdmin() throws Exception;
    public QueryScroller listUsersByDateRegisteredForAdmin(Class beanClass) throws Exception;
    
    public QueryScroller listUsersByLastVisitDateForAdmin() throws Exception;
    public QueryScroller listUsersByLastVisitDateForAdmin(Class beanClass) throws Exception;
    
    public QueryScroller listUserCountsByMonthAndRegion() throws Exception;
    public QueryScroller listUserCountsByMonthAndRegion(Class beanClass) throws Exception;

    public QueryScroller listUsersWithNoGlobalId() throws Exception;
    
    public QueryScroller listLikeMeSearch(User user) throws Exception;
    public QueryScroller listAdvancedSearch(String firstName, String lastName, String jobtitle, String regions, String expertise, String keywords, String scrollkey) throws Exception;
    public QueryScroller listAdvancedSearch(String firstName) throws Exception;
    public List getSysAdminUsers() throws Exception;
    public List getUnvalidatedUsers() throws Exception;
    public void deleteOneMonthOldUnvalidatedUsers() throws Exception;

    public int getUserCount() throws Exception;
    public int getUserCount(String groupId) throws Exception;
    public int getUserValidatedCount() throws Exception;
    public int getContributorCount(int groupId) throws Exception;
    public int getUserCountAt(Date date) throws Exception;
    public int getUserCountAt(Date date, String region) throws Exception;
    public int getContributorCountAt(Date date, int groupId) throws Exception;
    public int getAdministratorCount() throws Exception;
    public int getAdministratorValidatedCount() throws Exception;
    
    public List getOrganisationsFromAllUsers(String organisation) throws Exception; 
    
    public List getInviteeListForEvent(int communityId, int userId, int eventId, int max) throws Exception;
    public List getDefaultInviteeListForCommunityEvent(int communityId, int userId, int max) throws Exception;
    
    public List<UserSearchDto> searchInAllConnections(int userId, String searchString) throws Exception;
    
    public List<UserSearchDto> searchUsersForInput(String input, int communityId) throws Exception;
    public List<UserSearchDto> searchAuthorsForInput(String input, int pconsId) throws Exception;
    
    public List<UserSearchDto> searchSimilarProfiles(int userId, int max) throws Exception;
    
    public QueryScroller listRecentUpdatedActiveCommunitiesForMember(User user) throws Exception;
    
    public QueryScroller showCommentsForType(int entryId, String type) throws Exception;
    
    public List getAllUser() throws Exception;
    
    public List getAssigneeListForCommunityEssignment(int communityId, int userId, int max) throws Exception;
    public int countAllMyContacts(int userId) throws Exception;
    public int countAllMyCommunities(int userId) throws Exception;
}