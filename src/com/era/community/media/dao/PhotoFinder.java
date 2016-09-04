package com.era.community.media.dao; 

import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.media.ui.dto.MediaHeaderDto;
import com.era.community.media.ui.dto.MediaInfoDto;

public interface PhotoFinder extends com.era.community.media.dao.generated.PhotoFinderBase
{
	public List<Photo> listPhotosForUser(int userId) throws Exception;
	public MediaInfoDto extraInfoForPhoto(int photoId, int currUserId) throws Exception;
	public List<Photo> listPhotosForAlbum(int albumId) throws Exception;
	public QueryScroller getIndexedPhotosForUser(int userId) throws Exception;
	public Photo getLatestPhotoInAlbum(int albumId) throws Exception;
	public void deleteAllPhotosForAlbum(int albumId) throws Exception;
	public QueryScroller getPhotosToEditForUser(int userId, int albumId) throws Exception;
	public Photo getCurrentProfilePhoto(int userId) throws Exception;
	public Photo getCurrentCoverPhoto(int userId) throws Exception;
	public List getPhotoForMediaPanel(int profileId, int max) throws Exception;
	public MediaHeaderDto getPhotoForHeader(int photoId) throws Exception;
	public int countPhotoListForHeader(int albumId, int userId, int mediaGroupNumber) throws Exception;
	public List<MediaHeaderDto> getPhotoListForHeader(int albumId, int userId, int mediaGroupNumber) throws Exception;
}