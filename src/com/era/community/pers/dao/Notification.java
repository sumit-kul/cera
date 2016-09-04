package com.era.community.pers.dao;

import com.era.community.base.CecAbstractEntity;
import com.era.community.pers.dao.generated.UserEntity;

/**
 * 
 * @entity name="NOTF" 
 * 
 */
public class Notification extends CecAbstractEntity
{    
    protected String ItemType;
    private int ItemId;
    private int CommunityId;
    protected int UserId;

    protected NotificationDao dao;

    public void update() throws Exception
    {
        dao.store(this); 
    }

    public final void setDao(NotificationDao dao)
    {
        this.dao = dao;
    }   

    public boolean isWriteAllowed(UserEntity user) throws Exception
    {
    	return true;
    }

    public boolean isReadAllowed(UserEntity user) throws Exception
    {
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