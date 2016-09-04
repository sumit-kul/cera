package com.era.community.wiki.dao; 

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.wiki.ui.dto.WikiEntryDto;

public class WikiEntryReferenceDaoImpl extends com.era.community.wiki.dao.generated.WikiEntryReferenceDaoBaseImpl implements WikiEntryReferenceDao
{
	public QueryScroller listReferenceForWikiEntry(WikiEntry entry) throws Exception
	{
		final String SQL = "select R.* from WikiEntryReference R where R.WikiEntryId = ? ";

		QueryScroller scroller = getQueryScroller(SQL, WikiEntryDto.class, entry.getId());
		scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}

	public int getReferenceCountForWikiEntry(int entryId) throws Exception
	{
		String query = "select count(*) from WikiEntryReference where WikiEntryReference.WikiEntryId = ? ";
		return getSimpleJdbcTemplate().queryForInt(query, entryId);
	}
}