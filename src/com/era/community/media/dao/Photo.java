package com.era.community.media.dao;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import support.community.database.BlobData;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * @entity name="PHOTO"
 */
public class Photo extends CecAbstractEntity
{
	private String Title = "";
	private int PhotoLength;
	private int Width;
	private int Height;
	private String PhotoContentType;
	private String FileName;
	private String Description = "";
	private int UserId;
	private int AlbumId;
	private int CurrentProfile;
	private int PrivacyLevel;
	private int MediaGroupNumber;

	private PhotoDao dao;

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

	public BlobData readPhoto() throws Exception
	{
		return dao.readPhoto(this);
	}

	public void storePhoto(InputStream data, int dataLength, String contentType) throws Exception
	{
		dao.storePhoto(this, data, dataLength, contentType);
	}

	public void storePhoto(MultipartFile data) throws Exception
	{
		if (data.getContentType().startsWith("video/")) {
			dao.storePhoto(this, data);
		} else {
			//dao.storePhoto(this, data.getInputStream());
			dao.storePhoto(this, data);
		}
	}

	public String getPhotoContentType() throws Exception
	{
		return dao.getPhotoContentType(this);
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public int getPhotoLength() {
		return PhotoLength;
	}

	public void setPhotoLength(int photoLength) {
		PhotoLength = photoLength;
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

	public void setPhotoContentType(String photoContentType) {
		PhotoContentType = photoContentType;
	}

	public void setDao(PhotoDao dao) {
		this.dao = dao;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public int getAlbumId() {
		return AlbumId;
	}

	public void setAlbumId(int albumId) {
		AlbumId = albumId;
	}

	public int getCurrentProfile() {
		return CurrentProfile;
	}

	public void setCurrentProfile(int currentProfile) {
		CurrentProfile = currentProfile;
	}

	public int getPrivacyLevel() {
		return PrivacyLevel;
	}

	public void setPrivacyLevel(int privacyLevel) {
		PrivacyLevel = privacyLevel;
	}

	public final int getWidth() {
		return Width;
	}

	public final void setWidth(int width) {
		Width = width;
	}

	public final int getHeight() {
		return Height;
	}

	public final void setHeight(int height) {
		Height = height;
	}

	public int getMediaGroupNumber() {
		return MediaGroupNumber;
	}

	public void setMediaGroupNumber(int mediaGroupNumber) {
		MediaGroupNumber = mediaGroupNumber;
	}
}