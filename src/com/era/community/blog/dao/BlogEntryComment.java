package com.era.community.blog.dao;

import java.util.Date;

import support.community.application.ElementNotFoundException;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 *
 * @entity name="BCOM"
 * 
 *  @entity.index name="01" unique="no" columns="BlogEntryId"   
 *  
  * @entity.foreignkey name="01" columns="BlogEntryId" target="BENT" ondelete="cascade"  
 */
public class BlogEntryComment extends CecAbstractEntity
{

    /**
     * @column integer not null
     */
    protected int BlogEntryId;
        
    /**
     * @column long varchar not null with default
     */
    protected String Comment = "";
        
    /**
     * @column timestamp not null with default
     */
    protected Date DatePosted;

    /**
     * @column integer not null
     */
    protected int PosterId ;
    
    /*
     * Injected references.
     */
    protected BlogEntryCommentDao dao;
    protected BlogEntryCommentLikeFinder blogEntryCommentLikeFinder;

    public String getComment()
    {
        return Comment;
    }
    
    public void setComment(String comment)
    {
        Comment = comment;
    }
    
    public Date getDatePosted()
    {
        return DatePosted;
    }
    
    public void setDatePosted(Date datePosted)
    {
        DatePosted = datePosted;
    }
    
    public int getBlogEntryId()
    {
        return BlogEntryId;
    }
    
    public void setBlogEntryId(int blogEntryId)
    {
        BlogEntryId = blogEntryId;
    }
    
    public int getPosterId()
    {
        return PosterId;
    }
    
    public void setPosterId(int posterId)
    {
        PosterId = posterId;
    }
    
    public boolean isReadAllowed(UserEntity user) throws Exception
    {
        return true;
   }
    
    public boolean isWriteAllowed(UserEntity user) throws Exception
    {
        if (user==null) return false;
        if (getPosterId()==user.getId()) return true;
        return false;
    }
    
    public boolean isAlreadyLike(int userId) throws Exception {
		try {
			BlogEntryCommentLike blogEntryCommentLike = blogEntryCommentLikeFinder.getLikeForBlogEntryCommentAndUser(this.getId(), userId);
			return true;
		}
		catch (ElementNotFoundException e) {
			return false;
		}
	}
    
    /**
     * Update or insert this entity in the database.
     */
    public void update() throws Exception
    {
       dao.store(this); 
    }

    /** 
     *  Delete this entity from the database.
     */
    public void delete() throws Exception
    {
        dao.delete(this);
    } 

    public final void setDao(BlogEntryCommentDao dao)
    {
        this.dao = dao;
    }
        
    public final BlogEntryCommentDao getDao()
    {
        return dao;
    }

	public void setBlogEntryCommentLikeFinder(
			BlogEntryCommentLikeFinder blogEntryCommentLikeFinder) {
		this.blogEntryCommentLikeFinder = blogEntryCommentLikeFinder;
	}
}