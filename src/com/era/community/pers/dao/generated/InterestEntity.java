package com.era.community.pers.dao.generated; 

public abstract class InterestEntity extends support.community.framework.CommandBeanImpl
{
	private java.lang.String Interest;
	private java.lang.String Created;
	private int Id;
	private int CategoryId;
	private int CreatorId;
	private int Active;
	public final java.lang.String getInterest() { return Interest; }
	public final void setInterest(java.lang.String v) {  Interest = v; }
	public final java.lang.String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final int getCategoryId() { return CategoryId; }
	public final void setCategoryId(int v) {  CategoryId = v; }
	public final int getCreatorId() { return CreatorId; }
	public final void setCreatorId(int v) {  CreatorId = v; }
	public final int getActive() { return Active; }
	public final void setActive(int v) {  Active = v; }
}