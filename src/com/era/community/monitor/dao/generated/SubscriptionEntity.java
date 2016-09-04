package com.era.community.monitor.dao.generated; 

public abstract class SubscriptionEntity extends support.community.framework.CommandBeanImpl
{
	private java.lang.Integer UserId;
	private java.lang.Integer CommunityId;
	private int Frequency;
	private java.util.Date Modified;
	private java.util.Date Created;
	private int Id;
	private java.lang.Integer WikiId;

	private java.lang.Integer DocLibId;

	private java.lang.Integer EventCalendarId;

	private java.lang.Integer BlogEntryId;

	private java.lang.Integer WikiEntryId;

	private java.lang.Integer ForumId;

	private java.lang.Integer BlogId;

	private java.lang.Integer DocumentId;

	private java.lang.Integer CommunityBlogId;
	
	private java.lang.Integer PersonalBlogId;

	private java.lang.Integer ThreadId;
	
	private java.lang.Integer EventId;
	
	private int WebSubscription;
	private int MailSubscription;
	private int PageSubscription;

	public final java.lang.Integer getUserId() { return UserId; }
	public final void setUserId(java.lang.Integer v) {  UserId = v; }
	public final java.lang.Integer getCommunityId() { return CommunityId; }
	public final void setCommunityId(java.lang.Integer v) {  CommunityId = v; }
	public final int getFrequency() { return Frequency; }
	public final void setFrequency(int v) {  Frequency = v; }
	public final java.util.Date getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v; }
	public final java.util.Date getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v; }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final java.lang.Integer getWikiId() { return WikiId; }
	public final void setWikiId(java.lang.Integer v) {  WikiId = v; }

	public final java.lang.Integer getDocLibId() { return DocLibId; }
	public final void setDocLibId(java.lang.Integer v) {  DocLibId = v; }

	public final java.lang.Integer getEventCalendarId() { return EventCalendarId; }
	public final void setEventCalendarId(java.lang.Integer v) {  EventCalendarId = v; }

	public final java.lang.Integer getBlogEntryId() { return BlogEntryId; }
	public final void setBlogEntryId(java.lang.Integer v) {  BlogEntryId = v; }

	public final java.lang.Integer getWikiEntryId() { return WikiEntryId; }
	public final void setWikiEntryId(java.lang.Integer v) {  WikiEntryId = v; }

	public final java.lang.Integer getForumId() { return ForumId; }
	public final void setForumId(java.lang.Integer v) {  ForumId = v; }

	public final java.lang.Integer getBlogId() { return BlogId; }
	public final void setBlogId(java.lang.Integer v) {  BlogId = v; }

	public final java.lang.Integer getDocumentId() { return DocumentId; }
	public final void setDocumentId(java.lang.Integer v) {  DocumentId = v; }

	public final java.lang.Integer getCommunityBlogId() { return CommunityBlogId; }
	public final void setCommunityBlogId(java.lang.Integer v) {  CommunityBlogId = v; }
	
	public final java.lang.Integer getPersonalBlogId() { return PersonalBlogId; }
	public final void setPersonalBlogId(java.lang.Integer v) {  PersonalBlogId = v; }

	public final java.lang.Integer getThreadId() { return ThreadId; }
	public final void setThreadId(java.lang.Integer v) {  ThreadId = v; }
	
	public final java.lang.Integer getEventId() { return EventId; }
	public final void setEventId(java.lang.Integer v) {  EventId = v; }
	
	public final int getWebSubscription() { return WebSubscription; }
	public final void setWebSubscription(int v) {  WebSubscription = v; }
	public final int getMailSubscription() { return MailSubscription; }
	public final void setMailSubscription(int v) {  MailSubscription = v; }
	public final int getPageSubscription() { return PageSubscription; }
	public final void setPageSubscription(int v) {  PageSubscription = v; }
}