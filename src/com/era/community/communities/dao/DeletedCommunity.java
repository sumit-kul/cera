package com.era.community.communities.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 *
 * @entity name="DELC"
 * 
 *  @entity.index name="01" unique="no" columns="CommunityId"  
 *  
 *  @entity.foreignkey name="01" columns="DeleterId" target="USER" ondelete="restrict"   
 *  
 */
public class DeletedCommunity extends CecAbstractEntity
{
    /**
     * @column integer not null
     */
    protected int CommunityId;
    
    /**
     * @column integer not null
     */
    protected int DeleterId ;
    
    /**
     * @column long varchar not null with default
     */
    protected String Comment = "";
       
    /*
     * Injected references.
     */
    protected DeletedCommunityDao dao;

    public String getComment()
    {
        return Comment;
    }
    
    public void setComment(String comment)
    {
        Comment = comment;
    }
    
    public int getCommunityId()
    {
        return CommunityId;
    }
    
    public void setCommunityId(int communityId)
    {
        CommunityId = communityId;
    }
    
    public int getDeleterId()
    {
        return DeleterId;
    }
    
    public void setDeleterId(int deleterId)
    {
        DeleterId = deleterId;
    }
    
    public boolean isReadAllowed(UserEntity user) throws Exception
    {
        if (user==null) return false;
        return true;
   }
    
    public boolean isWriteAllowed(UserEntity user) throws Exception
    {
        if (user==null) return false;
        if (getDeleterId()==user.getId()) return true;
        return false;
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

    public final void setDao(DeletedCommunityDao dao)
    {
        this.dao = dao;
    }
        
    public final DeletedCommunityDao getDao()
    {
        return dao;
    }
    
}
