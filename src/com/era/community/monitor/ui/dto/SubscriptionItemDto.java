package com.era.community.monitor.ui.dto; 

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SubscriptionItemDto
{
    private String ItemUrl;
    private String ItemTitle;
    private Integer ItemId;
    private Date ItemDate;
    private String ItemType;
    private Integer AuthorId;
    private String AuthorName;
    private Integer CommunityId;
    private String CommunityName;
    
    final DateFormat DATE_FORMAT = new SimpleDateFormat("d MMM yyyy kk:mm");
    
    public final String getDateAndTitle()
    {
        if (this.getItemDate()!=null)
            return " "+DATE_FORMAT.format(this.getItemDate()) + " \n"+getItemTitle()+"\n";
        else
            return "";
    }
    
    public String getDateEntered()
    {
        if (this.getItemDate()!=null)
            return " on "+DATE_FORMAT.format(this.getItemDate());
        else
            return "";
    }

	public String getItemUrl() {
		return ItemUrl;
	}

	public void setItemUrl(String itemUrl) {
		ItemUrl = itemUrl;
	}

	public String getItemTitle() {
		return ItemTitle;
	}

	public void setItemTitle(String itemTitle) {
		ItemTitle = itemTitle;
	}

	public Integer getItemId() {
		return ItemId;
	}

	public void setItemId(Integer itemId) {
		ItemId = itemId;
	}

	public Date getItemDate() {
		return ItemDate;
	}

	public void setItemDate(Date itemDate) {
		ItemDate = itemDate;
	}

	public String getItemType() {
		return ItemType;
	}

	public void setItemType(String itemType) {
		ItemType = itemType;
	}

	public Integer getAuthorId() {
		return AuthorId;
	}

	public void setAuthorId(Integer authorId) {
		AuthorId = authorId;
	}

	public String getAuthorName() {
		return AuthorName;
	}

	public void setAuthorName(String authorName) {
		AuthorName = authorName;
	}

	public Integer getCommunityId() {
		return CommunityId;
	}

	public void setCommunityId(Integer communityId) {
		CommunityId = communityId;
	}

	public String getCommunityName() {
		return CommunityName;
	}

	public void setCommunityName(String communityName) {
		CommunityName = communityName;
	}
}