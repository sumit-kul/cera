package com.era.community.forum.dao.generated; 

public abstract class ForumItemEntity extends support.community.framework.CommandBeanImpl
{
	private int ForumId;
	private java.lang.String Subject;
	private java.lang.String Body;
	private int AuthorId;
	private java.lang.String DatePosted;
	private boolean Closed;
	private java.lang.Integer ClosedById;
	private java.lang.String ClosedOn;
	private boolean Sticky;
	private int DeleteStatus;
	private java.lang.String Modified;
	private java.lang.String Created;
	private int Id;
	private int Visitors;
	private String LastVisitorsTime;
	private java.lang.Integer TopicId;
	private java.lang.Integer ParentId;
	private Integer Lft;
	private Integer Rht;
	private Integer Lvl;

	public final int getForumId() { return ForumId; }
	public final void setForumId(int v) {  ForumId = v; }
	public final java.lang.String getSubject() { return Subject; }
	public final void setSubject(java.lang.String v) {  Subject = v; }
	public final java.lang.String getBody() { return Body; }
	public final void setBody(java.lang.String v) {  Body = v; }
	public final int getAuthorId() { return AuthorId; }
	public final void setAuthorId(int v) {  AuthorId = v; }
	public final java.lang.String getDatePosted() { return DatePosted; }
	public final void setDatePosted(java.util.Date v) {  DatePosted = v.toString(); }
	public final boolean getClosed() { return Closed; }
	public final void setClosed(boolean v) {  Closed = v; }
	public final java.lang.Integer getClosedById() { return ClosedById; }
	public final void setClosedById(java.lang.Integer v) {  ClosedById = v; }
	public final java.lang.String getClosedOn() { return ClosedOn; }
	public final void setClosedOn(java.util.Date v) {  ClosedOn = v.toString(); }
	public final boolean getSticky() { return Sticky; }
	public final void setSticky(boolean v) {  Sticky = v; }
	public final int getDeleteStatus() { return DeleteStatus; }
	public final void setDeleteStatus(int v) {  DeleteStatus = v; }
	public final java.lang.String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final java.lang.String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final java.lang.Integer getTopicId() { return TopicId; }
	public final void setTopicId(java.lang.Integer v) {  TopicId = v; }
	public final java.lang.Integer getParentId() { return ParentId; }
	public final void setParentId(java.lang.Integer v) {  ParentId = v; }
	public final int getVisitors() { return Visitors; }
	public final void setVisitors(Long v) {  if (v == null) Visitors = 0; else Visitors = v.intValue(); }
	public final String getLastVisitorsTime() { return LastVisitorsTime; }
	public final void setLastVisitorsTime(java.util.Date v) {  LastVisitorsTime = v.toString(); }
	public final java.lang.Integer getLft() { return Lft; }
	public final void setLft(java.lang.Integer v) {  Lft = v; }
	public final java.lang.Integer getRht() { return Rht; }
	public final void setRht(java.lang.Integer v) {  Rht = v; }
	public final java.lang.Integer getLvl() { return Lvl; }
	public final void setLvl(java.lang.Integer v) {  Lvl = v; }
}