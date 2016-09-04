package com.era.community.monitor.dao; 

import java.util.List;

public interface SubscriptionFinder extends com.era.community.monitor.dao.generated.SubscriptionFinderBase
{
	public CommunitySubscription getCommunitySubscriptionForUser(int communityId, int userId) throws Exception;

	public ForumSubscription  getForumSubscriptionForUser(int forumId, int userId) throws Exception;
	public ThreadSubscription  getThreadSubscriptionForUser(int threadId, int userId) throws Exception;

	public EventCalendarSubscription  getEventCalendarSubscriptionForUser(int calendarId, int userId) throws Exception;
	public EventSubscription  getEventSubscriptionForUser(int eventId, int userId) throws Exception;

	public DocLibSubscription  getDocLibSubscriptionForUser(int doclibId, int userId) throws Exception;
	public DocumentSubscription  getDocumentSubscriptionForUser(int documentId, int userId) throws Exception;

	public WikiSubscription  getWikiSubscriptionForUser(int wikiId, int userId) throws Exception;
	public WikiEntrySubscription  getWikiEntrySubscriptionForUser(int wikientryId, int userId) throws Exception;

	public CommunityBlogSubscription  getCommunityBlogSubscriptionForUser(int consId, int userId) throws Exception;
	public PersonalBlogSubscription  getPersonalBlogSubscriptionForUser(int consId, int userId) throws Exception;

	public BlogEntrySubscription getCommunityBlogEntrySubscriptionForUser(int entryId, int userId, int communityId) throws Exception;
	public BlogEntrySubscription getPersonalBlogEntrySubscriptionForUser(int entryId, int userId) throws Exception;

	public List getSubscriptionsForForum(int forumId) throws Exception;
	public List getSubscriptionsForThread(int threadId) throws Exception;
	public void deleteSubscriptionsForThread(int threadId) throws Exception;
	public void deleteSubscriptionsForCommunityBlogEntry(int blogEntryId, int communityId) throws Exception;
	public void deleteSubscriptionsForPersonalBlogEntry(int blogEntryID) throws Exception;
	public void deleteSubscriptionsForUser(int userId) throws Exception;
	public void deleteSubscriptionsForUserAndCommunity(int userId, int communityId) throws Exception;

	public List getSubscriptionsForEventCalendar(int calendarId) throws Exception;

	public List getSubscriptionsForDocLib(int doclibId) throws Exception;
	public List getSubscriptionsForDocument(int documentId) throws Exception;

	public List getSubscriptionsForWiki(int wikiId) throws Exception;
	public List getSubscriptionsForWikiEntry(int wikiEntryId) throws Exception;
	public void deleteSubscriptionsForWikiEntry(int entryId) throws Exception;

	public List getSubscriptionsForBlog(int blogId) throws Exception;
	public List getSubscriptionsForBlogEntry(int blogEntryId) throws Exception;

	public List  getSubscriptionsForCommunityBlog(int communityBlogId) throws Exception;
	public List<PersonalBlogSubscription>  getSubscriptionsForPersonalBlog(int PersonalBlogId) throws Exception;

	public List getSubscriptionsForUserAndFrequency(int userId, int frequency) throws Exception;
}