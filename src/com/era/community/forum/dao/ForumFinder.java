package com.era.community.forum.dao; 

import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.communities.dao.Community;

public interface ForumFinder extends com.era.community.forum.dao.generated.ForumFinderBase
{
	public Forum getForumForCommunity(Community comm) throws Exception;
	public Forum getForumForCommunityId(int commId) throws Exception;
	public List getForumThemeOptionList(int communityId) throws Exception;
	public QueryScroller listAllUnThemedTopicsByMostRecentResponse(String sortBy, String filterTagList) throws Exception;
}