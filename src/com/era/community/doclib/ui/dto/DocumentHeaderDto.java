package com.era.community.doclib.ui.dto; 

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DocumentHeaderDto extends com.era.community.doclib.dao.generated.DocumentEntity
{     
    private String itemDesc;
    private Long commentCount;
    private String firstName;
	private String lastName;
	private boolean photoPresent;
	private Long likeCount;
	private Long imageCount;
	private Double avgRating;
	private int communityId;
	private int docId;
	
	public String getIconType() {
    	if (this.getFileContentType() != null) {
    		if (getFileContentType().contains("application/vnd.ms-excel") || getFileContentType().contains("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
        		return "xls";
    		} else if (getFileContentType().contains("application/x-tar") || getFileContentType().contains("application/zip")) {
    			return "zip";
    		} else if (getFileContentType().contains("application/pdf")) {
    			return "pdf";
    		} else if (getFileContentType().contains("application/xml")) {
    			return "xml";
    		} else if (getFileContentType().contains("image/jpeg")) {
    			return "img";
    		} else if (getFileContentType().contains("text/richtext") || getFileContentType().contains("application/msword") || getFileContentType().contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
    			return "doc";
    		} else if (getFileContentType().contains("application/vnd.ms-powerpoint") || getFileContentType().contains("application/vnd.openxmlformats-officedocument.presentationml.presentation")) {
    			return "ppt";
    		} 
    		return "file";
		} else {
			return "file";
		}
    }
    
    public String getPostedOn() throws Exception
	{
		SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
		SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
		Date today = new Date();
		String sToday = fmter.format(today);

		try {

			Date date = formatter.parse(getDatePosted());
			if (fmter.format(date).equals(sToday)) {
				return "Today " + fmt.format(date);
			}
			return fmt2.format(date);

		} catch (ParseException e) {
			return getCreated();
		}
	}

	public boolean getPhotoPresent() {
		return photoPresent;
	}

	public void setPhotoPresent(boolean photoPresent) {
		this.photoPresent = photoPresent;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}

	public Long getImageCount() {
		return imageCount;
	}

	public void setImageCount(Long imageCount) {
		this.imageCount = imageCount;
	}
	
	public double getAvgRating() {
		return avgRating == null ? 0.0 : avgRating.doubleValue();
	}

	public void setAvgRating(BigDecimal avgRating) {
		this.avgRating = avgRating == null ? 0.0 : avgRating.doubleValue();
	}

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}
}