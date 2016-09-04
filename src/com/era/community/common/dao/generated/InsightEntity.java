package com.era.community.common.dao.generated; 

public abstract class InsightEntity extends support.community.framework.CommandBeanImpl
{
		  private java.lang.String Screen;
		  private int TimeSpent;
		  private int SessionId;
		  private int PreviousPageId;
		  private java.lang.String Modified;
		  private java.lang.String Created;
		  private int Id;
		  
		  public final java.lang.String getScreen() { return Screen; }
		  public final void setScreen(java.lang.String v) {  Screen = v; }
		  public final int getSessionId() { return SessionId; }
		  public final void setSessionId(int v) {  SessionId = v; }		  
		  public final int getPreviousPageId() { return PreviousPageId; }
		  public final void setPreviousPageId(int v) {  PreviousPageId = v; }
		  public final int getTimeSpent() { return TimeSpent; }
		  public final void setTimeSpent(int v) {  TimeSpent = v; }
		  
		  public final java.lang.String getModified() { return Modified; }
		  public final void setModified(java.util.Date v) {  Modified = v.toString(); }
		  public final java.lang.String getCreated() { return Created; }
		  public final void setCreated(java.util.Date v) {  Created = v.toString(); }
		  public final int getId() { return Id; }
		  public final void setId(int v) {  Id = v; }
}