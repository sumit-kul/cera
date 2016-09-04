package com.era.community.blog.dao.generated; 
import com.era.community.blog.dao.*;

/**
 * Basic Dao .
*/
public interface BlogEntryCommentFinderBase
{
    public BlogEntryComment getBlogEntryCommentForId(int id) throws Exception;
    public BlogEntryComment newBlogEntryComment() throws Exception;
    public void clearBlogEntryCommentsForBlogEntry(BlogEntry entry) throws Exception;
}

