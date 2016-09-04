package com.era.community.doclib.dao.generated; 

public abstract class DocGroupEntity extends support.community.framework.CommandBeanImpl
{
	private java.lang.String Modified;
	private java.lang.String Created;
	private int Id;
	private int DocumentId;
	private int FolderId;
	private int PhotoId;
	private int AlbumId;

	public final java.lang.String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final java.lang.String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final int getDocumentId() { return DocumentId; }
	public final void setDocumentId(int v) {  DocumentId = v; }
	public final int getFolderId() { return FolderId; }
	public final void setFolderId(int v) {  FolderId = v; }
	public final int getPhotoId() { return PhotoId; }
	public final void setPhotoId(int v) {  PhotoId = v; }
	public final int getAlbumId() { return AlbumId; }
	public final void setAlbumId(int v) {  AlbumId = v; }
}