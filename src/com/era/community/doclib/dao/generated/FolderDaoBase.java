package com.era.community.doclib.dao.generated; 

import com.era.community.doclib.dao.Folder;

public interface FolderDaoBase extends FolderFinderBase
{
  public void store(Folder o) throws Exception;
  //public void deleteAlbumForId(int id) throws Exception;
  public void delete(Folder o) throws Exception;
}