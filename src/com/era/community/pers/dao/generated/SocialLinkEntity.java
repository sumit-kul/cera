package com.era.community.pers.dao.generated; 

public abstract class SocialLinkEntity extends support.community.framework.CommandBeanImpl
{
	private int Id;
	private int UserId;
	private String Name;
	private String Link;
	private java.lang.String Modified;
	private java.lang.String Created;
	public final String getName() { return Name; }
	public final void setName(String v) {  Name = v; }
	public final String getLink() { return Link; }
	public final void setLink(String v) {  Link = v; }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final int getUserId() { return UserId; }
	public final void setUserId(int v) {  UserId = v; }
	public final java.lang.String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final java.lang.String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
}