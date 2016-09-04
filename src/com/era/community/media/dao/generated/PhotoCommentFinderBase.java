package com.era.community.media.dao.generated; 

import com.era.community.media.dao.PhotoComment;

public interface PhotoCommentFinderBase
{
    public PhotoComment getPhotoCommentForId(int id) throws Exception;
    public PhotoComment newPhotoComment() throws Exception;
}