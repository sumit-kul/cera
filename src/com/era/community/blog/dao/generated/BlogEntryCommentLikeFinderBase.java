package com.era.community.blog.dao.generated; 
import com.era.community.blog.dao.BlogEntry;
import com.era.community.blog.dao.BlogEntryCommentLike;

public interface BlogEntryCommentLikeFinderBase
{
    public BlogEntryCommentLike getBlogEntryCommentLikeForId(int id) throws Exception;
    public BlogEntryCommentLike newBlogEntryCommentLike() throws Exception;
    public void clearBlogEntryCommentLikesForBlogEntry(BlogEntry entry) throws Exception;
}