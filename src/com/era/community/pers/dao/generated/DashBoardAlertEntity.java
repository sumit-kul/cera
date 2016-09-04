package com.era.community.pers.dao.generated; 

public abstract class DashBoardAlertEntity extends support.community.framework.CommandBeanImpl
{
	private int UserId;
	private int MessageCount;
	private int NotificationCount;
	private int LikeCount;
	private int ProfileVisitCount;
	private int ConnectionReceivedCount;
	private int ConnectionApprovedCount;
	private java.lang.String Modified;
	private java.lang.String Created;
	private int Id;
	public final int getUserId() { return UserId; }
	public final void setUserId(int v) {  UserId = v; }
	public final int getMessageCount() { return MessageCount; }
	public final void setMessageCount(int v) {  MessageCount = v; }
	public final int getNotificationCount() { return NotificationCount; }
	public final void setNotificationCount(int v) {  NotificationCount = v; }
	public final int getLikeCount() { return LikeCount; }
	public final void setLikeCount(int v) {  LikeCount = v; }
	public final int getProfileVisitCount() { return ProfileVisitCount; }
	public final void setProfileVisitCount(int v) {  ProfileVisitCount = v; }
	public final int getConnectionReceivedCount() { return ConnectionReceivedCount; }
	public final void setConnectionReceivedCount(int v) {  ConnectionReceivedCount = v; }
	public final int getConnectionApprovedCount() { return ConnectionApprovedCount; }
	public final void setConnectionApprovedCount(int v) {  ConnectionApprovedCount = v; }
	public final java.lang.String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final java.lang.String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
}