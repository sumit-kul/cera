package com.era.community.media.ui.dto; 

import com.era.community.media.dao.generated.AlbumEntity;

public class AlbumDto extends AlbumEntity
{
	private int photoCount;

	public int getPhotoCount() {
		return photoCount;
	}

	public void setPhotoCount(Long photoCount) {
		this.photoCount = photoCount.intValue();
	}
}