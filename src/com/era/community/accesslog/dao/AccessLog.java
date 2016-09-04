package com.era.community.accesslog.dao;


import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * 
 * @entity name="ALOG" 
 * 
 * @entity.index name="01" unique="no" columns="ItemId"
 * @entity.index name="02" unique="no" columns="CommunityId"
 * 
 * @entity.foreignkey name="01" columns="ItemId" target="DCMT" ondelete="cascade"
 * @entity.foreignkey name="02" columns="CommunityId" target="COMM" ondelete="cascade"  
 * @entity.foreignkey name="03" columns="UserId" target="USER" ondelete="restrict"  
 *
 */
public class AccessLog extends CecAbstractEntity
{    
    /**
     * @column varchar(60) not null with default
     */
    protected String ItemType;

    /**
     * @column integer not null
     */
    private int ItemId;

    /**
     * @column integer not null
     */
    private int CommunityId;

    /**
     * @column integer not null
     */
    protected int UserId;


    /*
     * Injected  references.
     */
    protected AccessLogDao dao;

    /**
     * Update or insert this entity in the database.
     */
    public void update() throws Exception
    {
        dao.store(this); 
    }

    public final void setDao(AccessLogDao dao)
    {
        this.dao = dao;
    }   

    public boolean isWriteAllowed(UserEntity user) throws Exception
    {
    	if (user == null) {
			return false;
		}
    	return true;
    }

    public boolean isReadAllowed(UserEntity user) throws Exception
    {
        if (user==null) 
            return false;
        else
            return true;
    }

    public final int getItemId()
    {
        return ItemId;
    }

    public final void setItemId(int itemId)
    {
        ItemId = itemId;
    }

    public String getItemType() {
        return ItemType;
    }

    public void setItemType(String itemType) {
        ItemType = itemType;
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

}
