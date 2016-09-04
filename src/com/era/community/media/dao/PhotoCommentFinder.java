package com.era.community.media.dao; 

import support.community.database.QueryScroller;

public interface PhotoCommentFinder extends com.era.community.media.dao.generated.PhotoCommentFinderBase
{
    public int getCommentCount() throws Exception;
    
    public int getCommentCountForPhoto(int photoId) throws Exception;
    
    QueryScroller listCommentsForPhoto(int photoId, int currUserId) throws Exception;
}