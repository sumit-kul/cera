package com.era.community.communities.dao; 

public interface CommunityActivityFinder extends com.era.community.communities.dao.generated.CommunityActivityFinderBase
{
	public CommunityActivity getMostRecentDocumentGroupActivity(int communityId, int folderId) throws Exception;
	public void clearCommunityActivityForBlogEntry(int entryId, int communityId) throws Exception;
	public void clearCommunityActivityForWikiEntry(int entryId, int communityId) throws Exception;
	public void clearCommunityActivityForForumItem(int itemId, int communityId) throws Exception;
	public void clearCommunityActivityForDocument(int docId, int folderId, int communityId) throws Exception;
	public void clearCommunityActivityForFolder(int folderId, int communityId) throws Exception;
	public void clearCommunityActivityForEvent(int eventId, int communityId) throws Exception;
}