package com.era.community.media.dao.generated; 

public abstract class AlbumEntity extends support.community.framework.CommandBeanImpl
{
	private java.lang.String Title;
	private java.lang.String Description;
	private java.lang.String Modified;
	private java.lang.String Created;
	private int Id;
	private int OwnerId;
	private int Shared;
	private int ProfileAlbum;
	private int CoverAlbum;
	private int PrivacyLevel;
	private int CoverPhotoId;
	public final java.lang.String getTitle() { return Title; }
	public final void setTitle(java.lang.String v) {  Title = v; }
	public final java.lang.String getDescription() { return Description; }
	public final void setDescription(java.lang.String v) {  Description = v; }
	public final java.lang.String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final java.lang.String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final int getOwnerId() { return OwnerId; }
	public final void setOwnerId(int v) {  OwnerId = v; }
	public final int getShared() { return Shared; }
	public final void setShared(int v) {  Shared = v; }
	public final int getProfileAlbum() { return ProfileAlbum; }
	public final void setProfileAlbum(int v) {  ProfileAlbum = v; }
	public final int getCoverAlbum() { return CoverAlbum; }
	public final void setCoverAlbum(int v) {  CoverAlbum = v; }
	public final int getPrivacyLevel() { return PrivacyLevel; }
	public final void setPrivacyLevel(int v) {  PrivacyLevel = v; }
	public final int getCoverPhotoId() { return CoverPhotoId; }
	public final void setCoverPhotoId(int v) {  CoverPhotoId = v; }
}