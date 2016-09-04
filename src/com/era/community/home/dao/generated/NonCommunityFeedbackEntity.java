package com.era.community.home.dao.generated; 

public abstract class NonCommunityFeedbackEntity extends support.community.framework.CommandBeanImpl
{
	private java.lang.String Name;
	private java.lang.String EmailAddress;
	private java.lang.String Subject;
	private java.lang.String Body;
	private int SubmitterId;
	private int type;
	private java.util.Date Created;
	private int Id;
	public final java.lang.String getName() { return Name; }
	public final void setName(java.lang.String v) {  Name = v; }
	public final java.lang.String getEmailAddress() { return EmailAddress; }
	public final void setEmailAddress(java.lang.String v) {  EmailAddress = v; }
	public final java.lang.String getSubject() { return Subject; }
	public final void setSubject(java.lang.String v) {  Subject = v; }
	public final java.lang.String getBody() { return Body; }
	public final void setBody(java.lang.String v) {  Body = v; }
	public final java.util.Date getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v; }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final int gettype() { return type; }
	public final void settype(int v) {  type = v; }
	public final int getSubmitterId() { return SubmitterId; }
	public final void setSubmitterId(int v) {  SubmitterId = v; }
}