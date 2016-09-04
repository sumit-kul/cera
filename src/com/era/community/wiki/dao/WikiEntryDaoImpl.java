package com.era.community.wiki.dao; 

import java.util.Date;
import java.util.List;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;
import support.community.framework.Option;

import com.era.community.communities.dao.Community;
import com.era.community.monitor.ui.dto.SubscriptionItemDto;
import com.era.community.wiki.ui.dto.ContributorDto;
import com.era.community.wiki.ui.dto.WikiEntryDto;
import com.era.community.wiki.ui.dto.WikiEntryHeaderDto;

public class WikiEntryDaoImpl extends com.era.community.wiki.dao.generated.WikiEntryDaoBaseImpl implements WikiEntryDao
{
	public WikiEntry getLatestWikiEntryForEntryId(int entryId) throws Exception
	{      
		return (WikiEntry)getEntityWhere("entryId = ? and entrySequence = " + Integer.MAX_VALUE, entryId);
	}  

	public WikiEntry getWikiEntryForEntryIdAndSequence(int entryId, int sequence) throws Exception
	{
		return (WikiEntry)getEntityWhere("entryId = ? and entrySequence = ?", entryId,  sequence);
	}   

	public int getNextSequenceNumberForEntry(WikiEntry entry) throws Exception
	{
		final String SQL = "select MAX(W.EntrySequence) as Value" 
			+" from WikiEntry W"
			+ " where W.WikiId = ? and" 
			+ " W.EntryId = ? and "
			+ " W.EntrySequence != " + Integer.MAX_VALUE;

		List lis = getBeanList(SQL, Option.class, entry.getWikiId(), entry.getEntryId());                    
		Integer currentVer = (Integer) ((Option) lis.get(0)).getValue();      

		if (currentVer != null) {
			/* Increment current version and return */
			return (currentVer.intValue() + 1);
		}
		else {
			/* First 'old' version, set to 1 */
			return 1;
		}
	}

	public QueryScroller listHistoryByEditDate(WikiEntry wikiEntry, boolean includeLatest) throws Exception
	{
		String latestClause = " and  W.EntrySequence != " + Integer.MAX_VALUE  ;
		final String SQL = "select W.*, CONCAT_WS(' ',U.FirstName,U.LastName) as EditedBy, " 
			+ "U.PHOTOPRESENT as EditedByPhotoPresent "
			+ "from WikiEntry W, User U " 
			+ "where W.EntryID = ? "
			// we only want this next line if 'includeLatest' is true
			+ (includeLatest? "" : latestClause) 
			+ " and W.PosterId = U.Id";
		QueryScroller scroller = getQueryScroller(SQL, WikiEntryDto.class, wikiEntry.getEntryId());
		scroller.addScrollKey("STEMP.EntrySequence", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_INTEGER);
		return scroller; 
	}

	public QueryScroller listAllCurrentEntries() throws Exception
	{
		String sql = "select WE.* from WikiEntry WE, Wiki W, Community C where " +
		" W.Id = WE.WikiId and C.ID = W.CommunityId and C.SysType != 'PrivateCommunity' and WE.EntrySequence = "+Integer.MAX_VALUE;
		QueryScroller scroller = getQueryScroller(sql, null);
		return scroller;        
	}

	public Date getLatestPostDate(WikiEntry entry) throws Exception
	{
		String query = "select max(DatePosted) from WikiEntry where EntryId = ?";
		return (Date)getSimpleJdbcTemplate().queryForObject(query, Date.class  ,entry.getId());
	}

	public List getItemsSince(WikiEntry entry, Date date) throws Exception
	{
		String sQuery = "Select E.Id as ItemId, E.Title as ItemTitle, E.DatePosted As ItemDate, " +
		" E.PosterId AS AuthorId, CONCAT_WS(' ',U.FirstName,U.LastName) AS AuthorName from WikiEntry E, User U"+
		"where U.ID = E.PosterId and E.DatePosted > ? and EntryId=?  and entrySequence = " + Integer.MAX_VALUE;
		return  getBeanList(sQuery, SubscriptionItemDto.class, date, entry.getId());
	}

	public int getWikiEditCount(int groupId) throws Exception
	{
		String queryNoGroup = "select count(*) from WikiEntry where entrySequence <> " + Integer.MAX_VALUE;
		String queryForGroup = "select count(*) from WikiEntry E, Wiki P, CommunityGroupLink L where "+
		" L.CommunityId = P.CommunityId and  L.CommunityGroupId=? and E.WikiId = P.Id  and  E.entrySequence <> " + Integer.MAX_VALUE;
		if ( groupId==0  )
			return getJdbcTemplate().queryForInt(queryNoGroup);
		else
		{
			return getSimpleJdbcTemplate().queryForInt(queryForGroup,  groupId );
		}
	}

	public int getWikiEntryCount(int groupId) throws Exception
	{
		String queryNoGroup = "select count(*) from WikiEntry E where E.entrySequence = " + Integer.MAX_VALUE;
		String queryForGroup = "select count(*) from WikiEntry E, Wiki P, CommunityGroupLink L where "+
		" L.CommunityId = P.CommunityId and  L.CommunityGroupId=? and E.WikiId = P.Id  and  E.entrySequence = " + Integer.MAX_VALUE;
		if ( groupId==0  )
			return getJdbcTemplate().queryForInt(queryNoGroup);
		else
		{
			return getSimpleJdbcTemplate().queryForInt(queryForGroup,groupId );
		}
	}

	public int getWikiEditCountForCommunity(Community comm) throws Exception
	{
		String query = "select count(*) from Wiki W, WikiEntry E where E.EntrySequence <> " + Integer.MAX_VALUE+
		" and W.Id = E.WikiId and W.CommunityId = ?" ;
		return getSimpleJdbcTemplate().queryForInt(query, comm.getId());
	}

	public int getWikiEntryCountForCommunity(Community comm) throws Exception
	{
		String query = "select count(*) from Wiki W, WikiEntry E where E.EntrySequence = " + Integer.MAX_VALUE+
		" and W.Id = E.WikiId and W.CommunityId = ?" ;
		return getSimpleJdbcTemplate().queryForInt(query,  comm.getId() );
	}

	/* 
	 * Returns the sequence of the entry prior to the latest entry or zero if none.
	 */
	private int getPreviousSequenceForEntryId(int entryId) throws Exception
	{      
		String query = "select max(EntrySequence) from WikiEntry where EntryId = ? and EntrySequence < " + Integer.MAX_VALUE;
		return getSimpleJdbcTemplate().queryForInt(query, entryId );
	}  

	/* 
	 * Returns the entry prior to the latest entry or null if none.
	 */
	public WikiEntry getPreviousWikiEntryForEntryId(int entryId) throws Exception
	{      
		int seq = getPreviousSequenceForEntryId(entryId);
		return seq==0 ? null : getWikiEntryForEntryIdAndSequence(entryId, seq);
	}  
	
	public WikiEntry getFirstWikiEntry(int wikiId) throws Exception
	{
		String where = "WikiId = ? order by dateposted asc LIMIT 1 ";
		return (WikiEntry)getEntityWhere(where, wikiId);
	}

	public void deleteWikiEntryWithAllVersions(int entryId) throws Exception
	{
		String sql="delete from " + getTableName() + " where EntryId=?";
		getSimpleJdbcTemplate().update(sql, entryId);        
	}

	public List getWikiVersionOptionList(int entryId) throws Exception
	{
		String query = "select EntrySequence As Label, ID as Value from WikiEntry  where EntryId = ? ";
		String sQuery = "select "+
		"Case " +
		"When W. EntrySequence = "+Integer.MAX_VALUE +
		" Then 'Latest version' " +
		"Else "+
		" CONCAT('Version ',CONVERT(EntrySequence, CHAR(10))) " +
		"End " +
		"Label, "+
		"CAST(W.EntrySequence AS UNSIGNED) as Value from WikiEntry W where W. EntryId = ?  order by W.EntrySequence desc";
		return getBeanList(sQuery, Option.class, entryId);
	}

	public int getWikiEntryVersionCount(int entryId) throws Exception
	{
		String query = "select count(*) from WikiEntry where EntryId = ?";
		return getSimpleJdbcTemplate().queryForInt(query, entryId);
	}
	
	public List getContributors(int entryId) throws Exception
	{
		String sQuery = "Select ContributorId, ContributionCount, CONCAT_WS(' ',U.FirstName,U.LastName) AS ContributorName, U.PhotoPresent as contributorPhotoPresent " +
		" from (Select E.PosterId as ContributorId, count(E.Id) as ContributionCount" +
		" from WikiEntry E where E.EntryId = ? group by E.PosterId order by 2 desc ) A, User U where U.ID = A.ContributorId ";
		return  getBeanList(sQuery, ContributorDto.class, entryId);
	}
	
	public WikiEntryHeaderDto getWikiEntryForHeader(int entryId) throws Exception
	{
		String query = " select w.Id, w.title, w.BODY as  itemDesc, " +
		" (select COUNT(ID) from WikiEntryComment WEC where WEC.WikiEntryId = w.id) as  commentCount, w.DatePosted, w.Visitors Visitors, " +
		" U.Id Posterid, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT photoPresent, " +
		" (select COUNT(ID) from Image I where I.WikiEntryId = w.Id) ImageCount , " +
		" (select COUNT(ID) from WikiEntryLike WL where WL.WikiEntryId = w.id) likeCount " +
		" from  TBWENT w, User U where U.Id = w.PosterId and w.EntrySequence = " + Integer.MAX_VALUE + " and w.Id = ?" ;
		return getBean(query, WikiEntryHeaderDto.class, entryId);
	}
}