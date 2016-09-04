package com.era.community.forum.dao; 

import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.communities.dao.Community;
import com.era.community.forum.ui.dto.ForumItemHeaderDto;
import com.era.community.forum.ui.dto.ForumTopicPannelDto;

public interface ForumItemFinder extends com.era.community.forum.dao.generated.ForumItemFinderBase
{
	public List getItemBeans(int iItem) throws Exception;
	public QueryScroller listAllItems() throws Exception;
	public QueryScroller listAllTopics() throws Exception;

	public int getTopicCount(int groupId) throws Exception;
	public int getThreadCount(int groupId) throws Exception;
	public int getResponseCount(int groupId) throws Exception;
	public int getParticipantCount(int groupId) throws Exception;

	public int getTopicCountForCommunity(Community comm) throws Exception;
	public int getThreadCountForCommunity(Community comm) throws Exception;
	public int getResponseCountForCommunity(Community comm) throws Exception;
	public int getParticipantCountForCommunity(Community comm) throws Exception;

	public int getStickyTopicCount(int forumId) throws Exception;
	public void updateForhierarchy(int parentId, int topicId, int newId, int isROOT) throws Exception;
	
	public int getLastSiblingId(int parentId, int topicId, int newId) throws Exception;
	public int getDepthForItem(int itemId) throws Exception;
	
	public ForumResponse getLatestPost(ForumItem item) throws Exception;
	public List<ForumTopicPannelDto> listHotTopics(int max) throws Exception ;
	public QueryScroller listForumResponsesForDsiplay(int topicId) throws Exception;
	
	public ForumItemHeaderDto getForumItemForHeader(int itemId) throws Exception;
}