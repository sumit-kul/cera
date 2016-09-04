package com.era.community.media.dao.generated;

import com.era.community.media.dao.PhotoGroup;

public interface PhotoGroupFinderBase
{
	public PhotoGroup getPhotoGroupForId(int id) throws Exception;
	public PhotoGroup newPhotoGroup() throws Exception;
}