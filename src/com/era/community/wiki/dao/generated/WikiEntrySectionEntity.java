package com.era.community.wiki.dao.generated; 

public abstract class WikiEntrySectionEntity extends support.community.framework.CommandBeanImpl
{
	private java.lang.String Modified;
	private java.lang.String Created;
	private java.lang.String SectionTitle;
	private java.lang.String SectionBody;
	private int SectionSeq;
	private int Id;
	private int WikiEntryId;
	public final java.lang.String getSectionTitle() { return SectionTitle; }
	public final void setSectionTitle(java.lang.String v) {  SectionTitle = v; }
	public final java.lang.String getSectionBody() { return SectionBody; }
	public final void setSectionBody(java.lang.String v) {  SectionBody = v; }
	public final java.lang.String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final java.lang.String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final int getWikiEntryId() { return WikiEntryId; }
	public final void setWikiEntryId(int v) {  WikiEntryId = v; }
	public final int getSectionSeq() { return SectionSeq; }
	public final void setSectionSeq(int v) {  SectionSeq = v; }
}