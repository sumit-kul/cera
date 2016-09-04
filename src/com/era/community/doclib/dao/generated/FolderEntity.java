package com.era.community.doclib.dao.generated; 

public abstract class FolderEntity extends support.community.framework.CommandBeanImpl
{
	private java.lang.String Title;
	private java.lang.String Description;
	private java.lang.String Modified;
	private java.lang.String Created;
	private int Id;
	private int OwnerId;
	private int LibraryId;
	private int PrivacyLevel;
	private int CoverPhotoId;
	private int ProfileFolder;
	private int BannerFolder;
	private int BlogFolder;
	private int ForumFolder;
	private int WikiFolder;
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
	public final int getLibraryId() { return LibraryId; }
	public final void setLibraryId(int v) {  LibraryId = v; }
	public final int getPrivacyLevel() { return PrivacyLevel; }
	public final void setPrivacyLevel(int v) {  PrivacyLevel = v; }
	public final int getCoverPhotoId() { return CoverPhotoId; }
	public final void setCoverPhotoId(int v) {  CoverPhotoId = v; }
	public final int getProfileFolder() { return ProfileFolder; }
	public final void setProfileFolder(int v) {  ProfileFolder = v; }
	public final int getBannerFolder() { return BannerFolder; }
	public final void setBannerFolder(int v) {  BannerFolder = v; }
	public final int getBlogFolder() { return BlogFolder; }
	public final void setBlogFolder(int v) {  BlogFolder = v; }
	public final int getForumFolder() { return ForumFolder; }
	public final void setForumFolder(int v) {  ForumFolder = v; }
	public final int getWikiFolder() { return WikiFolder; }
	public final void setWikiFolder(int v) {  WikiFolder = v; }
}