package com.era.community.monitor.dao;

import java.util.Date;

import com.era.community.forum.dao.ForumFinder;

public class ForumSubscription extends Subscription
{
	protected Integer ForumId  ;
	protected ForumFinder forumFinder;

	@Override
	public String getItemName() throws Exception
	{
		return "Forum";
	}

	@Override
	public String getItemUrl() throws Exception
	{
		return  "cid/"+this.getCommunityId()+"/forum/showTopics.do";
	}

	@Override
	public String getItemType() throws Exception
	{
		return "Forum";
	}

	@Override
	public Date getItemLastUpdateDate() throws Exception
	{
		return forumFinder.getForumForId(getForumId()).getLatestPostDate(0);
	}

	public final void setForumFinder(ForumFinder forumFinder)
	{
		this.forumFinder = forumFinder;
	}

	@Override
	public Object getItem() throws Exception
	{
		return forumFinder.getForumForId(this.getForumId());
	}

	public int compareTo(Subscription o) 
	{
		return this.sortOrder-o.sortOrder;
	}
	
	@Override
	public int getSortOrder() throws Exception
	{
		return 9;
	}

	public Integer getForumId() {
		return ForumId;
	}

	public void setForumId(Integer forumId) {
		ForumId = forumId;
	}
}