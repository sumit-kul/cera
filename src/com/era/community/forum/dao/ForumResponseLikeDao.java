package com.era.community.forum.dao; 

import support.community.database.QueryScroller;

interface ForumResponseLikeDao extends com.era.community.forum.dao.generated.ForumResponseLikeDaoBase, ForumResponseLikeFinder
{
	QueryScroller listCommentLikesForForumResponse(int forumItemId, int forumTopicId) throws Exception;
}
