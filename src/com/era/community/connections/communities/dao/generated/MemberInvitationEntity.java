package com.era.community.connections.communities.dao.generated; 

public abstract class MemberInvitationEntity extends support.community.framework.CommandBeanImpl
{
	private int CommunityId;
	private int UserId;
	private int InvitorId;
	private java.util.Date RequestDate;
	private int Status;
	private java.util.Date ApproveRejectDate;
	private int Id;
	public final int getCommunityId() { return CommunityId; }
	public final void setCommunityId(int v) {  CommunityId = v; }
	public final int getUserId() { return UserId; }
	public final void setUserId(int v) {  UserId = v; }
	public final int getInvitorId() { return InvitorId; }
	public final void setInvitorId(int v) {  InvitorId = v; }
	public final java.util.Date getRequestDate() { return RequestDate; }
	public final void setRequestDate(java.util.Date v) {  RequestDate = v; }
	public final int getStatus() { return Status; }
	public final void setStatus(int v) {  Status = v; }
	public final java.util.Date getApproveRejectDate() { return ApproveRejectDate; }
	public final void setApproveRejectDate(java.util.Date v) {  ApproveRejectDate = v; }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
}