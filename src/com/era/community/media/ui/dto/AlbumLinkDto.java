package com.era.community.media.ui.dto; 

import com.era.community.media.dao.generated.AlbumLinkEntity;

public class AlbumLinkDto extends AlbumLinkEntity
{
	private String photoPresent;
	private String displayName;
	public String getPhotoPresent() {
		return photoPresent;
	}
	public void setPhotoPresent(String photoPresent) {
		this.photoPresent = photoPresent;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}