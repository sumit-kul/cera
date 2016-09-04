package com.era.community.blog.dao.generated; 

public abstract class BlogAuthorEntity extends support.community.framework.CommandBeanImpl
{
	private int UserId;
	private int BlogId;
	private int PersonalBlogId;
	private int Role;
	private int Active;
	private int Id;
	public final int getUserId() { return UserId; }
	public final void setUserId(int v) {  UserId = v; }
	public final int getBlogId() { return BlogId; }
	public final void setBlogId(int v) {  BlogId = v; }
	public final int getPersonalBlogId() { return PersonalBlogId; }
	public final void setPersonalBlogId(int v) {  PersonalBlogId = v; }
	public final int getRole() { return Role; }
	public final void setRole(int v) {  Role = v; }
	public final int getActive() { return Active; }
	public final void setActive(int v) {  Active = v; }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
}