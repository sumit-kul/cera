package com.era.community.pers.ui.dto; 

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.era.community.pers.dao.generated.NotificationEntity;

public class NotificationDto extends NotificationEntity
{
	private int posterId;
	private String name;
	private String itemTitle;
	private String firstName;
	private String lastName;
	private boolean photoPresent;
	private boolean logoPresent;
	private String datePosted;

	public String getDatePostedOn()
	{
		SimpleDateFormat fmter = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat fmt = new SimpleDateFormat("HH:mm a");
		SimpleDateFormat fmt2 = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
		Date today = new Date();
		String sToday = fmter.format(today);

		try {

			Date date = formatter.parse(getDatePosted());
			if (date.compareTo(today) == 0) {
				return "Today " + fmt.format(date);
			}
			return fmt2.format(date);

		} catch (ParseException e) {
			return getDatePosted();
		}
	}

	public String getItemTitleDisplay() throws Exception
	{
		String itemTitle = "";

		if (getCommunityId() == getItemId()) {
			itemTitle += "<span>"+ getFirstName()+ " " + getLastName() + "</span> published a new community <span>" + getName()+"</span>";
		} else if ("MembershipPending".equalsIgnoreCase(getItemType())) {
			itemTitle += "<span>"+ getFirstName()+ " " + getLastName() + "</span> requested to join community: <span>" + getName()+"</span>";
		} else if ("Document".equalsIgnoreCase(getItemType())) {
			itemTitle += "<span>"+ getFirstName()+ " " + getLastName() + "</span> added the file to the community <span>" + getName()+"</span>: <span>" + getItemTitle() + "</span>";
		} else if ("BlogEntry".equalsIgnoreCase(getItemType())) {
			itemTitle += "<span>"+ getFirstName()+ " " + getLastName() + "</span> posted in the community <span style='color: #9597bd;'>" + getName()+"</span>: <span>" + getItemTitle() + "</span>";			
		} else if ("ForumTopic".equalsIgnoreCase(getItemType())) {
			itemTitle += "<span>"+ getFirstName()+ " " + getLastName() + "</span> started topic in the community <span>" + getName()+"</span>: <span>" + getItemTitle() + "</span>";			
		} else if ("WikiEntry".equalsIgnoreCase(getItemType())) {
			itemTitle += "<span>"+ getFirstName()+ " " + getLastName() + "</span> added wikipage in the community <span>" + getName()+"</span>: <span>" + getItemTitle() + "</span>";			
		} else if ("Event".equalsIgnoreCase(getItemType())) {
			itemTitle += "<span>"+ getFirstName()+ " " + getLastName() + "</span> published event in the community <span>" + getName()+"</span>: <span>" + getItemTitle() + "</span>";			
		} 
		return itemTitle;
	}


	public String getDisplayItemType() throws Exception
	{
		if (this.getItemType().contains("Topic"))
			return "Forum";
		else

			if (this.getItemType().contains("Response"))
				return "Forum";
			else

				if (this.getItemType().contains("Wiki"))
					return "Wiki";

		if (this.getItemType().contains("Blog"))
			return "Blog";

		if (this.getItemType().contains("Member") | this.getItemType().contains("Community Admin") )
			return "Member";


		return this.getItemType();
	}


	public String getItemUrl() throws Exception
	{
		if (this.getItemType().contains("Community")) {

			return ("cid/"+this.getCommunityId()+"/home.do");
		} 
		if (this.getItemType().contains("Event")) {

			return ("cid/"+this.getCommunityId()+"/event/showEventEntry.do?id="+this.getItemId());
		}  


		if (this.getItemType().contains("Forum")) {
			return ("cid/"+this.getCommunityId()+"/forum/forumThread.do?id="+this.getItemId());
		}


		if (this.getItemType().contains("Wiki")) {

			return ("cid/"+this.getCommunityId()+"/wiki/wikiDisplay.do?id="+this.getItemId());
		}


		if (this.getItemType().endsWith("BlogEntry")) {
			return ("cid/"+this.getCommunityId()+"/blog/blogEntry.do?id="+this.getItemId());
		}


		if (this.getItemType().endsWith("Document")) {
			return ("cid/"+this.getCommunityId()+"/library/documentdisplay.do?id="+this.getItemId());

		}

		if (this.getItemType().contains("Member")) {
			return ("cid/"+this.getCommunityId()+"/members/member-display.do?id="+this.getItemId());

		}


		return this.getItemType();
	}

	public boolean isPhotoPresent() {
		return photoPresent;
	}

	public void setPhotoPresent(boolean photoPresent) {
		this.photoPresent = photoPresent;
	}

	public boolean isLogoPresent() {
		return logoPresent;
	}

	public void setLogoPresent(boolean logoPresent) {
		this.logoPresent = logoPresent;
	}

	public int getPosterId() {
		return posterId;
	}

	public void setPosterId(int posterId) {
		this.posterId = posterId;
	}

	public String getDatePosted() {
		return datePosted;
	}

	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}
}