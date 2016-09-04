package com.era.community.doclib.dao.generated; 

import com.era.community.doclib.dao.DocumentCommentLike;


public interface DocumentCommentLikeDaoBase extends DocumentCommentLikeFinderBase
{
  public void store(DocumentCommentLike o) throws Exception;
  public void deleteDocumentCommentLikeForId(int id) throws Exception;
  public void delete(DocumentCommentLike o) throws Exception;
}

