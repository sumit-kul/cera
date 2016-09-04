package com.era.community.doclib.dao.generated; 

import com.era.community.doclib.dao.*;

public interface DocumentCommentDaoBase extends DocumentCommentFinderBase
{
  public void store(DocumentComment o) throws Exception;
  public void deleteDocumentCommentForId(int id) throws Exception;
  public void delete(DocumentComment o) throws Exception;
}