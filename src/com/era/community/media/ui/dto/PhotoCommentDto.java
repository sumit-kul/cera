package com.era.community.media.ui.dto; 

import com.era.community.media.dao.generated.PhotoCommentEntity;

public class PhotoCommentDto extends PhotoCommentEntity
{
	private String DisplayName;
	private String PhotoPresent;
	
	public String getDisplayName() {
		return DisplayName;
	}
	public void setDisplayName(String displayName) {
		DisplayName = displayName;
	}
	public String getPhotoPresent() {
		return PhotoPresent;
	}
	public void setPhotoPresent(String photoPresent) {
		PhotoPresent = photoPresent;
	}
}