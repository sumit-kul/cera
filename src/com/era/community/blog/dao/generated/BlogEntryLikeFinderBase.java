package com.era.community.blog.dao.generated; 
import com.era.community.blog.dao.*;

/**
 * Basic Dao .
*/
public interface BlogEntryLikeFinderBase
{
    public BlogEntryLike getBlogEntryLikeForId(int id) throws Exception;
    public BlogEntryLike newBlogEntryLike() throws Exception;
    public void clearBlogEntryLikesForBlogEntry(BlogEntry entry) throws Exception;
}

