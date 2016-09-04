package com.era.community.forum.dao; 

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.blog.dao.generated.BlogEntryCommentLikeEntity;

public class ForumResponseLikeDaoImpl extends com.era.community.forum.dao.generated.ForumResponseLikeDaoBaseImpl implements ForumResponseLikeDao
{
	public QueryScroller listCommentLikesForForumResponse(int forumItemId, int forumTopicId) throws Exception
	{
		final String SQL = "select C.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PhotoPresent as PhotoPresent " + 
		"from ForumResponseLike C, User U " +
		"where C.ForumItemId = ? and C.ForumTopicId = ?" +
		"and C.PosterId = U.Id";

		QueryScroller scroller = getQueryScroller(SQL, BlogEntryCommentLikeEntity.class, forumItemId, forumTopicId);
		scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}

	public int getCommentLikeCount() throws Exception
	{
		String query = "select count(*) from ForumResponseLike";
		return getJdbcTemplate().queryForInt(query);
	}

	public int getCommentLikeCountForForumResponse(int forumItemId, int forumTopicId) throws Exception
	{
		String query = "select count(*) from ForumResponseLike where ForumResponseLike.ForumItemId = ? and ForumResponseLike.ForumTopicId = ? ";
		return getSimpleJdbcTemplate().queryForInt(query, forumItemId, forumTopicId);
	}
}

