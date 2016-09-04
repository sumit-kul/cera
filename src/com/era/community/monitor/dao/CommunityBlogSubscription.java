package com.era.community.monitor.dao;

import java.util.Date;

import com.era.community.blog.dao.CommunityBlogFinder;

public class CommunityBlogSubscription extends Subscription
{
	protected Integer CommunityBlogId;
	protected CommunityBlogFinder communityBlogFinder;

	@Override
	public String getItemName() throws Exception
	{
		return "Blog";
	}

	@Override
	public String getItemUrl() throws Exception
	{
		return  "cid/"+this.getCommunityId()+"/blog/viewBlog.do";
	}

	@Override
	public String getItemType() throws Exception
	{
		return "Blog";
	}

	@Override
	public Date getItemLastUpdateDate() throws Exception
	{
		return communityBlogFinder.getCommunityBlogForId(this.getCommunityBlogId()).getLatestPostDate();
	}

	public final Integer getCommunityBlogId()
	{
		return CommunityBlogId;
	}

	public final void setCommunityBlogId(Integer communityBlogId)
	{
		CommunityBlogId = communityBlogId;
	}

	public Object getItem() throws Exception
	{
		return communityBlogFinder.getCommunityBlogForId(this.getCommunityBlogId());
	}

	public int getSortOrder() throws Exception
	{
		return 2;
	}

	public int compareTo(Subscription o) 
	{
		return this.sortOrder-o.sortOrder;
	}

	public void setCommunityBlogFinder(CommunityBlogFinder communityBlogFinder) {
		this.communityBlogFinder = communityBlogFinder;
	}
}