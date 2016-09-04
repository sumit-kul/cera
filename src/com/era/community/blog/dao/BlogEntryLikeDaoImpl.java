package com.era.community.blog.dao; 

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.blog.dao.generated.BlogEntryEntity;

public class BlogEntryLikeDaoImpl extends com.era.community.blog.dao.generated.BlogEntryLikeDaoBaseImpl implements BlogEntryLikeDao
{
    public QueryScroller listLikesForBlogEntry(BlogEntry entry) throws Exception
    {
        final String SQL = "select C.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PhotoPresent as PhotoPresent " + 
            "from BlogEntryLike C, User U " +
            "where C.BlogEntryId = ? " +
            "and C.PosterId = U.Id";
        
        QueryScroller scroller = getQueryScroller(SQL, BlogEntryEntity.class, entry.getId());
        scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
        return scroller;
    }

    public int getLikeCount() throws Exception
    {
        String query = "select count(*) from BlogEntryLike";
        return getJdbcTemplate().queryForInt(query);
    }
    
    public int getLikeCountForBlogEntry(int entryId) throws Exception
    {
        String query = "select count(*) from BlogEntryLike where BlogEntryLike.BlogEntryId = ? ";
        return getSimpleJdbcTemplate().queryForInt(query, entryId);
    }
    
    public BlogEntryLike getLikeForBlogEntryAndUser(int entryId, int userId) throws Exception
    {
    	return (BlogEntryLike)getEntityWhere("BlogEntryId = ? and PosterId = ?", entryId, userId);
    }
}