package com.era.community.media.dao.generated;

import com.era.community.media.dao.Album;

public interface AlbumFinderBase
{
	public Album getAlbumForId(int id) throws Exception;
	public Album newAlbum() throws Exception;
}