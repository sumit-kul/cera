package com.era.community.wiki.dao; 

public interface WikiEntryCommentFinder extends com.era.community.wiki.dao.generated.WikiEntryCommentFinderBase
{
    public int getCommentCountForWikiEntry(int entryId) throws Exception;
    
    public void deleteAllWikiEntryComments(int entryId) throws Exception;
}