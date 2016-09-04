package com.era.community.communities.dao.generated; 

public abstract class CommunityEntity extends support.community.framework.CommandBeanImpl
{
	private java.lang.Integer ParentId;
	private java.lang.String Name;
	private java.lang.String WelcomeText;
	private java.lang.String Description;
	private boolean IncludeParentMembers;
	private int CreatorId;
	private int Status;
	private String Modified;
	private String Created;
	private int Id;
	private boolean CommunityLogoPresent;
	private int CommunityLogoLength;
	private String CommunityLogoContentType;
	private String CommunityUpdated;
	private boolean CommunityBannerPresent;
	private int Width;
	private int Height;
	private int VisitCount;
	private boolean Featured;

	public final java.lang.Integer getParentId() { return ParentId; }
	public final void setParentId(java.lang.Integer v) {  ParentId = v; }
	public final java.lang.String getName() { return Name; }
	public final void setName(java.lang.String v) {  Name = v; }
	public final java.lang.String getWelcomeText() { return WelcomeText; }
	public final void setWelcomeText(java.lang.String v) {  WelcomeText = v; }
	public final java.lang.String getDescription() { return Description; }
	public final void setDescription(java.lang.String v) {  Description = v; }
	public final boolean getIncludeParentMembers() { return IncludeParentMembers; }
	public final void setIncludeParentMembers(boolean v) {  IncludeParentMembers = v; }
	public final int getCreatorId() { return CreatorId; }
	public final void setCreatorId(int v) {  CreatorId = v; }
	public final int getStatus() { return Status; }
	public final void setStatus(int v) {  Status = v; }
	public final String getModified() { return Modified; }
	public final void setModified(java.util.Date v) {  Modified = v.toString(); }
	public final String getCreated() { return Created; }
	public final void setCreated(java.util.Date v) {  Created = v.toString(); }
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final boolean getCommunityLogoPresent() { return CommunityLogoPresent; }
	public final void setCommunityLogoPresent (boolean v) {  CommunityLogoPresent = v; }
	public final int getCommunityLogoLength() { return CommunityLogoLength; }
	public final void setCommunityLogoLength (int v) {  CommunityLogoLength = v; }
	public final String getCommunityLogoContentType() { return CommunityLogoContentType; }
	public final void setCommunityLogoContentType (String v) {  CommunityLogoContentType = v; }
	public final boolean getCommunityBannerPresent() { return CommunityBannerPresent; }
	public final void setCommunityBannerPresent (boolean v) {  CommunityBannerPresent = v; }
	public final String getCommunityUpdated() { return CommunityUpdated; }
	public final void setCommunityUpdated(java.util.Date v) {  CommunityUpdated = v.toString(); }
	public final int getWidth() { return Width; }
	public final void setWidth(int v) {  Width = v; }
	public final int getHeight() { return Height; }
	public final void setHeight(int v) {  Height = v; }
	public final int getVisitCount() { return VisitCount; }
	public final void setVisitCount(int v) {  VisitCount = v; }
	public final boolean getFeatured() { return Featured; }
	public final void setFeatured(boolean v) {  Featured = v; }
}