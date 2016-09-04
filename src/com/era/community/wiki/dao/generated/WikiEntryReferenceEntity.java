package com.era.community.wiki.dao.generated; 

public abstract class WikiEntryReferenceEntity extends support.community.framework.CommandBeanImpl
{
	private int WikiEntryId;
	private java.lang.String Modified;
	private java.lang.String Created;
	private java.lang.String ReferenceTitle;
	private java.lang.String ReferenceURL;
	private int ReferenceType;
	private int Id;
	public final int getWikiEntryId() { return WikiEntryId; }
	public final void setWikiEntryId(int v) {  WikiEntryId = v; }
	public final java.lang.String getReferenceTitle() { return ReferenceTitle; }
	public final void setReferenceTitle(java.lang.String v) {  ReferenceTitle = v; }
	public final java.lang.String getReferenceURL() { return ReferenceURL; }
	public final void setReferenceURL(java.lang.String v) {  ReferenceURL = v; }
	public final int getReferenceType() { return ReferenceType; }
	public final void setReferenceType(int v) {  ReferenceType = v; }
	public final java.lang.String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final java.lang.String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
}