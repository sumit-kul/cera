package com.era.community.doclib.dao.generated; 

import com.era.community.doclib.dao.DocumentLike;

public interface DocumentLikeDaoBase extends DocumentLikeFinderBase
{
  public void store(DocumentLike o) throws Exception;
  public void deleteDocumentLikeForId(int id) throws Exception;
  public void delete(DocumentLike o) throws Exception;
}

