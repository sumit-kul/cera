package com.era.community.media.dao;

import java.util.List;

import support.community.database.QueryPaginator;
import support.community.database.QueryScroller;

import com.era.community.media.ui.dto.MediaHeaderDto;
import com.era.community.media.ui.dto.MediaInfoDto;

public class PhotoDaoImpl extends com.era.community.media.dao.generated.PhotoDaoBaseImpl implements PhotoDao
{
	public boolean isPhotoPresent(Photo photo) throws Exception
	{
		Integer v = (Integer) getValue(photo, "case when Photo is null then 0 else 1 end", Integer.class);
		return v.intValue() == 1;
	}

	public List<Photo> listPhotosForUser(int userId) throws Exception
	{
		String query = "select * from Photo where UserId = ? order by Created desc LIMIT 10 ";
		return getBeanList(query, Photo.class, userId);
	}
	
	public Photo getLatestPhotoInAlbum(int albumId) throws Exception
	{
		String query = "select * from Photo where AlbumId = ? order by Created desc LIMIT 1";
		return getBean(query, Photo.class, albumId);
	}
	
	public List<Photo> listPhotosForAlbum(int albumId) throws Exception
	{
		String query = "select * from Photo where AlbumId = ? order by Created desc";
		return getBeanList(query, Photo.class, albumId);
	}

	public MediaInfoDto extraInfoForPhoto(int photoId, int currUserId) throws Exception
	{
		String uld = " , 0 as UserLikeId "; 
    	if (currUserId > 0) {
    		uld = " , (select count(*) from PhotoLike L where L.PhotoId = P.Id and L.PosterId = ? ) as PhotoLikeId ";
		}
    	
		String query = "select P.Id, P.Title, P.Description, P.Modified, P.Created, P.UserId, P.AlbumId, P.CurrentProfile, P.PrivacyLevel, " +
		" CONCAT_WS(' ',U.FirstName,U.LastName) as OwnerName, U.PhotoPresent " +
		" , (select count(*) from PhotoLike L where L.PhotoId = P.Id ) as PhotoLikeCount  " + uld + " from Photo P,  User U where P.UserId = U.ID and P.ID = ? ";

		if (currUserId > 0) {
			return getBean(query, MediaInfoDto.class, currUserId, photoId);
		} else {
			return getBean(query, MediaInfoDto.class, photoId);
		}
	}

	public QueryScroller getIndexedPhotosForUser(int userId) throws Exception
	{
		String query = "select * from Photo where UserId = ? ";
		QueryScroller scroller = getQueryScroller(query, userId);
		scroller.addScrollKey("Created", "Created", QueryPaginator.DIRECTION_DESCENDING, QueryPaginator.TYPE_DATE);
		return scroller;

	}
	
	public QueryScroller getPhotosToEditForUser(int userId, int albumId) throws Exception
	{
		String query = "select * from Photo where UserId = ? and AlbumId = ?";
		QueryScroller scroller = getQueryScroller(query, null, userId, albumId);
		scroller.addScrollKey("Created", "Created", QueryPaginator.DIRECTION_ASCENDING, QueryPaginator.TYPE_DATE);
		return scroller;

	}
	
	public void deleteAllPhotosForAlbum(int albumId) throws Exception
    {
        String sql="delete from Photo where AlbumId = ?";
        getSimpleJdbcTemplate().update(sql, albumId);       
    }
	
	public Photo getCurrentProfilePhoto(int userId) throws Exception
	{
		String query = "select * from Photo where CurrentProfile = 1 and UserId = ? order by Created desc LIMIT 1";
		return getBean(query, Photo.class, userId);
	}
	
	public Photo getCurrentCoverPhoto(int userId) throws Exception
	{
		String query = "select * from Photo where CurrentProfile = 2 and UserId = ? order by Created desc LIMIT 1";
		return getBean(query, Photo.class, userId);
	}
	
	public List getPhotoForMediaPanel(int profileId, int max) throws Exception
	{
		String query = "select * from Photo where UserId = ? order by Created desc ";

		if (max != 0) {
			query += " LIMIT " + max + " ";
		}
		return getBeanList(query, Photo.class, profileId);
	}
	
	public int countPhotoListForHeader(int albumId, int userId, int mediaGroupNumber) throws Exception
	{
		String query = " select count(Id) from Photo where UserId = ? and AlbumId = ? and MediaGroupNumber = ? ";
		
		return getSimpleJdbcTemplate().queryForInt(query, userId, albumId, mediaGroupNumber);
	}
	
	public MediaHeaderDto getPhotoForHeader(int photoId) throws Exception
	{
		String query = " select p.id docId, p.title, CAST(p.Description as CHAR(1000) ) itemDesc, " +
		" '' FileName, d.PhotoContentType, 0 as ImageCount, " +
		" (select COUNT(ID) from PhotoComment PC where PC.PhotoId = p.id) as commentCount, " +
		" p.Created, 0 as Downloads, U.Id Posterid, U.FirstName FirstName, U.LastName LastName, U.PHOTOPRESENT photoPresent, " +
		" (select COUNT(ID) from PhotoLike PL where PL.PhotoId = d.id) likeCount " +
		" from Photo p, User U where U.Id = p.UserId and p.Id = ?";
		return getBean(query, MediaHeaderDto.class, photoId);
	}
	
	public List<MediaHeaderDto> getPhotoListForHeader(int albumId, int userId, int mediaGroupNumber) throws Exception
	{
		String query = " select p.id docId, p.Title, '' itemDesc, " +
		" '' FileName, p.PhotoContentType, 0 as ImageCount, " +
		" (select COUNT(ID) from PhotoComment PC where PC.PhotoId = p.id) as commentCount, " +
		" p.Created, 0 as Downloads, " +
		" (select COUNT(ID) from PhotoLike PL where PL.PhotoId = p.id) likeCount " +
		" from Photo p where p.AlbumId = ? and p.UserId = ? and p.MediaGroupNumber = ? order by docId desc ";
		return getBeanList(query, MediaHeaderDto.class, albumId, userId, mediaGroupNumber);
	}
	
	
}