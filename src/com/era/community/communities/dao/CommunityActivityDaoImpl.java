package com.era.community.communities.dao; 

import com.era.community.pers.dao.UserActivity;

import support.community.application.ElementNotFoundException;


public class CommunityActivityDaoImpl extends com.era.community.communities.dao.generated.CommunityActivityDaoBaseImpl implements CommunityActivityDao
{
	public CommunityActivity getMostRecentDocumentGroupActivity(int communityId, int folderId) throws Exception
	{
		String where = " ";
		if (folderId > 0) {
			where = " CommunityId = ? and FolderId = ? and DocGroupNumber > 0 order by DocGroupNumber desc LIMIT 1 ";
			return (CommunityActivity)getEntityWhere(where, communityId, folderId);
		} else {
			where = " CommunityId = ? and DocGroupNumber > 0 order by DocGroupNumber desc LIMIT 1 ";
			return (CommunityActivity)getEntityWhere(where, communityId);
		}
	}
	
	public void clearCommunityActivityForBlogEntry(int entryId, int communityId) throws Exception
	{
		String sql="delete CommunityActivity, UserActivity from CommunityActivity, UserActivity " +
			" where CommunityActivity.Id = UserActivity.CommunityActivityId and BlogEntryId = ? and CommunityId = ? ";
		getSimpleJdbcTemplate().update(sql, entryId, communityId); 

	}
	
	public void clearCommunityActivityForWikiEntry(int entryId, int communityId) throws Exception
	{
		String sql="delete CommunityActivity, UserActivity from CommunityActivity, UserActivity " +
		" where CommunityActivity.Id = UserActivity.CommunityActivityId and WikiEntryId = ? and CommunityId = ? ";
		getSimpleJdbcTemplate().update(sql, entryId, communityId);     

	}
	
	public void clearCommunityActivityForForumItem(int itemId, int communityId) throws Exception
	{
		String sql="delete CommunityActivity, UserActivity from CommunityActivity, UserActivity " +
		" where CommunityActivity.Id = UserActivity.CommunityActivityId and ForumItemId = ? and CommunityId = ? ";
		getSimpleJdbcTemplate().update(sql, itemId, communityId);     

	}
	
	public void clearCommunityActivityForDocument(int docId, int folderId, int communityId) throws Exception
	{
		String sql="delete CommunityActivity, UserActivity from CommunityActivity, UserActivity " +
		" where CommunityActivity.Id = UserActivity.CommunityActivityId and CommunityActivity.DocumentId = ? and CommunityActivity.FolderId = ? and CommunityActivity.CommunityId = ? ";
		getSimpleJdbcTemplate().update(sql, docId, folderId, communityId);     

	}
	
	public void clearCommunityActivityForFolder(int folderId, int communityId) throws Exception
	{
		String sql="delete CommunityActivity, UserActivity from CommunityActivity, UserActivity " +
		" where CommunityActivity.Id = UserActivity.CommunityActivityId and FolderId = ? and CommunityId = ? ";
		getSimpleJdbcTemplate().update(sql, folderId, communityId);     

	}
	
	public void clearCommunityActivityForEvent(int eventId, int communityId) throws Exception
	{
		String sql="delete CommunityActivity, UserActivity from CommunityActivity, UserActivity " +
		" where CommunityActivity.Id = UserActivity.CommunityActivityId and EventId = ? and CommunityId = ? ";
		getSimpleJdbcTemplate().update(sql, eventId, communityId);     

	}
}