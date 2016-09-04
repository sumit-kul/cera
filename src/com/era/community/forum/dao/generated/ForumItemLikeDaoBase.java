package com.era.community.forum.dao.generated; 

import com.era.community.forum.dao.ForumItemLike;

public interface ForumItemLikeDaoBase extends ForumItemLikeFinderBase
{
	public void store(ForumItemLike o) throws Exception;
	public void deleteForumItemLikeForId(int id) throws Exception;
	public void delete(ForumItemLike o) throws Exception;
}

