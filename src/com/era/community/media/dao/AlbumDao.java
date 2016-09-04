package com.era.community.media.dao; 

interface AlbumDao extends com.era.community.media.dao.generated.AlbumDaoBase, AlbumFinder
{
	public boolean isPhotoPresent(Album album) throws Exception;
}