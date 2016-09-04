package com.era.community.blog.dao.generated; 

public abstract class PersonalBlogEntity extends support.community.framework.CommandBeanImpl
{
	private int UserId;
	private java.lang.String Name;
	private java.lang.String Description;
	private boolean Inactive;
	private boolean AllowCoEdit;
	private boolean AllowComments;
	private boolean ModerateComments;
	private boolean DefaultAllowComments;
	private int DefaultCommentDays;
	private java.util.Date Modified;
	private java.util.Date Created;
	private int Id;
	public final int getUserId() { return UserId; }
	public final void setUserId(int v) {  UserId = v; }
	public final java.lang.String getName() { return Name; }
	public final void setName(java.lang.String v) {  Name = v; }
	public final java.lang.String getDescription() { return Description; }
	public final void setDescription(java.lang.String v) {  Description = v; }
	public final boolean getInactive() { return Inactive; }
	public final void setInactive(boolean v) {  Inactive = v; }
	public final boolean getAllowCoEdit() { return AllowCoEdit; }
	public final void setAllowCoEdit(boolean v) {  AllowCoEdit = v; }
	public final boolean getAllowComments() { return AllowComments; }
	public final void setAllowComments(boolean v) {  AllowComments = v; }
	public final boolean getModerateComments() { return ModerateComments; }
	public final void setModerateComments(boolean v) {  ModerateComments = v; }
	public final boolean getDefaultAllowComments() { return DefaultAllowComments; }
	public final void setDefaultAllowComments(boolean v) {  DefaultAllowComments = v; }
	public final int getDefaultCommentDays() { return DefaultCommentDays; }
	public final void setDefaultCommentDays(int v) {  DefaultCommentDays = v; }
	public final java.util.Date getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v; }
	public final java.util.Date getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v; }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
}