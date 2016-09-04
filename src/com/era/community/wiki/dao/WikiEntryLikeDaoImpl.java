package com.era.community.wiki.dao; 

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.forum.dao.ForumItemLike;
import com.era.community.wiki.ui.dto.WikiEntryDto;

public class WikiEntryLikeDaoImpl extends com.era.community.wiki.dao.generated.WikiEntryLikeDaoBaseImpl implements WikiEntryLikeDao
{
	public QueryScroller listLikesForWikiEntry(WikiEntry entry) throws Exception
	{
		final String SQL = "select C.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PhotoPresent as PhotoPresent " + 
		"from WikiEntryLike C, User U " +
		"where C.WikiEntryId = ? " +
		"and C.PosterId = U.Id";

		QueryScroller scroller = getQueryScroller(SQL, WikiEntryDto.class, entry.getId());
		scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}

	public int getLikeCount() throws Exception
	{
		String query = "select count(*) from WikiEntryLike";
		return getJdbcTemplate().queryForInt(query);
	}

	public int getLikeCountForWikiEntry(int entryId) throws Exception
	{
		String query = "select count(*) from WikiEntryLike where WikiEntryLike.WikiEntryId = ? ";
		return getSimpleJdbcTemplate().queryForInt(query, entryId);
	}
	
	public WikiEntryLike getLikeForWikiEntryAndUser(int entryId, int userId) throws Exception
	{
		return (WikiEntryLike)getEntityWhere("WikiEntryId = ? and PosterId = ?", entryId, userId);
	}
}