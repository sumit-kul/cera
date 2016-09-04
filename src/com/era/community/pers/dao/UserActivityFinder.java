package com.era.community.pers.dao; 

public interface UserActivityFinder extends com.era.community.pers.dao.generated.UserActivityFinderBase
{
	public void clearUserActivityForPhoto(int photoId, int mediaGroupNumber, int albumId) throws Exception;
	public void clearUserActivityForAlbum(int albumId) throws Exception;
	public UserActivity getMostRecentMediaGroupActivity(int userId, int albumId) throws Exception;
	public UserActivity getMediaGroupUserActivity(int userId, int mediaGroupNumber, int albumId) throws Exception;
}