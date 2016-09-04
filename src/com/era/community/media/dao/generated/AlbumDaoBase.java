package com.era.community.media.dao.generated; 

import com.era.community.media.dao.Album;

public interface AlbumDaoBase extends AlbumFinderBase
{
  public void store(Album o) throws Exception;
  //public void deleteAlbumForId(int id) throws Exception;
  public void delete(Album o) throws Exception;
}