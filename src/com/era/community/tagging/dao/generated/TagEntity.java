package com.era.community.tagging.dao.generated; 

public abstract class TagEntity extends support.community.framework.CommandBeanImpl
{
	private java.lang.Integer CommunityId;
	private int PosterId;
	private int ParentId;
	private java.lang.String ParentType;
	private java.lang.String TagText;
	private java.lang.String Modified;
	private java.lang.String Created;
	private int Id;
	public final java.lang.Integer getCommunityId() { return CommunityId; }
	public final void setCommunityId(java.lang.Integer v) {  CommunityId = v; }
	public final int getPosterId() { return PosterId; }
	public final void setPosterId(int v) {  PosterId = v; }
	public final int getParentId() { return ParentId; }
	public final void setParentId(int v) {  ParentId = v; }
	public final java.lang.String getParentType() { return ParentType; }
	public final void setParentType(java.lang.String v) {  ParentType = v; }
	public final java.lang.String getTagText() { return TagText; }
	public final void setTagText(java.lang.String v) {  TagText = v; }
	public final java.lang.String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final java.lang.String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString();}
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
}

