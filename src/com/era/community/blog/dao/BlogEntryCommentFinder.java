package com.era.community.blog.dao; 

public interface BlogEntryCommentFinder extends com.era.community.blog.dao.generated.BlogEntryCommentFinderBase
{
    public int getCommentCount() throws Exception;
    public int getCommentCountForBlogEntry(int entryId) throws Exception;
}