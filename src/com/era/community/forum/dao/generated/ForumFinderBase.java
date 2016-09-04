package com.era.community.forum.dao.generated; 

import com.era.community.forum.dao.Forum;

public interface ForumFinderBase
{
	public Forum getForumForId(int id) throws Exception;
	public Forum newForum() throws Exception;
}

