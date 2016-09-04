package com.era.community.blog.dao; 

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;
import com.era.community.blog.dao.generated.BlogEntryCommentLikeEntity;

/**
 *
*/
public class BlogEntryCommentLikeDaoImpl extends com.era.community.blog.dao.generated.BlogEntryCommentLikeDaoBaseImpl implements BlogEntryCommentLikeDao
{
    public QueryScroller listCommentLikesForBlogEntry(BlogEntry entry) throws Exception
    {
        final String SQL = "select C.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PhotoPresent as PhotoPresent " + 
            "from BlogEntryCommentLike C, User U " +
            "where C.BlogEntryId = ? and C.BlogEntryCommentId = ?" +
            "and C.PosterId = U.Id";
        
        QueryScroller scroller = getQueryScroller(SQL, BlogEntryCommentLikeEntity.class, entry.getId());
        scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
        return scroller;
    }

    public int getCommentLikeCount() throws Exception
    {
        String query = "select count(*) from BlogEntryCommentLike";
        return getJdbcTemplate().queryForInt(query);
    }
    
    public int getCommentLikeCountForBlogEntry(int entryId, int commentId) throws Exception
    {
        String query = "select count(*) from BlogEntryCommentLike where BlogEntryCommentLike.BlogEntryId = ? and BlogEntryCommentLike.BlogEntryCommentId = ? ";
        return getSimpleJdbcTemplate().queryForInt(query, entryId, commentId);
    }
    
    public BlogEntryCommentLike getLikeForBlogEntryCommentAndUser(int commentId, int userId) throws Exception
    {
    	return (BlogEntryCommentLike)getEntityWhere("BlogEntryCommentId = ? and PosterId = ?", commentId, userId);
    }
}