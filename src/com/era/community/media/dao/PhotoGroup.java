package com.era.community.media.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * @entity name="PhotoGroup"
 */
public class PhotoGroup extends CecAbstractEntity
{
	private int PhotoId;
	private int AlbumId;
	
	private PhotoGroupDao dao;

	public boolean isReadAllowed(UserEntity user)
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user)
	{
		if (user != null)
			return true;
		return false;
	}
	
	public void update() throws Exception
	{
		dao.store(this);
	}

	public void delete() throws Exception
	{
		dao.delete(this);
	}

	public void setDao(PhotoGroupDao dao) {
		this.dao = dao;
	}

	public int getPhotoId() {
		return PhotoId;
	}

	public void setPhotoId(int photoId) {
		PhotoId = photoId;
	}

	public int getAlbumId() {
		return AlbumId;
	}

	public void setAlbumId(int albumId) {
		AlbumId = albumId;
	}
}