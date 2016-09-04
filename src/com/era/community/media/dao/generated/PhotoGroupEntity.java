package com.era.community.media.dao.generated; 

public abstract class PhotoGroupEntity extends support.community.framework.CommandBeanImpl
{
	private java.lang.String Modified;
	private java.lang.String Created;
	private int Id;
	private int PhotoId;
	private int AlbumId;

	public final java.lang.String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final java.lang.String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final int getPhotoId() { return PhotoId; }
	public final void setPhotoId(int v) {  PhotoId = v; }
	public final int getAlbumId() { return AlbumId; }
	public final void setAlbumId(int v) {  AlbumId = v; }
}