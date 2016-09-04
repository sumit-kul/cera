package com.era.community.pers.dao.generated; 

public abstract class MessageEntity extends support.community.framework.CommandBeanImpl
{
	private int ToUserId;
	private int FromUserId;
	private java.lang.String Subject;
	private java.lang.String Body;
	private java.lang.String DateSent;
	private boolean AlreadyRead;
	private java.lang.String Modified;
	private java.lang.String Created;
	private int Id;
	private boolean AttachmentPresent;
	private int AttachmentLength;
	private String AttachmentContentType;
	private String sysType;
	private int DeleteFlag;
	private int ReadFlag;

	public final int getToUserId() { return ToUserId; }
	public final void setToUserId(int v) {  ToUserId = v; }
	public final int getFromUserId() { return FromUserId; }
	public final void setFromUserId(int v) {  FromUserId = v; }
	public final java.lang.String getSubject() { return Subject; }
	public final void setSubject(java.lang.String v) {  Subject = v; }
	public final java.lang.String getBody() { return Body; }
	public final void setBody(java.lang.String v) {  Body = v; }
	public final String getDateSent() { return DateSent; }
	public final void setDateSent(java.util.Date v) {  DateSent = v.toString(); }
	public final boolean getAlreadyRead() { return AlreadyRead; }
	public final void setAlreadyRead(boolean v) {  AlreadyRead = v; }
	public final String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final boolean getAttachmentPresent() { return AttachmentPresent; }
	public final void setAttachmentPresent (boolean v) {  AttachmentPresent = v; }
	public final int getAttachmentLength() { return AttachmentLength; }
	public final void setAttachmentLength (int v) {  AttachmentLength = v; }
	public final String getAttachmentContentType() { return AttachmentContentType; }
	public final void setAttachmentContentType (String v) {  AttachmentContentType = v; }
	public final String getSysType() { return sysType; }
	public final void setSysType (String v) {  sysType = v; }
	public final int getDeleteFlag() { return DeleteFlag; }
	public final void setDeleteFlag(int v) {  DeleteFlag = v; }
	public final int getReadFlag() { return ReadFlag; }
	public final void setReadFlag(int v) {  ReadFlag = v; }

}

