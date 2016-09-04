package com.era.community.media.dao; 

import java.util.List;

import com.era.community.media.ui.dto.AlbumDto;
import com.era.community.media.ui.dto.MediaInfoDto;

public interface AlbumFinder extends com.era.community.media.dao.generated.AlbumFinderBase
{
	public List<AlbumDto> listAlbumsForUser(int userId, int albumId) throws Exception;
	public List<AlbumDto> listOtherAlbumsForUser(int userId, int albumId) throws Exception;
	public MediaInfoDto extraInfoForAlbum(int albumId) throws Exception;
	public Album getProfileAlbumForUser(int ownerId) throws Exception;
	public Album getCoverAlbumForUser(int ownerId) throws Exception;
}