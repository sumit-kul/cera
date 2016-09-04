package com.era.community.connections.communities.dao;

import java.util.Date;

import com.era.community.base.CecBaseEntity;
import com.era.community.communities.dao.CommunityRoleConstants;
import com.era.community.pers.dao.User;
import com.era.community.pers.dao.UserFinder;

/**
 * 
 * @entity name="CMEM" 
 *
 * @entity.index name="01" unique="yes" columns="UserId,MemberListId" include="DateJoined" cluster="yes"  
 * @entity.index name="02" unique="yes" columns="MemberListId,UserId" include="DateJoined"   
 * @entity.index name="03" unique="no" columns="ApproverId"    
 *
 * @entity.foreignkey name="01" columns="MemberListId" target="MLST" ondelete="cascade"  
 * @entity.foreignkey name="02" columns="UserId" target="USER" ondelete="cascade"  
 * @entity.foreignkey name="03" columns="ApproverId" target="USER" ondelete="restrict"  
 * 
 */
public class Membership extends CecBaseEntity implements CommunityRoleConstants
{
    /**
     * @column integer not null  
     */
    protected int MemberListId;
    /**
     * @column integer not null  
     */
    protected int UserId;
    /**
     * Date the user joined the community.
     * @column timestamp
     */
    protected Date DateJoined;
    
    /**
     * The role this user has in the community.
     * @column varchar(30)
     */
    protected String Role = MEMBER ;  

    /**
     * Date the user last visited the community.
     * @column timestamp
     */
    protected Date DateLastVisit;
    
    /**
     * If the member required manual approval to join then this is the ID of the approver.
     * @column integer 
     */
    protected Integer ApproverId = null; 
    
    
    /*
     * Injected dao reference.
     */
    protected MembershipDao dao;
    
    protected UserFinder userFinder;
    
    public UserFinder getUserFinder()
    {
        return userFinder;
    }


    public void setUserFinder(UserFinder userFinder)
    {
        this.userFinder = userFinder;
    }


    public User getUser() throws Exception
    {
        return userFinder.getUserEntity(this.getId());
    }

    
    /**
     * Update or insert the object in the database.
     * 
     * @throws Exception
     */
    public void update() throws Exception
    {
       dao.store(this); 
    }

    /** 
     *  
     */
    public void delete() throws Exception
    {
        dao.delete(this);
    }



    public final void setDao(MembershipDao dao)
    {
        this.dao = dao;
    }
    public final Date getDateJoined()
    {
        return DateJoined;
    }
    public final void setDateJoined(Date dateJoined)
    {
        DateJoined = dateJoined;
    }
    public final Date getDateLastVisit()
    {
        return DateLastVisit;
    }
    public final void setDateLastVisit(Date dateLastVisit)
    {
        DateLastVisit = dateLastVisit;
    }
    public final int getMemberListId()
    {
        return MemberListId;
    }
    public final void setMemberListId(int memberListId)
    {
        MemberListId = memberListId;
    }
    public final String getRole()
    {
        return Role;
    }
    public final void setRole(String role)
    {
        Role = role;
    }
    public final int getUserId()
    {
        return UserId;
    }
    public final void setUserId(int userId)
    {
        UserId = userId;
    }

    public final Integer getApproverId()
    {
        return ApproverId;
    }

    public final void setApproverId(Integer approverId)
    {
        ApproverId = approverId;
    }
}
