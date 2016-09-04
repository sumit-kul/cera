package com.era.community.blog.dao; 

import java.util.Date;
import java.util.List;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.blog.ui.dto.BlogAuthorDto;
import com.era.community.blog.ui.dto.BlogEntryDto;
import com.era.community.pers.dao.User;

public class PersonalBlogDaoImpl extends com.era.community.blog.dao.generated.PersonalBlogDaoBaseImpl implements PersonalBlogDao
{
	public PersonalBlog getPersonalBlogForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (PersonalBlog)getEntity(PersonalBlog.class, keys);
	}
	
	public PersonalBlog getPersonalBlogForUser(int pbId, int userId) throws Exception
	{
		return (PersonalBlog)getEntityWhere("Id = ? and UserId = ?", pbId, userId );
	}

	public QueryScroller getAllBlogsForUser(User usr) throws Exception 
	{
		String query = "select * from ("
			// Personal Blog
			+ " select PB.id blogId, PB.Name blogName, PB.systype blogType, CAST(PB.Description as CHAR(100) ) description, " 
			+ " PB.Created created, 0 as communityId from "
			+ " PersonalBlog PB, User U where PB.UserId = U.Id and U.Id = ? "
			+ " union "

			// Community Blog
			+ " select BC.id blogId, BC.Name blogName, BC.systype blogType, '' description, " 
			+ " BC.Created created, BC.CommunityId communityId from "
			+ " CommunityBlog BC, User U, MemberList L, Membership M where M.MEMBERLISTID = L.ID " 
			+ " and L.CommunityId = BC.CommunityId and M.USERID = ? "
			+ " ) A "; 

		QueryScroller scroller = getQueryScroller(query, null, usr.getId(), usr.getId());
		scroller.addScrollKey("STEMP.created", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller; 
	}

	public QueryScroller getBlogsForBlogAuther(User usr, User current) throws Exception 
	{
		String sub = "";
		if (current != null) {
			sub = " union select BC.id blogId, BC.Name blogName, BC.systype blogType, '' description, C.Name CommunityName, " 
				+ " BC.Created created, C.CreatorId as ownerId, CONCAT_WS(' ',U.FirstName,U.LastName) as ownerName, C.CommunityLogoPresent as PHOTOPRESENT, BC.CommunityId as commId from "
				+ " CommunityBlog BC, User U, Community C, MemberList L, Membership M, BlogAuthor BA where C.Id = BC.CommunityId and C.SysType = 'PrivateCommunity' " 
				+ " and BA.BlogId = BC.ID and U.ID = BA.UserId and BA.UserId = ?  and BA.UserId = U.ID and M.MEMBERLISTID = L.ID and L.COMMUNITYID = C.Id and M.USERID = ? and C.Status = 0 ";
		}

		String query = "select * from ("
			// Personal Blog
			+ " select PB.id blogId, PB.Name blogName, PB.systype blogType, CAST(PB.Description as CHAR(100) ) description, CAST('' as CHAR(100) ) CommunityName, " 
			+ " PB.Created created, PB.UserId as ownerId, CONCAT_WS(' ',U.FirstName,U.LastName) as ownerName, U.PHOTOPRESENT, 0 as commId from "
			+ " PersonalBlog PB, BlogAuthor BA, User U where U.ID = BA.UserId and BA.PersonalBlogId = PB.ID and BA.UserId = ? "
			+ " union "

			// Public Community Blog
			+ " select BC.id blogId, BC.Name blogName, BC.systype blogType, '' description, C.Name CommunityName, " 
			+ " BC.Created created, C.CreatorId as ownerId, CONCAT_WS(' ',U.FirstName,U.LastName) as ownerName, C.CommunityLogoPresent as PHOTOPRESENT, BC.CommunityId as commId from "
			+ " CommunityBlog BC, User U, Community C, BlogAuthor BA where C.Id = BC.CommunityId and C.SysType = 'PublicCommunity' and C.Status = 0 "
			+ " and BA.BlogId = BC.ID and U.ID = BA.UserId and BA.UserId = ? "

			// Private Community Blog, I am also a member of
			+ sub


			+ " ) A "; 

		QueryScroller scroller = null;
		if (current != null) {
			scroller = getQueryScroller(query, null, usr.getId(), usr.getId(), usr.getId(), current.getId());
		} else {
			scroller = getQueryScroller(query, null, usr.getId(), usr.getId());
		}
		return scroller; 
	}

	public List getAuthorsForPersonalBlog(int personalBlogId, int max) throws Exception 
	{              
		String sql = "select PB.id blogId, U.Id authorId, U.FirstName firstName, U.LastName lastName from "
			+ " PersonalBlog PB, User U where PB.UserId = U.Id and PB.Id = ? ";                        

		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}
		List list = getBeanList(sql, BlogAuthorDto.class, personalBlogId);           
		return list;                      
	}
	
	public List<BlogEntryDto> getEntrisForPersonalBlog(int personalBlogId, int max) throws Exception 
	{              
		String sql = "select distinct E.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PHOTOPRESENT, 'PersonalBlog' as bType, " +
		"(select count(*) As CommentCount from BlogEntryComment C where C.BlogEntryId = E.Id) " +    
		"from BlogEntry E, PersonalBlog PB, User U " +
		"where PB.Id = E.PersonalBlogId and PB.Id = ? " +
		"and U.Id = E.PosterId ";                     

		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}
		List<BlogEntryDto> list = getBeanList(sql, BlogEntryDto.class, personalBlogId);           
		return list;                      
	}
	
	public int getAuthorCountPersonalBlog(int personalBlogId) throws Exception 
	{              
		String query = "select count(U.Id) from PersonalBlog PB, User U "
			+ " where PB.UserId = U.Id and PB.Id = ? ";
		return getSimpleJdbcTemplate().queryForInt(query, personalBlogId);                       
	}

	public QueryScroller listPersonalBlogEntries(PersonalBlog pb) throws Exception
	{
		final String SQL = "select distinct E.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PHOTOPRESENT, " +
		"(select count(*) As CommentCount from BlogEntryComment C where C.BlogEntryId = E.Id) " +    
		"from BlogEntry E, User U " +
		"where E.PersonalBlogId = ? " +
		"and U.Id = E.PosterId";

		QueryScroller scroller = getQueryScroller(SQL, BlogEntryDto.class, pb.getId());
		return scroller;
	}

	public Date getLatestPostDate(PersonalBlog pb) throws Exception
	{
		String query = "select max(Modified) from BlogEntry where PersonalBlogId = ?";
		return (Date)getSimpleJdbcTemplate().queryForObject(query, Date.class, pb.getId() );
	}
}