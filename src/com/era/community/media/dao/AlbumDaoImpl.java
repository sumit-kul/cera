package com.era.community.media.dao;

import java.util.List;

import com.era.community.media.ui.dto.AlbumDto;
import com.era.community.media.ui.dto.MediaInfoDto;

public class AlbumDaoImpl extends com.era.community.media.dao.generated.AlbumDaoBaseImpl implements AlbumDao
{
	public boolean isPhotoPresent(Album album) throws Exception
    {
        //Integer v = (Integer) getValue(album, "case when Photo is null then 0 else 1 end", Integer.class);
        //return v.intValue() == 1;
        return false;
    }
	
	public List<AlbumDto> listAlbumsForUser(int userId, int albumId) throws Exception
    {
		String albNtIncl = "";
		if (albumId > 0) {
			albNtIncl = " and A.ID != ? and A.ProfileAlbum != 1 and A.CoverAlbum != 1 ";
		}
		String query = "select A.*, (select COUNT(P.ID) from Photo P where P.AlbumId = A.ID) photoCount " +
			" from Album A where OwnerId = ? "+albNtIncl+" order by Created desc ";
		
		if (albumId > 0) {
        return getBeanList(query, AlbumDto.class, userId, albumId);
		}
        
        return getBeanList(query, AlbumDto.class, userId);
    }
	
	public List<AlbumDto> listOtherAlbumsForUser(int userId, int albumId) throws Exception
    {
		String albNtIncl = "";
		if (albumId > 0) {
			albNtIncl = " and A.ID != ? and A.ProfileAlbum != 1 and A.CoverAlbum != 1 ";
		}
		String query = "select A.*, (select COUNT(P.ID) from Photo P where P.AlbumId = A.ID) photoCount " +
			" from Album A where OwnerId = ? "+albNtIncl+" order by Created desc ";
		
		if (albumId > 0) {
        return getBeanList(query, AlbumDto.class, userId, albumId);
		}
        
        return getBeanList(query, AlbumDto.class, userId);
    }
	
	public MediaInfoDto extraInfoForAlbum(int albumId) throws Exception
	{
		String query = "select P.Id, P.Title, P.Description, P.Modified, P.Created, P.UserId, P.AlbumId, P.CurrentProfile, P.PrivacyLevel, " +
		" CONCAT_WS(' ',U.FirstName,U.LastName) as OwnerName, U.PhotoPresent from photo P,  User U where P.UserId = U.ID and P.ID = ? ";
		
		 return getBean(query, MediaInfoDto.class, albumId);
	}
	
	public Album getProfileAlbumForUser(int ownerId) throws Exception
	{
		String query = "select * from Album where OwnerId = ? and ProfileAlbum = 1";
		return getBean(query, Album.class, ownerId);
	}
	
	public Album getCoverAlbumForUser(int ownerId) throws Exception
	{
		String query = "select * from Album where OwnerId = ? and CoverAlbum = 1";
		return getBean(query, Album.class, ownerId);
	}
}