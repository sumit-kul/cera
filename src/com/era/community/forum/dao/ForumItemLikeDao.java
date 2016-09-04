package com.era.community.forum.dao; 

import support.community.database.QueryScroller;

interface ForumItemLikeDao extends com.era.community.forum.dao.generated.ForumItemLikeDaoBase, ForumItemLikeFinder
{
	QueryScroller listLikesForForumItem(ForumItem entry) throws Exception;
}
