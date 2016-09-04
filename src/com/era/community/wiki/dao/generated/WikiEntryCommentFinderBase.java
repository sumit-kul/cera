package com.era.community.wiki.dao.generated; 

import com.era.community.wiki.dao.WikiEntry;
import com.era.community.wiki.dao.WikiEntryComment;

public interface WikiEntryCommentFinderBase
{
    public WikiEntryComment getWikiEntryCommentForId(int id) throws Exception;
    public WikiEntryComment newWikiEntryComment() throws Exception;
    public void clearWikiEntryCommentsForWikiEntry(WikiEntry entry) throws Exception;
}