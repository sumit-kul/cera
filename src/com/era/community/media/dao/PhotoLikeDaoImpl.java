package com.era.community.media.dao; 

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

public class PhotoLikeDaoImpl extends com.era.community.media.dao.generated.PhotoLikeDaoBaseImpl implements PhotoLikeDao
{
    public QueryScroller listLikesForPhoto(Photo photo) throws Exception
    {
        final String SQL = "select C.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PhotoPresent as PhotoPresent " + 
            "from PhotoLike C, User U " +
            "where C.PhotoId = ? " +
            "and C.PosterId = U.Id";
        
        QueryScroller scroller = getQueryScroller(SQL, PhotoLike.class, photo.getId());
        scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
        return scroller;
    }

    public int getLikeCount() throws Exception
    {
        String query = "select count(*) from PhotoLike";
        return getJdbcTemplate().queryForInt(query);
    }
    
    public int getLikeCountForPhoto(int photoId) throws Exception
    {
        String query = "select count(*) from PhotoLike where PhotoLike.PhotoId = ? ";
        return getSimpleJdbcTemplate().queryForInt(query, photoId);
    } 
    
    public PhotoLike getLikeForPhotoAndUser(int photoId, int userId) throws Exception
    {
    	return (PhotoLike)getEntityWhere("PhotoId = ? and PosterId = ? ", photoId, userId);
    }
}