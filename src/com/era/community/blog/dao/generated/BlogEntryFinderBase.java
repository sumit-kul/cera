package com.era.community.blog.dao.generated; 
import com.era.community.blog.dao.*;

/**
 * Basic Dao .
*/
public interface BlogEntryFinderBase
{
    public BlogEntry getBlogEntryForId(int id) throws Exception;
    public BlogEntry newBlogEntry() throws Exception;
}

