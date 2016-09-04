package com.era.community.media.dao.generated; 

import com.era.community.media.dao.PhotoComment;

public interface PhotoCommentDaoBase extends PhotoCommentFinderBase
{
  public void store(PhotoComment o) throws Exception;
  public void deletePhotoCommentForId(int id) throws Exception;
  public void delete(PhotoComment o) throws Exception;
}