package com.era.community.pers.dao.generated; 

public abstract class ContactEntity extends support.community.framework.CommandBeanImpl
{
	private int OwningUserId;
	private int ContactUserId;
	private int Status;
	private java.lang.String DateConnection;
	private int FollowOwner;
	private int FollowContact;
	private java.lang.String Modified;
	private java.lang.String Created;
	private int Id;
	public final int getOwningUserId() { return OwningUserId; }
	public final void setOwningUserId(int v) {  OwningUserId = v; }
	public final int getContactUserId() { return ContactUserId; }
	public final void setContactUserId(int v) {  ContactUserId = v; }
	public final int getStatus() { return Status; }
	public final void setStatus(int v) {  Status = v; }
	public final java.lang.String getDateConnection() { return DateConnection; }
	public final int getFollowOwner() { return FollowOwner; }
	public final void setFollowOwner(int v) {  FollowOwner = v; }
	public final int getFollowContact() { return FollowContact; }
	public final void setFollowContact(int v) {  FollowContact = v; }
	public final void setDateConnection(java.util.Date v) {  DateConnection = v.toString(); }
	public final java.lang.String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final java.lang.String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
}