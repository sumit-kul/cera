package com.era.community.wiki.dao; 

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.wiki.ui.dto.WikiEntryCommentDto;

public class WikiEntryCommentDaoImpl extends com.era.community.wiki.dao.generated.WikiEntryCommentDaoBaseImpl implements WikiEntryCommentDao
{
    public QueryScroller listCommentsForWikiEntry(WikiEntry entry) throws Exception
    {
        final String SQL = "select C.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PhotoPresent as PhotoPresent " + 
            "from WikiEntryComment C, User U " +
            "where C.WikiEntryId = ? " +
            "and C.PosterId = U.Id";
        
        QueryScroller scroller = getQueryScroller(SQL, WikiEntryCommentDto.class, entry.getEntryId());
        scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
        return scroller;
    }

    public int getCommentCountForWikiEntry(int entryId) throws Exception
    {
        String query = "select count(*) from WikiEntryComment where WikiEntryComment.WikiEntryId = ? ";
        return getSimpleJdbcTemplate().queryForInt(query, entryId);
    }
    
    public void deleteAllWikiEntryComments(int entryId) throws Exception
	{
		String sql="delete from " + getTableName() + " where WikiEntryId = ?";
		getSimpleJdbcTemplate().update(sql, entryId);        
	}
}