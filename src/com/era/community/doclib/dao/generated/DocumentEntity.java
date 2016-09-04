package com.era.community.doclib.dao.generated; 

public abstract class DocumentEntity extends support.community.framework.CommandBeanImpl
{
	private int LibraryId;
	private int FolderId;
	private java.lang.String Title;
	private java.lang.String FileName;
	private java.lang.String Description;
	private java.lang.String DatePosted;
	private int PosterId;
	private int RevisionNumber;
	private java.lang.String DateRevised;
	private boolean Inactive;
	private java.lang.String Modified;
	private java.lang.String Created;
	private int Id;
	private boolean FilePresent;
	private int FileLength;
	private String FileContentType;
	private int Downloads;
	private String LastDownloadTime;
	private int Width;
	private int Height;
	private int PrivacyLevel;
	private int CurrentProfile;
	public final int getLibraryId() { return LibraryId; }
	public final void setLibraryId(int v) {  LibraryId = v; }
	public final int getFolderId() { return FolderId; }
	public final void setFolderId(int v) {  FolderId = v; }
	public final java.lang.String getTitle() { return Title; }
	public final void setTitle(java.lang.String v) {  Title = v; }
	public final java.lang.String getFileName() { return FileName; }
	public final void setFileName(java.lang.String v) {  FileName = v; }
	public final java.lang.String getDescription() { return Description; }
	public final void setDescription(java.lang.String v) {  Description = v; }
	public final java.lang.String getDatePosted() { return DatePosted; }
	public final void setDatePosted(java.util.Date v) {  DatePosted = v.toString(); }
	public final int getPosterId() { return PosterId; }
	public final void setPosterId(int v) {  PosterId = v; }
	public final int getRevisionNumber() { return RevisionNumber; }
	public final void setRevisionNumber(int v) {  RevisionNumber = v; }
	public final java.lang.String getDateRevised() { return DateRevised; }
	public final void setDateRevised(java.util.Date v) {  DateRevised = v.toString(); }
	public final boolean getInactive() { return Inactive; }
	public final void setInactive(boolean v) {  Inactive = v; }
	public final java.lang.String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final java.lang.String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final boolean getFilePresent() { return FilePresent; }
	public final void setFilePresent (boolean v) {  FilePresent = v; }
	public final int getFileLength() { return FileLength; }
	public final void setFileLength (int v) {  FileLength = v; }
	public final String getFileContentType() { return FileContentType; }
	public final void setFileContentType (String v) {  FileContentType = v; }
	public final int getDownloads() { return Downloads; }
	public final void setDownloads(int v) {  Downloads = v; }
	public final String getLastDownloadTime() { return LastDownloadTime; }
	public final void setLastDownloadTime(java.util.Date v) {  LastDownloadTime = v.toString(); }
	public final int getWidth() { return Width; }
	public final void setWidth(int v) {  Width = v; }
	public final int getHeight() { return Height; }
	public final void setHeight(int v) {  Height = v; }
	public final int getPrivacyLevel() { return PrivacyLevel; }
	public final void setPrivacyLevel(int v) {  PrivacyLevel = v; }
	public final int getCurrentProfile() { return CurrentProfile; }
	public final void setCurrentProfile(int v) {  CurrentProfile = v; }
}