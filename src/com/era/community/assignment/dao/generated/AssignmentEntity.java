package com.era.community.assignment.dao.generated; 

public abstract class AssignmentEntity extends support.community.framework.CommandBeanImpl
{
	private int AssignmentsId;
	private java.lang.String Title;
	private java.lang.String DatePosted;
	private java.lang.String DueDate;
	private java.lang.String Body;
	private int CreatorId;
	private java.lang.String Modified;
	private java.lang.String Created;
	private int Id;
	private java.lang.Integer TaskId;
	private java.lang.Integer ParentId;
	private Integer Lft;
	private Integer Rht;
	private Integer Lvl;
	private java.lang.Integer EntryType;
	public final int getAssignmentsId() { return AssignmentsId; }
	public final void setAssignmentsId(int v) {  AssignmentsId = v; }
	public final java.lang.String getTitle() { return Title; }
	public final void setTitle(java.lang.String v) {  Title = v; }
	public final java.lang.String getBody() { return Body; }
	public final void setBody(java.lang.String v) {  Body = v; }
	public final java.lang.String getDatePosted() { return DatePosted; }
	public final void setDatePosted(java.util.Date v) {  DatePosted = v.toString(); }
	public final java.lang.String getDueDate() { return DueDate; }
	public final void setDueDate(java.util.Date v) {  DueDate = v.toString(); }
	public final int getCreatorId() { return CreatorId; }
	public final void setCreatorId(int v) {  CreatorId = v; }
	public final java.lang.String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final java.lang.String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final java.lang.Integer getTaskId() { return TaskId; }
	public final void setTaskId(java.lang.Integer v) {  TaskId = v; }
	public final java.lang.Integer getParentId() { return ParentId; }
	public final void setParentId(java.lang.Integer v) {  ParentId = v; }
	public final java.lang.Integer getEntryType() { return EntryType; }
	public final void setEntryType(java.lang.Integer v) {  EntryType = v; }
	public final java.lang.Integer getLft() { return Lft; }
	public final void setLft(java.lang.Integer v) {  Lft = v; }
	public final java.lang.Integer getRht() { return Rht; }
	public final void setRht(java.lang.Integer v) {  Rht = v; }
	public final java.lang.Integer getLvl() { return Lvl; }
	public final void setLvl(java.lang.Integer v) {  Lvl = v; }
}