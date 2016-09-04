package com.era.community.forum.dao.generated; 

import com.era.community.forum.dao.ForumResponseLike;

public interface ForumResponseLikeDaoBase extends ForumResponseLikeFinderBase
{
	public void store(ForumResponseLike o) throws Exception;
	public void deleteForumResponseLikeForId(int id) throws Exception;
	public void delete(ForumResponseLike o) throws Exception;
}

