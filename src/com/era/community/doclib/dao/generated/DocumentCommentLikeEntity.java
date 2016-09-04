package com.era.community.doclib.dao.generated; 

public abstract class DocumentCommentLikeEntity extends support.community.framework.CommandBeanImpl
{
	private int DocumentId;
	private int DocumentCommentId;
	private int PosterId;
	private java.lang.String Modified;
	private java.lang.String Created;
	private int Id;
	public final int getDocumentId() { return DocumentId; }
	public final void setDocumentId(int v) {  DocumentId = v; }
	public final int getDocumentCommentId() { return DocumentCommentId; }
	public final void setDocumentCommentId(int v) {  DocumentCommentId = v; }
	public final int getPosterId() { return PosterId; }
	public final void setPosterId(int v) {  PosterId = v; }
	public final java.lang.String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final java.lang.String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
}

