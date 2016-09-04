package com.era.community.wiki.dao;

import java.util.Date;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 *
 * @entity name="WECOM"
 */
public class WikiEntryComment extends CecAbstractEntity
{

    /**
     * @column integer not null
     */
    protected int WikiEntryId;
        
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
    protected WikiEntryCommentDao dao;

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
    
    public int getWikiEntryId()
    {
        return WikiEntryId;
    }
    
    public void setWikiEntryId(int wikiEntryId)
    {
        WikiEntryId = wikiEntryId;
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
    
    public void update() throws Exception
    {
       dao.store(this); 
    }

    public void delete() throws Exception
    {
        dao.delete(this);
    } 

    public final void setDao(WikiEntryCommentDao dao)
    {
        this.dao = dao;
    }
        
    public final WikiEntryCommentDao getDao()
    {
        return dao;
    }
}