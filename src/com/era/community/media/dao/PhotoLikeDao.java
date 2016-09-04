package com.era.community.media.dao; 

import support.community.database.QueryScroller;

interface PhotoLikeDao extends com.era.community.media.dao.generated.PhotoLikeDaoBase, PhotoLikeFinder
{
    QueryScroller listLikesForPhoto(Photo photo) throws Exception;
}