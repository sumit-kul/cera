package com.era.community.communities.ui.dto; 

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CommunityEntryPannelDto
{     
	private int entryId;
	private String name;
	private String welcomeText;
	private boolean logoPresent;
	private boolean bannerPresent;
	private String created;
	private int memberCount;
	private int visitCount;

	public String getMemberCountString() throws Exception
	{
		int n = getMemberCount();
		if (n == 1)
			return n + " member";
		else
			return n + " members";
	}

	public String getVisitCountString() throws Exception
	{
		int n = getVisitCount();
		if (n == 1)
			return n + " view";
		else
			return n + " views";
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

			Date date = formatter.parse(getCreated());
			if (fmter.format(date).equals(sToday)) {
				return "Today " + fmt.format(date);
			}
			return fmt2.format(date);

		} catch (ParseException e) {
			return getCreated();
		}
	}

	public int getEntryId() {
		return entryId;
	}
	public void setEntryId(int entryId) {
		this.entryId = entryId;
	}
	public String getName() {
		return name;
	}
	public String getEditedName() {
		String sName = name;
		if(sName.length() >= 55) {
			sName = sName.substring(0, 53);
			sName = sName.concat("...");
		}
		return sName;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWelcomeText() {
		return welcomeText;
	}
	public void setWelcomeText(String welcomeText) {
		this.welcomeText = welcomeText;
	}
	public String getCreated() { 
		return created; 
	}
	public void setCreated(java.util.Date created) {
		this.created = created.toString(); 
	}
	public boolean isLogoPresent() {
		return logoPresent;
	}
	public void setLogoPresent(boolean logoPresent) {
		this.logoPresent = logoPresent;
	}
	public boolean isBannerPresent() {
		return bannerPresent;
	}
	public void setBannerPresent(boolean bannerPresent) {
		this.bannerPresent = bannerPresent;
	}

	public int getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(Long memberCount) {
		this.memberCount = memberCount.intValue();
	}

	public int getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}
}