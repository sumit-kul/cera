package com.era.community.wiki.dao; 

public interface WikiEntryCommentLikeFinder extends com.era.community.wiki.dao.generated.WikiEntryCommentLikeFinderBase
{
    public int getCommentLikeCount() throws Exception;
    
    public int getCommentLikeCountForWikiEntry(int entryId, int commentId) throws Exception;
    
    public WikiEntryCommentLike getLikeForWikiEntryCommentAndUser(int commentId, int userId) throws Exception;
}