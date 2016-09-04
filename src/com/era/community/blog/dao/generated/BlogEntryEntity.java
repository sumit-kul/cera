package com.era.community.blog.dao.generated; 

public abstract class BlogEntryEntity extends support.community.framework.CommandBeanImpl
{
		  private java.lang.String Title;
		  private int PosterId;
		  private java.lang.String Body;
		  private String DatePosted;
		  private String Modified;
		  private String Created;
		  private int Id;
		  private int Visitors;
		  private String LastVisitorsTime;
		  private int CommunityBlogId;
		  private int PersonalBlogId;
		  public final java.lang.String getTitle() { return Title; }
		  public final void setTitle(java.lang.String v) {  Title = v; }
		  public final int getPosterId() { return PosterId; }
		  public final void setPosterId(int v) {  PosterId = v; }
		  public final int getCommunityBlogId() { return CommunityBlogId; }
		  public final void setCommunityBlogId(int v) {  CommunityBlogId = v; }
		  public final int getPersonalBlogId() { return PersonalBlogId; }
		  public final void setPersonalBlogId(int v) {  PersonalBlogId = v; }
		  public final java.lang.String getBody() { return Body; }
		  public final void setBody(java.lang.String v) {  Body = v; }
		  public final String getDatePosted() { return DatePosted; }
		  public final void setDatePosted(java.util.Date v) {  DatePosted = v.toString(); }
		  public final String getModified() { return Modified; }
		  public final void setModified(java.util.Date v) {  Modified = v.toString(); }
		  public final String getCreated() { return Created; }
		  public final void setCreated(java.util.Date v) {  Created = v.toString(); }
		  public final int getId() { return Id; }
		  public final void setId(int v) {  Id = v; }
		  public final int getVisitors() { return Visitors; }
		  public final void setVisitors(int v) {  Visitors = v; }
		  public final String getLastVisitorsTime() { return LastVisitorsTime; }
		  public final void setLastVisitorsTime(java.util.Date v) {  LastVisitorsTime = v.toString(); }
}