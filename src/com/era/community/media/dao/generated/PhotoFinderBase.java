package com.era.community.media.dao.generated;

import com.era.community.media.dao.Photo;

public interface PhotoFinderBase
{
	public Photo getPhotoForId(int id) throws Exception;
	public Photo newPhoto() throws Exception;
}