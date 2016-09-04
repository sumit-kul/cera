package com.era.community.media.dao; 

public interface PhotoCommentLikeFinder extends com.era.community.media.dao.generated.PhotoCommentLikeFinderBase
{
	public int getCommentLikeCount() throws Exception;
	public int getLikeCountForPhotoComment(int photoId, int commentId) throws Exception;
	public PhotoCommentLike getLikeForPhotoCommentAndUser(int photoId, int commentId, int userId) throws Exception;
}