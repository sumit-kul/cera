package com.era.community.blog.dao; 

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.blog.ui.dto.BlogEntryCommentDto;

public class BlogEntryCommentDaoImpl extends com.era.community.blog.dao.generated.BlogEntryCommentDaoBaseImpl implements BlogEntryCommentDao
{

    /* (non-Javadoc)
     * @see com.era.community.blog.dao.BlogEntryCommentDao#listCommentsForBlogEntry(com.era.community.blog.dao.BlogEntry)
     */
    public QueryScroller listCommentsForBlogEntry(BlogEntry entry) throws Exception
    {
        final String SQL = "select C.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PhotoPresent as PhotoPresent, " + 
        	"(select count(*) from BlogEntryCommentLike L where L.BlogEntryId = C.BlogEntryId and L.BlogEntryCommentId = C.Id) As LikeCommentCount " +
            "from BlogEntryComment C, User U " +
            "where C.BlogEntryId = ? " +
            "and C.PosterId = U.Id";
        
        QueryScroller scroller = getQueryScroller(SQL, BlogEntryCommentDto.class, entry.getId());
        scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
        return scroller;
    }

    public int getCommentCount() throws Exception
    {
        String query = "select count(*) from BlogEntryComment";
        return getJdbcTemplate().queryForInt(query);
    }
    
    public int getCommentCountForBlogEntry(int entryId) throws Exception
    {
        String query = "select count(*) from BlogEntryComment where BlogEntryComment.BlogEntryId = ? ";
        return getSimpleJdbcTemplate().queryForInt(query, entryId);
    }
}

