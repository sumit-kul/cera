package com.era.community.tagging.dao; 

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.communities.dao.Community;
import com.era.community.tagging.ui.dto.TagDto;
import com.era.community.tagging.ui.dto.TagSearchResultDto;

public class TagDaoImpl extends com.era.community.tagging.dao.generated.TagDaoBaseImpl implements TagDao
{
	public void clearExistingTag(int parentId, int communityId, String parentType, String tag) throws Exception
	{
		String sql="delete from " + getTableName() + " where ParentId = ? and communityId = ? and ParentType = ? and TagText = ?";
		getSimpleJdbcTemplate().update(sql, parentId, communityId, parentType, tag);        
	}

	/**
	 *  Returns a list of popular tags for cloud on community level 
	 */
	public List getTagsForAllCommunities(int maxTags, String communityOption, String filterTagList) throws Exception 
	{
		String parentClause = "";
		if (communityOption != null && !"".equals(communityOption)) {
			if (communityOption.equalsIgnoreCase("1")) {
				parentClause = " and PARENTTYPE like '%Community' ";
			} else if (communityOption.equalsIgnoreCase("2")) {
				parentClause = " and PARENTTYPE = 'PublicCommunity' ";
			} else if (communityOption.equalsIgnoreCase("3")) {
				parentClause = " and PARENTTYPE = 'PrivateCommunity' ";
			} 
		}
		String filterTag = "";
		String filterTagPart = "";
		int count = 0;
		if(filterTagList != null && !"".equals(filterTagList)) {
			if (filterTagList.contains(",")) {
				StringTokenizer st = new StringTokenizer(filterTagList, ",");
				while (st.hasMoreTokens()) {
					String tag = st.nextToken().trim().toLowerCase();
					filterTagPart += " TagText = \'"+tag+"\' ";
					if (st.hasMoreTokens()) filterTagPart += " or ";
					count++;
				}
			} else {
				filterTagPart += " TagText = \'"+filterTagList+"\' ";
				count = 1;
			}
			filterTag = " and CommunityId in (select COMMUNITYID from (select count(ID), COMMUNITYID  from Tag T where " +
			filterTagPart + " group by COMMUNITYID having count(ID) >= "+count+" )as T ) " ;
		}

		String sql="select TG.tagtext, count(*) as count from  Tag TG where CommunityId is not null " + filterTag + parentClause +
		" group by TG.tagtext order by count desc, TG.tagtext ";

		if (maxTags != 0) {
			sql += " LIMIT " + maxTags + " ";
		}
		return getBeanList(sql, TagDto.class);
	}

	/**
	 *  Returns a list of popular tags for cloud on community level only for community follower
	 */
	public List getTagsForFollowerCommunities(int maxTags, int userId, String communityOption, String filterTagList) throws Exception 
	{
		String filterTagPart = "";
		if(filterTagList != null && !"".equals(filterTagList)) {
			filterTagPart = " and ";
			if (filterTagList.contains(",")) {
				StringTokenizer st = new StringTokenizer(filterTagList, ",");
				while (st.hasMoreTokens()) {
					String tag = st.nextToken().trim().toLowerCase();
					filterTagPart += " T.TagText = \'"+tag+"\' ";
					if (st.hasMoreTokens()) filterTagPart += " or ";
				}
			} else {
				filterTagPart += " T.TagText = \'"+filterTagList+"\' ";
			}
		}

		String sql="select tagtext, count(*) as count from Tag T, Subscription S where T.CommunityId = S.CommunityId " +
		" and S.SYSTYPE= 'CommunitySubscription' and S.UserId = ? and T.PARENTTYPE like '%Community' " + filterTagPart +
		" group by tagtext order by count desc, tagtext";

		if (maxTags != 0) {
			sql += " LIMIT " + maxTags + " ";
		}
		return getBeanList(sql, TagDto.class, userId);
	}

	/**
	 *  Returns a list of popular tags for cloud on community level only for community member
	 */
	public List getTagsForMemberCommunities(int maxTags, int userId, String communityOption, String filterTagList) throws Exception 
	{
		String typeClause = "";
		if (communityOption != null && !"".equals(communityOption)) {
			if ("4".equalsIgnoreCase(communityOption)) {
				typeClause = " and M.Role = 'Owner' ";
			} else if ("5".equalsIgnoreCase(communityOption)) {
				typeClause = " and (M.Role = 'Member' or M.Role = 'Owner') ";
			} 
		}

		String filterTagPart = "";
		if(filterTagList != null && !"".equals(filterTagList)) {
			filterTagPart = " and ";
			if (filterTagList.contains(",")) {
				StringTokenizer st = new StringTokenizer(filterTagList, ",");
				while (st.hasMoreTokens()) {
					String tag = st.nextToken().trim().toLowerCase();
					filterTagPart += " T.TagText = \'"+tag+"\' ";
					if (st.hasMoreTokens()) filterTagPart += " or ";
				}
			} else {
				filterTagPart += " T.TagText = \'"+filterTagList+"\' ";
			}
		}

		String sql="select tagtext, count(*) as count from Tag T, MemberList L, Membership M where T.CommunityId = L.COMMUNITYID " +
		" and M.MEMBERLISTID = L.ID and M.UserId = ? and T.PARENTTYPE like '%Community' " + filterTagPart + typeClause +
		" group by tagtext order by count desc, tagtext";

		if (maxTags != 0) {
			sql += " LIMIT " + maxTags + " ";
		}
		return getBeanList(sql, TagDto.class, userId);
	}

	public List getPopularCommunityTagsToAdd(int maxTags) throws Exception 
	{
		String sql="select tagtext, count(*) as count from Tag T where CommunityId = ParentId and PARENTTYPE like '%Community' " 
			+ " group by tagtext order by count desc, tagtext";                        
		if (maxTags != 0) {
			sql += " LIMIT " + maxTags + " ";
		}
		return getBeanList(sql, TagDto.class);
	}

	public List getPopularTagsByParentTypeToAdd(String parentType, int maxTags) throws Exception 
	{
		List tagList = new ArrayList();
		String parentClause = "";
		if (parentType != null && !"".equals(parentType)) {
			if (parentType.equalsIgnoreCase("BlogEntry")) {
				parentClause = " where PARENTTYPE = 'BlogEntry' ";
			} else if (parentType.equalsIgnoreCase("ForumTopic")) {
				parentClause = " and PARENTTYPE = 'ForumTopic' ";
			} else if (parentType.equalsIgnoreCase("WikiEntry")) {
				parentClause = " and PARENTTYPE = 'WikiEntry' ";
			}

			String sql="select tagtext, count(*) as count from Tag T " + parentClause 
			+ " group by tagtext"
			+ " order by count desc, tagtext";                        
			if (maxTags != 0) {
				sql += " LIMIT " + maxTags + " ";
			}
			return getBeanList(sql, TagDto.class);

		} else {
			return tagList;
		}
	}

	public List getAllTagTextForParentID(int parentId, int communityID, String parentType) throws Exception 
	{              
		String parentClause = "";
		if (parentType != null && !"".equals(parentType)) {
			if (parentType.equalsIgnoreCase("Community")) {
				parentClause = " and PARENTTYPE like '%Community' ";
			} else {
				parentClause = " and PARENTTYPE = '"+parentType+"' ";
			} 
		}
		String sql = "select tagtext from Tag T where PARENTID = ? and CommunityId = ? ";
		sql = sql + parentClause;
		return getBeanList(sql, TagDto.class, parentId, communityID);
	}

	public List getTagsForAllBlogs(int maxTags, String filterTagList) throws Exception 
	{
		String filterTag = "";
		String filterTagPart = "";
		int count = 0;
		if(filterTagList != null && !"".equals(filterTagList)) {
			if (filterTagList.contains(",")) {
				StringTokenizer st = new StringTokenizer(filterTagList, ",");
				while (st.hasMoreTokens()) {
					String tag = st.nextToken().trim().toLowerCase();
					filterTagPart += " TagText = \'"+tag+"\' ";
					if (st.hasMoreTokens()) filterTagPart += " or ";
					count++;
				}
			} else {
				filterTagPart += " TagText = \'"+filterTagList+"\' ";
				count = 1;
			}
			filterTag = " and ParentId in (select ParentId from (select count(ID), ParentId  from Tag T where " +
			filterTagPart + " group by ParentId having count(ID) >= "+count+" )as T ) " ;
		}

		String sql="select tagtext, count(*) as count from Tag T where T.PARENTTYPE like '%Consolidator%' " + filterTag +
		" group by tagtext order by count desc, tagtext";

		if (maxTags != 0) {
			sql += " LIMIT " + maxTags + " ";
		}
		return getBeanList(sql, TagDto.class);
	}

	public List getTagsForAllTools(int maxTags, String parentType, int toolId) throws Exception 
	{
		String toolClause = "";
		if (toolId > 0) {
			toolClause = " and T.ParentId = " + toolId;
		}

		String sql="";
		if ("Event".equals(parentType)) {
			sql="select tagtext, count(*) as count from Tag T, Event E, Community C where T.ParentId = E.ID and E.StartDate >= CURRENT_TIMESTAMP " +
			" and C.Id = T.CommunityId and C.Status = " + Community.STATUS_ACTIVE + " and C.SysType = 'PublicCommunity' and E.status > 0 and T.PARENTTYPE = '"+parentType+"' " + toolClause +
			" group by tagtext order by count desc, tagtext";
		} else {
			sql="select tagtext, count(*) as count from Tag T, Community C where C.Status = " + Community.STATUS_ACTIVE + " and C.SysType = 'PublicCommunity' and T.PARENTTYPE = '"+parentType+"' " + toolClause +
			" group by tagtext order by count desc, tagtext";
		}


		if (maxTags != 0) {
			sql += " LIMIT " + maxTags + " ";
		}
		return getBeanList(sql, TagDto.class);
	}

	public List getItemTagsForToolIdAndType(int toolId, int max, String toolType) throws Exception 
	{
		String sql = "";
		if ("CommunityBlog".equalsIgnoreCase(toolType)) {
			sql="select T.tagtext, count(*) as count from Tag T, BlogEntry BE, CommunityBlog CB where T.ParentId = BE.Id and CB.ID = BE.CommunityBlogId " +
					"and T.PARENTTYPE = 'BlogEntry' and CB.Id = ? group by tagtext order by count desc, tagtext";
		} else if ("EventCalendar".equalsIgnoreCase(toolType)) {
			sql="select T.tagtext, count(*) as count from Tag T, Event E where T.ParentId = E.Id and T.PARENTTYPE = 'Event' " +
			" and E.CalendarId = ? group by tagtext order by count desc, tagtext";
		} else if ("PersonalBlog".equalsIgnoreCase(toolType)) {
			sql="select T.tagtext, count(*) as count from Tag T, PersonalBlogEntry BPCE where T.ParentId = BPCE.BlogEntryId and T.PARENTTYPE = 'BlogEntry' " +
			" and BPCE.PersonalBlogId = ? group by tagtext order by count desc, tagtext";
		}

		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}
		return getBeanList(sql, TagDto.class, toolId);
	}

	public void clearTagsForParentId(int parentId) throws Exception
	{
		String sql="delete from " + getTableName() + " where ParentId=?";
		getSimpleJdbcTemplate().update(sql, parentId);        
	}

	public void clearTagsForParentIdUserId(int parentId, int userId) throws Exception
	{
		String sql="delete from " + getTableName() + " where ParentId=? and PosterId = ?";
		getSimpleJdbcTemplate().update(sql, parentId, userId);        
	}

	public void clearTagsForParentIdUserIdCommId(int parentId, int userId, int commId) throws Exception
	{
		String sql="delete from " + getTableName() + " where ParentId=? and PosterId = ? and CommunityId = ?";
		getSimpleJdbcTemplate().update(sql, parentId, userId, commId);        
	}

	public void clearTagsForCommIdUserId(int commId, int userId) throws Exception
	{
		String sql="delete from " + getTableName() + " where ParentId = ? and PosterId = ? and CommunityId = ?";
		getSimpleJdbcTemplate().update(sql, commId, userId, commId);        
	}

	public List getTagsForParentId(int parentId) throws Exception 
	{	    	   
		final String sql="select * from Tag T where ParentId = ?";                        
		List lis=getBeanList(sql,  TagDto.class, parentId);	        
		return lis; 	       	    
	}

	public List getTagsForParentIdUserId(int parentId, int userId) throws Exception
	{
		final String sql="select * from Tag T where ParentId = ? and PosterId = ?";                        
		List lis=getBeanList(sql, TagDto.class,  parentId, userId);           
		return lis;       
	}

	public List getTagsForParentIdByPopularity(int parentId, int max) throws Exception 
	{              
		String sql="select tagtext, count(*) as count from Tag T where ParentId = ?"
			+ " group by tagtext"
			+ " order by count desc, tagtext";                        
		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}
		List lis=getBeanList(sql, TagDto.class, parentId);           
		return lis;                      
	}

	public List getAlphabaticTagsForAllCommunities(int max) throws Exception 
	{
		String sql="select tagtext, count(*) as count from Tag T where CommunityId is not null and PARENTTYPE like '%Community' "
			+ " group by tagtext"
			+ " order by tagtext";                        

		/* Limit number of results returned if max set */
		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}



		List lis = getBeanList(sql,  TagDto.class);	        
		return lis; 	 
	}

	/* Return tags for specified community in alphabetic order  
	 * Set max to zero to return all tags */
	public List getTagsAlphaForCommunityId(int commId, int max) throws Exception 
	{
		String sql="select tagtext, count(*) as count from Tag T where CommunityId = ?"
			+ " group by tagtext"
			+ " order by tagtext";                        

		/* Limit number of results returned if max set */
		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}
		List lis=getBeanList(sql,  TagDto.class, commId);	        
		return lis; 	 
	}

	/* Return tags for specified parent  in alphabetic order  
	 * Set max to zero to return all tags */
	public List getTagsAlphaForParentId(int parentId, int max) throws Exception 
	{
		String sql="select tagtext, count(*) as count from Tag T where ParentId = ?"
			+ " group by tagtext"
			+ " order by tagtext";                        

		/* Limit number of results returned if max set */
		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}


		List lis=getBeanList(sql, TagDto.class, parentId);           
		return lis;      
	}

	/* Return tags for specified user in order of popularity.  
	 * Set max to zero to return all tags */
	public List getTagsForUserId(int userId, int max) throws Exception 
	{
		String sql="select tagtext, count(*) as count from Tag T where PosterId = ?"
			+ " group by tagtext"
			+ " order by count desc, tagtext";                        

		/* Limit number of results returned if max set */
		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}


		List lis=getBeanList(sql, TagDto.class,  userId);	        
		return lis; 	 
	}

	public List getTagsForcommunitiesForParentByPopularity(int parentId, int max) throws Exception
	{
		String sql="select tagtext, count(*) as count from Tag T, " +
		"TBBCEN BCEN , TBBENT BENT, TBBCON BCON , TBCOMM COMM " +
		" where BCEN.BLOGENTRYID = BENT.ID and BCON.ID = BCEN.CommunityBlogID " +
		" and COMM.ID = BCON.COMMUNITYID and BCEN.BLOGENTRYID = ? and COMM.Status = 0 " +
		" group by tagtext" +
		" order by count desc, tagtext";                        

		/* Limit number of results returned if max set */
		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}
		List lis=getBeanList(sql, TagDto.class,  parentId);	        
		return lis; 	 
	}

	public List getItemsForTagInCommunity(int commId, String tagText) throws Exception
	{
		String sql="select ParentType, ParentId from Tag T where T.CommunityId = ? and T.TagText = ?"
			+ " group by ParentType, ParentId"
			+ " order by ParentType";                        


		List lis=getBeanList(sql, TagSearchResultDto.class, commId, tagText);	        
		return lis; 	 
	}

	public List getTagsForOnlyCommunityByPopularity(int communityId, int max) throws Exception 
	{              
		String sql="select tagtext, count(*) as count from Tag T where COMMUNITYID = ? and PARENTID = ? and PARENTTYPE like '%Community'"
			+ " group by tagtext"
			+ " order by count desc, tagtext";                        

		/* Limit number of results returned if max set */
		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}
		List lis=getBeanList(sql, TagDto.class, communityId, communityId);           
		return lis;                      
	}

	public List getTagsForParentTypeByPopularity(int parentId, int communityID, int max, String parentType) throws Exception 
	{              
		String parentClause = "";
		if (parentType != null && !"".equals(parentType)) {
			if (parentType.equalsIgnoreCase("Community")) {
				parentClause = " and PARENTTYPE like '%Community' ";
			} else {
				parentClause = " and PARENTTYPE = '"+parentType+"' ";
			} 
		}

		String sql = "";

		if (communityID > 0) {
			sql = "select tagtext, count(*) as count from Tag T where PARENTID = ? and CommunityId = ? ";
		} else {
			sql = "select tagtext, count(*) as count from Tag T where PARENTID = ? ";
		}
		if (!"".equals(parentClause)) {
			sql = sql + parentClause;
		}
		sql = sql +  " group by tagtext order by count desc, tagtext";                        
		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}
		List list = null;
		if (communityID > 0) {
			list = getBeanList(sql, TagDto.class, parentId, communityID);
		} else {
			list = getBeanList(sql, TagDto.class, parentId);
		}
		return list;                      
	}

	public List getTagsForParentEntryByPopularity(int parentId, int communityId, int max) throws Exception 
	{              
		String sql="select tagtext, count(*) as count from Tag T where PARENTID = ? and COMMUNITYID = ? "
			+ " group by tagtext"
			+ " order by count desc, tagtext";                        

		/* Limit number of results returned if max set */
		if (max != 0) {
			sql += " LIMIT " + max + " ";
		}
		List lis=getBeanList(sql, TagDto.class, parentId, communityId);           
		return lis;                      
	}

	public QueryScroller getAllTagsSearchIndexing() throws Exception 
	{
		String query = "select T.* from Tag T where CommunityId is null ";
		QueryScroller scroller = getQueryScroller(query, Tag.class);
		scroller.addScrollKey("tagText", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		return scroller;
	}

	public List getTagsForAllCommunityByPopularity(int commId, int maxTags, String parentType) throws Exception 
	{
		String parentClause = "";
		if (parentType != null && !"".equals(parentType)) {
			if (parentType.equalsIgnoreCase("Community")) {
				parentClause = " and PARENTTYPE like '%Community' ";
			} else if (parentType.equalsIgnoreCase("BlogEntry")) {
				parentClause = " where PARENTTYPE = 'BlogEntry' ";
			} else if (parentType.equalsIgnoreCase("ForumTopic")) {
				parentClause = " and PARENTTYPE = 'ForumTopic' ";
			} else if (parentType.equalsIgnoreCase("WikiEntry")) {
				parentClause = " and PARENTTYPE = 'WikiEntry' ";
			}
		}

		String idClause = "";
		if (!parentType.equalsIgnoreCase("BlogEntry") && !parentType.equalsIgnoreCase("Community")) {
			idClause =  commId>0 ? "where CommunityId = ? " : "where CommunityId is not null";
		} else {
			idClause = "where CommunityId is not null";
		}
		String sql="select tagtext, count(*) as count from Tag T " + idClause + parentClause 
		+ " group by tagtext"
		+ " order by count desc, tagtext";                        
		if (maxTags != 0) {
			sql += " LIMIT " + maxTags + " ";
		}
		List tags;
		if (!parentType.equalsIgnoreCase("BlogEntry") && !parentType.equalsIgnoreCase("Community") && commId>0)
			return getBeanList(sql, TagDto.class, commId);	        
		else
			return getBeanList(sql, TagDto.class);
	}

	public List getAllTagsForACommunityByPopularity(int commId, int maxTags) throws Exception 
	{
		String sql="select tagtext, count(*) as count from Tag T where CommunityId = ? and PARENTTYPE not like '%Community' "
			+ " group by tagtext"
			+ " order by count desc, tagtext";                        
		if (maxTags != 0) {
			sql += " LIMIT " + maxTags + " ";
		}
		return getBeanList(sql, TagDto.class, commId);
	}

	public QueryScroller getAllItemsByTagInCommunity(int commId, String filterTagList, String submitType) throws Exception
	{
		String filterTag = "";
		int count = 0;
		if(filterTagList != null && !"".equals(filterTagList)) {
			String filterTagPart = "";
			if (filterTagList.contains(",")) {
				StringTokenizer st = new StringTokenizer(filterTagList, ",");
				while (st.hasMoreTokens()) {
					String tag = st.nextToken().trim().toLowerCase();
					filterTagPart += " T2.TagText = \'"+tag+"\' ";
					if (st.hasMoreTokens()) filterTagPart += " or ";
					count++;
				}
			} else {
				filterTagPart = " T2.TagText = \'"+filterTagList+"\' ";
				count = 1;
			}
			filterTag = " where exists (select ParentId from (select count(ID), ParentId  from Tag T2 where "+filterTagPart+" group by ParentId having count(ID) >= "+count+" ) as T2 where T2.ParentId = T.itemId) ";
		}

		String query = "select * from  ( ";
		//document
		if (submitType.equalsIgnoreCase("all") || submitType.equalsIgnoreCase("Document")) {
			query = query + " select lib.Communityid communityid, d.id itemId, d.systype itemType, "
			+ " d.title itemTitle, CAST(d.description as CHAR(100) ) itemDesc, "
			+ " d.filename itemfilename, "
			+ " (select SUM(STARS) / COUNT(STARS) from DocumentRating DR where DR.documentid = d.id) avgRating, "
			+ " (select COUNT(ID) from DocumentComment DC where DC.documentid = d.id) commentCount, "
			+ " d.modified datePosted, d.posterid posterid, "
			+ " user.FirstName firstName, user.LastName lastName, user.PHOTOPRESENT photoPresent, "
			+ " (select COUNT(ID) from DocumentLike DL where DL.DocumentId = d.id) likeCount "
			+ " from Document d, DocumentLibrary lib, User user "
			+ " where lib.id = d.libraryid and  user.id = d.posterid "
			+ " and lib.communityId= ? ";
		}
		if (submitType.equalsIgnoreCase("all")) {
			query = query + " union all ";
		}
		//Event
		if (submitType.equalsIgnoreCase("all") || submitType.equalsIgnoreCase("Event")) {
			query = query + " select 	cal.communityId communityid, e.id itemId, e.systype itemType, "
			+ " e.name itemTitle, CAST(e.Description as CHAR(100) ) itemDesc, '' itemfilename , CAST(NULL as DECIMAL ) as avgRating, "
			+ " CAST(NULL as UNSIGNED INTEGER) as commentCount, "
			+ " e.modified datePosted, e.PosterId posterid, "
			+ " user.FirstName firstName, user.LastName lastName, user.PHOTOPRESENT photoPresent, "
			+ " CAST(NULL as UNSIGNED INTEGER) as likeCount "
			+ " from Event e, EventCalendar cal, User user "
			+ " where cal.id = e.calendarid and  user.id = posterid "
			+ " and cal.communityId= ? ";
		}
		if (submitType.equalsIgnoreCase("all")) {
			query = query + " union all ";
		}
		//Blog
		/*if (submitType.equalsIgnoreCase("all") || submitType.equalsIgnoreCase("Blog")) {
			query = query + " select 	Cons.communityid communityid, Cons.Id itemId, Cons.Systype itemType, Cons.Name itemTitle, "
			+ " CAST(Cons.Description as CHAR(100) ) itemDesc, '' itemfilename, "
			+ " CAST(NULL as DECIMAL ) as avgRating, "
			+ " CAST(NULL as DECIMAL ) as commentCount, "
			//+ " (select COUNT(ID) from BlogEntryComment BC where BC.BLOGENTRYID = E.id) commentCount, "
			+ " Cons.Modified datePosted, Cons.OwnerId posterid, "
			+ " user.FirstName firstName, user.LastName lastName, user.PHOTOPRESENT photoPresent  "
			//+ " ,(select COUNT(ID) from BlogEntryLike BL where BL.BlogEntryId = E.id) likeCount "
			+ " ,CAST(NULL as DECIMAL ) as likeCount, "
			+ " from CommunityBlog Cons, User user "
			+ " where user.id = Cons.OwnerId "
			+ " and Cons.communityId= ? ";
		}
		if (submitType.equalsIgnoreCase("all")) {
			query = query + " union all ";
		}*/
		//Blog Entry
		if (submitType.equalsIgnoreCase("all") || submitType.equalsIgnoreCase("BlogEntry")) {
			query = query + " select 	Cons.communityid communityid, E.Id itemId, E.Systype itemType, E.Title itemTitle, "
			+ " CAST(E.BODY as CHAR(100) ) itemDesc, '' itemfilename, "
			+ " CAST(NULL as DECIMAL ) as avgRating, "
			+ " (select COUNT(ID) from BlogEntryComment BC where BC.BLOGENTRYID = E.id) commentCount, "
			+ " E.Modified datePosted, E.PosterId posterid, "
			+ " user.FirstName firstName, user.LastName lastName, user.PHOTOPRESENT photoPresent,  "
			+ " (select COUNT(ID) from BlogEntryLike BL where BL.BlogEntryId = E.id) likeCount "
			+ " from BlogEntry E, CommunityBlog Cons, User user "
			+ " where Cons.Id = E.CommunityBlogId and user.id = E.PosterId "
			+ " and Cons.communityId= ? ";
		}
		if (submitType.equalsIgnoreCase("all")) {
			query = query + " union all ";
		}
		//Wiki
		if (submitType.equalsIgnoreCase("all") || submitType.equalsIgnoreCase("WikiEntry")) {
			query = query + " select  wiki.communityid communityid, w.id itemId, w.systype itemType, "
			+ " w.title itemTitle, CAST(Twe.BODY as CHAR(100) ) as  itemDesc, '' itemfilename, "
			+ " CAST(NULL as DECIMAL ) as avgRating, "
			+ " CAST(NULL as UNSIGNED INTEGER) as commentCount, "
			+ " w.modified datePosted, w.posterid posterid, "
			+ " user.FirstName firstName, user.LastName lastName, user.PHOTOPRESENT photoPresent, "
			+ " (select COUNT(ID) from WikiEntryLike WL where WL.WikiEntryId = w.id) likeCount "
			+ " from WikiEntry w, Wiki wiki, TBWENT Twe, User user "
			+ " where wiki.id = w.wikiid and user.id = w.posterid and "
			+ " wiki.communityId= ? "
			+ " and Twe.id = w.id "
			+ " and w.EntrySequence = (select MAX(EntrySequence) from WikiEntry WE where WE.EntryId = w.EntryId) ";
		}
		if (submitType.equalsIgnoreCase("all")) {
			query = query + " union all ";
		}
		//Forums
		if (submitType.equalsIgnoreCase("all") || submitType.equalsIgnoreCase("ForumTopic")) {
			query = query + " select 	forum.communityid communityid, f.id as itemId, f.systype itemType, "
			+ " f.subject itemTitle, CAST(f.BODY as CHAR(100) ) itemDesc, '' itemfilename , "
			+ " CAST(NULL as DECIMAL ) as avgRating, "
			+ " CAST(NULL as UNSIGNED INTEGER) as commentCount, "
			+ " f.modified datePosted, f.AUTHORID posterid, "
			+ " user.FirstName firstName, user.LastName lastName, user.PHOTOPRESENT photoPresent, "
			+ " (select COUNT(ID) from ForumItemLike FL where FL.ForumItemId = f.id) likeCount "
			+ " from ForumItem f, Forum forum, User user "
			+ " where forum.id = f.forumid and user.id = f.AUTHORID and "
			+ " forum.communityId = ? "
			+ " and f.deletestatus <> 9 and f.SysType = 'ForumTopic'";
		}
		query = query + " ) as T "
		+ filterTag ;

		QueryScroller scroller = null;
		if (submitType.equalsIgnoreCase("all")) {
			scroller = getQueryScroller(query, true, null, commId, commId, commId, commId, commId);
		} else {
			scroller = getQueryScroller(query, true, null, commId);
		}
		scroller.addScrollKey("STEMP.datePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}

	public QueryScroller getAllBlogEntriesByTagForPersonalBlog(int bpcId, String filterTagList) throws Exception
	{
		String filterTag = "";
		int count = 0;
		if(filterTagList != null && !"".equals(filterTagList)) {
			String filterTagPart = "";
			if (filterTagList.contains(",")) {
				StringTokenizer st = new StringTokenizer(filterTagList, ",");
				while (st.hasMoreTokens()) {
					String tag = st.nextToken().trim().toLowerCase();
					filterTagPart += " T2.TagText = \'"+tag+"\' ";
					if (st.hasMoreTokens()) filterTagPart += " or ";
					count++;
				}
			} else {
				filterTagPart = " T2.TagText = \'"+filterTagList+"\' ";
				count = 1;
			}
			filterTag = " where exists (select ParentId from (select count(ID), ParentId  from Tag T2 where "+filterTagPart+" group by ParentId having count(ID) >= "+count+" ) as T2 where T2.ParentId = T.itemId) ";
		}

		String query = "select * from  ( ";
		query = query + " select Cons.Id consId, E.Id itemId, E.Systype itemType, E.Title itemTitle, "
		+ " CAST(E.BODY as CHAR(100) ) itemDesc, '' itemfilename, "
		+ " CAST(NULL as DECIMAL ) as avgRating, "
		+ " (select COUNT(ID) from BlogEntryComment BC where BC.BLOGENTRYID = E.id) commentCount, "
		+ " E.Modified datePosted, Cons.OWNERID posterid, "
		+ " user.FirstName firstName, user.LastName lastName, user.PHOTOPRESENT photoPresent,  "
		+ " (select COUNT(ID) from BlogEntryLike BL where BL.BlogEntryId = E.id) likeCount "
		+ " from BlogEntry E, PersonalBlogEntry BPCE, PersonalBlog Cons, User user "
		+ " where BPCE.BlogEntryId = E.Id and user.id = Cons.OWNERID "
		+ " and Cons.Id= ? "
		+ " and Cons.Id = BPCE.PersonalBlogId ";
		query = query + " ) as T "
		+ filterTag ;

		QueryScroller scroller = null;
		scroller = getQueryScroller(query, true, null, bpcId);
		scroller.addScrollKey("STEMP.datePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}
}