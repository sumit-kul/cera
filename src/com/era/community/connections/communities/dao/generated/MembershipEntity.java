package com.era.community.connections.communities.dao.generated; 

public abstract class MembershipEntity extends support.community.framework.CommandBeanImpl
{
	private int MemberListId;
	private int UserId;
	private java.util.Date DateJoined;
	private java.lang.String Role;
	private java.util.Date DateLastVisit;
	private java.lang.Integer ApproverId;
	private int Id;
	public final int getMemberListId() { return MemberListId; }
	public final void setMemberListId(int v) {  MemberListId = v; }
	public final int getUserId() { return UserId; }
	public final void setUserId(int v) {  UserId = v; }
	public final java.util.Date getDateJoined() { return DateJoined; }
	public final void setDateJoined(java.util.Date v) {  DateJoined = v; }
	public final java.lang.String getRole() { return Role; }
	public final void setRole(java.lang.String v) {  Role = v; }
	public final java.util.Date getDateLastVisit() { return DateLastVisit; }
	public final void setDateLastVisit(java.util.Date v) {  DateLastVisit = v; }
	public final java.lang.Integer getApproverId() { return ApproverId; }
	public final void setApproverId(java.lang.Integer v) {  ApproverId = v; }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
}

