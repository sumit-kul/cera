package com.era.community.media.dao; 

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.media.dao.generated.PhotoCommentLikeEntity;

public class PhotoCommentLikeDaoImpl extends com.era.community.media.dao.generated.PhotoCommentLikeDaoBaseImpl implements PhotoCommentLikeDao
{
	public QueryScroller listCommentLikesForPhoto(Photo photo) throws Exception
	{
		final String SQL = "select C.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PhotoPresent as PhotoPresent " + 
		"from PhotoCommentLike C, User U " +
		"where C.PhotoId = ? and C.PhotoCommentId = ?" +
		"and C.PosterId = U.Id";

		QueryScroller scroller = getQueryScroller(SQL, PhotoCommentLikeEntity.class, photo.getId());
		scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}

	public int getCommentLikeCount() throws Exception
	{
		String query = "select count(*) from PhotoCommentLike";
		return getJdbcTemplate().queryForInt(query);
	}

	public int getLikeCountForPhotoComment(int photoId, int commentId) throws Exception
	{
		String query = "select count(*) from PhotoCommentLike where PhotoCommentLike.PhotoId = ? and PhotoCommentLike.PhotoCommentId = ? ";
		return getSimpleJdbcTemplate().queryForInt(query, photoId, commentId);
	}

	public PhotoCommentLike getLikeForPhotoCommentAndUser(int photoId, int commentId, int userId) throws Exception
	{
		return (PhotoCommentLike)getEntityWhere("PhotoId = ? and PhotoCommentId = ? and PosterId = ?", photoId, commentId, userId);
	}
}