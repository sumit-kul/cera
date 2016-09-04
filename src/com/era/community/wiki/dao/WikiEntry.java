package com.era.community.wiki.dao;

import java.util.Date;
import java.util.List;

import support.community.application.ElementNotFoundException;
import support.community.database.QueryScroller;

import com.era.community.blog.dao.BlogEntryLike;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.dao.generated.UserEntity;
import com.era.community.tagging.dao.TaggedEntity;

/**
 *
 *  @entity name="WENT"
 *  @entity.longtext name="Body"
 *   
 *  @entity.index name="01" unique="no" columns="WikiId"   
 *  @entity.index name="02" unique="no" columns="Title"   
 *  @entity.index name="03" unique="no" columns="PosterId"   
 *  @entity.index name="04" unique="yes" columns="EntryId,EntrySequence"   
 *  
 *  @entity.foreignkey name="01" columns="WikiId" target="WIKI" ondelete="cascade"  
 *  @entity.foreignkey name="02" columns="PosterId" target="USER" ondelete="restrict"  
 * 
 */
public class WikiEntry extends TaggedEntity
{
	/**
	 * @column integer not null
	 */
	protected int WikiId;

	/**
	 * @column integer not null
	 */
	protected int EntryId;

	/**
	 * Indicates the sequence of versions within an EntryId.
	 * The current entry will always have Integer.MAX_VALUE in this field.
	 * 
	 * @column integer not null with default
	 */
	protected int EntrySequence ;

	/**
	 * @column varchar(150) not null with default
	 */
	protected String Title;

	/**
	 * @column timestamp not null with default
	 */
	protected Date DatePosted = new Date();

	/**
	 * @column integer not null
	 */
	protected int PosterId ;

	/**
	 * @column varchar(500)
	 */
	protected String ReasonForUpdate;

	protected int Visitors;

	protected Date LastVisitorsTime = new Date();

	/*
	 * Injected references.
	 */
	protected WikiEntryDao dao;
	protected WikiFinder wikiFinder;
	protected UserFinder userFinder;
	protected WikiEntryCommentDao wikiEntryCommentDao;
	protected WikiEntryLikeFinder wikiEntryLikeFinder;

	public List getItemsSince(Date date) throws Exception
	{
		return dao.getItemsSince(this, date);
	}


	public Date getLatestPostDate() throws Exception
	{
		return dao.getLatestPostDate(this);
	}

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public void delete() throws Exception
	{
		super.delete();
		dao.delete(this);
	}     

	public void deleteWikiEntryWithAllVersions(int entryId) throws Exception
	{
		super.delete();
		dao.deleteWikiEntryWithAllVersions(entryId);
	}  

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		if (getWiki().getCommunity().isPrivate()) {
			if (user==null) return false;
			return getWiki().getCommunity().isMember(user.getId());
		}
		return true;
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		if (user==null) return false;
		return getWiki().getCommunity().isMember(user.getId());
	}
	
	public boolean isAlreadyLike(int userId) throws Exception {
		try {
			WikiEntryLike wikiEntryLike = wikiEntryLikeFinder.getLikeForWikiEntryAndUser(this.getId(), userId);
			return true;
		}
		catch (ElementNotFoundException e) {
			return false;
		}
	}

	public Wiki getWiki() throws Exception
	{
		return wikiFinder.getWikiForId(getWikiId());
	}

	public String getBody() throws Exception
	{
		return dao.getBody(this);
	}

	public void setBody(String value) throws Exception
	{
		dao.setBody(this, value);
	}  

	public Date getDatePosted()
	{
		return DatePosted;
	}

	public void setDatePosted(Date datePosted)
	{
		DatePosted = datePosted;
	}

	public int getPosterId()
	{
		return PosterId;
	}

	public User getPoster() throws Exception
	{
		return userFinder.getUserEntity(getPosterId());
	}

	public void setPosterId(int posterId)
	{
		PosterId = posterId;
	}

	public String getTitle()
	{
		return Title;
	}

	public void setTitle(String title)
	{
		Title = title;
	}

	public int getWikiId()
	{
		return WikiId;
	}

	public void setWikiId(int wikiId)
	{
		WikiId = wikiId;
	}    

	public void setDao(WikiEntryDao dao)
	{
		this.dao = dao;
	}

	public int getEntryId()
	{
		return EntryId;
	}

	public void setEntryId(int entryId)
	{
		EntryId = entryId;
	}

	public int getEntrySequence()
	{
		return EntrySequence;
	}

	public void setEntrySequence(int entrySequence)
	{
		EntrySequence = entrySequence;
	}

	public QueryScroller listHistoryByEditDate(boolean includeLatest) throws Exception {
		return dao.listHistoryByEditDate(this, includeLatest);
	}
	
	public QueryScroller listComments() throws Exception {
        return wikiEntryCommentDao.listCommentsForWikiEntry(this);
    }

	public final void setWikiFinder(WikiFinder wikiFinder)
	{
		this.wikiFinder = wikiFinder;
	}

	public String getReasonForUpdate()
	{
		return ReasonForUpdate;
	}

	public void setReasonForUpdate(String reasonForUpdate)
	{
		ReasonForUpdate = reasonForUpdate;
	}

	 public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public int getVisitors() {
		return Visitors;
	}

	public void setVisitors(int visitors) {
		Visitors = visitors;
	}

	public Date getLastVisitorsTime() {
		return LastVisitorsTime;
	}

	public void setLastVisitorsTime(Date lastVisitorsTime) {
		LastVisitorsTime = lastVisitorsTime;
	}

	public void setWikiEntryCommentDao(WikiEntryCommentDao wikiEntryCommentDao) {
		this.wikiEntryCommentDao = wikiEntryCommentDao;
	}


	public void setWikiEntryLikeFinder(WikiEntryLikeFinder wikiEntryLikeFinder) {
		this.wikiEntryLikeFinder = wikiEntryLikeFinder;
	}
}