package com.era.community.base;

import java.util.Date;

import org.acegisecurity.userdetails.UserDetails;

import support.community.database.CecSecuredEntity;

import com.era.community.pers.dao.generated.UserEntity;

public abstract class CecAbstractEntity extends CecBaseEntity implements CecSecuredEntity
{
    protected Date Modified;
    protected Date Created;
    private CommunityEraContextManager contextManager;
    
    public final Date getModified()
    {
        return Modified;
    }

    public final Date getCreated()
    {
        return Created;
    }
    
    private final void setCreated(Date created)
    {
        this.Created = created;
    }

    public void setModified(Date modified)
    {
        this.Modified = modified;
    }
    
    public final boolean isReadAllowed(UserDetails user) throws Exception
    {
        return isReadAllowed((UserEntity)user);
    }

    public final boolean isWriteAllowed(UserDetails user) throws Exception
    {
        return isWriteAllowed((UserEntity)user);
    }

    public boolean isReadAllowed(UserEntity user) throws Exception
    {
        return isWriteAllowed(user);
    }

    public boolean isWriteAllowed(UserEntity user) throws Exception
    {
        return false;
    }
    
    public final CommunityEraContext getCommunityEraContext()
    {
        return contextManager.getContext();
    }
    
    public final void setContextHolder(CommunityEraContextManager contextManager)
    {
        this.contextManager = contextManager;
    }
}