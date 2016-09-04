package com.era.community.wiki.dao.generated; 

import com.era.community.wiki.dao.WikiEntryComment;

public interface WikiEntryCommentDaoBase extends WikiEntryCommentFinderBase
{
  public void store(WikiEntryComment o) throws Exception;
  public void deleteWikiEntryCommentForId(int id) throws Exception;
  public void delete(WikiEntryComment o) throws Exception;
}