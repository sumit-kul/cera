package com.era.community.forum.ui.dto; 

public class ForumTopicPannelDto
{     
	private int topicId;
	private String subject;
	private int communityId;
	private int authorId;
	private String communityName;
	private String fullName;
	private String logoPresent;
	private boolean photoPresent;
	private int lastPostId;
	private int ResponseCount;
	private int topicLike;
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}	
	public int getTopicLike() {
		return topicLike;
	}
	public void setTopicLike(Long topicLike) {
		this.topicLike = topicLike.intValue();
	}
	public int getTopicId() {
		return topicId;
	}
	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}
	public String getSubject() {
		return subject;
	}
	public String getEditedSubject() {
		String sSubject = subject;
		if(sSubject.length() >= 55) {
			sSubject = sSubject.substring(0, 52);
			sSubject = sSubject.concat("...");
		}
		return sSubject;
	}
		
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getCommunityId() {
		return communityId;
	}
	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public String getLogoPresent() {
		return logoPresent;
	}
	public void setLogoPresent(String logoPresent) {
		this.logoPresent = logoPresent;
	}
	public int getLastPostId() {
		return lastPostId;
	}
	public void setLastPostId(Long lastPostId) {
		this.lastPostId = lastPostId.intValue();
	}
	public int getResponseCount() {
		return ResponseCount;
	}
	public void setResponseCount(Long responseCount) {
		ResponseCount = responseCount.intValue();
	}
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public boolean isPhotoPresent() {
		return photoPresent;
	}
	public void setPhotoPresent(boolean photoPresent) {
		this.photoPresent = photoPresent;
	}
	
}