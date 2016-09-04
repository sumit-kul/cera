package com.era.community.forum.dao.generated; 

import com.era.community.forum.dao.ForumItem;
import com.era.community.forum.dao.ForumResponse;
import com.era.community.forum.dao.ForumTopic;

public interface ForumItemFinderBase
{
	public ForumItem getForumItemForId(int id) throws Exception;
	public ForumResponse newForumResponse() throws Exception;
	public ForumTopic newForumTopic() throws Exception;
}

