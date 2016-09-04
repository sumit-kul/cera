package com.era.community.media.dao.generated; 

public abstract class PhotoEntity extends support.community.framework.CommandBeanImpl
{
	private java.lang.String Title;
	private java.lang.String Description;
	private java.lang.String Modified;
	private java.lang.String Created;
	private int Id;
	private int PhotoLength;
	private String PhotoContentType;
	private boolean PhotoPresent;
	private int UserId;
	private int AlbumId;
	private int CurrentProfile;
	private int PrivacyLevel;
	private int Width;
	private int Height;
	public final boolean getPhotoPresent() { return PhotoPresent; }
	public final void setPhotoPresent (boolean v) {  PhotoPresent = v; }
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
	public final int getUserId() { return UserId; }
	public final void setUserId(int v) {  UserId = v; }
	public final int getAlbumId() { return AlbumId; }
	public final void setAlbumId(int v) {  AlbumId = v; }
	public final int getPhotoLength() { return PhotoLength; }
	public final void setPhotoLength (int v) {  PhotoLength = v; }
	public final String getPhotoContentType() { return PhotoContentType; }
	public final void setPhotoContentType (String v) {  PhotoContentType = v; }
	public final int getCurrentProfile() { return CurrentProfile; }
	public final void setCurrentProfile(int v) {  CurrentProfile = v; }
	public final int getPrivacyLevel() { return PrivacyLevel; }
	public final void setPrivacyLevel(int v) {  PrivacyLevel = v; }
	public final int getWidth() { return Width; }
	public final void setWidth(int v) {  Width = v; }
	public final int getHeight() { return Height; }
	public final void setHeight(int v) {  Height = v; }
}