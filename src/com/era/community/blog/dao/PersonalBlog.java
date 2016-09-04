package com.era.community.blog.dao;

import java.util.Date;

import support.community.database.QueryScroller;

import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;
import com.era.community.pers.dao.generated.UserEntity;
import com.era.community.tagging.dao.TaggedEntity;

public class PersonalBlog extends TaggedEntity
{
	/**
	 * @column integer not null
	 */
	private int UserId;

	/**
	 * @column varchar(150) not null with default
	 */
	protected String Name;

	/**
	 * @column varchar(255) not null with default
	 */
	protected String Description;

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
	protected PersonalBlogDao dao;
	protected UserFinder userFinder;

	public int getUserId()
	{
		return UserId;
	}

	public void setUserId(int userId)
	{
		UserId = userId;
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

	public void update() throws Exception
	{
		dao.store(this); 
	}

	public void delete() throws Exception
	{    	
		dao.delete(this);
	} 

	public boolean isReadAllowed(UserEntity user) throws Exception
	{
		return true;
	}

	/* Need to override method in TaggedEntity as blog entries may not be 
	 * associated with a community, or may be associated with more 
	 * than one. */
	/*public void setTags(String tagString) throws Exception
	{		
		CommunityEraContext context = getCommunityEraContext();
		clearTagsForUser(context.getCurrentUser().getId());
		StringTokenizer st = new StringTokenizer(tagString, " ");
		while (st.hasMoreTokens()) {
			String tag = st.nextToken().trim().toLowerCase();
			 Create new tag with no community set 
			Tag newTag = tagFinder.newTag();						
			newTag.setTagText(tag);			
			newTag.setPosterId(context.getCurrentUser().getId());
			newTag.setParentId(getId());
			newTag.setParentType(this.getClass().getSimpleName());
			newTag.update();	
		}			
	}*/

	public Date getLatestPostDate() throws Exception
	{
		return dao.getLatestPostDate(this);
	}

	public User getUser() throws Exception
	{
		return userFinder.getUserEntity(getUserId());
	}

	public boolean isWriteAllowed(UserEntity user) throws Exception
	{
		return true;
	}

	public final void setDao(PersonalBlogDao dao)
	{
		this.dao = dao;
	}

	public final PersonalBlogDao getDao()
	{
		return dao;
	}

	public QueryScroller listPersonalBlogEntries() throws Exception {
		return dao.listPersonalBlogEntries(this);
	}

	public final void setUserFinder(UserFinder userFinder)
	{
		this.userFinder = userFinder;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
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
}