package com.era.community.forum.dao.generated; 

import com.era.community.forum.dao.Forum;

public interface ForumDaoBase extends ForumFinderBase
{
	public void store(Forum o) throws Exception;
	public void deleteForumForId(int id) throws Exception;
	public void delete(Forum o) throws Exception;
}

