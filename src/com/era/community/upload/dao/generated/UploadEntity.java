package com.era.community.upload.dao.generated; 

public abstract class UploadEntity extends support.community.framework.CommandBeanImpl
{
	private int CreatorId;
	private java.lang.String Title;
	private java.lang.String Description;
	private java.util.Date Modified;
	private java.util.Date Created;
	private int Id;
	private boolean DataPresent;
	private int DataLength;
	private String DataContentType;
	public final int getCreatorId() { return CreatorId; }
	public final void setCreatorId(int v) {  CreatorId = v; }
	public final java.lang.String getTitle() { return Title; }
	public final void setTitle(java.lang.String v) {  Title = v; }
	public final java.lang.String getDescription() { return Description; }
	public final void setDescription(java.lang.String v) {  Description = v; }
	public final java.util.Date getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v; }
	public final java.util.Date getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v; }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final boolean getDataPresent() { return DataPresent; }
	public final void setDataPresent (boolean v) {  DataPresent = v; }
	public final int getDataLength() { return DataLength; }
	public final void setDataLength (int v) {  DataLength = v; }
	public final String getDataContentType() { return DataContentType; }
	public final void setDataContentType (String v) {  DataContentType = v; }
}

