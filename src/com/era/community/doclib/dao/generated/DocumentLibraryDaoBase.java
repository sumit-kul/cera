package com.era.community.doclib.dao.generated; 

import com.era.community.doclib.dao.*;

public interface DocumentLibraryDaoBase extends DocumentLibraryFinderBase
{
  public void store(DocumentLibrary o) throws Exception;
  public void deleteDocumentLibraryForId(int id) throws Exception;
  public void delete(DocumentLibrary o) throws Exception;
}