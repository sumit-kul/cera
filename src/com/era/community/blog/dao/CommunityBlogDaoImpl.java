package com.era.community.blog.dao; 

import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;
import support.community.framework.Option;

import com.era.community.blog.ui.dto.BlogAuthorDto;
import com.era.community.blog.ui.dto.BlogEntryDto;
import com.era.community.communities.dao.Community;
import com.era.community.monitor.ui.dto.SubscriptionItemDto;
import com.era.community.pers.dao.User;

public class CommunityBlogDaoImpl extends com.era.community.blog.dao.generated.CommunityBlogDaoBaseImpl implements CommunityBlogDao
{
	public CommunityBlog getCommunityBlogForCommunity(Community comm) throws Exception
	{
		return (CommunityBlog)getEntityWhere("CommunityId=?", comm.getId() );
	}
	
	public CommunityBlog getCommunityBlogForCommunityId(int commId) throws Exception
	{
		return (CommunityBlog)getEntityWhere("CommunityId=?", commId );
	}

	public List getCommunityBlogOptionList() throws Exception
	{             
		final String SQL="select name as label, id as value from " + getTableName();
		List lis=getBeanList(SQL, null, Option.class);        
		return lis;        
	}

	public QueryScroller getCommunityBlogOptionListForUser( User user ) throws Exception
	{                     
		String query = "select M.DateJoined, M.DateLastVisit, M.Role, BC.Id as communityBlogId, C.*  from Community C, CommunityBlog BC, " +
		"MemberList L, Membership M " + 
		"where M.UserId = ? " +
		"and M.MemberListId = L.Id " +
		"and L.CommunityId = C.Id " +      
		"and C.Id = BC.CommunityID and C.SysType = 'PublicCommunity' ";

		QueryScroller scroller = getQueryScroller(query, null, user.getId());
		scroller.addScrollKey("STEMP.NAME", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		return scroller;        
	}

	public QueryScroller listBlogEntries(CommunityBlog cb) throws Exception
	{
		final String SQL = "select E.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PHOTOPRESENT " +
		", (select count(*) from BlogEntryComment C where C.BlogEntryId = E.Id) As CommentCount " +  
		", (select count(*) from Image I where I.BlogEntryId = E.Id) As ImageCount " + 
		"from BlogEntry E, User U, CommunityBlog BC " +
		"where BC.Id = E.CommunityBlogId " +
		"and U.Id = E.PosterId " +
		"and BC.Id = ? ";

		QueryScroller scroller = getQueryScroller(SQL, BlogEntryDto.class, cb.getId());
		return scroller;

	}

	public QueryScroller listBlogEntriesForUser(CommunityBlog communityBlogId, User user, String sortBy) throws Exception
	{
		return listBlogEntriesForUserId(communityBlogId, user.getId(), sortBy);
	}

	public QueryScroller listBlogEntriesForUserId(CommunityBlog communityBlog, int userId, String sortBy) throws Exception
	{
		final String SQL = "select E.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PHOTOPRESENT, BCE.CommunityId " +
		"from BlogEntry E, CommunityBlog BC, User U " +
		"where BC.Id = E.CommunityBlogId " +
		"and BC.Id = ? " +
		"and E.PosterId = ? " +
		"and U.Id = E.PosterId"; 

		QueryScroller scroller = getQueryScroller(SQL, BlogEntryDto.class, communityBlog.getId(), userId);
		if (sortBy != null && "Title".equalsIgnoreCase(sortBy)) {
			scroller.addScrollKey("STEMP.Title", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		} else {
			scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}
		return scroller;
	}

	public Date getLatestPostDate(CommunityBlog cb) throws Exception
	{
		String query = "select max(Modified) from BlogEntry where CommunityBlogId = ?";
		return (Date)getSimpleJdbcTemplate().queryForObject(query, Date.class, cb.getId() );
	}

	public List getItemsSince(CommunityBlog cb, Date date) throws Exception
	{
		String sQuery = "Select BE.Id as ItemId, BE.Title as ItemTitle , BE.DatePosted as ItemDate, "+
		" BE.PosterId AS AuthorId, CONCAT_WS(' ',U.FirstName,U.LastName) AS AuthorName " +
		" from BlogEntry BE, User U, CommunityBlog BC "+
		" where BE.CommunityBlogId = BC.Id and BE.PosterId = U.Id and BE.DatePosted > ? and BC.Id = ? ";

		return  getBeanList(sQuery, SubscriptionItemDto.class, date, cb.getId());
	}
	
	public List getAuthorsForCommunityBlog(int communityBlogId, int max) throws Exception 
	{              
		String sql = "select BC.id blogId, U.Id authorId, U.FirstName firstName, U.LastName lastName " +
				" from CommunityBlog BC, Community C, MemberList L, Membership M, User U " +
				" where M.MEMBERLISTID = L.ID and L.CommunityId = BC.CommunityId and BC.CommunityId = C.ID " +
				" and M.UserId = U.Id and BC.Id = ? ";                  

		/* Limit number of results returned if max set */
		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}
		List lis=getBeanList(sql, BlogAuthorDto.class, communityBlogId);           
		return lis;                      
	}
	
	public int getAuthorCountCommunityBlog(int bcId) throws Exception 
	{              
		String query = "select count(U.Id) from CommunityBlog BC, Community C, MemberList L, Membership M, User U " +
				" where M.MEMBERLISTID = L.ID and L.CommunityId = BC.CommunityId and BC.CommunityId = C.ID " +
				" and M.UserId = U.Id and BC.Id = ? ";
		return getSimpleJdbcTemplate().queryForInt(query, bcId);                       
	}
	
	public List<BlogEntryDto> getEntrisForCommunityBlog(int communityBlogId, int max) throws Exception 
	{              
		String sql = "select distinct E.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PHOTOPRESENT, BC.CommunityId ," +
		"(select count(*) As CommentCount from BlogEntryComment C where C.BlogEntryId = E.Id) " +    
		"from BlogEntry E, User U, CommunityBlog BC " +
		"where BC.Id = E.CommunityBlogId " +
		"and U.Id = E.PosterId " +
		"and BC.Id = ? ";                    

		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}
		List<BlogEntryDto> list = getBeanList(sql, BlogEntryDto.class, communityBlogId);           
		return list;                      
	}
	
	public QueryScroller listAllBlogsForHeader(String sortBy, String filterTagList) throws Exception
	{
		String filterTagPers = "";
		String filterTagComm = "";
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
			filterTagPers = " and exists (select ParentId from (select count(ID), ParentId  from Tag T where PARENTTYPE = 'PersonalBlog' " +
						" and " +filterTagPart+ " group by ParentId having count(ID) >= "+count+" ) as T where T.ParentId = BPC.Id) " ;
			
			filterTagComm = " and exists (select ParentId from (select count(ID), ParentId  from Tag T where PARENTTYPE = 'CommunityBlog' " +
			" and " +filterTagPart+ " group by ParentId having count(ID) >= "+count+" ) as T where T.ParentId = BC.Id) " ;
		}

		final String SQL = "select T.* from ( " +
		// Personal Blog
		" select distinct E.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PHOTOPRESENT, 'N' as logoPresent, 0 as commId, " +
		" (select count(*) from BlogEntryComment C where C.BlogEntryId = E.Id) As CommentCount , " +
		" (select count(*) from Image I where I.BlogEntryId = E.Id) As ImageCount, " +
		" (select count(*) from BlogEntryLike L where L.BlogEntryId = E.Id) As LikeCount " +
		" from BlogEntry E, PersonalBlog BPC, User U " +
		" where BPC.Id = E.PersonalBlogId and U.Id = E.PosterId " + filterTagPers +
		" union " + 

		// Community Blog
		
		" select distinct E.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PHOTOPRESENT, C.CommunityLogoPresent as logoPresent, BC.CommunityId as commId," +
		" (select count(*) from BlogEntryComment C where C.BlogEntryId = E.Id) As CommentCount , " +    
		" (select count(*) from Image I where I.BlogEntryId = E.Id) As ImageCount , " +
		" (select count(*) from BlogEntryLike L where L.BlogEntryId = E.Id) As LikeCount " +
		" from BlogEntry E, User U, CommunityBlog BC, Community C " +
		" where BC.Id = E.CommunityBlogId and BC.CommunityId = C.ID and U.Id = E.PosterId " + filterTagComm +
		" ) as T";

		QueryScroller scroller = getQueryScroller(SQL, true, null);
		
		if ("1".equalsIgnoreCase(sortBy) || "Most Recent".equalsIgnoreCase(sortBy)) {
			scroller.addScrollKey("STEMP.Created", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		} else if ("2".equalsIgnoreCase(sortBy)) {
			scroller.addScrollKey("STEMP.Title", "title", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		} else if ("3".equalsIgnoreCase(sortBy)) {
			scroller.addScrollKey("STEMP.LikeCount", "LikeCount", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_INTEGER);
			scroller.addScrollKey("STEMP.Created", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		} else if ("4".equalsIgnoreCase(sortBy)) {
			scroller.addScrollKey("STEMP.CommentCount", "CommentCount", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_INTEGER);
			scroller.addScrollKey("STEMP.Created", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		}
		return scroller;
	}
}