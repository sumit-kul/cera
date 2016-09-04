package com.era.community.monitor.dao;

import java.util.Date;

import com.era.community.blog.dao.BlogEntryFinder;

public class BlogEntrySubscription extends Subscription
{
	protected Integer BlogEntryId  ;
	protected BlogEntryFinder blogEntryFinder;

	public Integer getBlogEntryId()
	{
		return BlogEntryId;
	}
	public void setBlogEntryId(Integer blogEntryId)
	{
		BlogEntryId = blogEntryId;
	}
	
	@Override
	public String getItemName() throws Exception
	{
		return null;
	}
	
	public final void setBlogEntryFinder(BlogEntryFinder blogEntryFinder)
	{
		this.blogEntryFinder = blogEntryFinder;
	}
	
	@Override
	public String getItemUrl() throws Exception
	{
		return  "cid/"+this.getCommunityId()+"/blog/blogEntry.do?id="+this.getBlogEntryId().intValue();
	}

	@Override
	public String getItemType() throws Exception
	{
		return "Blog Entry";
	}
	
	@Override
	public Date getItemLastUpdateDate() throws Exception
	{
		return null;
	}
	
	@Override
	public Object getItem() throws Exception
	{
		return null;
	}
	
	@Override
	public int getSortOrder() throws Exception
	{
		return 1;
	}

	public int compareTo(Subscription o) 
	{
		return this.sortOrder-o.sortOrder;
	}
}