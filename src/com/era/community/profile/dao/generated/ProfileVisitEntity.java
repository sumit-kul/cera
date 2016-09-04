package com.era.community.profile.dao.generated; 

public abstract class ProfileVisitEntity extends support.community.framework.CommandBeanImpl
{
	private int ProfileUserId;
	private int VisitingUserId;
	private int Status;
	private int VisitingCounter;
	private java.lang.String Modified;
	private java.lang.String Created;
	private int Id;
	public final int getProfileUserId() { return ProfileUserId; }
	public final void setProfileUserId(int v) {  ProfileUserId = v; }
	public final int getVisitingUserId() { return VisitingUserId; }
	public final void setVisitingUserId(int v) {  VisitingUserId = v; }
	public final int getStatus() { return Status; }
	public final void setStatus(int v) {  Status = v; }
	public final int getVisitingCounter() { return VisitingCounter; }
	public final void setVisitingCounter(int v) {  VisitingCounter = v; }
	public final java.lang.String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final java.lang.String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
}