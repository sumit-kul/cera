package com.era.community.media.ui.dto; 

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MediaInfoDto 
{
	private int id;
	private String title;
	private String description;
	private String modified;
	private String created;
	private int userId;
	private int albumId;
	private int currentProfile;
	private int privacyLevel;
	private String ownerName;
	private String photoPresent;
	private int photoLikeId;
	private int photoLikeCount;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreated() {
		if (created != null) {
			SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
			Date today = new Date();
			String sToday = fmter.format(today);
			try {
				Date date = formatter.parse(created);
				if (fmter.format(date).equals(sToday)) {
					return "Today " + fmt.format(date);
				}
				return fmt2.format(date);

			} catch (ParseException e) {
				return created;
			}
		}
		return "";
	}
	public void setModified(Date modified) {
		this.modified = modified.toString();
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getAlbumId() {
		return albumId;
	}
	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}
	public int getCurrentProfile() {
		return currentProfile;
	}
	public void setCurrentProfile(int currentProfile) {
		this.currentProfile = currentProfile;
	}
	public int getPrivacyLevel() {
		return privacyLevel;
	}
	public void setPrivacyLevel(int privacyLevel) {
		this.privacyLevel = privacyLevel;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getPhotoPresent() {
		return photoPresent;
	}
	public void setPhotoPresent(String photoPresent) {
		this.photoPresent = photoPresent;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getModified() {
		return modified;
	}
	public void setCreated(Date created) {
		this.created = created.toString();
	}
	public int getPhotoLikeId() {
		return photoLikeId;
	}
	public void setPhotoLikeId(Long photoLikeId) {
		this.photoLikeId = photoLikeId.intValue();
	}
	public int getPhotoLikeCount() {
		return photoLikeCount;
	}
	public void setPhotoLikeCount(Long photoLikeCount) {
		this.photoLikeCount = photoLikeCount.intValue();
	}
}