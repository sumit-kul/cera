package com.era.community.communities.dao.generated; 

public abstract class FeedbackEntity extends support.community.framework.CommandBeanImpl
{
	private int CommunityId;
	private int SubmitterId;
	private java.lang.String Subject;
	private java.lang.String Body;
	private java.util.Date Modified;
	private java.util.Date Created;
	private int Id;
	public final int getCommunityId() { return CommunityId; }
	public final void setCommunityId(int v) {  CommunityId = v; }
	public final int getSubmitterId() { return SubmitterId; }
	public final void setSubmitterId(int v) {  SubmitterId = v; }
	public final java.lang.String getSubject() { return Subject; }
	public final void setSubject(java.lang.String v) {  Subject = v; }
	public final java.lang.String getBody() { return Body; }
	public final void setBody(java.lang.String v) {  Body = v; }
	public final java.util.Date getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v; }
	public final java.util.Date getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v; }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
}