package com.era.community.assignment.dao;

import java.util.Date;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * @entity name="ASNMTCOM"
 */
public class AssignmentComment extends CecAbstractEntity
{

    /**
     * @column integer not null
     */
    protected int AssignmentId;
        
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
    protected AssignmentCommentDao dao;

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
    
    public int getAssignmentId()
    {
        return AssignmentId;
    }
    
    public void setAssignmentId(int assignmentId)
    {
    	AssignmentId = assignmentId;
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

    public final void setDao(AssignmentCommentDao dao)
    {
        this.dao = dao;
    }
        
    public final AssignmentCommentDao getDao()
    {
        return dao;
    }
}