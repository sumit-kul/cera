package com.era.community.media.dao; 

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

public class PhotoCommentDaoImpl extends com.era.community.media.dao.generated.PhotoCommentDaoBaseImpl implements PhotoCommentDao
{
    public QueryScroller listCommentsForPhoto(int photoId, int currUserId) throws Exception
    {
    	String uld = " , 0 as UserLikeId ";
    	if (currUserId > 0) {
    		uld = " , (select count(*) from PhotoCommentLike L where L.PhotoId = C.PhotoId and L.PhotoCommentId = C.Id and L.PosterId = ? ) as UserLikeId ";
		}
    	
        final String SQL = "select T.* from ( select C.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PhotoPresent as PhotoPresent " + 
        	" , (select count(*) from PhotoCommentLike L where L.PhotoId = C.PhotoId and L.PhotoCommentId = C.Id) as LikeCommentCount  " +
        	uld + " from PhotoComment C, User U " +
            " where C.PhotoId = ? " +
            " and C.PosterId = U.Id ) as T";
        
        QueryScroller scroller;
        if (currUserId > 0) {
        	scroller = getQueryScroller(SQL, true, PhotoComment.class, currUserId, photoId);
        } else {
        	scroller = getQueryScroller(SQL, true, PhotoComment.class, photoId);
        }
        
        scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
        return scroller;
    }

    public int getCommentCount() throws Exception
    {
        String query = "select count(*) from PhotoComment";
        return getJdbcTemplate().queryForInt(query);
    }
    
    public int getCommentCountForPhoto(int photoId) throws Exception
    {
        String query = "select count(*) from PhotoComment where PhotoComment.PhotoId = ? ";
        return getSimpleJdbcTemplate().queryForInt(query, photoId);
    }
}