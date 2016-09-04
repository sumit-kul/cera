package com.era.community.doclib.ui.dto; 

import java.text.SimpleDateFormat;
import java.util.Date;

import com.era.community.doclib.dao.generated.FolderEntity;

public class FolderDto extends FolderEntity
{
	private int photoCount;
	private String updatedOn;
	private String updatedBy;
	private String firstName;
	private String lastName;
	
	public String getCreatedOn() throws Exception
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
		try {
			Date date = formatter.parse(getCreated());
			return fmt2.format(date);
		} catch (Exception e) {
			return getCreated();
		}
	}

	public int getPhotoCount() {
		return photoCount;
	}

	public void setPhotoCount(Long photoCount) {
		this.photoCount = photoCount.intValue();
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn.toString();
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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
}