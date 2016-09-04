package com.era.community.blog.dao; 

public interface BlogEntryLikeFinder extends com.era.community.blog.dao.generated.BlogEntryLikeFinderBase
{
    public int getLikeCount() throws Exception;
    
    public int getLikeCountForBlogEntry(int entryId) throws Exception;
    
    public BlogEntryLike getLikeForBlogEntryAndUser(int entryId, int userId) throws Exception;
}

