package com.era.community.blog.dao;

import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import support.community.application.ElementNotFoundException;
import support.community.database.QueryScroller;

import com.era.community.base.CommunityEraContext;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.dao.generated.UserEntity;
import com.era.community.tagging.dao.Tag;
import com.era.community.tagging.dao.TaggedEntity;

/**
 * 
 *  @entity name="BENT" 
 *
 *  @entity.blob name="File1"
 *  @entity.blob name="File2"
 *  @entity.blob name="File3"
 * 
 *  @entity.index name="01" unique="no" columns="BlogId"   
 *  @entity.index name="02" unique="no" columns="PosterId"   
 * 
 * @entity.foreignkey name="01" columns="BlogId" target="BLOG" ondelete="cascade"
 * @entity.foreignkey name="02" columns="PosterId" target="USER" ondelete="cascade"    
 */
public class BlogEntry extends TaggedEntity
{

	/**
	 * @column integer not null
	 */
	protected int CommunityBlogId;

	protected int PersonalBlogId;

	/**
	 * @column varchar(150) not null with default
	 */
	private String Title;

	/**
	 * @column integer not null
	 */
	private int PosterId;

	/**
	 * @column long varchar not null with default
	 */
	protected String Body;

	/**
	 * @column timestamp not null with default
	 */
	protected Date DatePosted;

	protected int Visitors;

	protected Date LastVisitorsTime;

	/*
	 * Injected references.
	 */
	protected BlogEntryDao dao;
	protected CommunityBlogDao communityBlogDao;
	protected CommunityBlogFinder communityBlogFinder;
	protected PersonalBlogDao personalBlogDao;
	protected BlogEntryCommentDao blogEntryCommentDao;
	protected UserFinder userFinder;
	protected BlogEntryLikeFinder blogEntryLikeFinder;

	public String getBody()
	{
		return Body;
	}

	public void setBody(String body)
	{
		Body = body;
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

	public BlogEntryDao getDao()
	{
		return dao;
	}

	public void setDao(BlogEntryDao dao)
	{
		this.dao = dao;
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

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		if (user == null) return false;
		if (user.getId() == getPosterId()) return true;
		return false;
	}

	public CommunityBlog getCommunityBlog() throws Exception
	{
		return communityBlogFinder.getCommunityBlogForId(getCommunityBlogId());
	}

	public QueryScroller listComments() throws Exception {
		return blogEntryCommentDao.listCommentsForBlogEntry(this);
	}

	public void setTags(String tagString) throws Exception
	{		

		CommunityEraContext context = getCommunityEraContext();

		clearTagsForUser(context.getCurrentUser().getId());
		/* Parse tag string */
		StringTokenizer st = new StringTokenizer(tagString, " ");
		while (st.hasMoreTokens()) {
			String tag = st.nextToken().trim().toLowerCase();
			Tag newTag = tagFinder.newTag();						
			newTag.setTagText(tag);			
			newTag.setPosterId(context.getCurrentUser().getId());
			if (this.getCommunityBlogId() > 0) {
				CommunityBlog cb = communityBlogDao.getCommunityBlogForId(this.getCommunityBlogId());
				newTag.setCommunityId(cb.getCommunityId());
			}
			newTag.setParentId(getId());
			newTag.setParentType(this.getClass().getSimpleName());
			newTag.update();
		}
	}

	public User getPoster() throws Exception
	{
		return userFinder.getUserEntity(getPosterId());
	}

	public boolean isAlreadyLike(int userId) throws Exception {
		try {
			BlogEntryLike blogEntryLike = blogEntryLikeFinder.getLikeForBlogEntryAndUser(this.getId(), userId);
			return true;
		}
		catch (ElementNotFoundException e) {
			return false;
		}
	}

	public List getTagsForBlogEntryByPopularity(int max) throws Exception
	{
		return tagFinder.getTagsForParentTypeByPopularity(this.getId(), 0, max, "BlogEntry"); 
	}

	public int getLikeCountForBlogEntry() throws Exception {
		return blogEntryLikeFinder.getLikeCountForBlogEntry(this.getId());
	}

	public void setBlogEntryCommentDao(BlogEntryCommentDao blogEntryCommentDao)
	{
		this.blogEntryCommentDao = blogEntryCommentDao;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public void setBlogEntryLikeFinder(BlogEntryLikeFinder blogEntryLikeFinder) {
		this.blogEntryLikeFinder = blogEntryLikeFinder;
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

	public void setCommunityBlogDao(CommunityBlogDao communityBlogDao) {
		this.communityBlogDao = communityBlogDao;
	}

	public void setPersonalBlogDao(PersonalBlogDao personalBlogDao) {
		this.personalBlogDao = personalBlogDao;
	}

	public int getCommunityBlogId() {
		return CommunityBlogId;
	}

	public void setCommunityBlogId(int communityBlogId) {
		CommunityBlogId = communityBlogId;
	}

	public int getPersonalBlogId() {
		return PersonalBlogId;
	}

	public void setPersonalBlogId(int personalBlogId) {
		PersonalBlogId = personalBlogId;
	}

	public void setCommunityBlogFinder(CommunityBlogFinder communityBlogFinder) {
		this.communityBlogFinder = communityBlogFinder;
	}
}