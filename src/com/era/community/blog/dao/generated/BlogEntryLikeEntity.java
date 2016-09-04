package com.era.community.blog.dao.generated; 

public abstract class BlogEntryLikeEntity extends support.community.framework.CommandBeanImpl
{
		  private int BlogEntryId;
		  private int PosterId;
		  private java.lang.String Modified;
		  private java.lang.String Created;
		  private int Id;
		  public final int getBlogEntryId() { return BlogEntryId; }
		  public final void setBlogEntryId(int v) {  BlogEntryId = v; }
		  public final int getPosterId() { return PosterId; }
		  public final void setPosterId(int v) {  PosterId = v; }
		  public final java.lang.String getModified() { return Modified; }
		  public final void setModified(java.util.Date v) {  Modified = v.toString(); }
		  public final java.lang.String getCreated() { return Created; }
		  public final void setCreated(java.util.Date v) {  Created = v.toString(); }
		  public final int getId() { return Id; }
		  public final void setId(int v) {  Id = v; }
}

