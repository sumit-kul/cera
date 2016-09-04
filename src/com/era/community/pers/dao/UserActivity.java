package com.era.community.pers.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 *
 * @entity name="UACT"
 * 
 */
public class UserActivity extends CecAbstractEntity
{
    protected int UserId;
    protected int CommunityActivityId;
    protected int CommunityId;
    protected int BlogEntryId;
    protected int PhotoId;
    protected int AlbumId;
    protected int MediaGroupNumber;

    protected UserActivityDao dao;

    public boolean isReadAllowed(UserEntity user) throws Exception
    {
        if (user==null) return false;
        return true;
   }
    
    public boolean isWriteAllowed(UserEntity user) throws Exception
    {
        return true;
    }
    
    public void update() throws Exception
    {
       dao.store(this); 
    }

    public void delete() throws Exception
    {
        dao.delete(this);
    } 

    public final void setDao(UserActivityDao dao)
    {
        this.dao = dao;
    }
        
    public final UserActivityDao getDao()
    {
        return dao;
    }

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public int getCommunityId() {
		return CommunityId;
	}

	public void setCommunityId(int communityId) {
		CommunityId = communityId;
	}

	public int getBlogEntryId() {
		return BlogEntryId;
	}

	public void setBlogEntryId(int blogEntryId) {
		BlogEntryId = blogEntryId;
	}

	public int getCommunityActivityId() {
		return CommunityActivityId;
	}

	public void setCommunityActivityId(int communityActivityId) {
		CommunityActivityId = communityActivityId;
	}

	public int getAlbumId() {
		return AlbumId;
	}

	public void setAlbumId(int albumId) {
		AlbumId = albumId;
	}

	public int getMediaGroupNumber() {
		return MediaGroupNumber;
	}

	public void setMediaGroupNumber(int mediaGroupNumber) {
		MediaGroupNumber = mediaGroupNumber;
	}

	public int getPhotoId() {
		return PhotoId;
	}

	public void setPhotoId(int photoId) {
		PhotoId = photoId;
	}
}