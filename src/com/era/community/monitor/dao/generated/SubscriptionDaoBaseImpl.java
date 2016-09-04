package com.era.community.monitor.dao.generated; 

import support.community.database.AbstractJdbcDaoSupport;

import com.era.community.monitor.dao.CommunityBlogSubscription;
import com.era.community.monitor.dao.BlogEntrySubscription;
import com.era.community.monitor.dao.PersonalBlogSubscription;
import com.era.community.monitor.dao.CommunitySubscription;
import com.era.community.monitor.dao.DocLibSubscription;
import com.era.community.monitor.dao.DocumentSubscription;
import com.era.community.monitor.dao.EventCalendarSubscription;
import com.era.community.monitor.dao.EventSubscription;
import com.era.community.monitor.dao.ForumSubscription;
import com.era.community.monitor.dao.Subscription;
import com.era.community.monitor.dao.ThreadSubscription;
import com.era.community.monitor.dao.WikiEntrySubscription;
import com.era.community.monitor.dao.WikiSubscription;

public abstract class SubscriptionDaoBaseImpl extends AbstractJdbcDaoSupport implements SubscriptionDaoBase
{
	public Subscription getSubscriptionForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		return (Subscription)getEntity(Subscription.class, keys);
	}

	public void deleteSubscriptionForId(int id) throws Exception
	{
		Object[] keys = new Object[] { new Integer(id) };
		deleteEntity(keys);
	}

	public void delete(Subscription o) throws Exception
	{
		deleteEntity(o);
	}

	public void store(Subscription o) throws Exception
	{
		storeEntity(o);
	}

	public CommunitySubscription newCommunitySubscription() throws Exception
	{
		return (CommunitySubscription)newEntity(CommunitySubscription.class);
	}

	public CommunityBlogSubscription newCommunityBlogSubscription() throws Exception
	{
		return (CommunityBlogSubscription)newEntity(CommunityBlogSubscription.class);
	}

	public BlogEntrySubscription newBlogEntrySubscription() throws Exception
	{
		return (BlogEntrySubscription)newEntity(BlogEntrySubscription.class);
	}

	public PersonalBlogSubscription newPersonalBlogSubscription() throws Exception
	{
		return (PersonalBlogSubscription)newEntity(PersonalBlogSubscription.class);
	}

	public DocLibSubscription newDocLibSubscription() throws Exception
	{
		return (DocLibSubscription)newEntity(DocLibSubscription.class);
	}

	public DocumentSubscription newDocumentSubscription() throws Exception
	{
		return (DocumentSubscription)newEntity(DocumentSubscription.class);
	}

	public EventCalendarSubscription newEventCalendarSubscription() throws Exception
	{
		return (EventCalendarSubscription)newEntity(EventCalendarSubscription.class);
	}

	public ForumSubscription newForumSubscription() throws Exception
	{
		return (ForumSubscription)newEntity(ForumSubscription.class);
	}

	public ThreadSubscription newThreadSubscription() throws Exception
	{
		return (ThreadSubscription)newEntity(ThreadSubscription.class);
	}

	public WikiEntrySubscription newWikiEntrySubscription() throws Exception
	{
		return (WikiEntrySubscription)newEntity(WikiEntrySubscription.class);
	}

	public WikiSubscription newWikiSubscription() throws Exception
	{
		return (WikiSubscription)newEntity(WikiSubscription.class);
	}

	public EventSubscription newEventSubscription() throws Exception
	{
		return (EventSubscription)newEntity(EventSubscription.class);
	}
}