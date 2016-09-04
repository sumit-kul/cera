package com.era.community.blog.ui.dto; 

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import support.community.util.StringHelper;

public class BlogEntryPannelDto
{     
	private int entryId;
	private String title;
	private int userId;
	private String fullName;
	private String photoPresent;
	private int communityId;
	private String datePosted;
	private String body;
	private int imageCount;
	private int commentCount;
	private int likeCount;
	private int visitors;
	
	public String getDisplayBody()
	{
		String sBody = StringHelper.formatForDisplay(getBody());
		if ( (sBody == null) || (sBody.length()==0)) return "";

		sBody = sBody.replaceAll("<p>","");
		sBody = sBody.replaceAll("</p>"," ");
		sBody = sBody.replaceAll("   ", "");

		if (sBody.contains("<")) {
			sBody = sBody.substring(0, sBody.indexOf("<"));
			if(sBody.length() >= 600) {
				sBody = sBody.substring(0, 600);
			}
			sBody = sBody.concat("...");
		} else if (sBody.length() >= 600) {
			sBody = sBody.substring(0, 600).concat("...");
		}
		return sBody;
	}
	
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
	public String getEditedTitle() {
		String stitle = title;
		if(stitle.length() >= 55) {
			stitle = stitle.substring(0, 53);
			stitle = stitle.concat("...");
		}
		return stitle;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getImageCount() {
		return imageCount;
	}

	public void setImageCount(Long imageCount) {
		this.imageCount = imageCount.intValue();
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount.intValue();
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount.intValue();
	}

	public int getVisitors() {
		return visitors;
	}

	public void setVisitors(int visitors) {
		this.visitors = visitors;
	}
}