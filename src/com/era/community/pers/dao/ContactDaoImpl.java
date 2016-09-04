package com.era.community.pers.dao; 

import java.util.List;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.pers.ui.dto.UserDto;

public class ContactDaoImpl extends com.era.community.pers.dao.generated.ContactDaoBaseImpl implements ContactDao
{

	public Contact getContact(int contactOwnerId, int contactUserId) throws Exception
	{
		Contact contact = (Contact)getEntityWhere(" (OwningUserId = ? and ContactUserId = ? ) or (ContactUserId = ? and OwningUserId = ? )", contactOwnerId, contactUserId, contactOwnerId, contactUserId);
		return contact;
	}

	public void deleteAllContactsForUser(int contactOwnerId) throws Exception
	{
		String sql="delete from Contact C where C.OwningUserId = ?";

		getSimpleJdbcTemplate().update(sql, contactOwnerId);       
	}

	/*
	 * Remove this user from other user's contact list - (used when member is marked inactive)
	 */
	public void deleteUserAsContact(int contactUserId) throws Exception
	{
		String sql="delete from Contact C where C.ContactUserId = ?";

		getSimpleJdbcTemplate().update(sql, contactUserId);       
	}

	public void deleteUserAsConnection(int contactOwnerId, int contactUserId) throws Exception
	{
		String sql="delete from Contact C where (C.OwningUserId = ? and C.ContactUserId = ?) or (C.ContactUserId = ? and C.OwningUserId = ?) ";

		getSimpleJdbcTemplate().update(sql, contactOwnerId, contactUserId, contactOwnerId, contactUserId);       
	}

	@Override
	public int getConnectionRequestReceivedCountForCurrentUser(int currentUserId) throws Exception 
	{
		String query = "select count(*) from CONTACT where CONTACTUSERID = ? and Status = 0 ";
		return getSimpleJdbcTemplate().queryForInt(query, currentUserId );
	}

	@Override
	public int getConnectionRequestPendingCountForCurrentUser(int currentUserId) throws Exception 
	{
		String query = "select count(*) from CONTACT where OWNINGUSERID = ? and Status in (0, 2, 3) ";
		return getSimpleJdbcTemplate().queryForInt(query, currentUserId );
	}

	@Override
	public QueryScroller listReceivedContactRequests(int userId) throws Exception {
		String sQuery = "" +
		" select U.ID ID, U.FIRSTNAME FIRSTNAME, U.LASTNAME LASTNAME, U.EMAILADDRESS EMAILADDRESS, U.CREATED CREATED, U.PHOTOPRESENT PHOTOPRESENT, U.ConnectionCount ConnectionCount, C.DATECONNECTION DATECONNECTION" +
		" from User U, Contact C where U.Validated = 'Y' and U.Id = C.OwningUserId and C.ContactUserId = ? and " +
		" C.STATUS = 0 ";

		QueryScroller scroller = getQueryScroller(sQuery, UserDto.class, userId);

		scroller.addScrollKey("STEMP.FIRSTNAME", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_TEXT);
		return scroller;
	}
	
	public List<UserDto> listConnectionForHeader(int userId) throws Exception {
		String sQuery = "" +
		" select U.ID ID, U.FIRSTNAME FIRSTNAME, U.LASTNAME LASTNAME, U.EMAILADDRESS EMAILADDRESS, U.CREATED CREATED, U.PHOTOPRESENT PHOTOPRESENT, U.ConnectionCount ConnectionCount, C.DATECONNECTION DATECONNECTION" +
		" from User U, Contact C where U.Id = C.OwningUserId and C.ContactUserId = ? and " +
		" C.STATUS = 0 and U.Validated = 'Y' order by DATECONNECTION LIMIT 3 ";

		return getBeanList(sQuery, UserDto.class, userId);
	}
	
	@Override
	public QueryScroller listSentContactRequests(int userId) throws Exception {
		String sQuery = "" +
		" select U.ID ID, U.FIRSTNAME FIRSTNAME, U.LASTNAME LASTNAME, U.EMAILADDRESS EMAILADDRESS, U.CREATED CREATED, U.PHOTOPRESENT PHOTOPRESENT, U.ConnectionCount ConnectionCount, C.DATECONNECTION DATECONNECTION" +
		" from User U, Contact C where U.Id = C.ContactUserId and C.OwningUserId = ? and " +
		" C.STATUS = 0 and U.Validated = 'Y' " ;

		QueryScroller scroller = getQueryScroller(sQuery, UserDto.class, userId);

		scroller.addScrollKey("STEMP.FIRSTNAME", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_TEXT);
		return scroller;
	}
	
	@Override
	public int countReceivedContactRequests(int userId) throws Exception {
		String sQuery = " select count(U.ID) " +
		" from User U, Contact C where U.Id = C.OwningUserId and C.ContactUserId = ? and " +
		" C.STATUS = 0 and U.Validated = 'Y' ";
		return getSimpleJdbcTemplate().queryForInt(sQuery, userId);
	}
	
	@Override
	public int countSentContactRequests(int userId) throws Exception {
		String sQuery = " select count(U.ID) " +
		" from User U, Contact C where U.Id = C.ContactUserId and C.OwningUserId = ? and " +
		" C.STATUS = 0 and U.Validated = 'Y' " ;
		return getSimpleJdbcTemplate().queryForInt(sQuery, userId);
	}
	
	public QueryScroller listCommonConnections(int ownerId, int currentUserId) throws Exception {
		String sQuery = " select  U.ID ID, U.FIRSTNAME FIRSTNAME, U.LASTNAME LASTNAME, U.EMAILADDRESS EMAILADDRESS, U.CREATED CREATED, " +
				" U.PHOTOPRESENT PHOTOPRESENT, U.ConnectionCount ConnectionCount" +
				//" , '' DATECONNECTION " +
				" from User U, " +
				" (select T1.FRIENDS_ID from ( select CASE WHEN ContactUserId = ? THEN OwningUserId ELSE ContactUserId END FRIENDS_ID " +
				" from Contact where (ContactUserId = ? or OwningUserId = ?) and STATUS = 1 and DATECONNECTION is not null ) as T1, " +
				" ( select CASE WHEN ContactUserId = ? THEN OwningUserId ELSE ContactUserId END FRIENDS_ID from Contact " +
				" where (ContactUserId = ? or OwningUserId = ? ) and STATUS = 1 and DATECONNECTION is not null ) as T2 " +
				" where T1.FRIENDS_ID = T2.FRIENDS_ID) AS C where U.Id = C.FRIENDS_ID and U.Validated = 'Y' ";

		QueryScroller scroller = getQueryScroller(sQuery, true, UserDto.class, ownerId, ownerId, ownerId, currentUserId, currentUserId, currentUserId);

		scroller.addScrollKey("STEMP.FIRSTNAME", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_TEXT);
		return scroller;
	}
	
	public int countCommonConnections(int ownerId, int currentUserId) throws Exception {
		String sQuery = " select count(U.ID) " +
				" from User U, " +
				" (select T1.FRIENDS_ID from ( select CASE WHEN ContactUserId = ? THEN OwningUserId ELSE ContactUserId END FRIENDS_ID " +
				" from Contact where (ContactUserId = ? or OwningUserId = ?) and STATUS = 1 and DATECONNECTION is not null ) as T1, " +
				" ( select CASE WHEN ContactUserId = ? THEN OwningUserId ELSE ContactUserId END FRIENDS_ID from Contact " +
				" where (ContactUserId = ? or OwningUserId = ? ) and STATUS = 1 and DATECONNECTION is not null ) as T2 " +
				" where T1.FRIENDS_ID = T2.FRIENDS_ID) AS C where U.Id = C.FRIENDS_ID and U.Validated = 'Y' ";
		return getSimpleJdbcTemplate().queryForInt(sQuery, ownerId, ownerId, ownerId, currentUserId, currentUserId, currentUserId);
	}
	
	public List listAllMyContacts(int userId, int max) throws Exception
	{
		String query = "select * from ( " +
		" select U.*" +
		" from User U, Contact C where U.Id = C.ContactUserId and C.OwningUserId = ? and " +
		" C.STATUS = 1 and C.DATECONNECTION is not null and U.Validated = 'Y' " +
		" union " +
		" select U.*" +
		" from User U, Contact C where U.Id = C.OwningUserId and C.ContactUserId = ? and " +
		" C.STATUS = 1 and C.DATECONNECTION is not null and U.Validated = 'Y' " +
		" ) A";
		
		if (max != 0) {
			query += " LIMIT " + max + " ";
		}
		return getBeanList(query, User.class, userId, userId);
	}
	
	public QueryScroller showPeopleYouMayKnow(int userId) throws Exception
	{
		String query = " select FRND.frndId, U.ID ID, U.FIRSTNAME FIRSTNAME, U.LASTNAME LASTNAME, U.EMAILADDRESS EMAILADDRESS, U.CREATED CREATED, U.PHOTOPRESENT PHOTOPRESENT, U.ConnectionCount ConnectionCount from ( " +
		" select ContactUserId frndId from Contact where OwningUserId IN ( " +
		" select CASE WHEN OwningUserId = ? THEN ContactUserId ELSE OwningUserId END FRIENDS_ID from Contact where (OwningUserId = ? or ContactUserId = ?) " +
		" ) and ContactUserId <> ? " +
		" UNION " +
		" select OwningUserId frndId from Contact where ContactUserId IN (	" +
		" select CASE WHEN OwningUserId = ? THEN ContactUserId ELSE OwningUserId END FRIENDS_ID from Contact where (OwningUserId = ? or ContactUserId = ?) " +
		" ) and OwningUserId <> ? ) as FRND, User U " +
		" where FRND.frndId = U.ID and U.Validated = 'Y' and " +
		" FRND.frndId NOT IN (select CASE WHEN OwningUserId = ? THEN ContactUserId ELSE OwningUserId END FRIENDS_ID from Contact where (OwningUserId = ? or ContactUserId = ?)) ";
		
		QueryScroller scroller =  getQueryScroller(query, null, userId, userId, userId, userId, userId, userId, userId, userId, userId, userId, userId);
		scroller.addScrollKey("CREATED", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}
	
	public List<UserDto> listPeopleYouMayKnowForHeader(int userId, int max) throws Exception {
		String query = " select FRND.frndId, U.ID ID, U.FIRSTNAME FIRSTNAME, U.LASTNAME LASTNAME, U.EMAILADDRESS EMAILADDRESS, U.CREATED CREATED, U.PHOTOPRESENT PHOTOPRESENT, U.ConnectionCount ConnectionCount from ( " +
		" select ContactUserId frndId from Contact where OwningUserId IN ( " +
		" select CASE WHEN OwningUserId = ? THEN ContactUserId ELSE OwningUserId END FRIENDS_ID from Contact where (OwningUserId = ? or ContactUserId = ?) " +
		" ) and ContactUserId <> ? " +
		" UNION " +
		" select OwningUserId frndId from Contact where ContactUserId IN (	" +
		" select CASE WHEN OwningUserId = ? THEN ContactUserId ELSE OwningUserId END FRIENDS_ID from Contact where (OwningUserId = ? or ContactUserId = ?) " +
		" ) and OwningUserId <> ? ) as FRND, User U " +
		" where FRND.frndId = U.ID and U.Validated = 'Y' and " +
		" FRND.frndId NOT IN (select CASE WHEN OwningUserId = ? THEN ContactUserId ELSE OwningUserId END FRIENDS_ID from Contact where (OwningUserId = ? or ContactUserId = ?)) ";
		
		if (max != 0) {
			query += " LIMIT " + max + " ";
		}
		return getBeanList(query, UserDto.class, userId, userId, userId, userId, userId, userId, userId, userId, userId, userId, userId);
	}
}