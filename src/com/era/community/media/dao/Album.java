package com.era.community.media.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * @entity name="ALBUM"
 */
public class Album extends CecAbstractEntity
{
	private String Title = "";
	private String Description = "";
	private int OwnerId;
	private int Shared;
	private int PrivacyLevel;
	private int CoverPhotoId;
	private int ProfileAlbum;
	private int CoverAlbum;

	private AlbumDao dao;

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
	
	public boolean isPhotoPresent() throws Exception
	{
		return dao.isPhotoPresent(this);
	}

	public void update() throws Exception
	{
		dao.store(this);
	}

	public void delete() throws Exception
	{
		dao.delete(this);
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public void setDao(AlbumDao dao) {
		this.dao = dao;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getPrivacyLevel() {
		return PrivacyLevel;
	}

	public void setPrivacyLevel(int privacyLevel) {
		PrivacyLevel = privacyLevel;
	}

	public int getOwnerId() {
		return OwnerId;
	}

	public void setOwnerId(int ownerId) {
		OwnerId = ownerId;
	}

	public int getShared() {
		return Shared;
	}

	public void setShared(int shared) {
		Shared = shared;
	}

	public int getCoverPhotoId() {
		return CoverPhotoId;
	}

	public void setCoverPhotoId(int coverPhotoId) {
		CoverPhotoId = coverPhotoId;
	}

	public int getProfileAlbum() {
		return ProfileAlbum;
	}

	public void setProfileAlbum(int profileAlbum) {
		ProfileAlbum = profileAlbum;
	}

	public int getCoverAlbum() {
		return CoverAlbum;
	}

	public void setCoverAlbum(int coverAlbum) {
		CoverAlbum = coverAlbum;
	}
}