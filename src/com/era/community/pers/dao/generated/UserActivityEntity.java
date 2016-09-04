package com.era.community.pers.dao.generated; 

public abstract class UserActivityEntity extends support.community.framework.CommandBeanImpl
{
	private int UserId;
	private int CommunityActivityId;
	private int CommunityId;
	private int BlogEntryId;
	private int PhotoId;
	private int AlbumId;
	private java.util.Date Modified;
	private java.util.Date Created;
	private int Id;
	public final int getUserId() { return UserId; }
	public final void setUserId(int v) {  UserId = v; }
	public final int getCommunityActivityId() { return CommunityActivityId; }
	public final void setCommunityActivityId(int v) {  CommunityActivityId = v; }
	public final int getCommunityId() { return CommunityId; }
	public final void setCommunityId(int v) {  CommunityId = v; }
	public final int getBlogEntryId() { return BlogEntryId; }
	public final void setBlogEntryId(int v) {  BlogEntryId = v; }
	public final int getPhotoId() { return PhotoId; }
	public final void setPhotoId(int v) {  PhotoId = v; }
	public final int getAlbumId() { return AlbumId; }
	public final void setAlbumId(int v) {  AlbumId = v; }
	public final java.util.Date getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v; }
	public final java.util.Date getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v; }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
}