package com.era.community.media.ui.dto; 

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MediaHeaderDto extends com.era.community.media.dao.generated.PhotoEntity
{     
    private String itemDesc;
    private Long commentCount;
    private String firstName;
	private String lastName;
	private Long likeCount;
	private Long imageCount;
	private int docId;
	
    public String getPostedOn() throws Exception
	{
		SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
		SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
		Date today = new Date();
		String sToday = fmter.format(today);

		try {

			Date date = formatter.parse(getCreated());
			if (fmter.format(date).equals(sToday)) {
				return "Today " + fmt.format(date);
			}
			return fmt2.format(date);

		} catch (ParseException e) {
			return getCreated();
		}
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
	
	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}
}