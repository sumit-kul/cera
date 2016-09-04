package com.era.community.media.dao.generated; 

import com.era.community.media.dao.PhotoLike;

public interface PhotoLikeDaoBase extends PhotoLikeFinderBase
{
  public void store(PhotoLike o) throws Exception;
  public void deletePhotoLikeForId(int id) throws Exception;
  public void delete(PhotoLike o) throws Exception;
}