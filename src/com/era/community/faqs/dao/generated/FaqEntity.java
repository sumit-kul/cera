package com.era.community.faqs.dao.generated; 

public abstract class FaqEntity extends support.community.framework.CommandBeanImpl
{
	private java.lang.String Subject;
	private java.lang.String Body;
	private java.lang.String FileName;
	private int Sequence;
	private boolean Inactive;
	private java.util.Date Modified;
	private java.util.Date Created;
	private int Id;
	private boolean FilePresent;
	private int FileLength;
	private String FileContentType;
	public final java.lang.String getSubject() { return Subject; }
	public final void setSubject(java.lang.String v) {  Subject = v; }
	public final java.lang.String getBody() { return Body; }
	public final void setBody(java.lang.String v) {  Body = v; }
	public final java.lang.String getFileName() { return FileName; }
	public final void setFileName(java.lang.String v) {  FileName = v; }
	public final int getSequence() { return Sequence; }
	public final void setSequence(int v) {  Sequence = v; }
	public final boolean getInactive() { return Inactive; }
	public final void setInactive(boolean v) {  Inactive = v; }
	public final java.util.Date getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v; }
	public final java.util.Date getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v; }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final boolean getFilePresent() { return FilePresent; }
	public final void setFilePresent (boolean v) {  FilePresent = v; }
	public final int getFileLength() { return FileLength; }
	public final void setFileLength (int v) {  FileLength = v; }
	public final String getFileContentType() { return FileContentType; }
	public final void setFileContentType (String v) {  FileContentType = v; }
}

