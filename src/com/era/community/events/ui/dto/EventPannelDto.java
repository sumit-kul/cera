package com.era.community.events.ui.dto; 

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventPannelDto
{     
	private int entryId;
	private int posterId;
	private int communityID;
	private String name;
	private String contactName;
	private String communityName;
	private String city;
	private String country;
	private String StartDate;
	private String EndDate;
	private String Type;
	private boolean photoPresent;
	private boolean eventPhotoPresent;	
	
	public String getDateStarted() throws Exception
	{
		SimpleDateFormat fmter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
		SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
		SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd");   
		Date today = new Date();
		String sToday = fmter.format(today);
		try {
			Date date = fmter.parse(getStartDate());
			if (fmter.format(date).equals(sToday)) {
				return "Today " + fmt.format(date);
			}
			return fmt2.format(date) + " at " + fmt.format(date);
		} catch (Exception e) {
			return "";
		}
	}

	public String getDateEnded() throws Exception
	{
		SimpleDateFormat fmter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
		SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
		SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd");      
		Date today = new Date();
		String sToday = fmter.format(today);
		try {
			Date date = fmter.parse(getEndDate());
			if (fmter.format(date).equals(sToday)) {
				return "Today " + fmt.format(date);
			}
			return fmt2.format(date) + " at " + fmt.format(date);
		} catch (Exception e) {
			return "";
		}
	}
	
	public int getEntryId() {
		return entryId;
	}
	public void setEntryId(int entryId) {
		this.entryId = entryId;
	}

	public int getPosterId() {
		return posterId;
	}

	public void setPosterId(int posterId) {
		this.posterId = posterId;
	}

	public int getCommunityID() {
		return communityID;
	}

	public void setCommunityID(int communityID) {
		this.communityID = communityID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStartDate() {
		return StartDate;
	}

	public void setStartDate(Date startDate) {
		StartDate = startDate.toString();
	}

	public String getEndDate() {
		return EndDate;
	}

	public void setEndDate(Date endDate) {
		EndDate = endDate.toString();
	}

	public boolean isPhotoPresent() {
		return photoPresent;
	}

	public void setPhotoPresent(boolean photoPresent) {
		photoPresent = photoPresent;
	}

	public boolean isEventPhotoPresent() {
		return eventPhotoPresent;
	}

	public void setEventPhotoPresent(boolean eventPhotoPresent) {
		this.eventPhotoPresent = eventPhotoPresent;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}
}