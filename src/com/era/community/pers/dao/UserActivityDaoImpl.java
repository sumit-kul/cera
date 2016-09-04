package com.era.community.pers.dao; 


public class UserActivityDaoImpl extends com.era.community.pers.dao.generated.UserActivityDaoBaseImpl implements UserActivityDao
{
	public void clearUserActivityForPersonalBlogEntry(int entryId) throws Exception
	{
		String sql="delete from " + getTableName() + " where  BlogEntryId = ?";
		getSimpleJdbcTemplate().update(sql, entryId); 
	}
	
	public void clearUserActivityForPhoto(int photoId, int mediaGroupNumber, int albumId) throws Exception
	{
		if (albumId > 0) {
			String sql="delete from " + getTableName() + " where  PhotoId = ? and MediaGroupNumber = ? and AlbumId = ?";
			getSimpleJdbcTemplate().update(sql, photoId, mediaGroupNumber, albumId);
		} else {
			String sql="delete from " + getTableName() + " where  PhotoId = ? and MediaGroupNumber =? ";
			getSimpleJdbcTemplate().update(sql, photoId, mediaGroupNumber);
		}
	}
	
	public void clearUserActivityForAlbum(int albumId) throws Exception
	{
		String sql="delete from " + getTableName() + " where  AlbumId = ?";
		getSimpleJdbcTemplate().update(sql, albumId); 
	}
	
	public UserActivity getMostRecentMediaGroupActivity(int userId, int albumId) throws Exception
	{
		String where = " ";
		if (albumId > 0) {
			where = " UserId = ? and albumId = ? and MediaGroupNumber > 0 order by MediaGroupNumber desc LIMIT 1 ";
			return (UserActivity)getEntityWhere(where, userId, albumId);
		} else {
			where = " UserId = ? and MediaGroupNumber > 0 order by MediaGroupNumber desc LIMIT 1 ";
			return (UserActivity)getEntityWhere(where, userId);
		}
	}
	
	public UserActivity getMediaGroupUserActivity(int userId, int mediaGroupNumber, int albumId) throws Exception
	{
		String where = " ";
		if (albumId > 0) {
			where = " UserId = ? and albumId = ? and MediaGroupNumber = ? ";
			return (UserActivity)getEntityWhere(where, userId, albumId, mediaGroupNumber);
		} else {
			where = " UserId = ? and MediaGroupNumber = ? ";
			return (UserActivity)getEntityWhere(where, userId, mediaGroupNumber);
		}
	}
}