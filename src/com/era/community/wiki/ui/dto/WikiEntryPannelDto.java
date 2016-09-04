package com.era.community.wiki.ui.dto; 

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WikiEntryPannelDto
{     
	private int entryId;
	private String title;
	private int posterId;
	private int likeCount;
	private int ImageCount;
	private int SectionImageCount;
	private String fullName;
	private String photoPresent;
	private int communityId;
	private String datePosted;
	
	public String getCreatedOn() throws Exception
	{
		SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
		SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy");
		Date today = new Date();
		String sToday = fmter.format(today);

		try {

			Date date = formatter.parse(getDatePosted());
			if (fmter.format(date).equals(sToday)) {
				return "Today " + fmt.format(date);
			}
			return fmt2.format(date);

		} catch (ParseException e) {
			return getDatePosted();
		}
	}

	public String getDatePosted() { 
		return datePosted; 
	}
	
	public void setDatePosted(java.util.Date datePosted) {  
		this.datePosted = datePosted.toString(); 
	}
	public int getEntryId() {
		return entryId;
	}
	public void setEntryId(int entryId) {
		this.entryId = entryId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhotoPresent() {
		return photoPresent;
	}
	public void setPhotoPresent(String photoPresent) {
		this.photoPresent = photoPresent;
	}
	public int getCommunityId() {
		return communityId;
	}
	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public int getImageCount() {
		return ImageCount;
	}

	public void setImageCount(Long imageCount) {
		ImageCount = imageCount.intValue();
	}

	public int getSectionImageCount() {
		return SectionImageCount;
	}

	public void setSectionImageCount(Long sectionImageCount) {
		SectionImageCount = sectionImageCount.intValue();
	}
	
	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount.intValue();
	}

	public int getPosterId() {
		return posterId;
	}

	public void setPosterId(int posterId) {
		this.posterId = posterId;
	}
}