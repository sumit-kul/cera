package com.era.community.media.dao.generated; 

public abstract class AlbumLinkEntity extends support.community.framework.CommandBeanImpl
{
	private int Id;
	private int AlbumId;
	private int ContributorId;
	private int AccessLevel;
	
	public final int getId() { return Id; }
	public final void setId(int v) {  Id = v; }
	public final int getAlbumId() { return AlbumId; }
	public final void setAlbumId(int v) {  AlbumId = v; }
	public final int getContributorId() { return ContributorId; }
	public final void setContributorId(int v) {  ContributorId = v; }
	public final int getAccessLevel() { return AccessLevel; }
	public final void setAccessLevel(int v) {  AccessLevel = v; }
}