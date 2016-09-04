package com.era.community.blog.dao; 

import support.community.database.QueryScroller;

/**
 *	Interface with package level access to hold methods private to the package.
 */
interface BlogEntryCommentLikeDao extends com.era.community.blog.dao.generated.BlogEntryCommentLikeDaoBase, BlogEntryCommentLikeFinder
{
    QueryScroller listCommentLikesForBlogEntry(BlogEntry entry) throws Exception;
    
}

