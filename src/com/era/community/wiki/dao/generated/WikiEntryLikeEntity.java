package com.era.community.wiki.dao.generated; 

public abstract class WikiEntryLikeEntity extends support.community.framework.CommandBeanImpl
{
	private int WikiEntryId;
	private int PosterId;
	private java.lang.String Modified;
	private java.lang.String Created;
	private int Id;
	public final int getWikiEntryId() { return WikiEntryId; }
	public final void setWikiEntryId(int v) {  WikiEntryId = v; }
	public final int getPosterId() { return PosterId; }
	public final void setPosterId(int v) {  PosterId = v; }
	public final java.lang.String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final java.lang.String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
}