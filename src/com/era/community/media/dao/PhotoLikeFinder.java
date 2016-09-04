package com.era.community.media.dao; 

public interface PhotoLikeFinder extends com.era.community.media.dao.generated.PhotoLikeFinderBase
{
	public int getLikeCount() throws Exception;
	public int getLikeCountForPhoto(int photoId) throws Exception;
	public PhotoLike getLikeForPhotoAndUser(int photoId, int userId) throws Exception;
}