package com.era.community.monitor.dao;

import java.util.Date;

import com.era.community.forum.dao.ForumItem;
import com.era.community.forum.dao.ForumItemFinder;
import com.era.community.forum.dao.ForumTopic;

public class ThreadSubscription extends Subscription
{
	/**
	 * @column integer
	 */
	protected Integer ThreadId;

	protected ForumItemFinder forumItemFinder;

	public Integer getThreadId()
	{
		return ThreadId;
	}

	public void setThreadId(Integer threadId)
	{
		ThreadId = threadId;
	}

	@Override
	public String getItemName() throws Exception
	{
		ForumItem item = forumItemFinder.getForumItemForId(this.getThreadId());
		return item.getSubject();
	}

	public final void setForumItemFinder(ForumItemFinder forumItemFinder)
	{
		this.forumItemFinder = forumItemFinder;
	}

	@Override
	public String getItemUrl() throws Exception
	{
		return "cid/" + this.getCommunityId() + "/forum/forumThread.do?id=" + this.getThreadId().intValue();
	}

	@Override
	public String getItemType() throws Exception
	{
		return "Forum Topic";
	}

	@Override
	public Date getItemLastUpdateDate() throws Exception
	{
		ForumItem item =  forumItemFinder.getForumItemForId(getThreadId());
		ForumTopic topic = (ForumTopic)item;
		return topic.getLastPostDate();
	}

	@Override
	public Object getItem() throws Exception
	{
		return forumItemFinder.getForumItemForId(this.getThreadId());
	}

	@Override
	public int getSortOrder() throws Exception
	{
		return 8 ;
	}

	public int compareTo(Subscription o) 
	{
		return this.sortOrder-o.sortOrder;

	}
}