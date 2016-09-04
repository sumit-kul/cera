package com.era.community.blog.dao; 

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import support.community.database.QueryScroller;
import support.community.framework.Option;

import com.era.community.blog.ui.dto.BlogEntryHeaderDto;
import com.era.community.blog.ui.dto.BlogEntryPannelDto;
import com.era.community.communities.dao.Community;

public class BlogEntryDaoImpl extends com.era.community.blog.dao.generated.BlogEntryDaoBaseImpl implements BlogEntryDao
{
	public List getCommBlogEntryAppearsIn(int entryId) throws Exception
	{
		final String SQL="select  C.Name as label, BC.CommunityId as value "
			+ "from Community C, BlogEntry BE, CommunityBlog BC " 
			+ "where BE.CommunityBlogId = BC.Id and C.Id = BC.CommunityId and BE.Id = ?  ";

		List lis = getBeanList(SQL,  Option.class, entryId);     
		return lis;        
	}

	public List getCommunitiesForBlogEntry(int beid) throws Exception
	{
		String query = "select COMM.NAME from TBBCEN BCEN , TBBENT BENT, TBBCON BCON ,  TBCOMM COMM " +
		" where BCEN.BLOGENTRYID = BENT.ID and BCON.ID = BCEN.CommunityBlogID " +
		" and COMM.ID = BCON.COMMUNITYID and BCEN.BLOGENTRYID = ? order by BCON.Created desc LIMIT 10 ";
		List data = getSimpleJdbcTemplate().queryForList(query, beid);
		List<String> result = new ArrayList<String>(data.size());
		for (int n = 0; n < data.size(); n++) {
			Map m = (Map) data.get(n);
			String name = ((String) m.get("Name")).toString();
			if (name.length() >= 50) {
				name = name.substring(0, 50).concat("...");
			} 
			result.add(name);
		}
		return result;
	}

	public QueryScroller listAllEntries() throws Exception
	{
		final String SQL = "select T.* from ( " +
		// Personal Blog
		" select distinct E.* from BlogEntry E, PersonalBlog BPC where BPC.Id = E.PersonalBlogId " +
		" union " + 
		
		// Community Blog
		" select distinct E.* from BlogEntry E, CommunityBlog BC, Community C " +
		" where BC.Id = E.CommunityBlogId and BC.CommunityId = C.ID and C.SysType != 'PrivateCommunity' " +
		" ) as T";
		QueryScroller scroller = getQueryScroller(SQL, null);
		return scroller;        
	}
	
	public int getEntryCount() throws Exception
	{        
		String query = "select count(*) from BlogEntry";
		return getJdbcTemplate().queryForInt(query);
	}

	public int getEntryCount(String sRegion) throws Exception
	{        
		String query = "select count(*) from BlogEntry";
		return getJdbcTemplate().queryForInt(query);
	}

	public List<BlogEntryPannelDto> listAllTopStories(int max) throws Exception 
	{              
		String sql = "select E.ID as entryId, E.Title, CONCAT_WS(' ',U.FirstName,U.LastName) as fullName, U.Id as userId, U.PHOTOPRESENT, BC.CommunityId, " +
		"(select count(*) from BlogEntryLike L where L.BlogEntryId = E.Id) LikeCount, E.VISITORS, " + 
		"(select count(*) from BlogEntryComment C where C.BlogEntryId = E.Id) CommentCount , " +
		" (select COUNT(ID) from Image I where I.BlogEntryId = E.Id) ImageCount " +
		"from BlogEntry E, User U, CommunityBlog BC " +
		"where BC.Id = E.CommunityBlogId and U.Id = E.PosterId " +
		" order by E.VISITORS desc ";                    

		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}
		List<BlogEntryPannelDto> list = getBeanList(sql, BlogEntryPannelDto.class);           
		return list;                      
	}

	public List<BlogEntryPannelDto> listAllLatestPosts(int max) throws Exception 
	{              
		String sql = "select E.ID as entryId, E.Title, CONCAT_WS(' ',U.FirstName,U.LastName) as fullName, U.Id as userId, U.PHOTOPRESENT, BC.CommunityId " +
		"from BlogEntry E, User U, CommunityBlog BC " +
		"where BC.Id = E.CommunityBlogId and U.Id = E.PosterId " +
		" order by DatePosted desc ";                    

		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}
		List<BlogEntryPannelDto> list = getBeanList(sql, BlogEntryPannelDto.class);           
		return list;                      
	}
	
	public BlogEntryHeaderDto getBlogEntryForHeader(int entryId) throws Exception
	{
		String query = "select E.Id, E.Title, CAST(E.BODY as CHAR(1000) ) itemDesc, " +
			" (select COUNT(ID) from BlogEntryComment BC where BC.BLOGENTRYID = E.id) as commentCount, " +
			" E.DatePosted datePosted, E.Visitors Visitors, U.Id Posterid, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT photoPresent, " +
			" (select COUNT(ID) from Image I where I.BlogEntryId = E.Id) ImageCount , " +
			" (select COUNT(ID) from BlogEntryLike BL where BL.BlogEntryId = E.id) likeCount " +
			" from BlogEntry E, User U where U.Id = E.PosterId and E.Id = ? ";
		return getBean(query, BlogEntryHeaderDto.class, entryId);
	}
}