package com.era.community.blog.dao;

import java.util.Date;
import java.util.List;

import support.community.database.QueryScroller;

import com.era.community.base.CommunityEraContext;
import com.era.community.communities.dao.Community;
import com.era.community.communities.dao.CommunityFinder;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.generated.UserEntity;
import com.era.community.tagging.dao.TaggedEntity;
import com.era.community.tagging.ui.dto.TagDto;

/**
 * A CommunityBlog is the collection of blog entries published to a particular community
 * 
 * @entity name="BCON" 
 *
 * @entity.index name="01" unique="no" columns="CommunityId"   
 * 
 * @entity.foreignkey name="01" columns="CommunityId" target="COMM" ondelete="cascade"  
 */
public class CommunityBlog extends TaggedEntity
{
	/**
	 * @column integer not null
	 */
	private int CommunityId;

	/**
	 * @column varchar(150) not null with default
	 */
	protected String Name;

	/**
	 * @column char(1) not null with default
	 */
	protected boolean Inactive = false;

	protected boolean AllowCoEdit = false;

	protected boolean AllowComments = true;

	protected boolean ModerateComments = false;

	protected boolean DefaultAllowComments = true;

	protected int DefaultCommentDays;

	/*
	 * Injected references.
	 */
	protected CommunityBlogDao dao;
	protected CommunityFinder communityFinder;

	public int getCommunityId()
	{
		return CommunityId;
	}

	public void setCommunityId(int communityId)
	{
		CommunityId = communityId;
	}

	public boolean isInactive()
	{
		return Inactive;
	}

	public void setInactive(boolean inactive)
	{
		Inactive = inactive;
	}

	public String getName()
	{
		return Name;
	}

	public void setName(String name)
	{
		Name = name;
	}

	public List getItemsSince(Date date) throws Exception
	{
		return dao.getItemsSince(this, date);
	}

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public void delete() throws Exception
	{    	
		dao.delete(this);
	} 
	
	public String[] getTagsAsArray() throws Exception 
	{
		CommunityEraContext context = getCommunityEraContext();
		List tagList;
			tagList = tagFinder.getTagsForParentId(getId());
		String[] retVal = new String[tagList.size()];
		for (int i=0; i<tagList.size(); i++) {
			retVal[i] = ((TagDto) tagList.get(i)).getTagText();
		}
		return retVal;
	}

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		return true;
	}

	public Community getCommunity() throws Exception
	{
		return communityFinder.getCommunityForId(getCommunityId());
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		return true;
	}

	public final void setDao(CommunityBlogDao dao)
	{
		this.dao = dao;
	}

	public final CommunityBlogDao getDao()
	{
		return dao;
	}

	public QueryScroller listBlogEntries() throws Exception {
		return dao.listBlogEntries(this);
	}

	public QueryScroller listBlogEntriesForUser(User user, String sortBy) throws Exception {
		return dao.listBlogEntriesForUser(this, user, sortBy);
	}

	public QueryScroller listBlogEntriesForUserId(int userId, String sortBy) throws Exception {
		return dao.listBlogEntriesForUserId(this, userId, sortBy);
	}

	public final void setCommunityFinder(CommunityFinder communityFinder)
	{
		this.communityFinder = communityFinder;
	}

	public Date getLatestPostDate() throws Exception
	{
		return dao.getLatestPostDate(this);
	}

	public boolean isAllowCoEdit() {
		return AllowCoEdit;
	}

	public void setAllowCoEdit(boolean allowCoEdit) {
		AllowCoEdit = allowCoEdit;
	}

	public boolean isAllowComments() {
		return AllowComments;
	}

	public void setAllowComments(boolean allowComments) {
		AllowComments = allowComments;
	}

	public boolean isModerateComments() {
		return ModerateComments;
	}

	public void setModerateComments(boolean moderateComments) {
		ModerateComments = moderateComments;
	}

	public boolean isDefaultAllowComments() {
		return DefaultAllowComments;
	}

	public void setDefaultAllowComments(boolean defaultAllowComments) {
		DefaultAllowComments = defaultAllowComments;
	}

	public int getDefaultCommentDays() {
		return DefaultCommentDays;
	}

	public void setDefaultCommentDays(int defaultCommentDays) {
		DefaultCommentDays = defaultCommentDays;
	}

	public CommunityFinder getCommunityFinder() {
		return communityFinder;
	}
}