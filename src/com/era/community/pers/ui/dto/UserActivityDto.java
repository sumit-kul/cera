package com.era.community.pers.ui.dto; 

public class UserActivityDto 
{
	private int communityId;
	private int documentId;
	private int folderId;
	private int docGroupNumber;
	private int blogEntryId;
	private int forumItemId;
	private int wikiEntryId;
	private int eventId;
	private int assignmentId;
	private int personalBlogEntryId;

	private int itemId;
	private String itemType;
	public int getCommunityId() {
		return communityId;
	}
	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	public int getFolderId() {
		return folderId;
	}
	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}
	public int getDocGroupNumber() {
		return docGroupNumber;
	}
	public void setDocGroupNumber(int docGroupNumber) {
		this.docGroupNumber = docGroupNumber;
	}
	public int getBlogEntryId() {
		return blogEntryId;
	}
	public void setBlogEntryId(int blogEntryId) {
		this.blogEntryId = blogEntryId;
	}
	public int getForumItemId() {
		return forumItemId;
	}
	public void setForumItemId(int forumItemId) {
		this.forumItemId = forumItemId;
	}
	public int getWikiEntryId() {
		return wikiEntryId;
	}
	public void setWikiEntryId(int wikiEntryId) {
		this.wikiEntryId = wikiEntryId;
	}
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public int getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(int assignmentId) {
		this.assignmentId = assignmentId;
	}
	public int getPersonalBlogEntryId() {
		return personalBlogEntryId;
	}
	public void setPersonalBlogEntryId(int personalBlogEntryId) {
		this.personalBlogEntryId = personalBlogEntryId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
}