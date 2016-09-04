package com.era.community.newsfeed.dao;

import java.util.Date;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * 
 * @entity name="NWFE" 
 *
 * @entity.index name="01" unique="no" columns="FeedId" cluster="yes"
 * @entity.index name="02" unique="yes" columns="FeedId,Uri"  
 * 
 * @entity.foreignkey name="01" columns="FeedId" target="NWFD" ondelete="cascade"  
 */
public class NewsFeedEntry extends CecAbstractEntity
{
	/**
	 * @column int not null
	 */
	protected int FeedId ;

	/**
	 * @column varchar(255) not null 
	 */
	protected String Uri ;

	/**
	 * @column varchar(255) not null with default
	 */
	protected String Link = "";

	/**
	 * @column varchar(400) not null 
	 */
	protected String Title ;

	/**
	 * @column varchar(100) not null with default
	 */
	protected String DescriptionType = "text/plain";

	/**
	 * @column long varchar with default
	 */
	protected String Description = "";

	/**
	 * @column timestamp not null 
	 */
	protected Date DatePublished;

	/**
	 * @column timestamp not null 
	 */
	protected Date DateRevised;

	/**
	 * @column varchar(255) not null with default
	 */
	protected  String Author = "";

	protected NewsFeedEntryDao dao;     
	protected NewsFeedFinder newsFeedFinder;

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		if (user==null) return false;
		return getFeed().isReadAllowed(user);
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		if (user==null) return false;        
		return getFeed().isWriteAllowed(user);
	}

	public NewsFeed getFeed() throws Exception
	{
		return newsFeedFinder.getNewsFeedForId(getFeedId());
	}

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public void delete() throws Exception
	{
		dao.delete(this);
	} 

	public final void setDao(NewsFeedEntryDao dao)
	{
		this.dao = dao;
	}

	public final String getAuthor()
	{
		return Author;
	}

	public final void setAuthor(String author)
	{
		Author = author;
	}

	public final Date getDatePublished()
	{
		return DatePublished;
	}

	public final void setDatePublished(Date datePublished)
	{
		DatePublished = datePublished;
	}

	public final Date getDateRevised()
	{
		return DateRevised;
	}

	public final void setDateRevised(Date dateRevised)
	{
		DateRevised = dateRevised;
	}

	public final String getDescription()
	{
		return Description;
	}

	public final void setDescription(String description)
	{
		Description = description;
	}

	public final String getDescriptionType()
	{
		return DescriptionType;
	}

	public final void setDescriptionType(String descriptionType)
	{
		DescriptionType = descriptionType;
	}

	public final int getFeedId()
	{
		return FeedId;
	}

	public final void setFeedId(int feedId)
	{
		FeedId = feedId;
	}

	public final String getLink()
	{
		return Link;
	}

	public final void setLink(String link)
	{
		Link = link;
	}

	public final String getTitle()
	{
		return Title;
	}

	public final void setTitle(String title)
	{
		Title = title;
	}

	public final String getUri()
	{
		return Uri;
	}

	public final void setUri(String uri)
	{
		Uri = uri;
	}

	public final void setNewsFeedFinder(NewsFeedFinder newsFeedFinder)
	{
		this.newsFeedFinder = newsFeedFinder;
	}



}
