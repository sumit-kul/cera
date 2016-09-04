package com.era.community.media.dao.generated; 
import com.era.community.media.dao.PhotoLike;

public interface PhotoLikeFinderBase
{
    public PhotoLike getPhotoLikeForId(int id) throws Exception;
    public PhotoLike newPhotoLike() throws Exception;
}