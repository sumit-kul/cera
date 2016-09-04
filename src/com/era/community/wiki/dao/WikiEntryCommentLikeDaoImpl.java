package com.era.community.wiki.dao; 

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.wiki.dao.generated.WikiEntryCommentLikeEntity;

public class WikiEntryCommentLikeDaoImpl extends com.era.community.wiki.dao.generated.WikiEntryCommentLikeDaoBaseImpl implements WikiEntryCommentLikeDao
{
    public QueryScroller listCommentLikesForWikiEntry(WikiEntry entry) throws Exception
    {
        final String SQL = "select C.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PhotoPresent as PhotoPresent " + 
            "from WikiEntryCommentLike C, User U " +
            "where C.WikiEntryId = ? and C.WikiEntryCommentId = ?" +
            "and C.PosterId = U.Id";
        
        QueryScroller scroller = getQueryScroller(SQL, WikiEntryCommentLikeEntity.class, entry.getId());
        scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
        return scroller;
    }

    public int getCommentLikeCount() throws Exception
    {
        String query = "select count(*) from WikiEntryCommentLike";
        return getJdbcTemplate().queryForInt(query);
    }
    
    public int getCommentLikeCountForWikiEntry(int entryId, int commentId) throws Exception
    {
        String query = "select count(*) from WikiEntryCommentLike where WikiEntryCommentLike.WikiEntryId = ? and WikiEntryCommentLike.WikiEntryCommentId = ? ";
        return getSimpleJdbcTemplate().queryForInt(query, entryId, commentId);
    }
    
    public WikiEntryCommentLike getLikeForWikiEntryCommentAndUser(int commentId, int userId) throws Exception
    {
    	return (WikiEntryCommentLike)getEntityWhere("WikiEntryCommentId = ? and PosterId = ?", commentId, userId);
    }
}