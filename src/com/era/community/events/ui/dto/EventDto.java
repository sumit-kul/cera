package com.era.community.events.ui.dto; 

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class EventDto extends com.era.community.events.dao.generated.EventEntity 
{
	private String displayName;
	private boolean communityBannerPresent;
	private int communityId;
	
	public boolean isNew()
	{
		if (getCreated() == null) return false;
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DATE, -14);
		Date fourteenDaysAgo = cal.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
		Date dt = formatter.parse(getCreated());
		
		if (dt.before(new Date())) {
			return false;
		} else if (dt.after(fourteenDaysAgo)) {
			return true; 
		}
		else {
			return false;
		}
		} catch (ParseException e) {
    		return false;
    	}
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public boolean isCommunityBannerPresent() {
		return communityBannerPresent;
	}

	public void setCommunityBannerPresent(boolean communityBannerPresent) {
		this.communityBannerPresent = communityBannerPresent;
	}

	public int getCommunityId() {
		return communityId;
	}

	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}
}