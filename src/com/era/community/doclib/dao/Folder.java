package com.era.community.doclib.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * @entity name="Folder"
 */
public class Folder extends CecAbstractEntity
{
	private String Title = "";
	private String Description = "";
	private int OwnerId;
	private int LibraryId;
	private int PrivacyLevel;
	private int CoverPhotoId;
	private int ProfileFolder;
	private int BannerFolder;
	private int BlogFolder;
	private int ForumFolder;
	private int WikiFolder;
	
	private FolderDao dao;

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

	public void setDao(FolderDao dao) {
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

	public int getCoverPhotoId() {
		return CoverPhotoId;
	}

	public void setCoverPhotoId(int coverPhotoId) {
		CoverPhotoId = coverPhotoId;
	}

	public int getLibraryId() {
		return LibraryId;
	}

	public void setLibraryId(int libraryId) {
		LibraryId = libraryId;
	}

	public int getOwnerId() {
		return OwnerId;
	}

	public void setOwnerId(int ownerId) {
		OwnerId = ownerId;
	}

	public int getProfileFolder() {
		return ProfileFolder;
	}

	public void setProfileFolder(int profileFolder) {
		ProfileFolder = profileFolder;
	}

	public int getBannerFolder() {
		return BannerFolder;
	}

	public void setBannerFolder(int bannerFolder) {
		BannerFolder = bannerFolder;
	}

	public int getBlogFolder() {
		return BlogFolder;
	}

	public void setBlogFolder(int blogFolder) {
		BlogFolder = blogFolder;
	}

	public int getForumFolder() {
		return ForumFolder;
	}

	public void setForumFolder(int forumFolder) {
		ForumFolder = forumFolder;
	}

	public int getWikiFolder() {
		return WikiFolder;
	}

	public void setWikiFolder(int wikiFolder) {
		WikiFolder = wikiFolder;
	}
}