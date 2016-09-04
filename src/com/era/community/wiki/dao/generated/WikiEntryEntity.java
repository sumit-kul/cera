package com.era.community.wiki.dao.generated; 

public abstract class WikiEntryEntity extends support.community.framework.CommandBeanImpl
{
	private int WikiId;
	private int EntryId;
	private int EntrySequence;
	private java.lang.String Title;
	private java.lang.String DatePosted;
	private int PosterId;
	private java.lang.String ReasonForUpdate;
	private java.lang.String Modified;
	private java.lang.String Created;
	private int Visitors;
	private String LastVisitorsTime;
	private int Id;
	public final int getWikiId() { return WikiId; }
	public final void setWikiId(int v) {  WikiId = v; }
	public final int getEntryId() { return EntryId; }
	public final void setEntryId(int v) {  EntryId = v; }
	public final int getEntrySequence() { return EntrySequence; }
	public final void setEntrySequence(int v) {  EntrySequence = v; }
	public final java.lang.String getTitle() { return Title; }
	public final void setTitle(java.lang.String v) {  Title = v; }
	public final java.lang.String getDatePosted() { return DatePosted; }
	public final void setDatePosted(java.util.Date v) {  DatePosted = v.toString(); }
	public final int getPosterId() { return PosterId; }
	public final void setPosterId(int v) {  PosterId = v; }
	public final java.lang.String getReasonForUpdate() { return ReasonForUpdate; }
	public final void setReasonForUpdate(java.lang.String v) {  ReasonForUpdate = v; }
	public final java.lang.String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final java.lang.String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
	public final int getVisitors() { return Visitors; }
	public final void setVisitors(int v) {  Visitors = v; }
	public final String getLastVisitorsTime() { return LastVisitorsTime; }
	public final void setLastVisitorsTime(java.util.Date v) {  LastVisitorsTime = v.toString(); }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
}