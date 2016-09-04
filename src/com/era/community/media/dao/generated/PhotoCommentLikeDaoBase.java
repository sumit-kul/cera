package com.era.community.media.dao.generated; 

import com.era.community.media.dao.PhotoCommentLike;

public interface PhotoCommentLikeDaoBase extends PhotoCommentLikeFinderBase
{
  public void store(PhotoCommentLike o) throws Exception;
  public void deletePhotoCommentLikeForId(int id) throws Exception;
  public void delete(PhotoCommentLike o) throws Exception;
}