package com.era.community.blog.dao; 

public interface BlogEntryCommentLikeFinder extends com.era.community.blog.dao.generated.BlogEntryCommentLikeFinderBase
{
    public int getCommentLikeCount() throws Exception;
    
    public int getCommentLikeCountForBlogEntry(int entryId, int commentId) throws Exception;
    
    public BlogEntryCommentLike getLikeForBlogEntryCommentAndUser(int commentId, int userId) throws Exception;
}

