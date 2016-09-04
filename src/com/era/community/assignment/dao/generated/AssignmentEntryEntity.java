package com.era.community.assignment.dao.generated; 

public abstract class AssignmentEntryEntity extends support.community.framework.CommandBeanImpl
{
	private int AssignmentId;
	private java.lang.String Title;
	private java.lang.String DatePosted;
	private int AssignerId;
	private int CreatorId;
	private java.lang.String ReasonForUpdate;
	private java.lang.String Modified;
	private java.lang.String Created;
	private int Id;
	public final int getAssignmentId() { return AssignmentId; }
	public final void setAssignmentId(int v) {  AssignmentId = v; }
	public final java.lang.String getTitle() { return Title; }
	public final void setTitle(java.lang.String v) {  Title = v; }
	public final java.lang.String getDatePosted() { return DatePosted; }
	public final void setDatePosted(java.util.Date v) {  DatePosted = v.toString(); }
	public final int getAssignerId() { return AssignerId; }
	public final void setAssignerId(int v) {  AssignerId = v; }
	public final int getCreatorId() { return CreatorId; }
	public final void setCreatorId(int v) {  CreatorId = v; }
	public final java.lang.String getReasonForUpdate() { return ReasonForUpdate; }
	public final void setReasonForUpdate(java.lang.String v) {  ReasonForUpdate = v; }
	public final java.lang.String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final java.lang.String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
}