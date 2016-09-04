package com.era.community.blog.dao; 

import support.community.database.QueryScroller;

/**
 	Interface with package level access to hold methods private to the package.
 */
interface BlogEntryCommentDao extends com.era.community.blog.dao.generated.BlogEntryCommentDaoBase, BlogEntryCommentFinder
{
    QueryScroller listCommentsForBlogEntry(BlogEntry entry) throws Exception;
    
}

