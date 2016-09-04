package com.era.community.forum.dao; 

public interface ForumItemLikeFinder extends com.era.community.forum.dao.generated.ForumItemLikeFinderBase
{
	public int getLikeCount() throws Exception;

	public int getLikeCountForForumItem(int itemId) throws Exception;

	public ForumItemLike getLikeForForumItemAndUser(int entryId, int userId) throws Exception;
}