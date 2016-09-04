package com.era.community.events.dao.generated; 

public abstract class EventInviteeLinkEntity extends support.community.framework.CommandBeanImpl
{
	private int UserId;
	private int EventId;
	private int JoiningStatus;
	private int Id;
	public final int getUserId() { return UserId; }
	public final void setUserId(int v) {  UserId = v; }
	public final int getEventId() { return EventId; }
	public final void setEventId(int v) {  EventId = v; }
	public final int getJoiningStatus() { return JoiningStatus; }
	public final void setJoiningStatus(int v) {  JoiningStatus = v; }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
}