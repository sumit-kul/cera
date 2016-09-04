package com.era.community.communities.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.blog.ui.dto.BlogEntryPannelDto;
import com.era.community.communities.ui.dto.CommunityEntryPannelDto;
import com.era.community.events.ui.dto.EventPannelDto;
import com.era.community.forum.ui.dto.ForumTopicPannelDto;
import com.era.community.pers.dao.User;
import com.era.community.pers.ui.dto.UserDto;
import com.era.community.wiki.ui.dto.WikiEntryPannelDto;

public class CommunityDaoImpl extends com.era.community.communities.dao.generated.CommunityDaoBaseImpl implements CommunityDao
{
	public QueryScroller listActiveCommunities(String filterTagList, String communityOption, String sortByOption) throws Exception
	{
		String filterTag = "";
		String typeClause = "";
		int count = 0;
		if(filterTagList != null && !"".equals(filterTagList)) {
			String filterTagPart = "";
			if (filterTagList.contains(",")) {
				StringTokenizer st = new StringTokenizer(filterTagList, ",");
				while (st.hasMoreTokens()) {
					String tag = st.nextToken().trim().toLowerCase();
					filterTagPart += " T.TagText = \'"+tag+"\' ";
					if (st.hasMoreTokens()) filterTagPart += " or ";
					count++;
				}
			} else {
				filterTagPart = " T.TagText = \'"+filterTagList+"\' ";
				count = 1;
			}
			filterTag = " and exists (select COMMUNITYID from (select count(ID), COMMUNITYID  from Tag T where PARENTTYPE like '%Community' ";
			filterTag = filterTag + " and " +filterTagPart+ " group by COMMUNITYID having count(ID) >= "+count+" ) as T where T.COMMUNITYID = C.Id) " ;
		}

		if ("1".equalsIgnoreCase(communityOption)) {
			typeClause = " and C.SysType != 'PrivateCommunity' ";
		}

		String query = "select * from (select U.FirstName, U.LastName, " +
		" ((select COALESCE(SUM(BE.Visitors),0) from BlogEntry BE, CommunityBlog BC where BC.Id = BE.CommunityBlogId and BC.CommunityId = C.ID ) + " +
		" (select COALESCE(SUM(WE.Visitors),0) from WikiEntry WE, Wiki W where WE.WikiId = W.Id and W.CommunityId = C.ID) + " +
		" (select COALESCE(SUM(FI.Visitors),0) from ForumItem FI, Forum F where FI.ForumId = F.Id and F.CommunityId = C.ID) + " +
		" (select COALESCE(SUM(E.Confirmed),0) as Visitors from Event E, EventCalendar EC where E.CalendarId = EC.Id and EC.CommunityId = C.ID) + " +
		" (select COALESCE(SUM(D.Downloads),0) as Visitors from Document D, DocumentLibrary DL where D.LibraryId = DL.Id and DL.CommunityId = C.ID)) Visitors, " +
		" C.* from Community C, User U where C.CreatorId = U.ID and C.Status = " + 
		Community.STATUS_ACTIVE + typeClause + filterTag + " ) as T";

		QueryScroller scroller = getQueryScroller(query, true, null);
		if ("2".equalsIgnoreCase(sortByOption)) {
			scroller.addScrollKey("STEMP.Created", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		} else if ("3".equalsIgnoreCase(sortByOption)) {
			scroller.addScrollKey("STEMP.NAME", "NAME", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		} else  {
			scroller.addScrollKey("STEMP.Visitors", "Visitors", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_INTEGER);
		}
		return scroller;
	}

	public QueryScroller listActiveCommunitiesForMember(User member, String filterTagList, String communityOption, String sortByOption) throws Exception
	{
		String filterTag = "";
		String typeClause = "";
		int count = 0;
		if(filterTagList != null && !"".equals(filterTagList)) {
			String filterTagPart = "";
			if (filterTagList.contains(",")) {
				StringTokenizer st = new StringTokenizer(filterTagList, ",");
				while (st.hasMoreTokens()) {
					String tag = st.nextToken().trim().toLowerCase();
					filterTagPart += " T.TagText = \'"+tag+"\' ";
					if (st.hasMoreTokens()) filterTagPart += " or ";
					count++;
				}
			} else {
				filterTagPart = " T.TagText = \'"+filterTagList+"\' ";
				count = 1;
			}
			filterTag = " and exists (select COMMUNITYID from (select count(ID), COMMUNITYID  from Tag T where PARENTTYPE like '%Community' ";
			filterTag = filterTag + " and " +filterTagPart+ " group by COMMUNITYID having count(ID) >= "+count+" ) as T where T.COMMUNITYID = C.Id) " ;
		}

		if ("2".equalsIgnoreCase(communityOption)) {
			typeClause = " and M.Role = 'Owner' ";
		} else if ("3".equalsIgnoreCase(communityOption)) {
			typeClause = " and (M.Role = 'Member' or M.Role = 'Owner') ";
		}

		String query = "select Creator.FirstName, Creator.LastName, C.* from Community C, User Creator, MemberList L, Membership M " +
		" where C.CreatorId = Creator.ID and L.COMMUNITYID = C.ID and M.MEMBERLISTID = L.ID and M.UserId = ? " + typeClause +
		" and C.Status = " + Community.STATUS_ACTIVE + filterTag ;

		QueryScroller scroller = getQueryScroller(query, true, null, member.getId());

		if ("2".equalsIgnoreCase(sortByOption)) {
			scroller.addScrollKey("STEMP.NAME", "NAME", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		} else {
			scroller.addScrollKey("STEMP.Created", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}
		return scroller;        
	}

	public QueryScroller listActiveCommunitiesForFollower(User follower, String filterTagList, String sortByOption) throws Exception
	{
		String filterTag = "";
		int count = 0;
		if(filterTagList != null && !"".equals(filterTagList)) {
			String filterTagPart = "";
			if (filterTagList.contains(",")) {
				StringTokenizer st = new StringTokenizer(filterTagList, ",");
				while (st.hasMoreTokens()) {
					String tag = st.nextToken().trim().toLowerCase();
					filterTagPart += " T.TagText = \'"+tag+"\' ";
					if (st.hasMoreTokens()) filterTagPart += " or ";
					count++;
				}
			} else {
				filterTagPart = " T.TagText = \'"+filterTagList+"\' ";
				count = 1;
			}
			filterTag = " and exists (select COMMUNITYID from (select count(ID), COMMUNITYID  from Tag T where PARENTTYPE like '%Community' " +
			" and " +filterTagPart+ " group by COMMUNITYID having count(ID) >= "+count+" ) as T where T.COMMUNITYID = C.Id) " ;
		}

		String query = "select Creator.FirstName, Creator.LastName, C.* from Community C, User Creator, Subscription S " +
		" where C.CreatorId = Creator.ID and S.CommunityId = C.Id and S.SYSTYPE= 'CommunitySubscription' and " +
		" S.UserId = ? and C.Status = " + Community.STATUS_ACTIVE + filterTag ;

		QueryScroller scroller = getQueryScroller(query, true, null, follower.getId());

		if ("2".equalsIgnoreCase(sortByOption)) {
			scroller.addScrollKey("STEMP.NAME", "NAME", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		} else {
			scroller.addScrollKey("STEMP.Created", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}
		return scroller; 
	}

	/**
	 * Return a scroller listing all communities by name for Indexing purpose. We don't want to index private communities...
	 */
	public QueryScroller listActiveCommunitiesByName() throws Exception
	{
		return listCommunitiesByName(Community.STATUS_ACTIVE);
	}

	/**
	 * Return a scroller listing all communities by name. Excluding private communities...
	 */
	protected QueryScroller listCommunitiesByName(int status) throws Exception
	{
		String query = "select * from Community where SysType != 'PrivateCommunity' and Status = ? ";
		QueryScroller scroller = getQueryScroller(query, null, status);
		scroller.addScrollKey("Name", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		return scroller;
	}

	public QueryScroller listActiveCommunitiesForTag(String tag) throws Exception
	{        
		String query = "select C.* from Community C where  C.Status = "+ Community.STATUS_ACTIVE + "  and " +
		"exists (select DISTINCT T.CommunityId from Tag T where T.TagText = ? and C.Id = T.CommunityId)" ;

		QueryScroller scroller = getQueryScroller(query,Community.class, tag);
		scroller.addScrollKey("Name", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		return scroller;
	}

	public List getActiveCommunitiesForMember(User user) throws Exception
	{
		String query = "select C.* from MemberList L, Membership M, Community C " + "where M.USERID = ? "
		+ "and M.MEMBERLISTID = L.ID " + "and L.COMMUNITYID = C.ID " + "and C.Status = "
		+ Community.STATUS_ACTIVE;
		return getEntityList(query, user.getId());        
	}

	public List getActiveOtherCommunitiesForMember(User user, int communityId) throws Exception
	{
		String query = "select C.* from MemberList L, Membership M, Community C " + "where M.USERID = ? "
		+ "and M.MEMBERLISTID = L.ID " + "and L.COMMUNITYID = C.ID " + "and C.ID <> ? and C.Status = "
		+ Community.STATUS_ACTIVE;
		return getEntityList(query, user.getId(), communityId);
	}

	public int countCommunitiesForBlogEntry(int blogentryId) throws Exception
	{
		String query = "select count(COMM.Id) from TBBCEN BCEN , TBBENT BENT, TBBCON BCON , Community COMM " +
		" where BCEN.BLOGENTRYID = BENT.ID and BCON.ID = BCEN.CommunityBlogID " +
		" and COMM.ID = BCON.COMMUNITYID and BCEN.BLOGENTRYID = ? and COMM.Status = " +
		Community.STATUS_ACTIVE;
		return getSimpleJdbcTemplate().queryForInt(query, blogentryId);       
	}

	public QueryScroller listActiveCommunitiesForMember(User user, User currUser, Class beanClass) throws Exception
	{
		String query = "";
		if (currUser == null) {
			query = "select M.DateJoined, M.DateLastVisit, M.Role, U.FirstName, U.LastName, C.* "
				+ "from MemberList L, Membership M, Community C, User U  where M.USERID = ? and C.CreatorId = U.ID "
				+ "and M.MEMBERLISTID = L.ID and L.COMMUNITYID = C.ID and C.SysType != 'PrivateCommunity' and C.Status = " 
				+ Community.STATUS_ACTIVE;
		} else if (currUser.getId() == user.getId()) {
			query = "select M.DateJoined, M.DateLastVisit, M.Role, U.FirstName, U.LastName, C.* "
				+ "from MemberList L, Membership M, Community C, User U  where M.USERID = ? and C.CreatorId = U.ID "
				+ "and M.MEMBERLISTID = L.ID and L.COMMUNITYID = C.ID and C.Status = " 
				+ Community.STATUS_ACTIVE;
		} else {
			query = "select * from ( " 
				+ " select M.DateJoined, M.DateLastVisit, M.Role, U.FirstName, U.LastName, C.* "
				+ " from MemberList L, Membership M, Community C, User U, MemberList L2, Membership M2 " 
				+ " where M.USERID = ? and C.CreatorId = U.ID "
				+ " and M.MEMBERLISTID = L.ID and C.SysType = 'PrivateCommunity' " 
				+ " and L.COMMUNITYID = C.ID and M2.USERID = ? and M2.MEMBERLISTID = L2.ID and L2.COMMUNITYID = C.ID and C.Status = " 
				+ Community.STATUS_ACTIVE
				+ " union "
				+ " select M.DateJoined, M.DateLastVisit, M.Role, U.FirstName, U.LastName, C.* "
				+ "from MemberList L, Membership M, Community C, User U  where M.USERID = ? and C.CreatorId = U.ID "
				+ "and M.MEMBERLISTID = L.ID and L.COMMUNITYID = C.ID and C.SysType != 'PrivateCommunity' and C.Status = " 
				+ Community.STATUS_ACTIVE 
				+ " ) A ";
		}
		QueryScroller scroller = null;
		if (currUser != null && currUser.getId() != user.getId()) {
			scroller = getQueryScroller(query, beanClass, user.getId(), currUser.getId(), user.getId());
			scroller.addScrollKey("STEMP.Modified", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		} else {
			scroller = getQueryScroller(query, beanClass, user.getId());
			scroller.addScrollKey("STEMP.NAME", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		}
		return scroller;
	}
	
	public QueryScroller listUserCommunitiesForMember(User user, String type, String role) throws Exception
	{
		String query = "";
		if ("Follower only".equals(role)) {
			query = "select S.CREATED as subscriptionDate, U.FirstName, U.LastName, C.* "
				+ "from Community C, SUBSCRIPTION S, User U " + "where S.USERID = ? "
				+ "and S.USERID = U.ID and S.COMMUNITYID = C.ID and S.SYSTYPE= 'CommunitySubscription' and C.Status = " 
				+ Community.STATUS_ACTIVE;
		} else {
			query = "select M.DateJoined, M.DateLastVisit, M.Role, U.FirstName, U.LastName, C.* "
				+ "from MemberList L, Membership M, Community C, User U " + "where M.USERID = ? "
				+ "and M.MEMBERLISTID = L.ID " + "and L.COMMUNITYID = C.ID and C.CreatorId = U.ID " + "and C.Status = " 
				+ Community.STATUS_ACTIVE;
		}

		if (type != null && !"All".equals(type)) {
			if (type.equalsIgnoreCase("Private")) {
				query += (" and C.SysType = 'PrivateCommunity' ");
			} else {
				query += (" and C.SysType = 'PublicCommunity' ");
			}
		}
		if (role != null && !"All".equals(role) && !"Follower only".equals(role)) {
			if ("Owner only".equals(role)) {
				query += (" and M.Role = 'Owner' ");
			} else if ("Community Admin only".equals(role)) {
				query += (" and M.Role = 'Community Admin' ");
			} else if ("Member only".equals(role)) {
				query += (" and M.Role = 'Member' ");
			}
		}
		QueryScroller scroller = getQueryScroller(query, null, user.getId());
		return scroller;
	}

	public QueryScroller listActiveCommunitiesForMemberByName(User user, User currUser) throws Exception
	{
		QueryScroller scroller = listActiveCommunitiesForMember(user, currUser, null);
		return scroller;
	}

	public QueryScroller listUserCommunitiesForMemberByName(User user, Class beanClass, String type, String role, String sortBy) throws Exception
	{
		QueryScroller scroller = listUserCommunitiesForMember(user, type, role);
		if (sortBy != null && sortBy.equalsIgnoreCase("Date of joining")) {
			if ("Follower only".equals(role)) {
				scroller.addScrollKey("STEMP.CREATED", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
			} else {
				scroller.addScrollKey("STEMP.DateJoined", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
			}
		} else if (sortBy != null && sortBy.equalsIgnoreCase("Date last visited") ) {
			if ("Follower only".equals(role)) {
				scroller.addScrollKey("STEMP.CREATED", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
			} else {
				scroller.addScrollKey("STEMP.DateLastVisit", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
			}
		} else {
			scroller.addScrollKey("STEMP.Name", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_TEXT);
		}
		return scroller;
	}

	public QueryScroller listCommunityInvitationsForMember(User user, String sortBy) throws Exception
	{
		String query = "select MI.Id InvitationId, MI.InvitorId, MI.RequestDate, U.FirstName, U.LastName, CONCAT_WS(' ',U2.FirstName,U2.LastName) as CreatedBy, C.* "
			+ "from Community C, MemberInvitation MI, User U, User U2 where MI.UserId = ? "
			+ "and C.CreatorId = U2.ID and MI.InvitorId = U.ID and MI.CommunityId = C.ID and MI.Status = 0 and C.Status = " 
			+ Community.STATUS_ACTIVE;

		QueryScroller scroller = getQueryScroller(query, null, user.getId());

		if (sortBy != null && sortBy.equalsIgnoreCase("Name Of Invitor") ) {
			scroller.addScrollKey("STEMP.FirstName", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		} else if (sortBy != null && sortBy.equalsIgnoreCase("Name Of Community") ) {
			scroller.addScrollKey("STEMP.Name", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		} else {
			scroller.addScrollKey("STEMP.RequestDate", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}
		return scroller;
	}

	/**
	 * Get community info for all communities the selected user is a member of, in community name order
	 * 
	 * @param user
	 * @return QueryScroller listing data for all communities the specified user is a member of
	 * @throws Exception
	 */
	public QueryScroller listActiveUserCommunitiesForMemberByName(User user, String type, String role, String sortBy) throws Exception
	{
		return listUserCommunitiesForMemberByName(user, null, type, role, sortBy);
	}

	/**
	 * Get community info for all communities the selected user is a member of, in date joined order (order is
	 * decsending - communities joined most recently at the top of the list)
	 * 
	 * @param user
	 * @param beanClass
	 * @return QueryScroller listing data for all communies the specified user is a member of
	 * @throws Exception
	 */
	public QueryScroller listActiveCommunitiesForMemberByDateJoined(User user, User currUser, Class beanClass) throws Exception
	{
		QueryScroller scroller = listActiveCommunitiesForMember(user, currUser, beanClass);
		scroller.addScrollKey("STEMP.DateJoined", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}

	/**
	 * Get community info for all communities the selected user is a member of, in date joined order (order is
	 * decsending - communities joined most recently at the top of the list)
	 * 
	 * @param user
	 * @return QueryScroller listing data for all communities the specified user is a member of
	 * @throws Exception
	 */
	public QueryScroller listActiveCommunitiesForMemberByDateJoined(User user, User currUser) throws Exception
	{
		return listActiveCommunitiesForMemberByDateJoined(user, currUser, null);
	}

	/**
	 * Get community info for all communities the selected user is a member of, in date visited order (order is
	 * decsending - communities visited most recently at the top of the list)
	 * 
	 * @param user
	 * @param beanClass
	 * @return QueryScroller listing data for all communies the specified user is a member of
	 * @throws Exception
	 */
	public QueryScroller listActiveCommunitiesForMemberByDateVisited(User user, User currUser, Class beanClass) throws Exception
	{
		QueryScroller scroller = listActiveCommunitiesForMember(user, currUser, beanClass);
		scroller.addScrollKey("STEMP.DateLastVisit", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}

	/**
	 * Get community info for all communities the selected user is a member of, in date visited order (order is
	 * decsending - communities visited most recently at the top of the list)
	 * 
	 * @param user
	 * @return QueryScroller listing data for all communities the specified user is a member of
	 * @throws Exception
	 */
	public QueryScroller listActiveCommunitiesForMemberByDateVisited(User user, User currUser) throws Exception
	{
		return listActiveCommunitiesForMemberByDateVisited(user, currUser, null);
	}

	/**
	 * Lists the users requesting membership to a particular community, sorted by request date
	 */
	public QueryScroller listUsersRequestingMembership(Community comm, String filterOption, String sortOption) throws Exception
	{
		String filterQuery = "";
		if (filterOption != null && !"".equals(filterOption)) {
			if (filterOption.equals("Accepted Requests")) {
				filterQuery = "and R.Status = 1 ";
			} else if (filterOption.equals("Rejected Requests")) {
				filterQuery = "and R.Status = 2 ";
			} else if (filterOption.equals("Unapproved Requests")) {
				filterQuery = "and R.Status = 0 ";
			}
		}
		String query = "select U.*, R.Id as requestId, R.RequestDate as mRequestDate from User U, CommunityJoiningRequest R where U.Id = R.UserId and R.CommunityId = ? and U.Inactive <> 'Y' " + filterQuery;
		QueryScroller scroller = getQueryScroller(query, UserDto.class, comm.getId());
		if (sortOption != null && !"".equals(sortOption)) {
			if (sortOption.equals("Name Of Requester")) {
				scroller.addScrollKey("STEMP.FirstName", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
				scroller.addScrollKey("STEMP.LastName", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
			} else {
				scroller.addScrollKey("STEMP.mRequestDate", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_DATE);
			}
		} else {
			scroller.addScrollKey("STEMP.mRequestDate", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_DATE);
		}

		return scroller;
	}

	public QueryScroller listUsersRejectedMembership(Community comm) throws Exception
	{
		String query = "select U.*, R.RequestDate from User U, CommunityJoiningRequest R where "+
		"U.Id = R.UserId and R.CommunityId = ? and R.Status = 2 and U.Inactive <> 'Y' ";
		QueryScroller scroller = getQueryScroller(query, UserDto.class, comm.getId());
		scroller.addScrollKey("STEMP.requestDate", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}

	public Community getCommunityForName(String name) throws Exception
	{
		return (Community) getEntityWhere("upper(name) = ?", name);
	}

	public boolean isLogoPresent(Community comm) throws Exception
	{
		Integer v = (Integer) getValue(comm, "case when CommunityLogo is null then 0 else 1 end ", Integer.class);
		return v.intValue() == 1;
	}
	
	public boolean isBannerPresent(Community comm) throws Exception
	{
		Integer v = (Integer) getValue(comm, "case when CommunityBanner is null then 0 else 1 end ", Integer.class);
		return v.intValue() == 1;
	}

	public void clearLogo(Community comm) throws Exception
	{
		setColumn(comm, "CommunityLogo", null);
		setColumn(comm, "CommunityLogoContentType", null);
	}
	
	public void clearBanner(Community comm) throws Exception
	{
		setColumn(comm, "CommunityBanner", null);
		setColumn(comm, "CommunityBannerContentType", null);
	}

	public int getCommunityCount(int groupId) throws Exception
	{
		String query = "select count(*) from Community ";
		return getJdbcTemplate().queryForInt(query);
	}
	
	public int getCommunityCountSoFar() throws Exception
	{
		String query = "select count(*) from Community where Status = " + Community.STATUS_ACTIVE ;
		return getJdbcTemplate().queryForInt(query);
	}

	public int getContributorCount(Community comm) throws Exception
	{
		String query = "select count(*) from User U "
			+ " where "
			+ " ( exists (select * from Forum F, ForumItem I where I.AuthorId = U.Id and I.ForumId = F.Id and F.CommunityId = ?  )"
			+ " or exists (select * from CommunityBlog B, BlogEntry I where I.PosterId = U.Id and B.Id = I.CommunityBlogId and B.CommunityId = ? )"
			+ " or exists (select * from DocumentLibrary L, Document I where I.PosterId = U.Id and L.Id = I.LibraryId and L.CommunityId = ? )"
			+ " or exists (select * from  DocumentLibrary L, Document D, DocumentComment I where I.PosterId = U.Id and D.Id  = I.DocumentId and L.Id = D.LibraryId and L.CommunityId = ? )"
			+ " or exists (select * from EventCalendar C, Event I where I.PosterId = U.Id and C.Id = I.CalendarId and C.CommunityId = ? )"
			+ " or exists (select * from Wiki W, WikiEntry I where I.PosterId = U.Id and W.Id = I.WikiId and W.CommunityId = ? ) )";
		int id = comm.getId();
		return getSimpleJdbcTemplate().queryForInt(query, id, id, id, id, id, id );
	}

	public void setSysType(Object obj, String column, Object value) throws Exception
	{
		setColumn(obj, column, value);
	}

	/*public QueryScroller getItemsForCommunityHome(Community comm, String typeBy, String sortBy) throws Exception
	{
		String itemTypeClause = "";
		if (typeBy != null && !typeBy.equals("") && !typeBy.equalsIgnoreCase("All")) {
			if (typeBy.equalsIgnoreCase("Blog")) {
				itemTypeClause = " where itemType = 'BlogEntry' ";
			} else if (typeBy.equalsIgnoreCase("Library")) {
				itemTypeClause = " where itemType = 'Document' ";
			} else if (typeBy.equalsIgnoreCase("Event")) {
				itemTypeClause = " where itemType = 'Event' ";
			} else if (typeBy.equalsIgnoreCase("Forum")) {
				itemTypeClause = " where itemType = 'ForumTopic' or itemType = 'ForumResponse'";
			} else if (typeBy.equalsIgnoreCase("Wiki")) {
				itemTypeClause = " where itemType = 'WikiEntry' ";
			}
		}

		String query = "select * from  ( " +
			//document
			" select distinct d.id itemId, d.title itemTitle, d.systype itemType, CAST(d.description as CHAR(1000) ) itemDesc, " +
			" d.filename itemfilename, d.FileContentType contentType, " +
			" (select SUM(STARS) / COUNT(STARS) from DocumentRating DR where DR.documentid = d.id) avgRating, '' Venue, '' Location, '' Address, CURRENT_TIMESTAMP as StartDate, CURRENT_TIMESTAMP as EndDate, " +
			" (select COUNT(ID) from DocumentComment DC where DC.documentid = d.id) as commentCount, " +
			" d.modified datePosted, d.Downloads Visitors, lib.Communityid communityId, U.Id Posterid, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT photoPresent, " +
			" 0 as ImageCount , " +
			" (select COUNT(ID) from DocumentLike DL where DL.DocumentId = d.id) likeCount " +
			" from Document d, DocumentLibrary lib, User U " +
			" where lib.id = d.libraryid and  U.id = d.posterid " +
			" and lib.communityId= ? " +

			" union all " +
			//Events
			" select distinct e.id itemId, e.name itemTitle,  e.systype itemType, CAST(e.Description as CHAR(1000) ) itemDesc, '' itemfilename, e.PhotoPresent contentType , CAST(NULL as DECIMAL ) as avgRating, e.Venue, e.Location, e.Address, e.StartDate, e.EndDate, " +
			" CAST(NULL as UNSIGNED INTEGER) as commentCount, " +
			" e.modified datePosted, e.Invited Visitors, cal.Communityid communityId, U.Id Posterid, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT photoPresent, " +
			" 0 as ImageCount , " +
			" CAST(NULL as UNSIGNED INTEGER) likeCount " +
			" from Event e, EventCalendar cal, User U " +
			" where cal.id = e.calendarid and  U.id = posterid " +
			" and cal.communityId= ? " +

			" union all " +
			//Blog
			" select distinct E.Id itemId, E.Title itemTitle, E.Systype itemType, CAST(E.BODY as CHAR(1000) ) itemDesc, '' itemfilename, '' contentType, " +
			" CAST(NULL as DECIMAL ) as avgRating, '' Venue, '' Location, '' Address, CURRENT_TIMESTAMP as StartDate, CURRENT_TIMESTAMP as EndDate, " +
			" (select COUNT(ID) from BlogEntryComment BC where BC.BLOGENTRYID = E.id) as commentCount, " +
			" E.Modified datePosted, E.Visitors Visitors, CB.Communityid communityId, U.Id Posterid, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT photoPresent, " +
			" (select COUNT(ID) from Image I where I.BlogEntryId = E.Id) ImageCount , " +
			" (select COUNT(ID) from BlogEntryLike BL where BL.BlogEntryId = E.id) likeCount " +
			" from BlogEntry E, CommunityBlog CB, User U " +
			" where CB.Id = E.CommunityBlogId and U.id = E.PosterId " +
			" and CB.communityId= ? " +

			" union all " +
			//Wiki
			" select distinct w.id itemId, w.title itemTitle,   w.systype itemType, w.BODY as  itemDesc, '' itemfilename, '' contentType, " +
			" CAST(NULL as DECIMAL ) as avgRating, '' Venue, '' Location, '' Address, CURRENT_TIMESTAMP as StartDate, CURRENT_TIMESTAMP as EndDate, " +
			" (select COUNT(ID) from WikiEntryComment WEC where WEC.WikiEntryId = w.id) as  commentCount, w.modified datePosted, w.Visitors Visitors, wiki.Communityid communityId, U.Id Posterid, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT photoPresent, " +
			" (select COUNT(ID) from Image I where I.WikiEntryId = w.Id) ImageCount , " +
			" (select COUNT(ID) from WikiEntryLike WL where WL.WikiEntryId = w.id) likeCount " +
			" from  TBWENT w, Wiki wiki , User U where wiki.id = w.wikiid and U.Id = w.PosterId and w.EntrySequence = " + Integer.MAX_VALUE + 
			" and wiki.communityId= ? " +

			" union all " +
			//Forums
			" select distinct f.id as itemId, f.subject itemTitle, f.systype itemType, CAST(f.BODY as CHAR(1000) ) itemDesc, '' itemfilename, '' contentType, " +
			" CAST(NULL as DECIMAL ) as avgRating, '' Venue, '' Location, '' Address, CURRENT_TIMESTAMP as StartDate, CURRENT_TIMESTAMP as EndDate, " +
			" (select COUNT(ID) from ForumResponse R where R.TopicId = f.id and R.DeleteStatus <> 9) as commentCount, f.modified datePosted, f.Visitors Visitors, forum.Communityid communityId, U.Id Posterid, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT photoPresent, " +
			" (select COUNT(ID) from Image I where I.ForumItemId = f.Id) ImageCount , " +
			" (select COUNT(ID) from ForumItemLike FL where FL.ForumItemId = f.id) likeCount " +
			" from ForumItem f, Forum forum, User U " +
			" where forum.id = f.forumid and U.id = f.AUTHORID and " +
			" forum.communityId = ? " +
			" and f.deletestatus <> 9 and f.systype = 'ForumTopic' " +
			" ) as T " + itemTypeClause ;

		QueryScroller scroller = null;
		scroller = getQueryScroller(query, true, null, comm.getId(), comm.getId(), comm.getId(), comm.getId(), comm.getId());

		if (sortBy != null && !sortBy.equals("")) {
			if ("Most Liked".equals(sortBy)) {
				scroller.addScrollKey("STEMP.likeCount", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_INTEGER);
			} else if ("Name".equals(sortBy)) {
				scroller.addScrollKey("STEMP.itemTitle", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
			} else if ("Top Rated".equals(sortBy)) {
				scroller.addScrollKey("STEMP.avgRating", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_INTEGER);
			} else if ("Most Visited".equals(sortBy)) {
				scroller.addScrollKey("STEMP.posterid", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_INTEGER);
			} else {
				scroller.addScrollKey("STEMP.datePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
			}
		} else {
			scroller.addScrollKey("STEMP.datePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}
		return scroller;
	}*/
	
	public QueryScroller getItemsForCommunityHome(Community comm) throws Exception
	{
		QueryScroller scroller = null;
		if (comm != null)  {
			String query =  "select distinct CommunityId, DocumentId, FolderId, DocGroupNumber, BlogEntryId, ForumItemId, " +
			" WikiEntryId, EventId, ItemStatus, AssignmentId, UserId, Created from CommunityActivity where CommunityId = ? and (EventId = 0 or ItemStatus > 0) " ;
			scroller = getQueryScroller(query, null, comm.getId() );
			scroller.addScrollKey("Created", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}
		return scroller;
	}

	public QueryScroller getItemsForSelectedTags(String filterTagList, String typeBy, String sortBy) throws Exception
	{
		String itemTypeClause = "";
		if (typeBy != null && !typeBy.equals("") && !typeBy.equalsIgnoreCase("All")) {
			if (typeBy.equalsIgnoreCase("Blog")) {
				itemTypeClause = " where itemType = 'BlogEntry' ";
			} else if (typeBy.equalsIgnoreCase("Library")) {
				itemTypeClause = " where itemType = 'Document' ";
			} else if (typeBy.equalsIgnoreCase("Event")) {
				itemTypeClause = " where itemType = 'Event' ";
			} else if (typeBy.equalsIgnoreCase("Forum")) {
				itemTypeClause = " where itemType = 'ForumTopic' or itemType = 'ForumResponse'";
			} else if (typeBy.equalsIgnoreCase("Wiki")) {
				itemTypeClause = " where itemType = 'WikiEntry' ";
			}
		}

		String query = "select * from  ( "
			//document
			+ " select distinct	lib.Communityid communityid, d.id itemId, d.systype itemType, "
			+ " d.title itemTitle, CAST(d.description as CHAR(100) ) itemDesc, "
			+ " d.filename itemfilename, "
			+ " (select DOUBLE(DOUBLE(SUM(STARS)) / COUNT(STARS)) from DocumentRating DR where DR.documentid = d.id) avgRating, "
			+ " (select COUNT(ID) from DocumentComment DC where DC.documentid = d.id) commentCount, "
			+ " d.modified datePosted, d.posterid posterid, "
			+ " user.FirstName firstName, user.LastName lastName, user.PHOTOPRESENT photoPresent, "
			+ " (select COUNT(ID) from DocumentLike DL where DL.DocumentId = d.id) likeCount "
			+ " from Document d, DocumentLibrary lib, User user, TAG tag "
			+ " where lib.id = d.libraryid and  user.id = d.posterid " + getFilterTagSubQuery(filterTagList, "document", "d")

			+ " union all "
			//Events
			+ " select distinct	cal.communityId communityid, e.id itemId, e.systype itemType, "
			+ " e.name itemTitle, CAST(e.Description as CHAR(100) ) itemDesc, CHAR('') filename itemfilename, CAST(NULL as DOUBLE ) as avgRating, "
			+ " CAST(NULL as BIGINT) as commentCount, "
			+ " e.modified datePosted, e.PosterId posterid, "
			+ " user.FirstName firstName, user.LastName lastName, user.PHOTOPRESENT photoPresent, "
			+ " CAST(NULL as BIGINT) as likeCount "
			+ " from Event e, EventCalendar cal, User user "
			+ " where cal.id = e.calendarid and  user.id = posterid " + getFilterTagSubQuery(filterTagList, "Event", "e")

			+ " union all "
			//Blog
			+ " select distinct	Cons.communityid, E.Id itemId, E.Systype itemType, E.Title itemTitle, "
			+ " CAST(E.BODY as CHAR(100) ) itemDesc, '' filename itemfilename, "
			+ " CAST(NULL as DOUBLE ) as avgRating, "
			+ " (select COUNT(ID) from BlogEntryComment BC where BC.BLOGENTRYID = E.id) commentCount, "
			+ " E.Modified datePosted, Blog.OWNERID posterid, "
			+ " user.FirstName firstName, user.LastName lastName, user.PHOTOPRESENT photoPresent,  "
			+ " (select COUNT(ID) from BlogEntryLike BL where BL.BlogEntryId = E.id) likeCount "
			+ " from BlogEntry E, CommunityBlog CB, User user "
			+ " where CB.Id = E.CommunityBlogId and user.id = Blog.OWNERID "
			+ " and Cons.Id = BCE.CommunityBlogId " + getFilterTagSubQuery(filterTagList, "BlogEntry", "E")

			+ " union all "
			//Wiki
			+ " select distinct wiki.communityid, w.id itemId, w.systype itemType, "
			+ " w.title itemTitle, CAST(Twe.BODY as CHAR(100) ) as  itemDesc, '' filename itemfilename, "
			+ " CAST(NULL as DOUBLE ) as avgRating, "
			+ " CAST(NULL as BIGINT) as commentCount, "
			+ " w.modified datePosted, w.posterid posterid, "
			+ " user.FirstName firstName, user.LastName lastName, user.PHOTOPRESENT photoPresent, "
			+ " (select COUNT(ID) from WikiEntryLike WL where WL.WikiEntryId = w.id) likeCount "
			+ " from WikiEntry w, Wiki wiki, TBWENT Twe, User user "
			+ " where wiki.id = w.wikiid and user.id = w.posterid "
			//+ " and wiki.communityId= ? "
			+ " and Twe.id = w.id "
			+ " and w.EntrySequence = (select MAX(EntrySequence) from WikiEntry WE where WE.ENTRYID = w.ENTRYID) " 
			+ getFilterTagSubQuery(filterTagList, "WikiEntry", "w")

			+ " union all "
			//Forums
			+ " select distinct	forum.communityid, coalesce (f.topicid, f.id) as itemId, f.systype itemType, "
			+ " f.subject itemTitle, CAST(f.BODY as CHAR(100) ) itemDesc, '' filename itemfilename, "
			+ " CAST(NULL as DOUBLE ) as avgRating, "
			+ " CAST(NULL as BIGINT) as commentCount, "
			+ " f.modified datePosted, f.AUTHORID posterid, "
			+ " user.FirstName firstName, user.LastName lastName, user.PHOTOPRESENT photoPresent, "
			+ " (select COUNT(ID) from ForumItemLike FL where FL.ForumItemId = f.id) likeCount "
			+ " from ForumItem f, Forum forum, User user "
			+ " where forum.id = f.forumid and user.id = f.AUTHORID "
			//+ " and forum.communityId = ? "
			+ " and f.deletestatus <> 9 "
			+ getFilterTagSubQuery(filterTagList, "Forumitem", "f")
			+ " )  A "
			+ itemTypeClause ;

		QueryScroller scroller = null;
		scroller = getQueryScroller(query, null);

		if (sortBy != null && !sortBy.equals("")) {
			if ("Most Liked".equals(sortBy)) {
				scroller.addScrollKey("STEMP.likeCount", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_INTEGER);
			} else if ("Name".equals(sortBy)) {
				scroller.addScrollKey("STEMP.itemTitle", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
			} else if ("Top Rated".equals(sortBy)) {
				scroller.addScrollKey("STEMP.avgRating", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_INTEGER);
			} else if ("Most Visited".equals(sortBy)) {
				scroller.addScrollKey("STEMP.posterid", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_INTEGER);
			} else {
				scroller.addScrollKey("STEMP.datePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
			}
		} else {
			scroller.addScrollKey("STEMP.datePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}
		return scroller;
	}

	private String getFilterTagSubQuery(String filterTagList, String pType, String type){
		String filterTag = "";
		int count = 0;
		if(filterTagList != null && !"".equals(filterTagList)) {
			String filterTagPart = "";
			if (filterTagList.contains(",")) {
				StringTokenizer st = new StringTokenizer(filterTagList, ",");
				while (st.hasMoreTokens()) {
					String tag = st.nextToken().trim().toLowerCase();
					filterTagPart += " T.TagText = \'"+tag+"\' ";
					if (st.hasMoreTokens()) filterTagPart += " or ";
					count++;
				}
			} else {
				filterTagPart = " T.TagText = \'"+filterTagList+"\' ";
				count = 1;
			}
			filterTag = " and exists (select PARENTID from (select count(ID), count(COMMUNITYID), PARENTID  from Tag T where PARENTTYPE = '"+pType+"' ";
			filterTag = filterTag + " and " +filterTagPart+ " group by PARENTID having count(ID) >= "+count+" and count(COMMUNITYID) = "+count+") as T where T.PARENTID = "+type+".Id) " ;
		}
		return filterTag;
	}

	public int countCommunitiesForConnection(int userId, int currUserId) throws Exception {
		String query = "";
		if (currUserId == 0) {
			query = "select count(C.ID) from MemberList L, Membership M, Community C, User U  where M.USERID = ? and C.CreatorId = U.ID "
				+ "and M.MEMBERLISTID = L.ID and L.COMMUNITYID = C.ID and C.SysType = 'PublicCommunity' and C.Status = " 
				+ Community.STATUS_ACTIVE;
			return getSimpleJdbcTemplate().queryForInt(query, userId);
		} else {
			query = "select count(A.ID) from ( " 
				+ " select C.ID from MemberList L, Membership M, Community C, User U, MemberList L2, Membership M2 " 
				+ " where M.USERID = ? and C.CreatorId = U.ID "
				+ " and M.MEMBERLISTID = L.ID and C.SysType = 'PrivateCommunity' " 
				+ " and L.COMMUNITYID = C.ID and M2.USERID = ? and M2.MEMBERLISTID = L2.ID and L2.COMMUNITYID = C.ID and C.Status = " 
				+ Community.STATUS_ACTIVE
				+ " union "
				+ " select C.ID from MemberList L, Membership M, Community C, User U  where M.USERID = ? and C.CreatorId = U.ID "
				+ "and M.MEMBERLISTID = L.ID and L.COMMUNITYID = C.ID and C.SysType != 'PrivateCommunity' and C.Status = " 
				+ Community.STATUS_ACTIVE 
				+ " ) A ";
			return getSimpleJdbcTemplate().queryForInt(query, userId, currUserId, userId);
		}
	}
	
	public List<CommunityEntryPannelDto> getMostActiveCommunities(int max) throws Exception
	{
		String limit = "";
			if (max > 0) {
				limit = " LIMIT " + max + " ";
			}
		String query = "select cid from (select cid, count(*) from ( "
			+ " select CB.CommunityId as cid from BlogEntry E, CommunityBlog CB where CB.Id = E.CommunityBlogId "
			+ " and E.DatePosted > current_timestamp - INTERVAL 60 day "
			+ " union all"
			+ " select DL.CommunityId as cid from DocumentLibrary DL, Document D where D.LibraryId = DL.ID and D.DatePosted > current_timestamp - INTERVAL 60 day "
			+ " union all"
			+ " select FM.CommunityId as cid from Forum FM, ForumItem F where F.ForumId = FM.ID and F.DatePosted > current_timestamp - INTERVAL 60 day "
			+ " union all"
			+ " select WK.CommunityId as cid from Wiki WK, WikiEntry W where W.WikiId = WK.ID and W.DatePosted > current_timestamp - INTERVAL 60 day "
			+ ") T, Community C where C.Id = T.cid and C.Status = 0 and C.CommunityLogoPresent = 'Y' and C.SysType != 'PrivateCommunity' group by cid order by 2 desc  "+ limit +" ) A";

		List data = getJdbcTemplate().queryForList(query);
		List<CommunityEntryPannelDto> result = new ArrayList<CommunityEntryPannelDto>(data.size());
		String wc = " ( ";
		for (int n = 0; n < data.size(); n++) {
			Map m = (Map) data.get(n);
			int id = ((Integer) m.get("cid")).intValue();
			wc = wc + id;
			if (n < data.size() - 1) {
				wc = wc + " , ";
			}
		}
		wc = wc + " ) ";
		return getCommunityEntryPannelDtoForId(wc);
	}
	
	private List<CommunityEntryPannelDto> getCommunityEntryPannelDtoForId(String wClause) throws Exception {
		String sql = "select C.ID as entryId, C.Name, C.Created, C.CommunityLogoPresent logoPresent, " + 
		" (select count(*) from Membership MS, MemberList ML where ML.CommunityId = C.ID and MS.MemberListId = ML.Id) memberCount " +
		" from Community C where C.ID in "+ wClause;
		List<CommunityEntryPannelDto> list = getBeanList(sql, CommunityEntryPannelDto.class);
		return list;
	}

	public List<CommunityEntryPannelDto> getMostRecentCommunities(int max) throws Exception 
	{              
		String sql = "select C.ID as entryId, C.Name, C.Created, C.CommunityLogoPresent logoPresent " + 
		" from Community C " +
		" where C.SysType != 'PrivateCommunity' and C.CommunityLogoPresent = 'Y' and C.Status = " + Community.STATUS_ACTIVE +
		" order by Created desc ";                    

		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}
		List<CommunityEntryPannelDto> list = getBeanList(sql, CommunityEntryPannelDto.class);           
		return list;                      
	}
	
	public List<CommunityEntryPannelDto> getMostViewedCommunities(int max) throws Exception 
	{              
		String sql = "select distinct T.CommunityId as entryId, C.Name, C.welcomeText, C.Created, C.VisitCount, " +
		" (select count(*) from Membership MS, MemberList ML where ML.CommunityId = C.ID and MS.MemberListId = ML.Id) memberCount, countComm from ( " +
		" select CommunityId, count(*) as countComm from CommunityVisit where Created > current_timestamp - INTERVAL 30 day " +
		" group by CommunityId order by 2 desc ) T, Community C " +
		" where T.CommunityId = C.ID and C.SysType != 'PrivateCommunity' and C.CommunityLogoPresent = 'Y' " +
		" and C.CommunityBannerPresent = 'Y' and C.Status = " + Community.STATUS_ACTIVE + " order by countComm desc ";
		
		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}

		List<CommunityEntryPannelDto> list = getBeanList(sql, CommunityEntryPannelDto.class);           
		return list;                    
	}
	
	public List<BlogEntryPannelDto> listAllTopStories(int max) throws Exception 
	{              
		String sql = "select distinct E.ID as entryId, E.DatePosted, E.Body, E.Title, CONCAT_WS(' ',U.FirstName,U.LastName) as fullName, U.Id as userId, U.PHOTOPRESENT, BC.CommunityId, " +
		"(select count(*) from BlogEntryLike L where L.BlogEntryId = E.Id) as LikeCount " + 
		"from BlogEntry E, User U, CommunityBlog BC, Community C, Image I " +
		"where BC.Id = E.CommunityBlogId and C.SysType != 'PrivateCommunity' and I.BlogEntryId = E.Id " +
		"and BC.CommunityId = C.Id " +
		"and U.Id = E.PosterId " +
		" order by E.Visitors desc ";                    

		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}
		List<BlogEntryPannelDto> list = getBeanList(sql, BlogEntryPannelDto.class);           
		return list;                      
	}
	
	public List<ForumTopicPannelDto> listAllTopTopics(int max) throws Exception 
	{              
		String sql = "select distinct T.ID as topicId, T.DatePosted, T.Subject, CONCAT_WS(' ',U.FirstName,U.LastName) as fullName, U.Id as AuthorId, U.PHOTOPRESENT, F.CommunityId, " +
		" (select count(*) from ForumItemLike L where L.ForumItemId = T.Id) As topicLike, " +
		" (select count(*)  from ForumResponse R where R.TopicId = T.Id and R.DeleteStatus<>9 and T.DeleteStatus<>9) as ResponseCount " +
		" from ForumTopic T, Forum F, User U, Community C " +
		" where F.CommunityId = C.Id and C.SysType != 'PrivateCommunity' and T.ForumId = F.Id " +
		" and U.Id = T.AuthorId and T.Subject != '' " +
		" order by topicLike desc ";                    

		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}
		List<ForumTopicPannelDto> list = getBeanList(sql, ForumTopicPannelDto.class);           
		return list;                      
	}
	
	public List<WikiEntryPannelDto> listAllWikiEntries(int max) throws Exception 
	{              
		String sql = "select * from ( select distinct E.ID as entryId, E.DatePosted, E.Title, CONCAT_WS(' ',U.FirstName,U.LastName) as fullName, U.Id as userId, U.PHOTOPRESENT, W.CommunityId, " +
		" (select COUNT(ID) from WikiEntryLike WL where WL.WikiEntryId = E.id) as likeCount, " +
		" (select count(*) from Image I where I.WikiEntryId = E.Id ) As ImageCount, " +
		" (select count(*) from Image I, WikiEntrySection WES where I.WikiEntrySectionId = WES.Id and WES.WikiEntryId = E.Id) As SectionImageCount " +
		" from WikiEntry E, Wiki W, User U, Community C " +
		" where W.CommunityId = C.Id and C.SysType != 'PrivateCommunity' and E.WikiId = W.Id and E.EntrySequence = " + Integer.MAX_VALUE +
		" and U.Id = E.PosterId  ) as T where ( T.ImageCount > 0 or  T.SectionImageCount > 0 ) " +
		" order by T.likeCount desc ";                    

		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}
		List<WikiEntryPannelDto> list = getBeanList(sql, WikiEntryPannelDto.class);           
		return list;                      
	}
	
	public List<EventPannelDto> getUpcomingEvents(int max) throws Exception
	{
		String sql = "select E.Id as EntryId, E.Name, E.PosterId, E.ContactName, E.StartDate, E.EndDate, E.City, E.Country, EC.CommunityID " +
		" from Event E, EventCalendar EC, Community C where EC.CommunityID = C.Id and E.CalendarId = EC.ID and C.SysType != 'PrivateCommunity' " +
		//" and E.PhotoPresent = 'Y' and E.status > 0 and E.StartDate > CURRENT_TIMESTAMP and C.Status = " + Community.STATUS_ACTIVE ;
		" and E.PhotoPresent = 'Y' and E.status > 0  and C.Status = " + Community.STATUS_ACTIVE ;
		
		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}
		List<EventPannelDto> list = getBeanList(sql, EventPannelDto.class);
		return list;
	}
	
	public List getAllCommunities() throws Exception
	{
		String query = "select * from Community";
			return getBeanList(query, Community.class);
	}
}