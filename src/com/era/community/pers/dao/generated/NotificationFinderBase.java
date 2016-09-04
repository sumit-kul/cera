package com.era.community.pers.dao.generated; 

import com.era.community.pers.dao.Notification;

public interface NotificationFinderBase
{
    public Notification getNotificationForId(int id) throws Exception;
    public Notification newNotification() throws Exception;
}