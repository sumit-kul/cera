package com.era.community.doclib.dao.generated; 

import com.era.community.doclib.dao.*;

public interface DocumentRatingDaoBase extends DocumentRatingFinderBase
{
  public void store(DocumentRating o) throws Exception;
  public void deleteDocumentRatingForId(int id) throws Exception;
  public void delete(DocumentRating o) throws Exception;
}