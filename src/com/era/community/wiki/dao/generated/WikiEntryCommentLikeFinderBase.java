package com.era.community.wiki.dao.generated; 

import com.era.community.wiki.dao.WikiEntry;
import com.era.community.wiki.dao.WikiEntryCommentLike;

public interface WikiEntryCommentLikeFinderBase
{
    public WikiEntryCommentLike getWikiEntryCommentLikeForId(int id) throws Exception;
    public WikiEntryCommentLike newWikiEntryCommentLike() throws Exception;
    public void clearWikiEntryCommentLikesForWikiEntry(WikiEntry entry) throws Exception;
}