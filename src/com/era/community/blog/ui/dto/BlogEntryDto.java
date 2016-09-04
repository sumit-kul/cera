package com.era.community.blog.ui.dto; 

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;

import support.community.util.StringHelper;

public class BlogEntryDto extends com.era.community.blog.dao.generated.BlogEntryEntity 
{     
    private int communityId;
    private String displayName;
    private String photoPresent;
    private String logoPresent;
    private String bType;
    private String tags;
    private String communityNames;
    
    public String getTags()
    {
        return tags;
    }

    public void setTags(String tags)
    {
        this.tags = tags;
    }
          
    public String getDisplayName()
    {
        return displayName;
    }
    
    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }
    
    public String getDisplayBody()
	{
		if ( (this.getBody()==null) || (this.getBody().length()==0)) return "";

		String sBody = this.getBody();
		sBody = Jsoup.parse(sBody).text();

		if (sBody.length() >= 340) {
			sBody = sBody.substring(0, 337).concat("...");
		}
		return sBody;
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
    
    // No setter here as it's a read only property
    public String getFormattedBody() throws Exception
    {
        return StringHelper.formatForDisplay(getBody());
    }

    public final int getCommunityId()
    {
        return communityId;
    }

    public final void setCommunityId(int communityId)
    {
        this.communityId = communityId;
    }

	public String getPhotoPresent() {
		return photoPresent;
	}

	public void setPhotoPresent(String photoPresent) {
		this.photoPresent = photoPresent;
	}

	public String getCommunityNames() {
		return communityNames;
	}

	public void setCommunityNames(String communityNames) {
		this.communityNames = communityNames;
	}

	public String getBType() {
		return bType;
	}

	public void setBType(String type) {
		bType = type;
	}

	public String getLogoPresent() {
		return logoPresent;
	}

	public void setLogoPresent(String logoPresent) {
		this.logoPresent = logoPresent;
	}
}