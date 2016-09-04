package com.era.community.media.dao;

import java.util.List;
import com.era.community.media.ui.dto.AlbumLinkDto;

public interface AlbumLinkFinder extends com.era.community.media.dao.generated.AlbumLinkFinderBase
{
	public AlbumLink getAlbumLinkForContributor(int albumId, int contId) throws Exception;
	public List<AlbumLinkDto> getAlbumLinksForAlbum(int albumId) throws Exception;
	public void deleteAllLinksForAlbum(int albumId) throws Exception;
	public void deleteAlbumLinkForId(int id) throws Exception;
}