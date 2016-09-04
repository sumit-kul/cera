package com.era.community.wiki.dao.generated; 

import com.era.community.wiki.dao.WikiEntryCommentLike;

public interface WikiEntryCommentLikeDaoBase extends WikiEntryCommentLikeFinderBase
{
  public void store(WikiEntryCommentLike o) throws Exception;
  public void deleteWikiEntryCommentLikeForId(int id) throws Exception;
  public void delete(WikiEntryCommentLike o) throws Exception;
}