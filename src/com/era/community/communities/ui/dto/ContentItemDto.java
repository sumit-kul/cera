package com.era.community.communities.ui.dto; 

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ContentItemDto 
{
    //private ForumItemFinder forumItemFinder;
    private int id;
    private int itemId;
    private String itemType;
    private String itemTitle;
    private String datePosted;
    private int communityId;
    private String FirstName;
    private String LastName;

    public final int getItemId()
    {
        return itemId;
    }

    public final void setItemId(int itemId)
    {
        this.itemId = itemId;
    }

    public final String getItemType()
    {
        return itemType;
    }

    public final void setItemType(String itemType)
    {
        this.itemType = itemType;
    }

    public final void setItemTitle(String itemTitle)
    {
        this.itemTitle = itemTitle;
    }

    public final String getDatePosted()
    {
        return datePosted;
    }
    
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
			if (fmter.format(date).equals(sToday)) {
				return "Today " + fmt.format(date);
			}
			return fmt2.format(date);

		} catch (ParseException e) {
			return getDatePosted();
		}
    }

    public final void setDatePosted(Date datePosted)
    {
        this.datePosted = datePosted.toString();
    }

    public final int getCommunityId()
    {
        return communityId;
    }

    public final void setCommunityId(int communityId)
    {
        this.communityId = communityId;
    }
    

    public String getItemTitle() throws Exception
    {
        return itemTitle;
        
        /*
        String responseTitle = itemTitle;
        if (this.getItemType().contains("Response")) {
            ForumItem topic = forumItemFinder.getForumItemForId(this.getItemId());
            return topic.getSubject() + " [" + responseTitle  + "]";
        }
        else
            return itemTitle;
            
            */
            
            
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

    /*public final ForumItemFinder getForumItemFinder()
    {
        return forumItemFinder;
    }

    public final void setForumItemFinder(ForumItemFinder forumItemFinder)
    {
        this.forumItemFinder = forumItemFinder;
    }*/
    
	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}