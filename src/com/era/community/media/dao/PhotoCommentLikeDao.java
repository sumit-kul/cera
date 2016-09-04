package com.era.community.media.dao; 

import support.community.database.QueryScroller;

interface PhotoCommentLikeDao extends com.era.community.media.dao.generated.PhotoCommentLikeDaoBase, PhotoCommentLikeFinder
{
    QueryScroller listCommentLikesForPhoto(Photo entry) throws Exception;
}