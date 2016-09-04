package com.era.community.tagging.dao; 

import java.util.List;

import support.community.database.QueryScroller;

public interface TagFinder extends com.era.community.tagging.dao.generated.TagFinderBase
{
	public List getTagsForAllCommunities(int maxTags, String communityOption, String filterTagList) throws Exception;
	public List getTagsForMemberCommunities(int maxTags, int userId, String communityOption, String filterTagList) throws Exception;
	public List getTagsForFollowerCommunities(int maxTags, int userId, String communityOption, String filterTagList) throws Exception;
	
	public List getPopularCommunityTagsToAdd(int maxTags) throws Exception;
	public List getPopularTagsByParentTypeToAdd(String parentType, int maxTags) throws Exception;
	public List getAllTagTextForParentID(int parentId, int communityID, String parentType) throws Exception;
	public void clearExistingTag(int parentId, int communityId, String parentType, String tag) throws Exception;
	
	
	
	
	
	public List getTagsForAllTools(int maxTags, String parentType, int toolId) throws Exception;
	public List getTagsForAllBlogs(int maxTags, String filterTagList) throws Exception;
	
	public List getItemTagsForToolIdAndType(int toolId, int max, String toolType) throws Exception;
	
	public void clearTagsForParentId(int parentId) throws Exception;	
	public void clearTagsForParentIdUserId(int parentId, int userId) throws Exception;
	public void clearTagsForCommIdUserId(int commId, int userId) throws Exception;
	public void clearTagsForParentIdUserIdCommId(int parentId, int userId, int commId) throws Exception;
	
	public List getTagsForParentId(int parentId) throws Exception;
	public List getTagsForParentIdUserId(int parentId, int userId) throws Exception;
	public List getTagsForParentIdByPopularity(int parentId, int max) throws Exception;
	
	public List getTagsAlphaForCommunityId(int commId, int max) throws Exception;
	public List getTagsAlphaForParentId(int commId, int max) throws Exception;
	public List getItemsForTagInCommunity(int commId, String tagText) throws Exception;
	public List getTagsForUserId(int userId, int max) throws Exception;	
	public List getTagsForOnlyCommunityByPopularity(int communityId, int max) throws Exception;

	
	public List getAlphabaticTagsForAllCommunities(int max) throws Exception;

	public List getTagsForParentTypeByPopularity(int parentId, int communityID, int max, String parentType) throws Exception;
	public List getTagsForParentEntryByPopularity(int parentId, int communityId, int max) throws Exception;
	public List getTagsForcommunitiesForParentByPopularity(int parentId, int max) throws Exception;
	
	
	
	
	public List getTagsForAllCommunityByPopularity(int commId, int max, String parentType) throws Exception;
	public List getAllTagsForACommunityByPopularity(int commId, int maxTags) throws Exception;
	public QueryScroller getAllTagsSearchIndexing() throws Exception;
	public QueryScroller getAllItemsByTagInCommunity(int commId, String filterTagList, String submitType) throws Exception;
	public QueryScroller getAllBlogEntriesByTagForPersonalBlog(int bpcId, String filterTagList) throws Exception;
}

