package com.era.community.forum.dao; 

public interface ForumResponseLikeFinder extends com.era.community.forum.dao.generated.ForumResponseLikeFinderBase
{
	public int getCommentLikeCount() throws Exception;
	public int getCommentLikeCountForForumResponse(int forumItemId, int forumTopicId) throws Exception;
}