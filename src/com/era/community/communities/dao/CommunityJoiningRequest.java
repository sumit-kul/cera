package com.era.community.communities.dao;

import java.util.Date;

import com.era.community.base.CecBaseEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * 
 * @entity name="CJRQ" 
 *
 * @entity.index name="01" unique="no" columns="CommunityId,UserId" cluster="yes"  
 * @entity.foreignkey name="01" columns="CommunityId" target="COMM" ondelete="cascade"  
 * @entity.foreignkey name="02" columns="UserId" target="USER" ondelete="cascade" 
 * @entity.longtext name="OptionalComment" 
 * 
 */
public class CommunityJoiningRequest extends CecBaseEntity
{
    /**
     * @column integer not null
     */
    protected int CommunityId;
    
    /**
     * @column integer not null
     */
    protected int UserId;
    
    /**
     * @column long varchar
     */
    protected String OptionalComment;
    
     /**
     * @column timestamp not null with default
     */
    protected Date RequestDate;
        
    /**
     * @column integer not null with default
     */
    protected int Status = 0;
    
    /**
     * @column timestamp not null with default
     */
    protected Date ApproveRejectDate;
    
    public static final int STATUS_UNAPPROVED = 0;
    public static final int STATUS_ACCEPTED = 1;
    public static final int STATUS_REJECTED = 2;
       
    /*
     * Injected  references.
     */
    protected CommunityJoiningRequestDao dao;
        
    /**
     * Update or insert this entity in the database.
     */
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
        if (user==null) return false;
        return false;  
    }
  
    public final int getCommunityId()
    {
        return CommunityId;
    }
    public final void setCommunityId(int communityId)
    {
        CommunityId = communityId;
    }
    public final int getUserId()
    {
        return UserId;
    }
    public final void setUserId(int userId)
    {
        UserId = userId;
    }
    
    public String getOptionalComment()
    {
        return OptionalComment;
    }
    public void setOptionalComment(String optionalComment)
    {
        OptionalComment = optionalComment;
    }
    
    public final void setDao(CommunityJoiningRequestDao dao)
    {
        this.dao = dao;
    }

    public Date getRequestDate()
    {
        return RequestDate;
    }

    public void setRequestDate(Date requestDate)
    {
        RequestDate = requestDate;
    }
    
    public final int getStatus()
    {
        return Status;
    }

    public final void setStatus(int status)
    {
        Status = status;
    }

    public final Date getApproveRejectDate()
    {
        return ApproveRejectDate;
    }

    public final void setApproveRejectDate(Date approveRejectDate)
    {
        ApproveRejectDate = approveRejectDate;
    }
  }
