package com.era.community.communities.dao.generated; 

public abstract class CommunityVisitEntity extends support.community.framework.CommandBeanImpl
{
	private int CommunityId;
	private int VisitingUserId;
	private java.lang.String Modified;
	private java.lang.String Created;
	private int Id;
	public final int getCommunityUserId() { return CommunityId; }
	public final void setCommunityUserId(int v) {  CommunityId = v; }
	public final int getVisitingUserId() { return VisitingUserId; }
	public final void setVisitingUserId(int v) {  VisitingUserId = v; }
	public final java.lang.String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final java.lang.String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
}