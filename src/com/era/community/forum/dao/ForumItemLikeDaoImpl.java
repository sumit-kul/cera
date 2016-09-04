package com.era.community.forum.dao; 

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.forum.ui.dto.ForumItemDto;

public class ForumItemLikeDaoImpl extends com.era.community.forum.dao.generated.ForumItemLikeDaoBaseImpl implements ForumItemLikeDao
{
	public QueryScroller listLikesForForumItem(ForumItem entry) throws Exception
	{
		final String SQL = "select C.*, CONCAT_WS(' ',U.FirstName,U.LastName) as DisplayName, U.PhotoPresent as PhotoPresent " + 
		"from ForumItemLike C, User U " +
		"where C.BlogEntryId = ? " +
		"and C.PosterId = U.Id";

		QueryScroller scroller = getQueryScroller(SQL, ForumItemDto.class, entry.getId());
		scroller.addScrollKey("STEMP.DatePosted", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller;
	}

	public int getLikeCount() throws Exception
	{
		String query = "select count(*) from ForumItemLike";
		return getJdbcTemplate().queryForInt(query);
	}

	public int getLikeCountForForumItem(int itemId) throws Exception
	{
		String query = "select count(*) from ForumItemLike where ForumItemLike.ForumItemId = ? ";
		return getSimpleJdbcTemplate().queryForInt(query, itemId);
	}

	public ForumItemLike getLikeForForumItemAndUser(int entryId, int userId) throws Exception
	{
		return (ForumItemLike)getEntityWhere("ForumItemId = ? and PosterId = ?", entryId, userId);
	}
}