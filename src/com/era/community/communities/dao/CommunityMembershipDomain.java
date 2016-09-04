package com.era.community.communities.dao;

import com.era.community.base.CecBaseEntity;
import com.era.community.pers.dao.generated.UserEntity;


/**
 * 
 * @entity name="CMDM" 
 *
 * @entity.index name="01" unique="yes" columns="CommunityId,Domain" cluster="yes"  
 * @entity.foreignkey name="01" columns="CommunityId" target="COMM" ondelete="cascade"  
 * 
 */
public class CommunityMembershipDomain extends CecBaseEntity
{
    /**
     * @column integer not null
     */
    protected int CommunityId;
    
    /**
     * @column varchar(255) not null with default
     */
    protected String Domain;
        
    /*
     * Injected  references.
     */
    protected CommunityMembershipDomainDao dao;
        
    
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
    
      
    public boolean isReadAllowed(UserEntity user)
    {
        return true;
    }
    
    public boolean isWriteAllowed(UserEntity user)
    {
        if (user==null) return false;
        return false;
    }

    

    public final String getDomain()
    {
        return Domain;
    }
    public final void setDomain(String domain)
    {
        Domain = domain;
        if (Domain!=null) Domain = Domain.toLowerCase();
    }
    public final void setDao(CommunityMembershipDomainDao dao)
    {
        this.dao = dao;
    }
    public final int getCommunityId()
    {
        return CommunityId;
    }
    public final void setCommunityId(int communityId)
    {
        CommunityId = communityId;
    }
  }
