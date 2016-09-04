package com.era.community.doclib.dao.generated; 

import com.era.community.doclib.dao.DocGroup;

public interface DocGroupDaoBase extends DocGroupFinderBase
{
  public void store(DocGroup o) throws Exception;
  //public void deleteAlbumForId(int id) throws Exception;
  public void delete(DocGroup o) throws Exception;
}