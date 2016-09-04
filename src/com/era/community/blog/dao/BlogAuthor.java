package com.era.community.blog.dao;

import com.era.community.base.CecBaseEntity;
import com.era.community.pers.dao.generated.UserEntity;

public class BlogAuthor extends CecBaseEntity
{
	/**
	 * @column int 
	 */
	private int UserId;

	/**
	 * @column int 
	 */    
	private int BlogId;
	
	/**
	 * @column int 
	 */    
	private int PersonalBlogId;
	
	/**
	 * @column int 
	 */    
	private int Active;
	
	/**
	 * @column int 
	 */    
	private int Role;

	protected BlogAuthorDao dao;

	public void update() throws Exception
	{
		dao.store(this); 
	}
	
	public void delete() throws Exception
	{
		dao.delete(this);
	}

	public boolean isReadAllowed(UserEntity user)
	{
		return true;
	}

	public boolean isWriteAllowed(UserEntity user)
	{
		return true;        
	}

	public int getUserId()
	{
		return UserId;
	}

	public void setUserId(int userId)
	{
		UserId = userId;
	}

	public void setDao(BlogAuthorDao dao)
	{
		this.dao = dao;
	}

	public int getBlogId() {
		return BlogId;
	}

	public void setBlogId(int blogId) {
		BlogId = blogId;
	}

	public int getActive() {
		return Active;
	}

	public void setActive(int active) {
		Active = active;
	}

	public int getPersonalBlogId() {
		return PersonalBlogId;
	}

	public void setPersonalBlogId(int personalBlogId) {
		PersonalBlogId = personalBlogId;
	}

	public int getRole() {
		return Role;
	}

	public void setRole(int role) {
		Role = role;
	}
}