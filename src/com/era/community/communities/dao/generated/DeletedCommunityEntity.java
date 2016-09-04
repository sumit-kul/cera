package com.era.community.communities.dao.generated; 

public abstract class DeletedCommunityEntity extends support.community.framework.CommandBeanImpl
{
	private int CommunityId;
	private int DeleterId;
	private java.lang.String Comment;
	private java.util.Date Modified;
	private java.util.Date Created;
	private int Id;
	public final int getCommunityId() { return CommunityId; }
	public final void setCommunityId(int v) {  CommunityId = v; }
	public final int getDeleterId() { return DeleterId; }
	public final void setDeleterId(int v) {  DeleterId = v; }
	public final java.lang.String getComment() { return Comment; }
	public final void setComment(java.lang.String v) {  Comment = v; }
	public final java.util.Date getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v; }
	public final java.util.Date getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v; }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
}