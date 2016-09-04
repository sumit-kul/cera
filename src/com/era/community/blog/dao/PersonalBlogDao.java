package com.era.community.blog.dao; 

import java.util.Date;

import support.community.database.QueryScroller;

interface PersonalBlogDao extends com.era.community.blog.dao.generated.PersonalBlogDaoBase, PersonalBlogFinder
{
	public QueryScroller listPersonalBlogEntries(PersonalBlog pblogs) throws Exception;
	public Date getLatestPostDate(PersonalBlog pblogs) throws Exception;
}