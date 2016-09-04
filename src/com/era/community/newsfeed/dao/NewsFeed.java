package com.era.community.newsfeed.dao;

import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import support.community.application.ElementNotFoundException;
import support.community.database.QueryScroller;

import com.era.community.base.CecAbstractEntity;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.pers.dao.generated.UserEntity;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * 
 * @entity name="NWFD" 
 *
 * @entity.index name="01" unique="yes" columns="Name,AggregatorId" include="Url"
 * @entity.index name="02" unique="yes" columns="Url,AggregatorId"  
 * @entity.index name="03" unique="no" columns="AggregatorId" cluster="yes   
 * 
 * @entity.foreignkey name="01" columns="AggregatorId" target="NWFA" ondelete="cascade"  
 * 
 */
public class NewsFeed extends CecAbstractEntity
{
	/**
	 * @column integer not null
	 */
	protected int AggregatorId ;

	/**
	 * @column varchar(64) not null with default
	 */
	protected String Name ;

	/**
	 * @column varchar(255) not null with default
	 */
	protected String Url ;

	/**
	 * @column varchar(255) not null with default
	 */
	protected String Uri ;

	/**
	 * @column varchar(255) not null with default
	 */
	protected String Link = "";

	/**
	 * @column varchar(400) not null with default
	 */
	protected String Title ;

	/**
	 * @column long varchar
	 */
	protected String Description = "";

	/**
	 * @column timestamp not null with default
	 */
	protected Date DatePublished;

	/**
	 * @column varchar(255) not null with default
	 */
	protected  String Author = "";

	/**
	 * @column varchar(400) not null with default
	 */
	protected String Copyright = "";
	protected NewsFeedDao dao;     
	protected NewsFeedEntryFinder entryFinder;   
	protected NewsFeedAggregatorFinder newsFeedAggregatorFinder;
	protected CommunityFinder communityFinder;

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		return true;       
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		if (user==null) return false;        
		Community comm = getCommunityEraContext().getCurrentCommunity();
		return comm.isAdminMember(user.getId());
	}

	public void readFeed() throws Exception
	{
		SyndFeedInput input = new SyndFeedInput();
		URL feedUrl = new URL(getUrl());
		SyndFeed feed = input.build(new XmlReader(feedUrl));
		setUri(coalesce(feed.getUri(), this.getUrl()));
		setLink(coalesce(feed.getLink(), ""));
		setTitle(coalesce(feed.getTitle(), this.getUrl()));
		setDescription(coalesce(feed.getDescription(), ""));
		setAuthor(coalesce(feed.getAuthor(), ""));
		setCopyright(coalesce(feed.getCopyright(), ""));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String dt = sdf.format(now);
		Timestamp ts = Timestamp.valueOf(dt);
		setDatePublished(coalesce(feed.getPublishedDate(), ts));

		update();

		for (Object o : feed.getEntries()) {

			SyndEntry se = (SyndEntry)o;

			NewsFeedEntry e ; 
			try {
				e = this.getEntryForUri(se.getUri());
			}
			catch (ElementNotFoundException x) {
				e = entryFinder.newNewsFeedEntry();
				e.setFeedId(this.getId());
				e.setDatePublished(coalesce(se.getPublishedDate(), coalesce(se.getUpdatedDate(), ts)));    
			}

			e.setUri(coalesce(se.getUri(), se.getLink()));    
			e.setTitle(coalesce(se.getTitle(), "No Title"));    
			if (se.getDescription()!=null) {
				e.setDescriptionType(coalesce(se.getDescription().getType(), "text/plain"));    
				e.setDescription(coalesce(se.getDescription().getValue(), ""));
			}
			e.setLink(coalesce(se.getLink(), ""));    
			e.setAuthor(coalesce(se.getAuthor(), ""));    
			e.setDateRevised(coalesce(se.getUpdatedDate(), ts));    

			e.update();
		}
	}

	private String coalesce(String s, String r)
	{
		return s==null || s.trim().length()==0 ? r : s;  
	}

	private Date coalesce(Date s, Date r)
	{
		return s==null  ? r : s;  
	}

	public String getCommunityName() throws Exception
	{
		NewsFeedAggregator agg = newsFeedAggregatorFinder.getNewsFeedAggregatorForId(this.getAggregatorId());
		Community comm = communityFinder.getCommunityForId(agg.getCommunityId());
		return comm.getName();
	}

	public NewsFeedEntry getEntryForUri(String uri) throws Exception
	{
		return entryFinder.getEntryForFeedAndUri(this, uri);
	}

	public QueryScroller listEntriesByDate() throws Exception
	{
		return dao.listEntriesByDate(this);
	}

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public void delete() throws Exception
	{
		dao.delete(this);
	} 

	public final void setDao(NewsFeedDao dao)
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

	public final String getCopyright()
	{
		return Copyright;
	}

	public final void setCopyright(String copyright)
	{
		Copyright = copyright;
	}

	public final Date getDatePublished()
	{
		return DatePublished;
	}

	public final void setDatePublished(Date datePublished)
	{
		DatePublished = datePublished;
	}

	public final String getDescription()
	{
		return Description;
	}

	public final void setDescription(String description)
	{
		Description = description;
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

	public final String getUrl()
	{
		return Url;
	}

	public final void setUrl(String url)
	{
		Url = url;
	}

	public final void setEntryFinder(NewsFeedEntryFinder entryFinder)
	{
		this.entryFinder = entryFinder;
	}

	public final int getAggregatorId()
	{
		return AggregatorId;
	}

	public final void setAggregatorId(int aggregatorId)
	{
		AggregatorId = aggregatorId;
	}

	public final String getName()
	{
		return Name;
	}

	public final void setName(String name)
	{
		Name = name;
	}

	public NewsFeedAggregatorFinder getNewsFeedAggregatorFinder()
	{
		return newsFeedAggregatorFinder;
	}

	public void setNewsFeedAggregatorFinder(NewsFeedAggregatorFinder newsFeedAggregatorFinder)
	{
		this.newsFeedAggregatorFinder = newsFeedAggregatorFinder;
	}

	public CommunityFinder getCommunityFinder()
	{
		return communityFinder;
	}

	public void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}
}