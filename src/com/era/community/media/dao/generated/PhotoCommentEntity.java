package com.era.community.media.dao.generated; 

public abstract class PhotoCommentEntity extends support.community.framework.CommandBeanImpl
{
		  private int PhotoId;
		  private java.lang.String Comment;
		  private java.lang.String DatePosted;
		  private int PosterId;
		  private java.lang.String Modified;
		  private java.lang.String Created;
		  private int Id;
		  public final int getPhotoId() { return PhotoId; }
		  public final void setPhotoId(int v) {  PhotoId = v; }
		  public final java.lang.String getComment() { return Comment; }
		  public final void setComment(java.lang.String v) {  Comment = v; }
		  public final java.lang.String getDatePosted() { return DatePosted; }
		  public final void setDatePosted(java.util.Date v) {  DatePosted = v.toString(); }
		  public final int getPosterId() { return PosterId; }
		  public final void setPosterId(int v) {  PosterId = v; }
		  public final java.lang.String getModified() { return Modified; }
		  public final void setModified(java.util.Date v) {  Modified = v.toString(); }
		  public final java.lang.String getCreated() { return Created; }
		  public final void setCreated(java.util.Date v) {  Created = v.toString(); }
		  public final int getId() { return Id; }
		  public final void setId(int v) {  Id = v; }
}