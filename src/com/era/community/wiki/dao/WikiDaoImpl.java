package com.era.community.wiki.dao; 

import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.communities.dao.Community;
import com.era.community.monitor.ui.dto.SubscriptionItemDto;
import com.era.community.pers.dao.User;
import com.era.community.wiki.ui.dto.WikiEntryDto;

public class WikiDaoImpl extends com.era.community.wiki.dao.generated.WikiDaoBaseImpl implements WikiDao
{
	public Wiki getWikiForCommunity(Community comm) throws Exception
	{
		return (Wiki)getEntityWhere("CommunityId=?", comm.getId() );
	}
	
	public Wiki getWikiForCommunityId(int commId) throws Exception
	{
		return (Wiki)getEntityWhere("CommunityId=?", commId );
	}

	public QueryScroller listEntriesByTitle(Wiki wiki) throws Exception
	{
		final String SQL = "select W.*, CONCAT_WS(' ',U.FirstName,U.LastName) as LastEditedBy, U.PhotoPresent as PhotoPresent from WikiEntry W, User U" 
			+ " where W.WikiId = ? and"  
			+ " W.EntrySequence = " + Integer.MAX_VALUE +" and"
			+ " W.PosterId = U.Id";

		QueryScroller scroller = getQueryScroller(SQL,WikiEntryDto.class, wiki.getId());
		scroller.addScrollKey("STEMP.Title", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		return scroller; 
	}


	public boolean entryExists(Wiki wiki, String title, Integer entryId) throws Exception
	{
		int rowCount = entryId == null ? entriesWithTitle(wiki,title) : entriesWithTitle(wiki, title, entryId);        
		if (rowCount > 0) return true;        
		return false;
	}

	public int entriesWithTitle(Wiki wiki, String title) throws Exception
	{
		final String SQL = "select W.Title from WikiEntry W"
			+ " where W.WikiId = ? and W.Title = ?";

		QueryScroller scroller = getQueryScroller(SQL, WikiEntryDto.class, wiki.getId(), title);
		scroller.addScrollKey("STEMP.Title", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		return scroller.readRowCount();
	}

	public int entriesWithTitle(Wiki wiki, String title, Integer entryId) throws Exception
	{
		final String SQL = "select W.Title from WikiEntry W"
			+ " where W.WikiId = ? and W.Title = ?  and W.entryId <> ?";

		QueryScroller scroller = getQueryScroller(SQL, WikiEntryDto.class, wiki.getId(), title, entryId);
		scroller.addScrollKey("STEMP.Title", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		return scroller.readRowCount();
	}

	public QueryScroller listEntriesByUpdateDate(Wiki wiki) throws Exception
	{
		final String SQL = "select W.*, CONCAT_WS(' ',U.FirstName,U.LastName) as LastEditedBy, U.PhotoPresent as PhotoPresent from TBWENT W, User U" 
			+ " where W.WikiId = ? and"  
			+ " W.EntrySequence = " + Integer.MAX_VALUE +" and"
			+ " W.PosterId = U.Id";
		QueryScroller scroller = getQueryScroller(SQL, WikiEntryDto.class, wiki.getId());
		scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller; 
	}

	public QueryScroller listAllWikiEntriesByUpdateDate(String filterTagList) throws Exception
	{
		String filterTag = "";
		int count = 0;
		if(filterTagList != null && !"".equals(filterTagList)) {
			String filterTagPart = "";
			if (filterTagList.contains(",")) {
				StringTokenizer st = new StringTokenizer(filterTagList, ",");
				while (st.hasMoreTokens()) {
					String tag = st.nextToken().trim().toLowerCase();
					filterTagPart += " TagText = \'"+tag+"\' ";
					if (st.hasMoreTokens()) filterTagPart += " or ";
					count++;
				}
			} else {
				filterTagPart = " TagText = \'"+filterTagList+"\' ";
				count = 1;
			}
			filterTag = " and exists (select ParentId from (select count(ID), ParentId  from Tag where PARENTTYPE = 'WikiEntry' "+
			" and " +filterTagPart+ " group by ParentId having count(ID) >= "+count+" ) as T where T.ParentId = W.Id) " ;
		}

		final String SQL = "select W.*, CONCAT_WS(' ',U.FirstName,U.LastName) as LastEditedBy, C.CommunityLogoPresent as logoPresent, C.Id as CommunityId, "
			+ " C.Name as CommunityName, C.SysType CommunitySysType, (select count(*) from Image I where I.WikiEntryId = W.Id ) As ImageCount, "
			+ " (select count(*) from Image I, WikiEntrySection WES where I.WikiEntrySectionId = WES.Id and WES.WikiEntryId = W.Id) As SectionImageCount from TBWENT W, User U, Wiki wiki, Community C " 
			+ " where W.WikiId = wiki.ID and wiki.CommunityId = C.Id and "  
			+ " W.EntrySequence = " + Integer.MAX_VALUE +" and"
			+ " W.PosterId = U.Id " + filterTag;
		QueryScroller scroller = getQueryScroller(SQL, false, WikiEntryDto.class);
		scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller; 
	}

	public QueryScroller listAllWikiEntriesByTitle(String filterTagList) throws Exception
	{
		String filterTag = "";
		int count = 0;
		if(filterTagList != null && !"".equals(filterTagList)) {
			String filterTagPart = "";
			if (filterTagList.contains(",")) {
				StringTokenizer st = new StringTokenizer(filterTagList, ",");
				while (st.hasMoreTokens()) {
					String tag = st.nextToken().trim().toLowerCase();
					filterTagPart += " TagText = \'"+tag+"\' ";
					if (st.hasMoreTokens()) filterTagPart += " or ";
					count++;
				}
			} else {
				filterTagPart = " TagText = \'"+filterTagList+"\' ";
				count = 1;
			}
			filterTag = " and exists (select ParentId from (select count(ID), ParentId  from Tag where PARENTTYPE = 'WikiEntry' ";
			filterTag = filterTag + " and " +filterTagPart+ " group by ParentId having count(ID) >= "+count+" ) as T where T.ParentId = W.Id) " ;
		}

		final String SQL = "select W.*, CONCAT_WS(' ',U.FirstName,U.LastName) as LastEditedBy, C.CommunityLogoPresent as logoPresent, C.Id as CommunityId, C.Name as CommunityName, " 
			+ " C.SysType CommunitySysType, (select count(*) As LikeCount from WikiEntryLike L where L.WikiEntryId = W.Id), "
			+ " (select count(*) from Image I, WikiEntrySection WES where I.WikiEntrySectionId = WES.Id and WES.WikiEntryId = W.Id) As SectionImageCount, "
			+ " (select count(*) from Image I where I.WikiEntryId = W.Id) As ImageCount from TBWENT W, User U, Wiki wiki, Community C " 
			+ " where W.WikiId = wiki.ID and wiki.CommunityId = C.Id and "  
			+ " W.EntrySequence = " + Integer.MAX_VALUE +" and"
			+ " W.PosterId = U.Id " + filterTag;
		QueryScroller scroller = getQueryScroller(SQL, true, WikiEntryDto.class);
		scroller.addScrollKey("STEMP.Title", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		return scroller; 
	}

	public QueryScroller listEntriesForUser(Wiki wiki, User user) throws Exception
	{
		final String SQL = "select W.*, CONCAT_WS(' ',U.FirstName,U.LastName) as LastEditedBy from WikiEntry W, User U " 
			+ "where W.WikiId = ? and "  
			+ " W.PosterId = ? and " 
			+ "W.EntrySequence = " + Integer.MAX_VALUE +" and "
			+ "W.PosterId = U.Id";
		QueryScroller scroller = getQueryScroller(SQL, WikiEntryDto.class, wiki.getId(), user.getId());
		scroller.addScrollKey("STEMP.Title", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_TEXT);
		return scroller; 
	}

	public WikiEntry getMostRecentWikiEntry(int wikiId) throws Exception
	{
		String where = "WikiId = ? order by dateposted desc LIMIT 1 ";
		return (WikiEntry)getEntityWhere(where, wikiId);
	}

	public Date getLatestPostDate(Wiki wiki) throws Exception
	{
		String query = "select max(DatePosted) from WikiEntry where WikiId = ?";
		return (Date)getSimpleJdbcTemplate().queryForObject(query, Date.class, wiki.getId() );
	}

	public List getItemsSince(Wiki wiki, Date date) throws Exception
	{
		String sQuery = "Select E.Id as ItemId, E.Title as ItemTitle, E.Created As ItemDate, " +
		" E.PosterId as AuthorId, CONCAT_WS(' ',U.FirstName,U.LastName) as AuthorName " +
		" from WikiEntry E, User U "+
		"where U.Id = E.PosterId and DatePosted > ? and WikiId=? and E.EntrySequence = " + Integer.MAX_VALUE;
		return  getBeanList(sQuery, SubscriptionItemDto.class, date, wiki.getId());
	}
}