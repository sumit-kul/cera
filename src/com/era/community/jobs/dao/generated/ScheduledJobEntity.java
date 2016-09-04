package com.era.community.jobs.dao.generated; 

public abstract class ScheduledJobEntity extends support.community.framework.CommandBeanImpl
{
	private java.lang.String TaskName;
	private java.util.Date Created;
	private java.util.Date Started;
	private java.util.Date Completed;
	private int Id;
	public final java.lang.String getTaskName() { return TaskName; }
	public final void setTaskName(java.lang.String v) {  TaskName = v; }
	public final java.util.Date getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v; }
	public final java.util.Date getStarted() { return Started; }
	public final void setStarted(java.util.Date v) {  Started = v; }
	public final java.util.Date getCompleted() { return Completed; }
	public final void setCompleted(java.util.Date v) {  Completed = v; }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
}