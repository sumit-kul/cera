package com.era.community.monitor.dao.generated; 
import com.era.community.monitor.dao.*;

public interface SubscriptionFinderBase
{
    public Subscription getSubscriptionForId(int id) throws Exception;
    public CommunitySubscription newCommunitySubscription() throws Exception;
    public CommunityBlogSubscription newCommunityBlogSubscription() throws Exception;
    public PersonalBlogSubscription newPersonalBlogSubscription() throws Exception;
    public DocLibSubscription newDocLibSubscription() throws Exception;
    public EventCalendarSubscription newEventCalendarSubscription() throws Exception;
    public ForumSubscription newForumSubscription() throws Exception;
    public WikiEntrySubscription newWikiEntrySubscription() throws Exception;
    
    public BlogEntrySubscription newBlogEntrySubscription() throws Exception;
    public EventSubscription newEventSubscription() throws Exception;
    public DocumentSubscription newDocumentSubscription() throws Exception;
    public ThreadSubscription newThreadSubscription() throws Exception;
    public WikiSubscription newWikiSubscription() throws Exception;
}