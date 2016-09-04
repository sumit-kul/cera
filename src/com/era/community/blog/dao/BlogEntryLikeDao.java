package com.era.community.blog.dao; 

import support.community.database.QueryScroller;

/**
 *	Interface with package level access to hold methods private to the package.
 */
interface BlogEntryLikeDao extends com.era.community.blog.dao.generated.BlogEntryLikeDaoBase, BlogEntryLikeFinder
{
    QueryScroller listLikesForBlogEntry(BlogEntry entry) throws Exception;
    
}

