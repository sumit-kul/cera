package com.era.community.pers.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.assignment.ui.dto.AssignmentAssigneeDto;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityRoleConstants;
import com.era.community.monitor.ui.dto.SubscriptionDto;
import com.era.community.pers.ui.dto.UserDto;
import com.era.community.pers.ui.dto.UserSearchDto;

public class UserDaoImpl extends com.era.community.pers.dao.generated.UserDaoBaseImpl implements UserDao
{
	public static final int NOPHOTO = 0;
	public static final String NOORGANISATION = "'' ";

	public User getUserForEmailAddress(String address) throws Exception
	{
		return (User) getEntityWhere("ucase(emailAddress) = ? ", address.toUpperCase());
	}

	public User getUserForProfileName(String profileName) throws Exception
	{
		return (User) getEntityWhere("ucase(ProfileName) = ? ", profileName.toUpperCase());
	}

	public <T> T getUserDataForEmailAddress(String address, Class<T> beanClass) throws Exception
	{
		return getBean("select * from User where ucase(emailAddress) = ? ", beanClass, address.toUpperCase());
	}

	public List getInviteeListForEvent(int communityId, int userId, int eventId, int max) throws Exception
	{
		String clause = "";
		if (eventId > 0) {
			clause = "and U.ID NOT IN (Select UserId from EventInviteeLink link where link.EventId = ?)";
		}

		String query = "select distinct U.* from User U, Membership MS, MemberList ML " 
			+ " where U.ID = MS.UserId and U.ID <> ? and MS.MemberListId = ML.Id and U.Inactive = 'N' and U.Validated = 'Y' and ML.CommunityId = ? " + clause;
		//+ " where U.ID = MS.UserId and U.ID <> ? and MS.MemberListId = ML.Id and U.Inactive = 'N'  " + clause;

		if (max != 0) {
			query += " LIMIT " + max + " ";
		}
		if (eventId > 0) {
			return getBeanList(query, User.class, userId, communityId, eventId);
			//return getBeanList(query, User.class, userId, communityId, eventId);
		} else {
			return getBeanList(query, User.class, userId, communityId);
			//return getBeanList(query, User.class, userId, communityId);
		}
	}

	public List getDefaultInviteeListForCommunityEvent(int communityId, int userId, int max) throws Exception
	{
		String query = "select U.* from User U, Membership MS, MemberList ML " 
			+ " where U.ID = MS.UserId and U.ID <> ? and MS.MemberListId = ML.Id and U.Inactive = 'N' and U.Validated = 'Y' and ML.CommunityId = ? ";

		if (max != 0) {
			query += " LIMIT " + max + " ";
		}
		return getBeanList(query, User.class, userId, communityId);
	}

	public List getAssigneeListForCommunityEssignment(int communityId, int userId, int max) throws Exception
	{
		String query = "select U.Id as UserId, U.FirstName, U.LastName, U.PhotoPresent, (select COUNT(ID) from AssignmentAssigneeLink L where L.UserId = U.Id) as Assigned "
			+ " from User U, Membership MS, MemberList ML " 
			+ " where U.ID = MS.UserId and U.ID <> ? and MS.MemberListId = ML.Id and U.Inactive = 'N' and U.Validated = 'Y' and ML.CommunityId = ? ";

		if (max != 0) {
			query += " LIMIT " + max + " ";
		}
		return getBeanList(query, AssignmentAssigneeDto.class, userId, communityId);
	}

	public QueryScroller listUsersByNameForAdmin() throws Exception
	{
		return listUsersByNameForAdmin(null);
	}

	public QueryScroller listUsersByLastVisitDateForAdmin() throws Exception
	{
		return listUsersByLastVisitDateForAdmin(null);
	}

	public QueryScroller listAdministratorsByLastVisitDateForAdmin() throws Exception
	{
		return listAdministratorsByLastVisitDateForAdmin(null);
	}

	/**
	 * 
	 */
	public QueryScroller listAdministratorsByNameForAdmin() throws Exception
	{
		return listAdministratorsByNameForAdmin(null);
	}

	/**
	 * 
	 */
	public QueryScroller listUnvalidatedUsers() throws Exception
	{
		return listUnvalidatedUsers(null);
	}

	public QueryScroller listUnvalidatedUsers(Class beanClass) throws Exception
	{
		QueryScroller scroller = getQueryScroller("select * from User where Validated <> 'Y'",  beanClass);
		scroller.addScrollKey("dateregistered", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}


	public void deleteOneMonthOldUnvalidatedUsers() throws Exception
	{
		String sql="delete from User where  Validated <> 'Y' and dateregistered < (Current_timestamp - INTERVAL 30 day)";

		getSimpleJdbcTemplate().update(sql);     

	}

	public QueryScroller listAllActiveUsersByName(Class beanClass) throws Exception
	{
		QueryScroller scroller = getQueryScroller("select * from User  where Inactive <> 'Y' and Validated = 'Y' and SuperAdministrator = 'N' ",  beanClass);
		scroller.addScrollKey("lastName", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		return scroller;
	}

	public QueryScroller listActiveUsersByName() throws Exception
	{
		String query = "select * from User where Inactive <> 'Y' and Validated = 'Y' ";
		QueryScroller scroller = getQueryScroller(query, User.class);
		scroller.addScrollKey("lastName", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		return scroller;
	}

	public QueryScroller listUsersByNameForAdmin(Class beanClass) throws Exception
	{
		QueryScroller scroller = getQueryScroller("select * from User ",  beanClass);

		scroller.addScrollKey("FullNameUC", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT, true);

		return scroller;
	}

	public QueryScroller listUsersByEmailForAdmin(Class beanClass) throws Exception
	{
		QueryScroller scroller = getQueryScroller("select * from User ", beanClass);
		scroller.addScrollKey("emailAddress", "emailAddress", QueryPaginator.DIRECTION_ASCENDING,
				QueryPaginator.TYPE_TEXT, true);
		return scroller;
	}

	public QueryScroller listUsersByDateRegisteredForAdmin(Class beanClass) throws Exception
	{
		QueryScroller scroller = getQueryScroller("select * from User ", beanClass);
		scroller.addScrollKey("dateRegistered", QueryPaginator.DIRECTION_DESCENDING,
				QueryPaginator.TYPE_DATE, true);
		return scroller;
	}

	public QueryScroller listUsersByLastVisitDateForAdmin(Class beanClass) throws Exception
	{
		QueryScroller scroller = getQueryScroller("select * from User ",  beanClass);
		scroller.addScrollKey("dateLastVisit", "dateLastVisit", QueryPaginator.DIRECTION_DESCENDING,
				QueryPaginator.TYPE_DATE, false);
		return scroller;
	}

	public QueryScroller listAdministratorsByNameForAdmin(Class beanClass) throws Exception
	{
		QueryScroller scroller = getQueryScroller("select * from User where SystemAdministrator = 'Y'", beanClass);

		scroller.addScrollKey("FullNameUC", "FullNameUC", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT,
				true);

		return scroller;
	}

	public QueryScroller listAdministratorsByEmailForAdmin(Class beanClass) throws Exception
	{
		QueryScroller scroller = getQueryScroller("select * from User where SystemAdministrator = 'Y'",  beanClass);
		scroller.addScrollKey("emailAddress", "emailAddress", QueryPaginator.DIRECTION_ASCENDING,
				QueryPaginator.TYPE_TEXT, true);
		return scroller;
	}

	public QueryScroller listAdministratorsByLastVisitDateForAdmin(Class beanClass) throws Exception
	{
		QueryScroller scroller = getQueryScroller("select * from User where SystemAdministrator = 'Y'", beanClass);
		scroller.addScrollKey("dateLastVisit", "dateLastVisit", QueryPaginator.DIRECTION_DESCENDING,
				QueryPaginator.TYPE_DATE, false);
		return scroller;
	}

	public boolean isPhotoPresent(User user) throws Exception
	{
		Integer v = (Integer) getValue(user, "case when Photo is null then 0 else 1 end", Integer.class);
		return v.intValue() == 1;
	}

	public void clearPhoto(User user) throws Exception
	{
		setColumn(user, "Photo", null);
		setColumn(user, "PhotoContentType", null);
	}

	public boolean isCoverPresent(User user) throws Exception
	{
		Integer v = (Integer) getValue(user, "case when Cover is null then 0 else 1 end", Integer.class);
		return v.intValue() == 1;
	}

	public void clearCover(User user) throws Exception
	{
		setColumn(user, "Cover", null);
		setColumn(user, "CoverContentType", null);
	}

	public QueryScroller listLikeMeSearch(User user) throws Exception
	{
		PeopleFinder peopleFinder = new PeopleFinder(user);
		peopleFinder.setLikeMeUserId(user.getId());
		peopleFinder.setExpertises(user.getExpertiseIds());

		String temp = peopleFinder.getQueryString();

		QueryScroller scroller = getQueryScroller(peopleFinder.getQueryString(), null, peopleFinder.getParameters());
		scroller.addScrollKey("firstName", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);

		return scroller;

	}

	public QueryScroller listAdvancedSearch(String firstName, String lastName, String jobtitle, String regions,
			String expertise, String keywords, String scrollkey) throws Exception
			{
		PeopleFinder peopleFinder = new PeopleFinder();
		peopleFinder.setFirstName(firstName);
		peopleFinder.setLastName(lastName);
		peopleFinder.setJobTitle(jobtitle);
		peopleFinder.setRegion(regions);
		peopleFinder.setExpertise(expertise);
		peopleFinder.setKeyword(keywords);

		QueryScroller scroller = getQueryScroller(peopleFinder.getQueryString(), null, peopleFinder.getParameters());
		scroller.addScrollKey(scrollkey, QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		scroller.addScrollKey("id", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);

		return scroller;
			}

	public QueryScroller listAdvancedSearch(String firstName) throws Exception
	{
		PeopleFinder peopleFinder = new PeopleFinder();
		peopleFinder.setFirstName(firstName);

		QueryScroller scroller = getQueryScroller(peopleFinder.getQueryString(), null, peopleFinder.getParameters());
		scroller.addScrollKey("lastname", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		scroller.addScrollKey("id", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);

		return scroller;
	}

	public void removeFromMemberLists(User user) throws Exception
	{
		String sSql = "delete from Membership where UserId = ?";
		getSimpleJdbcTemplate().update(sSql,  user.getId() );
	}

	public void removeFromContactLists(User user) throws Exception
	{
		String sSql = "delete from Contact where ContactUserId = ?";
		getSimpleJdbcTemplate().update(sSql,  user.getId());
	}

	public QueryScroller listContacts(User user) throws Exception
	{
		String sQuery = "select * from ( " +
		" select U.ID ID, U.FIRSTNAME FIRSTNAME, U.LASTNAME LASTNAME, U.EMAILADDRESS EMAILADDRESS, U.CREATED CREATED, U.PHOTOPRESENT PHOTOPRESENT, U.ConnectionCount ConnectionCount, C.DATECONNECTION DATECONNECTION" +
		" from User U, Contact C where U.Id = C.ContactUserId and C.OwningUserId = ? and " +
		" C.STATUS = 1 and C.DATECONNECTION is not null and U.Inactive = 'N' and U.Validated = 'Y' " +
		" union " +
		" select U.ID ID, U.FIRSTNAME FIRSTNAME, U.LASTNAME LASTNAME, U.EMAILADDRESS EMAILADDRESS, U.CREATED CREATED, U.PHOTOPRESENT PHOTOPRESENT, U.ConnectionCount ConnectionCount, C.DATECONNECTION DATECONNECTION" +
		" from User U, Contact C where U.Id = C.OwningUserId and C.ContactUserId = ? and " +
		" C.STATUS = 1 and C.DATECONNECTION is not null and U.Inactive = 'N' and U.Validated = 'Y' " +
		" ) A";

		QueryScroller scroller = getQueryScroller(sQuery, UserDto.class, user.getId(), user.getId());

		scroller.addScrollKey("STEMP.FIRSTNAME", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_TEXT);
		return scroller;
	}

	public List<UserSearchDto> searchInAllConnections(int userId, String searchString) throws Exception
	{
		String sQuery = "select * from ( " +
		" select U.ID ID, U.FIRSTNAME FIRSTNAME, U.LASTNAME LASTNAME, U.PHOTOPRESENT PHOTOPRESENT " +
		" from User U, Contact C where U.Id = C.ContactUserId and C.OwningUserId = ? and " +
		" C.STATUS = 1 and C.DATECONNECTION is not null and U.Inactive = 'N' and U.Validated = 'Y' and (LOWER(U.FIRSTNAME) like '%"+searchString+"%' or LOWER(U.LASTNAME) like '%"+searchString+"%') " +
		" union " +
		" select U.ID ID, U.FIRSTNAME FIRSTNAME, U.LASTNAME LASTNAME, U.PHOTOPRESENT PHOTOPRESENT " +
		" from User U, Contact C where U.Id = C.OwningUserId and C.ContactUserId = ? and U.Inactive = 'N' and U.Validated = 'Y' and " +
		" C.STATUS = 1 and C.DATECONNECTION is not null and (LOWER(U.FIRSTNAME) like '%"+searchString+"%' or LOWER(U.LASTNAME) like '%"+searchString+"%') " +
		" union " +
		" select distinct U.ID ID, U.FIRSTNAME FIRSTNAME, U.LASTNAME LASTNAME, U.PHOTOPRESENT PHOTOPRESENT " +
		" from User U, Membership M, Membership M2 where U.Id = M.UserId and M2.UserId = ? and M.UserId <> ? and U.Inactive = 'N' and U.Validated = 'Y' " +
		" and (LOWER(U.FIRSTNAME) like '%"+searchString+"%' or LOWER(U.LASTNAME) like '%"+searchString+"%') " +
		" ) A order by A.FIRSTNAME asc LIMIT 10";

		return getBeanList(sQuery, UserSearchDto.class, userId, userId, userId, userId);
	}

	/*
	 * Lists all the contacts for the current suer
	 */ 
	public List listAllContacts(User user) throws Exception
	{
		String query = "select U.*  from User U, Contact C " + " where U.Id = C.ContactUserId and C.OwningUserId=? and U.Inactive = 'N' and U.Validated = 'Y' ";
		return getBeanList(query, User.class, user.getId());
	}

	public int countAllMyContacts(int userId) throws Exception
	{
		String query = "select count(distinct A.ID) from ( " +
		" select U.ID ID, U.FIRSTNAME FIRSTNAME, U.LASTNAME LASTNAME, U.EMAILADDRESS EMAILADDRESS, U.CREATED CREATED, U.PHOTOPRESENT PHOTOPRESENT, U.ConnectionCount ConnectionCount, C.DATECONNECTION DATECONNECTION" +
		" from User U, Contact C where U.Id = C.ContactUserId and C.OwningUserId = ? and U.Inactive = 'N' and U.Validated = 'Y' and " +
		" C.STATUS = 1 and C.DATECONNECTION is not null " +
		" union " +
		" select U.ID ID, U.FIRSTNAME FIRSTNAME, U.LASTNAME LASTNAME, U.EMAILADDRESS EMAILADDRESS, U.CREATED CREATED, U.PHOTOPRESENT PHOTOPRESENT, U.ConnectionCount ConnectionCount, C.DATECONNECTION DATECONNECTION" +
		" from User U, Contact C where U.Id = C.OwningUserId and C.ContactUserId = ? and U.Inactive = 'N' and U.Validated = 'Y' and " +
		" C.STATUS = 1 and C.DATECONNECTION is not null " +
		" ) A";

		return getSimpleJdbcTemplate().queryForInt(query, userId, userId);
	}

	public int countAllMyCommunities(int userId) throws Exception {
		String query = "select count(C.ID) from MemberList L, Membership M, Community C, User U  where M.USERID = ? and C.CreatorId = U.ID "
			+ "and M.MEMBERLISTID = L.ID and L.COMMUNITYID = C.ID and C.SysType != 'PrivateCommunity' and C.Status = " 
			+ Community.STATUS_ACTIVE;
		return getSimpleJdbcTemplate().queryForInt(query, userId);
	}

	/*
	 * Lists the contacts for the current user by job title
	 */
	public QueryScroller listContactsByJob(User user) throws Exception
	{
		String sQuery = "select U.*  from User U, Contact C " + " where U.Id = C.ContactUserId and U.Inactive = 'N' and U.Validated = 'Y' and C.OwningUserId=?";
		QueryScroller scroller = getQueryScroller(sQuery, UserDto.class , user.getId());
		scroller.addScrollKey("STEMP.JobTitle", "U.LastName", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_TEXT);
		return scroller;
	}

	/*
	 * Lists the contacts for the current user by region
	 */
	public QueryScroller listContactsByRegion(User user) throws Exception
	{
		String sQuery = "select U.*  from User U, Contact C " + " where U.Id = C.ContactUserId and U.Inactive = 'N' and U.Validated = 'Y' and C.OwningUserId=?";

		QueryScroller scroller = getQueryScroller(sQuery,  UserDto.class, user.getId());

		scroller.addScrollKey("STEMP.Region", "U.LastName", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_TEXT);
		return scroller;
	}

	/*
	 * Lists the contacts for the current user byregion
	 */
	public QueryScroller listContactsByOrg(User user) throws Exception
	{
		String sQuery = "select U.*  from User U, Contact C " + " where U.Id = C.ContactUserId and U.Validated = 'Y' and C.OwningUserId=?";

		QueryScroller scroller = getQueryScroller(sQuery, UserDto.class, user.getId());

		scroller.addScrollKey("STEMP.Organisation", "U.LastName", QueryPaginator.DIRECTION_DESCENDING,
				QueryPaginator.TYPE_TEXT);
		return scroller;
	}

	public QueryScroller listUserSubscriptions(User user) throws Exception
	{
		final String SQL = "select S.*, C.Name as communityName " + "from Subscription S,  Community C "
		+ "where (S.UserId = ? and C.Id = S.CommunityId) ";

		QueryScroller scroller = getQueryScroller(SQL,  SubscriptionDto.class, user.getId());

		scroller.addScrollKey("STEMP.communityId", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		scroller.addScrollKey("STEMP.sysType", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);

		return scroller;
	}

	public List listSubscriptionsForUser(User user) throws Exception
	{
		final String SQL = "select * " + "from Subscription " + "where (UserId = ? ) ";
		return getBeanList(SQL, SubscriptionDto.class, user.getId() );
	}

	public QueryScroller listUserSubscriptionsForCommunity(User user, int communityId) throws Exception
	{
		final String SQL = "select S.*, C.Name as communityName " + "from Subscription S,  Community C "
		+ "where (S.UserId = ? and C.Id = S.CommunityId and C.Id=?) ";

		QueryScroller scroller = getQueryScroller(SQL, 
				SubscriptionDto.class, new Object[] { user.getId(), communityId });

		scroller.addScrollKey("STEMP.communityId", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		scroller.addScrollKey("STEMP.sysType", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		return scroller;
	}

	public boolean isAdminFor(User user, int id) throws Exception
	{
		String query = "select M1.id from Membership M1, Membership M2 " + " where M1.MemberListId = M2.MemberListId "
		+ " and M1.UserId = ? and M2.UserId = ? " + " and M2.Role = '" + CommunityRoleConstants.ADMIN + "' ";
		List list = getSimpleJdbcTemplate().queryForList(query, user.getId(), id );

		return !list.isEmpty();
	}

	public boolean isConnectedWith(User user, int id) throws Exception
	{
		String query = "select * from Contact " + 
		" where (OwningUserId = ? and ContactUserId = ? ) or (ContactUserId = ? and OwningUserId = ? ) and Status = 1 ";

		// queryForList returns a List where each entry is a Map with each entry in the map representing the column value for that row.
		List list = getSimpleJdbcTemplate().queryForList(query, user.getId(), id, user.getId(), id );

		return !list.isEmpty();
	}

	public List getSysAdminUsers() throws Exception
	{
		String query = "select * from User where SystemAdministrator = ?";
		return getBeanList(query, User.class, "Y");
	}

	public List getUnvalidatedUsers() throws Exception
	{
		String query = "select * from User where Validated <> 'Y'";
		return getBeanList(query, User.class);
	}


	public List getUnvalidatedUsersOlder3Months() throws Exception
	{
		String query = "select * from User where Validated <> 'Y'";
		return getBeanList(query, User.class);
	}

	public QueryScroller listUsersWithNoGlobalId() throws Exception
	{
		final String SQL = "select * from User where GlobalId <= 0";
		QueryScroller scroller = getQueryScroller(SQL, User.class);
		scroller.addScrollKey("STEMP.Id", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_INTEGER);
		return scroller;
	}

	public int getUserCount() throws Exception
	{
		String query = "select count(*) from User";
		return getJdbcTemplate().queryForInt(query);
	}

	public int getUserCount(String sRegion) throws Exception
	{    
		String query = "select count(*) from User where region=?";

		return getSimpleJdbcTemplate().queryForInt(query, sRegion);
	}

	public int getAdministratorCount() throws Exception
	{
		String query = "select count(*) from User where SystemAdministrator = 'Y'";
		return getJdbcTemplate().queryForInt(query);
	}

	public int getUserValidatedCount() throws Exception
	{
		String query = "select count(*) from User where Validated = 'Y' ";
		return getJdbcTemplate().queryForInt(query);
	}

	public int getAdministratorValidatedCount() throws Exception
	{
		String query = "select count(*) from User where SystemAdministrator = 'Y' and Validated = 'Y' ";
		return getJdbcTemplate().queryForInt(query);
	}

	public int getUserCountAt(Date date) throws Exception
	{
		String query = "select count(*) from User where Created <= ?";
		return getSimpleJdbcTemplate().queryForInt(query, date );
	}


	public int getUserCountAt(Date date, String region) throws Exception
	{
		String query = "select count(*) from User where Created <= ? and Region = ?";
		return getSimpleJdbcTemplate().queryForInt(query, date, region );
	}


	public int getContributorCountAt(Date date, int groupId) throws Exception
	{
		if (groupId>0) {
			String query = "select count(*) from User U " +
			" where  U.Created <= ? and U.Inactive = 'N' and U.Validated = 'Y' and " +
			" ( exists (select * from Forum F, ForumItem I, CommunityGroupLink G where I.AuthorId = U.Id and I.ForumId = F.Id and F.CommunityId = G.CommunityId and G.CommunityGroupId = ?)" +
			" or exists (select * from DocumentLibrary L, Document I, CommunityGroupLink G where I.PosterId = U.Id and L.Id = I.LibraryId and L.CommunityId = G.CommunityId and G.CommunityGroupId = ? )" +
			" or exists (select * from  DocumentLibrary L, Document D, DocumentComment I, CommunityGroupLink G where I.PosterId = U.Id and D.Id  = I.DocumentId and L.Id = D.LibraryId and L.CommunityId = G.CommunityId and G.CommunityGroupId = ? )" +
			" or exists (select * from EventCalendar C, Event I, CommunityGroupLink G where I.PosterId = U.Id and C.Id = I.CalendarId and C.CommunityId = G.CommunityId and G.CommunityGroupId = ? )" +
			" or exists (select * from Wiki W, WikiEntry I, CommunityGroupLink G where I.PosterId = U.Id and W.Id = I.WikiId and W.CommunityId = G.CommunityId and G.CommunityGroupId = ? ) )";
			return getSimpleJdbcTemplate().queryForInt(query, date, groupId, groupId, groupId, groupId, groupId, groupId);
		}
		else {
			String query = "select count(*) from User U " +
			" where  U.Created <= ? and U.Inactive = 'N' and U.Validated = 'Y' and " +
			" ( exists (select * from ForumItem I where I.AuthorId = U.Id)" +
			" or exists (select * from BlogEntry I where I.PosterId = U.Id)" +
			" or exists (select * from BlogEntryComment I where I.PosterId = U.Id)" +
			" or exists (select * from Document I where I.PosterId = U.Id)" +
			" or exists (select * from DocumentComment I where I.PosterId = U.Id)" +
			" or exists (select * from Event I where I.PosterId = U.Id)" +
			" or exists (select * from WikiEntry I where I.PosterId = U.Id) )";
			return getSimpleJdbcTemplate().queryForInt(query, date);
		}
	}

	public int getContributorCount(int groupId) throws Exception
	{
		return getContributorCountAt(new Date(), groupId);
	}

	public String getLocalAuthorityName(User user) throws Exception
	{
		return null;
	}

	public QueryScroller listUsersByEmailForAdmin() throws Exception
	{
		return listUsersByEmailForAdmin(null);
	}

	public QueryScroller listAdministratorsByEmailForAdmin() throws Exception
	{
		return listAdministratorsByEmailForAdmin(User.class);
	}

	public QueryScroller listUserCountsByMonthAndRegion() throws Exception
	{
		return listUserCountsByMonthAndRegion(User.class);
	}

	public QueryScroller listUserCountsByMonthAndRegion(Class beanClass) throws Exception
	{
		String sQuery = "select count(*) as count, region, month(created)  from user  group by region, month(created)order by month(created)";
		return getQueryScroller(sQuery, beanClass);
	}

	public QueryScroller listUsersByDateRegisteredForAdmin() throws Exception
	{
		return listUsersByDateRegisteredForAdmin(null);
	}

	public int getBlogCommentCountSinceLastLogin(User user) throws Exception
	{
		String query = "select count(*) from BlogEntryComment C, User U, BlogEntry E, Blog B " + "where U.Id = ? and U.Inactive = 'N' and U.Validated = 'Y' "
		+ " and B.OwnerId = U.Id " + " and E.BlogId = B.Id " + "and C.BlogEntryId = E.Id "
		+ " and  C.DatePosted > U.DateLastVisit ";
		return getSimpleJdbcTemplate().queryForInt(query, user.getId());
	}

	public int getMembershipCount(User user) throws Exception
	{
		String query = "select count(*) from Membership where userid=?";
		return getSimpleJdbcTemplate().queryForInt(query, user.getId());
	}

	public List<UserSearchDto> searchUsersForInput(String input, int communityId) throws Exception
	{
		String subQ = "";
		if (communityId > 0) {
			subQ = " and U.ID not in (select M.UserId from Membership M, MemberList L where L.COMMUNITYID = "+communityId+" and M.MEMBERLISTID = L.ID)";
		}
		String query = "select U.ID ID, U.FIRSTNAME FIRSTNAME, U.LASTNAME LASTNAME, U.EmailAddress, U.PHOTOPRESENT PHOTOPRESENT " +
		" from User U where U.Inactive <> 'Y' and U.Validated = 'Y' and (LOWER(U.FIRSTNAME) like '%"+input+"%' or LOWER(U.LASTNAME) like '%"+input+"%' or LOWER(U.EmailAddress) like '%"+input+"%' ) " +
		subQ + " order by U.FIRSTNAME desc LIMIT 10";
		return getBeanList(query, UserSearchDto.class);
	}

	public List<UserSearchDto> searchAuthorsForInput(String input, int pconsId) throws Exception
	{
		String subQ = "";
		if (pconsId > 0) {
			subQ = " and U.ID not in (select BA.UserId from BlogAuthor BA where BA.PersonalBlogId = "+pconsId+" )";
		}
		String query = "select U.ID ID, U.FIRSTNAME FIRSTNAME, U.LASTNAME LASTNAME, U.EmailAddress, U.PHOTOPRESENT PHOTOPRESENT " +
		" from User U where U.Inactive <> 'Y' and U.Validated = 'Y' and (LOWER(U.FIRSTNAME) like '%"+input+"%' or LOWER(U.LASTNAME) like '%"+input+"%' or LOWER(U.EmailAddress) like '%"+input+"%' ) " +
		subQ + " order by U.FIRSTNAME desc LIMIT 10";
		return getBeanList(query, UserSearchDto.class);
	}

	/* Return list of organisations from all users */
	public List getOrganisationsFromAllUsers(String organisation) throws Exception
	{
		List list = new ArrayList();
		if (organisation != null) {
			String sql="select DISTINCT U.Organisation from User U where U.Organisation <> ? order by organisation"; 
			list=getBeanList(sql, UserDto.class, organisation);   
		}
		else {
			String sql="select DISTINCT U.Organisation from User U order by organisation"; 
		}

		return list;      
	}

	public List<UserSearchDto> searchSimilarProfiles(int userId, int max) throws Exception
	{
		String query = "select U.ID ID, U.FIRSTNAME FIRSTNAME, U.LASTNAME LASTNAME, U.EmailAddress, U.PHOTOPRESENT PHOTOPRESENT " +
		" from User U, InterestLink IL, Interest I, InterestLink MIL, Interest MI where U.Inactive <> 'Y' and U.Validated = 'Y' and I.ID = IL.InterestId and IL.ProfileId = U.ID " +
		" and MI.ID = MIL.InterestId and MIL.ProfileId = ? and U.ID <> ? " +
		" order by U.FIRSTNAME desc ";

		if (max != 0) {
			query += " LIMIT " + max + " ";
		}
		return getBeanList(query, UserSearchDto.class, userId, userId);
	}

/*	public QueryScroller listRecentUpdatedActiveCommunitiesForMember(User user) throws Exception
	{
		QueryScroller scroller = null;
		int uid = user.getId();
		if (user != null)  {
			String query =  "select * from (select CommId, min(dateConnected) as dateConnected, CommunityName, CommunityType, logoPresent from ( " +
			" select ML.CommunityId CommId, MS.DateJoined dateConnected, CC.Name CommunityName, CC.SysType CommunityType, CC.CommunityLogoPresent logoPresent " +
			" from MemberList ML, Membership MS, Community CC, Subscription S where CC.ID = ML.CommunityId and ML.Id = MS.MemberListId and CC.ID = S.CommunityId and S.PageSubscription = 1 and S.SYSTYPE = 'CommunitySubscription' and CC.Status = 0 and MS.UserId = ? " +
			" union " +
			" select S.CommunityId CommId, S.Created dateConnected, CC.Name CommunityName, CC.SysType CommunityType, CC.CommunityLogoPresent logoPresent from Subscription S, Community CC where CC.ID = S.CommunityId and S.PageSubscription = 1 and S.SYSTYPE = 'CommunitySubscription' and CC.Status = 0 and S.userId = ? " +
			" union " +
			" select ML.CommunityId CommId, CN.DateConnection dateConnected, CC.Name CommunityName, CC.SysType CommunityType, CC.CommunityLogoPresent logoPresent from Contact CN, MemberList ML, Membership MS, Community CC " +
			" where CC.ID = ML.CommunityId and CC.SysType != 'PrivateCommunity' and CC.Status = 0 and " +
			" ((CN.OwningUserId = ? and MS.UserId = CN.ContactUserId and ML.Id = MS.MemberListId) or (CN.ContactUserId = ? and MS.UserId = CN.OwningUserId and ML.Id = MS.MemberListId)) " +
			" and not exists ( Select * from Subscription S where S.CommunityId = ML.CommunityId and S.SYSTYPE = 'CommunitySubscription' and S.UserId = ? ) " +
			" )  as Comm group by CommId, CommunityName, CommunityType, logoPresent) as tt, ( " +
			// Document
			" select distinct d.id itemId, d.title itemTitle, d.systype itemType, CAST(d.description as CHAR(1000) ) itemDesc, " +
			" d.filename itemfilename, d.FileContentType contentType, " +
			" (select SUM(STARS) / COUNT(STARS) from DocumentRating DR where DR.documentid = d.id) avgRating, '' Venue, '' Location, '' Address, CURRENT_TIMESTAMP as StartDate, CURRENT_TIMESTAMP as EndDate, " +
			" (select COUNT(ID) from DocumentComment DC where DC.documentid = d.id) as commentCount, " +
			" d.DatePosted datePosted, d.Downloads Visitors, lib.Communityid communityId, U.Id Posterid, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT photoPresent, " +
			" 0 as ImageCount , " +
			" (select COUNT(ID) from DocumentLike DL where DL.DocumentId = d.id) likeCount " +
			" from Document d, DocumentLibrary lib, User U, Subscription S where lib.id = d.libraryid and U.Id = d.PosterId " + 
			" and ( not exists ( Select * from Subscription S where S.DocLibId = lib.ID and S.SYSTYPE = 'DocLibSubscription' and S.UserId = ? ) " +
			" or exists ( Select * from Subscription S where S.DocLibId = lib.ID and S.SYSTYPE = 'DocLibSubscription' and S.UserId = ? and S.PageSubscription = 1 ))" +
			" union " +
			// Event
			" select distinct e.id itemId, e.name itemTitle,  e.systype itemType, CAST(e.Description as CHAR(1000) ) itemDesc, '' itemfilename, e.PhotoPresent contentType , CAST(NULL as DECIMAL ) as avgRating, e.Venue, e.Location, e.Address, e.StartDate, e.EndDate, " +
			" CAST(NULL as UNSIGNED INTEGER) as commentCount, " +
			" e.modified datePosted, e.Invited Visitors, cal.Communityid communityId, U.Id Posterid, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT photoPresent, " +
			" 0 as ImageCount , " +
			" CAST(NULL as UNSIGNED INTEGER) likeCount " +
			" from Event e, EventCalendar cal, User U where cal.id = e.calendarid  and U.Id = e.PosterId " +
			" and ( not exists ( Select * from Subscription S where S.EventCalendarId = cal.ID and S.SYSTYPE = 'EventCalendarSubscription' and S.UserId = ? ) " +
			" or exists ( Select * from Subscription S where S.EventCalendarId = cal.ID and S.SYSTYPE = 'EventCalendarSubscription' and S.UserId = ? and S.PageSubscription = 1 ))" +
			" union " +
			// BlogEntry
			" select distinct E.Id itemId, E.Title itemTitle, E.Systype itemType, CAST(E.BODY as CHAR(1000) ) itemDesc, '' itemfilename, '' contentType, " +
			" CAST(NULL as DECIMAL ) as avgRating, '' Venue, '' Location, '' Address, CURRENT_TIMESTAMP as StartDate, CURRENT_TIMESTAMP as EndDate, " +
			" (select COUNT(ID) from BlogEntryComment BC where BC.BLOGENTRYID = E.id) as commentCount, " +
			" E.DatePosted datePosted, E.Visitors Visitors, CB.Communityid communityId, U.Id Posterid, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT photoPresent, " +
			" (select COUNT(ID) from Image I where I.BlogEntryId = E.Id) ImageCount , " +
			" (select COUNT(ID) from BlogEntryLike BL where BL.BlogEntryId = E.id) likeCount " +
			" from BlogEntry E, CommunityBlog CB, User U where CB.Id = E.CommunityBlogId and U.Id = E.PosterId " +
			" and ( not exists ( Select * from Subscription S where S.CommunityBlogId = CB.ID and S.SYSTYPE = 'CommunityBlogSubscription' and S.UserId = ? ) " +
			" or exists ( Select * from Subscription S where S.CommunityBlogId = CB.ID and S.SYSTYPE = 'CommunityBlogSubscription' and S.UserId = ? and S.PageSubscription = 1 ))" +
			" union " +
			// WikiEntry
			" select distinct w.id itemId, w.title itemTitle,   w.systype itemType, w.BODY as  itemDesc, '' itemfilename, '' contentType, " +
			" CAST(NULL as DECIMAL ) as avgRating, '' Venue, '' Location, '' Address, CURRENT_TIMESTAMP as StartDate, CURRENT_TIMESTAMP as EndDate, " +
			" (select COUNT(ID) from WikiEntryComment WEC where WEC.WikiEntryId = w.id) as  commentCount, w.DatePosted datePosted, w.Visitors Visitors, wiki.Communityid communityId, U.Id Posterid, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT photoPresent, " +
			" (select COUNT(ID) from Image I where I.WikiEntryId = w.Id) ImageCount , " +
			" (select COUNT(ID) from WikiEntryLike WL where WL.WikiEntryId = w.id) likeCount " +
			" from  TBWENT w, Wiki wiki , User U where wiki.id = w.wikiid and U.Id = w.PosterId and w.EntrySequence = " + Integer.MAX_VALUE + 
			" and ( not exists ( Select * from Subscription S where S.WikiId = wiki.ID and S.SYSTYPE = 'WikiSubscription' and S.UserId = ? ) " +
			" or exists ( Select * from Subscription S where S.WikiId = wiki.ID and S.SYSTYPE = 'WikiSubscription' and S.UserId = ? and S.PageSubscription = 1 ))" +
			" union " +
			// ForumItem
			" select distinct f.id as itemId, f.subject itemTitle, f.systype itemType, CAST(f.BODY as CHAR(1000) ) itemDesc, '' itemfilename, '' contentType, " +
			" CAST(NULL as DECIMAL ) as avgRating, '' Venue, '' Location, '' Address, CURRENT_TIMESTAMP as StartDate, CURRENT_TIMESTAMP as EndDate, " +
			" (select COUNT(ID) from ForumResponse R where R.TopicId = f.id and R.DeleteStatus <> 9) as commentCount, f.DatePosted datePosted, f.Visitors Visitors, forum.Communityid communityId, U.Id Posterid, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT photoPresent, " +
			" (select COUNT(ID) from Image I where I.ForumItemId = f.Id) ImageCount , " +
			" (select COUNT(ID) from ForumItemLike FL where FL.ForumItemId = f.id) likeCount " +
			" from ForumTopic f, Forum forum, User U where forum.id = f.forumid and U.Id = f.AuthorId and f.DeleteStatus <> 9 " +
			" and ( not exists ( Select * from Subscription S where S.ForumId = forum.ID and S.SYSTYPE = 'ForumSubscription' and S.UserId = ? ) " +
			" or exists ( Select * from Subscription S where S.ForumId = forum.ID and S.SYSTYPE = 'ForumSubscription' and S.UserId = ? and S.PageSubscription = 1 ))" +
			" ) as main where communityId = CommId and datePosted >= dateConnected ";

			scroller = getQueryScroller(query, null, uid, uid, uid, uid, uid, uid, uid, uid, uid, uid, uid, uid, uid, uid, uid);
			scroller.addScrollKey("datePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}
		return scroller;
	}*/
	
	public QueryScroller listRecentUpdatedActiveCommunitiesForMember(User user) throws Exception
	{
		QueryScroller scroller = null;
		int uid = user.getId();
		if (user != null)  {
			String query =  "select distinct coalesce(CA2.Id, 0) Id, coalesce(CA2.CommunityId, 0) CommunityId, coalesce(CA2.DocumentId, 0) DocumentId, coalesce(CA2.FolderId, 0) FolderId, " +
			" coalesce(CA2.DocGroupNumber, 0) DocGroupNumber, coalesce(CA2.BlogEntryId, 0) BlogEntryId, coalesce(CA2.ForumItemId, 0) ForumItemId, " +
			" coalesce(CA2.WikiEntryId, 0) WikiEntryId, coalesce(CA2.EventId, 0) EventId, coalesce(CA2.AssignmentId, 0) AssignmentId, UA2.BlogEntryId personalBlogEntryId, UA2.Created DatePosted, " +
			" UA2.UserId, UA2.PhotoId, UA2.AlbumId, UA2.MediaGroupNumber, UA2.CommunityId startCommunityId from (select UA.ID UaId, UA.CommunityActivityId CaId from Contact CN " +
			" inner join UserActivity UA on UA.UserId <> ? and ((CN.ContactUserId = ? and UA.UserId = CN.OwningUserId and FollowOwner = 1 ) " +
			" or (CN.OwningUserId = ? and UA.UserId = CN.ContactUserId and FollowContact = 1 )) " +
			" where CN.created <= UA.created " +
			" union " +
			" select  UA.ID UaId, CA.ID CaId from Subscription S " +
			" inner join CommunityActivity CA on CA.CommunityId = S.CommunityId and S.SYSTYPE = 'CommunitySubscription' and S.PageSubscription = 1 and S.userId = ? " +
			" inner Join UserActivity UA on CA.Id = UA.CommunityActivityId where S.created <= CA.created and (CA.EventId = 0 or CA.ItemStatus > 0)) as T " +
			" inner join UserActivity UA2 on T.UaId = UA2.Id " +
			" left outer join CommunityActivity CA2 on UA2.CommunityActivityId = CA2.ID" ;

			scroller = getQueryScroller(query, true, null, uid, uid, uid, uid);
			scroller.addScrollKey("DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}
		return scroller;
	}

	public QueryScroller showCommentsForType(int entryId, String type) throws Exception
	{
		String sQuery = "";
		if ("BlogEntry".equalsIgnoreCase(type)) {
			sQuery = "select 'BlogEntry' as type, EC.Id, EC.BlogEntryId entryId, EC.Comment, EC.DatePosted, EC.PosterId, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT photoPresent " +
			" from BlogEntryComment EC, User U where U.Id = EC.PosterId and EC.BlogEntryId = ? ";
		} else if ("WikiEntry".equalsIgnoreCase(type)) {
			sQuery = "select 'WikiEntry' as type, EC.Id, EC.WikiEntryId entryId, EC.Comment, EC.DatePosted, EC.PosterId, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT photoPresent " +
			" from WikiEntryComment EC, User U where U.Id = EC.PosterId and EC.WikiEntryId = ? ";
		} else if ("Document".equalsIgnoreCase(type)) {
			sQuery = "select 'Document' as type, EC.Id, EC.DocumentId entryId, EC.Comment, EC.DatePosted, EC.PosterId, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT photoPresent " +
			" from DocumentComment EC, User U where U.Id = EC.PosterId and EC.DocumentId = ? ";
		}
		QueryScroller scroller =  getQueryScroller(sQuery, null, entryId);
		scroller.addScrollKey("DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}

	public List getAllUser() throws Exception
	{
		String query = "select * from User";
		return getBeanList(query, User.class);
	}
}