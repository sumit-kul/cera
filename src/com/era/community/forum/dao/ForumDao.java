package com.era.community.forum.dao; 

import java.util.Date;
import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.communities.dao.Community;
import com.era.community.pers.dao.User;

interface ForumDao extends com.era.community.forum.dao.generated.ForumDaoBase, ForumFinder
{
	public Forum getForumForCommunity(Community comm) throws Exception;
	public QueryScroller listTopicsByMostRecentResponse(Forum forum, int themeId, String sortBy) throws Exception;
	public QueryScroller listItemsByDate(Forum forum, int themeId) throws Exception;
	public Date getLatestPostDate(Forum forum, int themeId) throws Exception;
	public List getItemsSince(Forum forum, Date date, int themeId) throws Exception;
}

