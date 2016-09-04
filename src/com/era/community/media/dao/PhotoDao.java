package com.era.community.media.dao; 


interface PhotoDao extends com.era.community.media.dao.generated.PhotoDaoBase, PhotoFinder
{
	public boolean isPhotoPresent(Photo photo) throws Exception;
}