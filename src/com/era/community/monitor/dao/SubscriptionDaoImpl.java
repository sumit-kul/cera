package com.era.community.monitor.dao; 

import java.util.List;

import com.era.community.monitor.ui.dto.SubscriptionDto;

public class SubscriptionDaoImpl extends com.era.community.monitor.dao.generated.SubscriptionDaoBaseImpl implements SubscriptionDao
{
	public CommunitySubscription getCommunitySubscriptionForUser(int communityId, int userId) throws Exception
	{      
		return (CommunitySubscription)getEntityWhere("COMMUNITYID = ? and userId = ? and SYSTYPE = 'CommunitySubscription' ", communityId , userId);
	}

	public ForumSubscription getForumSubscriptionForUser(int forumId, int userId) throws Exception
	{      
		return (ForumSubscription)getEntityWhere("forumId = ? and userId = ?", forumId , userId);
	}

	public ThreadSubscription getThreadSubscriptionForUser(int threadId, int userId) throws Exception
	{      
		return (ThreadSubscription)getEntityWhere("threadId = ? and userId = ?", threadId , userId);
	}

	public EventCalendarSubscription getEventCalendarSubscriptionForUser(int calendarId, int userId) throws Exception
	{      
		return (EventCalendarSubscription)getEntityWhere("eventCalendarId = ? and userId = ?", calendarId , userId);
	}

	public EventSubscription getEventSubscriptionForUser(int eventId, int userId) throws Exception
	{      
		return (EventSubscription)getEntityWhere("eventId = ? and userId = ?", eventId , userId);
	}

	public DocLibSubscription getDocLibSubscriptionForUser(int doclibId, int userId) throws Exception
	{      
		return (DocLibSubscription)getEntityWhere("doclibId = ? and userId = ?", doclibId, userId);
	}

	public DocumentSubscription getDocumentSubscriptionForUser(int documentId, int userId) throws Exception
	{      
		return (DocumentSubscription)getEntityWhere("documentId = ? and userId = ?", documentId , userId);
	}

	public BlogEntrySubscription getCommunityBlogEntrySubscriptionForUser(int entryId, int userId, int communityId) throws Exception
	{      
		return (BlogEntrySubscription)getEntityWhere("blogEntryId = ? and userId = ? and CommunityId = ?", entryId, userId, communityId);
	}
	
	public BlogEntrySubscription getPersonalBlogEntrySubscriptionForUser(int entryId, int userId) throws Exception
	{      
		return (BlogEntrySubscription)getEntityWhere("blogEntryId = ? and userId = ? and CommunityId = 0", entryId, userId);
	}

	public WikiSubscription getWikiSubscriptionForUser(int wikiId, int userId) throws Exception
	{      
		return (WikiSubscription)getEntityWhere("wikiId = ? and userId = ?", wikiId, userId);
	}

	public WikiEntrySubscription getWikiEntrySubscriptionForUser(int wikientryId, int userId) throws Exception
	{      
		return (WikiEntrySubscription)getEntityWhere("wikiEntryId = ? and userId = ?", wikientryId , userId);
	}

	public List getSubscriptionsForForum(int forumId) throws Exception
	{
		return getBeanList("select * from ForumSubscription where forumId = ? order by 1", ForumSubscription.class, forumId);
	}

	public List getSubscriptionsForThread(int threadId) throws Exception
	{
		return getBeanList("select * from ThreadSubscription  where ThreadId = ? order by 1", ThreadSubscription.class, threadId);
	}
	
	public void deleteSubscriptionsForWikiEntry(int entryId) throws Exception
	{
		String sql="delete from WikiEntrySubscription where WikiEntryId = ?";
		getSimpleJdbcTemplate().update(sql, entryId);       
	}

	public void deleteSubscriptionsForThread(int threadId) throws Exception
	{
		String sql="delete from ThreadSubscription where ThreadId = ?";
		getSimpleJdbcTemplate().update(sql, threadId);       
	}
	
	public void deleteSubscriptionsForCommunityBlogEntry(int blogEntryId, int communityId) throws Exception
	{
		String sql="delete from BlogEntrySubscription where BlogEntryId = ? and CommunityId = ? ";
		getSimpleJdbcTemplate().update(sql, blogEntryId, communityId);       
	}
	
	public void deleteSubscriptionsForPersonalBlogEntry(int blogEntryId) throws Exception
	{
		String sql="delete from BlogEntrySubscription where BlogEntryId = ? and CommunityId = 0 ";
		getSimpleJdbcTemplate().update(sql, blogEntryId);       
	}

	public void deleteSubscriptionsForUser(int userId) throws Exception
	{
		String sql="delete from Subscription where UserId = ?";
		getSimpleJdbcTemplate().update(sql,userId);       
	}

	public void deleteSubscriptionsForUserAndCommunity(int userId, int communityId) throws Exception
	{
		String sql="delete from Subscription where UserId = ? and CommunityId= ?";
		getSimpleJdbcTemplate().update(sql, userId, communityId);       
	}

	public List getSubscriptionsForWiki(int wikiId) throws Exception
	{
		return getBeanList("select * from WikiSubscription where WikiId = ? order by 1", WikiSubscription.class, wikiId);
	}

	public List  getSubscriptionsForWikiEntry(int wikiEntryId) throws Exception
	{
		return getBeanList("select * from WikiEntrySubscription  where WikiEntryId = ? order by 1", WikiEntrySubscription.class, wikiEntryId);
	}

	public List  getSubscriptionsForBlog(int blogId) throws Exception
	{
		return getBeanList("select * from CommunityBlogSubscription  where BlogId = ? order by 1", SubscriptionDto.class, blogId);
	}

	public List<CommunityBlogSubscription>  getSubscriptionsForCommunityBlog(int communityBlogId) throws Exception
	{
		return getEntityList("select * from CommunityBlogSubscription  where CommunityBlogId = ? order by 1", new Object[] {new Integer(communityBlogId)});
	}
	
	public List<PersonalBlogSubscription>  getSubscriptionsForPersonalBlog(int PersonalBlogId) throws Exception
	{
		return getEntityList("select * from PersonalBlogSubscription  where PersonalBlogId = ? order by 1", new Object[] {new Integer(PersonalBlogId)});
	}

	public List  getSubscriptionsForBlogEntry(int threadId) throws Exception
	{
		return getBeanList("select * from BlogEntrySubscription  where BlogEntryId = ? order by 1", SubscriptionDto.class, threadId);
	}

	public List  getSubscriptionsForEventCalendar(int calendarId) throws Exception
	{
		return getEntityList("select * from EventCalendarSubscription where eventCalendarId = ? order by 1", new Object[] {new Integer(calendarId)});
	}

	public List getSubscriptionsForDocLib(int doclibId) throws Exception
	{
		return getEntityList("select * from DocLibSubscription where DocLibId = ? order by 1", new Object[] {new Integer(doclibId)});
	}

	public List getSubscriptionsForDocument(int documentId) throws Exception
	{
		return getEntityList("select * from DocumentSubscription  where DocumentId = ? order by 1", new Object[] {new Integer(documentId)});
	}

	public CommunityBlogSubscription getCommunityBlogSubscriptionForUser(int consId, int userId) throws Exception
	{
		return (CommunityBlogSubscription)getEntityWhere("communityBlogId = ? and userId = ?", consId, userId);
	}

	public PersonalBlogSubscription getPersonalBlogSubscriptionForUser(int consId, int userId) throws Exception
	{
		return (PersonalBlogSubscription)getEntityWhere("PersonalBlogId = ? and userId = ?", consId, userId);
	}

	public List getSubscriptionsForUserAndFrequency(int userId, int frequency) throws Exception
	{
		return getEntityList("select * from Subscription where MailSubscription = 1 and UserId = ? and Frequency = ? order by SysType desc, CommunityId ", new Object[] {new Integer(userId),new Integer(frequency)});
	}
}