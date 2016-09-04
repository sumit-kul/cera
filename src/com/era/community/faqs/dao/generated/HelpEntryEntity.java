package com.era.community.faqs.dao.generated; 

public abstract class HelpEntryEntity extends support.community.framework.CommandBeanImpl
{
	private java.lang.String Question;
	private java.lang.String Answer;
	private int Sequence;
	private int ParentId;
	private boolean Inactive;
	private java.util.Date Modified;
	private java.util.Date Created;
	private int Id;
	public final java.lang.String getQuestion() { return Question; }
	public final void setQuestion(java.lang.String v) {  Question = v; }
	public final java.lang.String getAnswer() { return Answer; }
	public final void setAnswer(java.lang.String v) {  Answer = v; }
	public final int getSequence() { return Sequence; }
	public final void setSequence(int v) {  Sequence = v; }
	public final int getParentId() { return ParentId; }
	public final void setParentId(int v) {  ParentId = v; }
	public final boolean getInactive() { return Inactive; }
	public final void setInactive(boolean v) {  Inactive = v; }
	public final java.util.Date getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v; }
	public final java.util.Date getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v; }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
}